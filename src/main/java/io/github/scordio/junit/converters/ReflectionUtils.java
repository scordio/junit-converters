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

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Methods borrowed from {@link org.junit.platform.commons.util.ReflectionUtils}.
 */
class ReflectionUtils {

	private static final Map<Class<?>, Class<?>> primitiveToWrapperMap;

	static {
		@SuppressWarnings("IdentityHashMapUsage")
		Map<Class<?>, Class<?>> primitivesToWrappers = new IdentityHashMap<>(9);

		primitivesToWrappers.put(boolean.class, Boolean.class);
		primitivesToWrappers.put(byte.class, Byte.class);
		primitivesToWrappers.put(char.class, Character.class);
		primitivesToWrappers.put(short.class, Short.class);
		primitivesToWrappers.put(int.class, Integer.class);
		primitivesToWrappers.put(long.class, Long.class);
		primitivesToWrappers.put(float.class, Float.class);
		primitivesToWrappers.put(double.class, Double.class);

		primitiveToWrapperMap = Collections.unmodifiableMap(primitivesToWrappers);
	}

	// https://github.com/junit-team/junit-framework/issues/5641
	static boolean isAssignableTo(Class<?> sourceType, Class<?> targetType) {
		Objects.requireNonNull(sourceType, "source type must not be null");
		Objects.requireNonNull(targetType, "target type must not be null");

		if (sourceType.isPrimitive()) {
			throw new IllegalArgumentException("source type must not be a primitive type");
		}

		if (targetType.isAssignableFrom(sourceType)) {
			return true;
		}

		if (targetType.isPrimitive()) {
			return sourceType == primitiveToWrapperMap.get(targetType) || isWideningConversion(sourceType, targetType);
		}

		return false;
	}

	private static boolean isWideningConversion(Class<?> sourceType, Class<?> targetType) {
		if (!targetType.isPrimitive()) {
			throw new IllegalArgumentException("targetType must be primitive");
		}

		boolean isPrimitive = sourceType.isPrimitive();
		boolean isWrapper = primitiveToWrapperMap.containsValue(sourceType);

		// Neither a primitive nor a wrapper?
		if (!isPrimitive && !isWrapper) {
			return false;
		}

		if (isPrimitive) {
			sourceType = primitiveToWrapperMap.get(sourceType);
		}

		// @formatter:off
		if (sourceType == Byte.class) {
			return
					targetType == short.class ||
					targetType == int.class ||
					targetType == long.class ||
					targetType == float.class ||
					targetType == double.class;
		}

		if (sourceType == Short.class || sourceType == Character.class) {
			return
					targetType == int.class ||
					targetType == long.class ||
					targetType == float.class ||
					targetType == double.class;
		}

		if (sourceType == Integer.class) {
			return
					targetType == long.class ||
					targetType == float.class ||
					targetType == double.class;
		}

		if (sourceType == Long.class) {
			return
					targetType == float.class ||
					targetType == double.class;
		}

		if (sourceType == Float.class) {
			return
					targetType == double.class;
		}
		// @formatter:on

		return false;
	}

}
