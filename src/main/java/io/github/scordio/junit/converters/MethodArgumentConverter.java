/*
 * Copyright © 2025-present Stefano Cordio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.scordio.junit.converters;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.jupiter.params.support.FieldContext;
import org.junit.platform.commons.support.HierarchyTraversalMode;
import org.junit.platform.commons.support.ModifierSupport;
import org.junit.platform.commons.support.ReflectionSupport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class MethodArgumentConverter implements ArgumentConverter, AnnotationConsumer<MethodConversion> {

	private static final Predicate<Method> IS_STATIC = ModifierSupport::isStatic;

	private @Nullable MethodConversion annotation;

	@Override
	public void accept(MethodConversion annotation) {
		this.annotation = annotation;
	}

	@Override
	public @Nullable Object convert(@Nullable Object source, ParameterContext context) {
		return convert(source, context.getParameter().getType(), context.getDeclaringExecutable().getDeclaringClass());
	}

	@Override
	public @Nullable Object convert(@Nullable Object source, FieldContext context) {
		return convert(source, context.getField().getType(), context.getField().getDeclaringClass());
	}

	private @Nullable Object convert(@Nullable Object source, Class<?> targetType, Class<?> declaringClass) {
		Objects.requireNonNull(source, "'null' is not supported");
		Objects.requireNonNull(annotation, "'annotation' must not be null");
		Method conversionMethod = annotation.value().isEmpty()
				? findCandidateMethod(source.getClass(), targetType, declaringClass)
				: findMethodByName(annotation.value(), source.getClass(), targetType, declaringClass);
		return ReflectionSupport.invokeMethod(conversionMethod, null, source);
	}

	private static Method findCandidateMethod(Class<?> sourceType, Class<?> targetType, Class<?> declaringClass) {
		Predicate<Method> filter = IS_STATIC.and(accepts(sourceType)).and(produces(targetType));
		List<Method> methods = ReflectionSupport.findMethods(declaringClass, filter, HierarchyTraversalMode.BOTTOM_UP);

		if (methods.isEmpty()) {
			throw new ArgumentConversionException(
					String.format("No conversion method found compatible with source type %s and target type %s",
							sourceType.getName(), targetType.getName()));
		}

		if (methods.size() > 1) {
			List<String> signatures = methods.stream()
				.map(method -> String.format("%s %s(%s)", method.getReturnType().getName(), method.getName(),
						method.getParameterTypes()[0].getName()))
				.collect(Collectors.toList());

			throw new ArgumentConversionException(
					String.format("Too many conversion methods compatible with source type %s and target type %s: %s",
							sourceType.getName(), targetType.getName(), signatures));
		}

		return methods.get(0);
	}

	private static Method findMethodByName(String name, Class<?> sourceType, Class<?> targetType,
			Class<?> declaringClass) {
		Predicate<Method> filter = IS_STATIC.and(hasName(name)).and(accepts(sourceType)).and(produces(targetType));
		List<Method> methods = ReflectionSupport.findMethods(declaringClass, filter, HierarchyTraversalMode.BOTTOM_UP);

		if (methods.isEmpty()) {
			throw new ArgumentConversionException(
					String.format("No conversion method found with the following signature: static %s %s(%s)",
							targetType.getName(), name, sourceType.getName()));
		}

		return methods.get(0);
	}

	private static Predicate<Method> accepts(Class<?> sourceType) {
		return method -> {
			Class<?>[] parameterTypes = method.getParameterTypes();
			return parameterTypes.length == 1 && ReflectionUtils.isAssignableTo(sourceType, parameterTypes[0]);
		};
	}

	private static Predicate<Method> produces(Class<?> targetType) {
		return method -> ReflectionUtils.isAssignableTo(method.getReturnType(), targetType);
	}

	private static Predicate<Method> hasName(String name) {
		return method -> method.getName().equals(name);
	}

}
