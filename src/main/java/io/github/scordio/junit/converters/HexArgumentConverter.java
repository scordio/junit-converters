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
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.TypedArgumentConverter;

import java.util.Objects;

class HexArgumentConverter extends TypedArgumentConverter<String, byte[]> {

	HexArgumentConverter() {
		super(String.class, byte[].class);
	}

	@Override
	protected byte[] convert(@Nullable String source) throws ArgumentConversionException {
		Objects.requireNonNull(source, "'null' is not supported");

		int length = source.length();
		if (length % 2 != 0) {
			throw new ArgumentConversionException("Hex string must have even length");
		}

		byte[] bytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			int hi = getDigit(source, i);
			int lo = getDigit(source, i + 1);
			bytes[i / 2] = (byte) ((hi << 4) | lo);
		}

		return bytes;
	}

	private static int getDigit(String source, int index) {
		int digit = Character.digit(source.charAt(index), 16);
		if (digit == -1) {
			throw new ArgumentConversionException("Invalid hex character at position " + index);
		}
		return digit;
	}

}
