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

import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @Elements} is a {@link ConvertWith} composed annotation that converts
 * collection-like string representations to JDK collection types.
 * <p>
 * The following target types are supported:
 *
 * <ul>
 * <li>One-dimensional {@code int}, {@code long}, and {@code double} arrays</li>
 * <li>One-dimensional primitive and object arrays &mdash; for example {@code Integers[]},
 * {@code String[]}, etc.</li>
 * <li>{@link java.util.Collection}</li>
 * <li>{@link java.util.List}</li>
 * <li>{@link java.util.Set}</li>
 * </ul>
 *
 * <p>
 * The elements should be separated by a comma, and each element is converted using
 * JUnit's {@link org.junit.platform.commons.support.conversion.ConversionSupport
 * conversion capabilities}.
 *
 * @see org.junit.platform.commons.support.conversion.ConversionSupport
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConvertWith(ElementsArgumentConverter.class)
@SuppressWarnings("exports")
public @interface Elements {

}
