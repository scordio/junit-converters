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
import java.util.Base64.Decoder;

/**
 * {@link ConvertWith} composed annotation that decodes Base64-encoded {@link String}
 * instances to {@code byte[]} instances.
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConvertWith(Base64ArgumentConverter.class)
@SuppressWarnings("exports")
public @interface Base64 {

	/**
	 * The encoding scheme the converter should use.
	 * <p>
	 * Defaults to {@link Encoding#BASIC}.
	 * @return the encoding scheme to use
	 */
	Encoding encoding() default Encoding.BASIC;

	/**
	 * Enumeration of Base64 encoding schemes.
	 *
	 * @see java.util.Base64
	 */
	enum Encoding {

		/**
		 * The Basic encoding scheme.
		 *
		 * @see java.util.Base64#getDecoder()
		 */
		BASIC {

			@Override
			Decoder getDecoder() {
				return java.util.Base64.getDecoder();
			}

		},

		/**
		 * The URL and Filename Safe encoding scheme.
		 *
		 * @see java.util.Base64#getUrlDecoder()
		 */
		URL {

			@Override
			Decoder getDecoder() {
				return java.util.Base64.getUrlDecoder();
			}

		},

		/**
		 * The MIME encoding scheme.
		 *
		 * @see java.util.Base64#getMimeDecoder()
		 */
		MIME {

			@Override
			Decoder getDecoder() {
				return java.util.Base64.getMimeDecoder();
			}

		};

		abstract Decoder getDecoder();

	}

}
