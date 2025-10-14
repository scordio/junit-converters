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

import io.github.scordio.junit.converters.Base64.Encoding;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.converter.AnnotationBasedArgumentConverter;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.util.Base64.Decoder;
import java.util.Objects;

class Base64ArgumentConverter extends AnnotationBasedArgumentConverter<Base64> {

	@Override
	protected Object convert(@Nullable Object source, Class<?> targetType, Base64 annotation) {
		Objects.requireNonNull(source, "'null' is not supported");

		if (targetType != byte[].class) {
			throw new ArgumentConversionException(
					String.format("Target type %s is not supported", targetType.getTypeName()));
		}

		Decoder decoder = getDecoder(annotation.encoding());

		if (source instanceof byte[] bytes) {
			return decoder.decode(bytes);
		}
		if (source instanceof String string) {
			return decoder.decode(string);
		}

		throw new ArgumentConversionException(
				String.format("Source type %s is not supported", source.getClass().getTypeName()));
	}

	private static Decoder getDecoder(Encoding encoding) {
		return switch (encoding) {
			case BASIC -> java.util.Base64.getDecoder();
			case URL -> java.util.Base64.getUrlDecoder();
			case MIME -> java.util.Base64.getMimeDecoder();
		};
	}

}
