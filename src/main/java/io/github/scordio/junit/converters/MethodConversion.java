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
 * {@code @MethodConversion} is a {@link ConvertWith} composed annotation that converts
 * arguments using a {@linkplain #value() static method} declared in the test class.
 *
 * @since 0.2.0
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConvertWith(MethodArgumentConverter.class)
@SuppressWarnings("exports")
public @interface MethodConversion {

	/**
	 * The name of the conversion method within the test class to use for the conversion.
	 * <p>
	 * If no name is declared, the converter will look for a single static method within
	 * the test class whose parameter type matches the source type and whose return type
	 * matches the target type. The search traverses the class hierarchy with
	 * {@linkplain org.junit.platform.commons.support.HierarchyTraversalMode#BOTTOM_UP
	 * bottom-up} semantics.
	 * @return the name of the conversion method to use
	 */
	String value() default "";

}
