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
import org.junit.jupiter.params.converter.AnnotationBasedArgumentConverter;
import org.junit.jupiter.params.converter.ArgumentConversionException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Objects;

class BytesArgumentConverter extends AnnotationBasedArgumentConverter<Bytes> {

	@Override
	protected Object convert(@Nullable Object source, Class<?> targetType, Bytes annotation) {
		Objects.requireNonNull(source, "'null' is not supported");

		if (targetType != byte[].class) {
			throw new ArgumentConversionException(
					String.format("Target type %s is not supported", targetType.getTypeName()));
		}

		if (source instanceof String) {
			return ((String) source).getBytes(forName(annotation.charset()));
		}
		if (source instanceof Byte) {
			return ByteBuffer.allocate(Byte.BYTES).order(getOrder(annotation)).put((byte) source).array();
		}
		if (source instanceof Short) {
			return ByteBuffer.allocate(Short.BYTES).order(getOrder(annotation)).putShort((short) source).array();
		}
		if (source instanceof Integer) {
			return ByteBuffer.allocate(Integer.BYTES).order(getOrder(annotation)).putInt((int) source).array();
		}
		if (source instanceof Long) {
			return ByteBuffer.allocate(Long.BYTES).order(getOrder(annotation)).putLong((long) source).array();
		}
		if (source instanceof Float) {
			return ByteBuffer.allocate(Float.BYTES).order(getOrder(annotation)).putFloat((float) source).array();
		}
		if (source instanceof Double) {
			return ByteBuffer.allocate(Double.BYTES).order(getOrder(annotation)).putDouble((double) source).array();
		}

		throw new ArgumentConversionException(
				String.format("Source type %s is not supported", source.getClass().getTypeName()));
	}

	private static Charset forName(String charsetName) {
		return charsetName.isEmpty() ? Charset.defaultCharset() : Charset.forName(charsetName);
	}

	private static ByteOrder getOrder(Bytes annotation) {
		return annotation.order() == Bytes.ByteOrder.BIG_ENDIAN //
				? java.nio.ByteOrder.BIG_ENDIAN //
				: java.nio.ByteOrder.LITTLE_ENDIAN;
	}

}
