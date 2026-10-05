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
package io.github.scordio.tests.junit.converters;

import io.github.scordio.junit.converters.MethodConversion;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class MethodConversionIntegrationTests {

	@Test
	void should_convert_parameter_use_cases() {
		executeTestsForClass(ParameterUseCasesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(12).succeeded(12));
	}

	@Test
	void should_convert_field_use_cases() {
		executeTestsForClass(FieldUseCasesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(2).succeeded(2));
	}

	@SuppressWarnings("unused")
	static class ParameterUseCasesTestCase {

		@Nested
		class local_method_accepting_source_type_and_producing_target_type {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") String value) {
				assertThat(value).isEqualTo("42");
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion String value) {
				assertThat(value).isEqualTo("42");
			}

			static String convert(Integer input) {
				return input.toString();
			}

		}

		@Nested
		class local_method_accepting_source_type_with_primitive_conversion {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion String value) {
				assertThat(value).isEqualTo("42");
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") String value) {
				assertThat(value).isEqualTo("42");
			}

			static String convert(int input) {
				return Integer.toString(input);
			}

		}

		@Nested
		class local_method_accepting_source_type_with_widening_primitive_conversion {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") String value) {
				assertThat(value).isEqualTo("42");
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion String value) {
				assertThat(value).isEqualTo("42");
			}

			static String convert(long input) {
				return Long.toString(input);
			}

		}

		@Nested
		class local_method_accepting_source_type_supertype {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion String value) {
				assertThat(value).isEqualTo("42");
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") String value) {
				assertThat(value).isEqualTo("42");
			}

			static String convert(Number input) {
				return input.toString();
			}

		}

		@Nested
		class local_method_producing_target_type_subtype {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion CharSequence value) {
				assertThat(value).isEqualTo("42");
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") CharSequence value) {
				assertThat(value).isEqualTo("42");
			}

			static String convert(Integer input) {
				return input.toString();
			}

		}

		@Nested
		class local_method_producing_target_type_with_widening_primitive_conversion {

			@ParameterizedTest
			@ValueSource(ints = 42)
			void implicit(@MethodConversion long value) {
				assertThat(value).isEqualTo(42L);
			}

			@ParameterizedTest
			@ValueSource(ints = 42)
			void explicit(@MethodConversion("convert") long value) {
				assertThat(value).isEqualTo(42L);
			}

			static Short convert(int input) {
				return (short) input;
			}

		}

	}

	@SuppressWarnings("unused")
	static class FieldUseCasesTestCase {

		@Nested
		@ParameterizedClass
		@ValueSource(ints = 42)
		class explicit_local_method_accepting_source_type_and_producing_target_type {

			@Parameter
			@MethodConversion("convert")
			String value;

			@Test
			void test() {
				assertThat(value).isEqualTo("42");
			}

			static String convert(Integer input) {
				return input.toString();
			}

		}

		@Nested
		@ParameterizedClass
		@ValueSource(ints = 42)
		class implicit_local_method_accepting_source_type_and_producing_target_type {

			@Parameter
			@MethodConversion
			String value;

			@Test
			void test() {
				assertThat(value).isEqualTo("42");
			}

			static String convert(Integer input) {
				return input.toString();
			}

		}

	}

}
