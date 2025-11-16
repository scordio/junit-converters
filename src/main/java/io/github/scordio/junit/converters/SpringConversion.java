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
 * {@code @SpringConversion} is a {@link ConvertWith} composed annotation that converts
 * arguments using the Spring {@link org.springframework.core.convert.ConversionService
 * conversion service}.
 * <p>
 * <strong>Note:</strong> The annotation requires {@code org.springframework:spring-core}
 * available in the test classpath, which is generally the case for Spring applications.
 *
 * @see org.springframework.core.convert.ConversionService
 * @see org.springframework.core.convert.support.DefaultConversionService
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConvertWith(SpringArgumentConverter.class)
@SuppressWarnings("exports")
public @interface SpringConversion {

}
