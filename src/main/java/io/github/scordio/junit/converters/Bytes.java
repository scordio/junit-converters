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

import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ConvertWith} composed annotation that converts {@link String} or number
 * instances into {@code byte[]} instances.
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConvertWith(BytesArgumentConverter.class)
@SuppressWarnings("exports")
public @interface Bytes {

	/**
	 * The charset the converter should use when converting strings.
	 * <p>
	 * Defaults to {@link java.nio.charset.Charset#defaultCharset()}.
	 * @return the charset to use
	 * @see java.nio.charset.Charset#forName(String)
	 */
	String charset() default "";

	/**
	 * The byte order the converter should use when converting numbers.
	 * <p>
	 * Defaults to {@link ByteOrder#BIG_ENDIAN}.
	 * @return the byte order to use
	 */
	ByteOrder order() default ByteOrder.BIG_ENDIAN;

	/**
	 * Enumeration of byte orders.
	 */
	enum ByteOrder {

		/**
		 * Big-endian byte order.
		 * <p>
		 * In this order, the bytes of a multibyte value are ordered from most significant
		 * to least significant.
		 *
		 * @see java.nio.ByteOrder#BIG_ENDIAN
		 */
		BIG_ENDIAN(java.nio.ByteOrder.BIG_ENDIAN),

		/**
		 * Little-endian byte order.
		 * <p>
		 * In this order, the bytes of a multibyte value are ordered from least
		 * significant to most significant.
		 *
		 * @see java.nio.ByteOrder#LITTLE_ENDIAN
		 */
		LITTLE_ENDIAN(java.nio.ByteOrder.LITTLE_ENDIAN);

		final java.nio.ByteOrder nioOrder;

		ByteOrder(java.nio.ByteOrder nioOrder) {
			this.nioOrder = nioOrder;
		}

	}

}
