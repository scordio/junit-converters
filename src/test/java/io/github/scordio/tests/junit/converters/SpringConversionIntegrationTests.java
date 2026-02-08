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

import io.github.scordio.junit.converters.SpringConversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.scordio.tests.junit.converters.JupiterEngineTestKit.executeTestsForClass;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.cause;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.instanceOf;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;

class SpringConversionIntegrationTests {

	@Test
	void should_convert_supported_values() {
		executeTestsForClass(SupportedValuesTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(13).succeeded(13));
	}

	static class SupportedValuesTestCase {

		@ParameterizedTest
		@FieldSource
		void array_to_array(@SpringConversion int[] array, int[] expected) {
			assertThat(array).isEqualTo(expected);
		}

		static List<?> array_to_array = List.of(arguments(new String[] { "123", "456" }, new int[] { 123, 456 }));

		@ParameterizedTest
		@FieldSource
		void array_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> array_to_list = List.of( //
				arguments(new int[] { 123, 456 }, List.of(123, 456)),
				arguments(new Integer[] { 123, 456 }, List.of(123, 456)),
				arguments(new String[] { "123", "456" }, List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void array_to_object(@SpringConversion int value, int expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> array_to_object = List.of( //
				arguments(new int[] { 123, 456 }, 123), //
				arguments(new Integer[] { 123, 456 }, 123), //
				arguments(new String[] { "123", "456" }, 123));

		@ParameterizedTest
		@FieldSource
		void array_to_string(@SpringConversion String string, String expected) {
			assertThat(string).isEqualTo(expected);
		}

		static List<?> array_to_string = List.of( //
				arguments(new int[] { 123, 456 }, "123,456"), //
				arguments(new Integer[] { 123, 456 }, "123,456"), //
				arguments(new String[] { "123", "456" }, "123,456"));

		@ParameterizedTest
		@FieldSource
		void list_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> list_to_list = List.of(arguments(List.of("123", "456"), List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void map_to_map(@SpringConversion Map<Integer, Double> map, Map<Integer, Double> expected) {
			assertThat(map).isEqualTo(expected);
		}

		static List<?> map_to_map = List.of(arguments(Map.of("1", "123.4", "2", "567.8"), Map.of(1, 123.4, 2, 567.8)));

		@ParameterizedTest
		@FieldSource
		void string_to_list(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> string_to_list = List.of(arguments("123, 456", List.of(123, 456)));

	}

	@Test
	void should_fail_without_spring_core_in_the_classpath() {
		executeTestsForClass(MissingSpringCoreTestCase.class, new SpringFilteringClassLoader()).testEvents()
			.assertStatistics(stats -> stats.started(1).failed(1))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause( //
							instanceOf(NoClassDefFoundError.class),
							message("org/springframework/core/convert/ConversionService"))));
	}

	static class MissingSpringCoreTestCase {

		@ParameterizedTest
		@ValueSource(strings = "123, 456")
		void test(@SuppressWarnings("unused") @SpringConversion List<Integer> list) {
			// never called
		}

	}

	private static class SpringFilteringClassLoader extends URLClassLoader {

		private static final String SPRING_CORE_PACKAGE = "org.springframework.core";

		private static final Set<String> TARGET_PACKAGES;

		private static final URL[] CLASSPATH_URLS;

		static {
			Set<Class<?>> targetClasses = Set.of(MissingSpringCoreTestCase.class, SpringConversion.class);

			TARGET_PACKAGES = targetClasses.stream().map(Class::getPackageName).collect(Collectors.toSet());

			CLASSPATH_URLS = targetClasses.stream()
				.map(Class::getProtectionDomain)
				.map(ProtectionDomain::getCodeSource)
				.map(CodeSource::getLocation)
				.toArray(URL[]::new);
		}

		private SpringFilteringClassLoader() {
			super(CLASSPATH_URLS);
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			synchronized (getClassLoadingLock(name)) {
				Class<?> loadedClass = findLoadedClass(name);
				if (loadedClass != null) {
					return loadedClass;
				}

				if (TARGET_PACKAGES.stream().anyMatch(name::startsWith)) {
					return findClass(name);
				}

				if (name.startsWith(SPRING_CORE_PACKAGE)) {
					throw new ClassNotFoundException();
				}

				return super.loadClass(name);
			}
		}

	}

}
