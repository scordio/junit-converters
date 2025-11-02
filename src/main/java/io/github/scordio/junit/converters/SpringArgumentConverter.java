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
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

class SpringArgumentConverter implements ArgumentConverter {

	private static final ConversionService CONVERSION_SERVICE = new DefaultConversionService();

	@Override
	public @Nullable Object convert(@Nullable Object source, ParameterContext context)
			throws ArgumentConversionException {
		TypeDescriptor sourceType = TypeDescriptor.forObject(source);
		TypeDescriptor targetType = new TypeDescriptor(MethodParameter.forParameter(context.getParameter()));
		return CONVERSION_SERVICE.convert(source, sourceType, targetType);
	}

	@Override
	public @Nullable Object convert(@Nullable Object source, FieldContext context) throws ArgumentConversionException {
		TypeDescriptor sourceType = TypeDescriptor.forObject(source);
		TypeDescriptor targetType = new TypeDescriptor(context.getField());
		return CONVERSION_SERVICE.convert(source, sourceType, targetType);
	}

}
