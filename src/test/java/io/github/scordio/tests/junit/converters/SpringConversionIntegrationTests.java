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
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.format.annotation.DurationFormat;
import org.springframework.format.annotation.NumberFormat;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import static org.springframework.format.annotation.DurationFormat.Style.SIMPLE;
import static org.springframework.format.annotation.NumberFormat.Style.PERCENT;

class SpringConversionIntegrationTests {

	@Test
	void should_convert_supported_values_without_format_annotations() {
		executeTestsForClass(SpringCoreTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(13).succeeded(13));
	}

	@Test
	void should_convert_supported_values_with_format_annotations() {
		executeTestsForClass(SpringFormatTestCase.class).testEvents()
			.assertStatistics(stats -> stats.started(4).succeeded(4));
	}

	@Test
	void should_convert_supported_values_without_format_annotations_if_org_springframework_format_is_not_in_the_classpath() {
		executeTestsForClass(SpringCoreTestCase.class, new SpringFormatFilteringClassLoader()).testEvents()
			.assertStatistics(stats -> stats.started(13).succeeded(13));
	}

	@Test
	void should_fail_without_format_annotations_if_org_springframework_core_is_not_in_the_classpath() {
		executeTestsForClass(SpringCoreTestCase.class, new SpringCoreFilteringClassLoader()).testEvents()
			.assertStatistics(stats -> stats.started(13).failed(13))
			.assertThatEvents()
			.haveExactly(13, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause( //
							instanceOf(NoClassDefFoundError.class),
							message("org/springframework/core/convert/ConversionService"))));
	}

	@Test
	void should_fail_with_format_annotations_if_org_springframework_core_is_not_in_the_classpath() {
		executeTestsForClass(SpringFormatTestCase.class, new SpringCoreFilteringClassLoader()).testEvents()
			.assertStatistics(stats -> stats.started(4).failed(4))
			.assertThatEvents()
			.haveExactly(4, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause( //
							instanceOf(NoClassDefFoundError.class),
							message("org/springframework/core/convert/ConversionService"))));
	}

	@Test
	void should_fail_with_format_annotations_if_org_springframework_format_is_not_in_the_classpath() {
		executeTestsForClass(SpringFormatTestCase.class, new SpringFormatFilteringClassLoader()).testEvents()
			.assertStatistics(stats -> stats.started(4).failed(4))
			.assertThatEvents()
			.haveExactly(1, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class), cause(instanceOf(ConversionFailedException.class))))
			.haveExactly(3, finishedWithFailure( //
					instanceOf(ParameterResolutionException.class),
					cause(instanceOf(ConverterNotFoundException.class))));
	}

	static class SpringCoreTestCase {

		@ParameterizedTest
		@FieldSource
		void array_to_array(@SpringConversion int[] array, int[] expected) {
			assertThat(array).isEqualTo(expected);
		}

		static List<?> array_to_array = List.of(arguments(new String[] { "123", "456" }, new int[] { 123, 456 }));

		@ParameterizedTest
		@FieldSource
		void array_to_List(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> array_to_List = List.of( //
				arguments(new int[] { 123, 456 }, List.of(123, 456)),
				arguments(new Integer[] { 123, 456 }, List.of(123, 456)),
				arguments(new String[] { "123", "456" }, List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void array_to_Object(@SpringConversion int value, int expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> array_to_Object = List.of( //
				arguments(new int[] { 123, 456 }, 123), //
				arguments(new Integer[] { 123, 456 }, 123), //
				arguments(new String[] { "123", "456" }, 123));

		@ParameterizedTest
		@FieldSource
		void array_to_String(@SpringConversion String string, String expected) {
			assertThat(string).isEqualTo(expected);
		}

		static List<?> array_to_String = List.of( //
				arguments(new int[] { 123, 456 }, "123,456"), //
				arguments(new Integer[] { 123, 456 }, "123,456"), //
				arguments(new String[] { "123", "456" }, "123,456"));

		@ParameterizedTest
		@FieldSource
		void List_to_List(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> List_to_List = List.of(arguments(List.of("123", "456"), List.of(123, 456)));

		@ParameterizedTest
		@FieldSource
		void Map_to_Map(@SpringConversion Map<Integer, Double> map, Map<Integer, Double> expected) {
			assertThat(map).isEqualTo(expected);
		}

		static List<?> Map_to_Map = List.of(arguments(Map.of("1", "123.4", "2", "234.5"), Map.of(1, 123.4, 2, 234.5)));

		@ParameterizedTest
		@FieldSource
		void String_to_List(@SpringConversion List<Integer> list, List<Integer> expected) {
			assertThat(list).isEqualTo(expected);
		}

		static List<?> String_to_List = List.of(arguments("123, 456", List.of(123, 456)));

	}

	static class SpringFormatTestCase {

		@ParameterizedTest
		@FieldSource
		void String_to_Double(@SpringConversion @NumberFormat(style = PERCENT) double value, double expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> String_to_Double = List.of(arguments("42%", 0.42));

		@ParameterizedTest
		@FieldSource
		void String_to_Duration(@SpringConversion @DurationFormat(style = SIMPLE) Duration value, Duration expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> String_to_Duration = List.of(arguments("42ms", Duration.ofMillis(42)));

		@ParameterizedTest
		@FieldSource
		void String_to_LocalDate(@SpringConversion LocalDate value, LocalDate expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> String_to_LocalDate = List.of(arguments("1970-01-01", LocalDate.EPOCH));

		@ParameterizedTest
		@FieldSource
		void String_to_LocalDateTime(@SpringConversion LocalDateTime value, LocalDateTime expected) {
			assertThat(value).isEqualTo(expected);
		}

		static List<?> String_to_LocalDateTime = List.of( //
				arguments("1970-01-01T00:00:00", LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT)));

	}

	private static class SpringCoreFilteringClassLoader extends FilteringClassLoader {

		@Override
		void filterClass(String name) throws ClassNotFoundException {
			if (name.startsWith("org.springframework.core")) {
				throw new ClassNotFoundException();
			}
		}

	}

	private static class SpringFormatFilteringClassLoader extends FilteringClassLoader {

		@Override
		void filterClass(String name) throws ClassNotFoundException {
			if (name.startsWith("org.springframework.format")) {
				throw new ClassNotFoundException();
			}
		}

	}

	private static abstract class FilteringClassLoader extends URLClassLoader {

		private static final Set<String> TARGET_PACKAGES;

		private static final URL[] CLASSPATH_URLS;

		static {
			Set<Class<?>> targetClasses = Set.of(SpringConversionIntegrationTests.class, SpringConversion.class);

			TARGET_PACKAGES = targetClasses.stream().map(Class::getPackageName).collect(Collectors.toSet());

			CLASSPATH_URLS = targetClasses.stream()
				.map(Class::getProtectionDomain)
				.map(ProtectionDomain::getCodeSource)
				.map(CodeSource::getLocation)
				.toArray(URL[]::new);
		}

		private FilteringClassLoader() {
			super(CLASSPATH_URLS);
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			synchronized (getClassLoadingLock(name)) {
				// Check if loading already happened to avoid duplicate resolution
				Class<?> loadedClass = findLoadedClass(name);
				if (loadedClass != null) {
					return loadedClass;
				}

				// Find target classes bypassing parent delegation to ensure classes to be
				// filtered are resolved through this class loader
				if (TARGET_PACKAGES.stream().anyMatch(name::startsWith)) {
					return findClass(name);
				}

				filterClass(name);

				return super.loadClass(name);
			}
		}

		abstract void filterClass(String name) throws ClassNotFoundException;

	}

}
