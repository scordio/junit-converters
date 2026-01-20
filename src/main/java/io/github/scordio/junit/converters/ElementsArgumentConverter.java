/*
 * Copyright © 2025 Stefano Cordio
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
import org.junit.jupiter.params.support.FieldContext;
import org.junit.platform.commons.support.conversion.ConversionSupport;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ElementsArgumentConverter implements ArgumentConverter {

	@Override
	public final Object convert(@Nullable Object source, ParameterContext context) throws ArgumentConversionException {
		return doConvert(source, context.getParameter().getParameterizedType());
	}

	@Override
	public final Object convert(@Nullable Object source, FieldContext context) throws ArgumentConversionException {
		return doConvert(source, context.getField().getGenericType());
	}

	private Object doConvert(@Nullable Object source, Type targetType) throws ArgumentConversionException {
		Objects.requireNonNull(source, "'null' is not supported");

		if (!(source instanceof String)) {
			throw new ArgumentConversionException(
					String.format("Source type %s is not supported", source.getClass().getTypeName()));
		}

		String[] elements = ((String) source).split(",");

		Stream<String> stream = Arrays.stream(elements).map(String::trim);

		if (targetType instanceof Class) {
			Class<?> clazz = (Class<?>) targetType;
			if (clazz.isArray()) {
				return convertArray(stream, clazz);
			}
		}
		if (targetType instanceof ParameterizedType) {
			return convertCollection(stream, (ParameterizedType) targetType);
		}

		throw new IllegalArgumentException(String.format("Target type %s is not supported", targetType.getTypeName()));
	}

	private static Object convertArray(Stream<String> stream, Class<?> arrayType) {
		Class<?> componentType = arrayType.getComponentType();
		if (componentType.isPrimitive()) {
			if (componentType == int.class) {
				return stream.mapToInt(value -> ConversionSupport.convert(value, int.class, null)).toArray();
			}
			if (componentType == long.class) {
				return stream.mapToLong(value -> ConversionSupport.convert(value, long.class, null)).toArray();
			}
			if (componentType == double.class) {
				return stream.mapToDouble(value -> ConversionSupport.convert(value, double.class, null)).toArray();
			}
		}
		return stream.map(value -> ConversionSupport.convert(value, componentType, null))
			.toArray(length -> (Object[]) Array.newInstance(componentType, length));
	}

	private static Collection<?> convertCollection(Stream<String> stream, ParameterizedType parameterizedType) {
		Type rawType = parameterizedType.getRawType();
		Class<?> elementType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
		Stream<?> convertedStream = stream.map(value -> ConversionSupport.convert(value, elementType, null));

		if (rawType == List.class || rawType == Collection.class || rawType == Iterable.class) {
			return convertedStream.collect(Collectors.toList());
		}
		if (rawType == Set.class) {
			return convertedStream.collect(Collectors.toSet());
		}
		throw new IllegalArgumentException(
				String.format("Target type %s is not supported", parameterizedType.getTypeName()));
	}

}
