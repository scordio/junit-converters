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
import org.junit.jupiter.params.converter.AnnotationBasedArgumentConverter;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.util.Objects;

class Base64ArgumentConverter extends AnnotationBasedArgumentConverter<Base64> {

	@Override
	protected Object convert(@Nullable Object source, Class<?> targetType, Base64 annotation) {
		Objects.requireNonNull(source, "'null' is not supported");

		if (targetType != byte[].class) {
			throw new ArgumentConversionException(
					String.format("Target type %s is not supported", targetType.getTypeName()));
		}

		if (source instanceof byte[]) {
			return annotation.encoding().decoder.decode((byte[]) source);
		}
		if (source instanceof String) {
			return annotation.encoding().decoder.decode((String) source);
		}

		throw new ArgumentConversionException(
				String.format("Source type %s is not supported", source.getClass().getTypeName()));
	}

}
