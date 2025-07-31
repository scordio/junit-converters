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
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.Objects;

class Base64ArgumentConverter extends TypedArgumentConverter<String, byte[]> implements AnnotationConsumer<Base64> {

	private @Nullable Base64 annotation;

	Base64ArgumentConverter() {
		super(String.class, byte[].class);
	}

	@Override
	public void accept(Base64 annotation) {
		this.annotation = annotation;
	}

	@SuppressWarnings({ "DataFlowIssue", "NullAway" })
	@Override
	protected byte[] convert(@Nullable String source) {
		Objects.requireNonNull(source, "'null' is unsupported");
		return annotation.encoding().getDecoder().decode(source);
	}

}
