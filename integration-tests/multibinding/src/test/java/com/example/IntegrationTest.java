package com.example;

import dagger.reflect.test.Backend;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assume.assumeTrue;

@RunWith(Parameterized.class)
public final class IntegrationTest {
  @Parameters(name = "{0}")
  public static Object[] parameters() {
    return Backend.values();
  }

  @Parameter public Backend backend;

  @Test public void bindIntoSet() {
    ignoreReflectionBackend();

    BindsIntoSet component = backend.create(BindsIntoSet.class);
    assertThat(component.strings()).containsExactly("foo");
  }

  @Test public void bindElementsIntoSet() {
    ignoreReflectionBackend();

    BindsElementsIntoSet component = backend.create(BindsElementsIntoSet.class);
    assertThat(component.strings()).containsExactly("foo");
  }

  @Test public void bindIntoMap() {
    ignoreReflectionBackend();

    BindsIntoMap component = backend.create(BindsIntoMap.class);
    assertThat(component.strings()).containsExactly("bar", "foo");
  }

  @Test public void multibindingSet() {
    ignoreReflectionBackend();

    MultibindingSet component = backend.create(MultibindingSet.class);
    assertThat(component.values()).containsExactly("one", "two");
  }

  @Test public void multibindingSetElements() {
    ignoreReflectionBackend();

    MultibindingSetElements component = backend.create(MultibindingSetElements.class);
    assertThat(component.values()).containsExactly("one", "two");
  }

  @Test public void multibindingProviderSet() {
    ignoreReflectionBackend();

    MultibindingProviderSet component = backend.create(MultibindingProviderSet.class);
    Provider<Set<String>> values = component.values();

    // Ensure the Provider is lazy in invoking and aggregating its backing @Provides methods.
    MultibindingProviderSet.Module1.oneCount.set(1);
    MultibindingProviderSet.Module1.twoCount.set(1);

    assertThat(values.get()).containsExactly("one1", "two1");
    assertThat(values.get()).containsExactly("one2", "two2");
  }

  @Test public void multibindingMap() {
    ignoreReflectionBackend();

    MultibindingMap component = backend.create(MultibindingMap.class);
    assertThat(component.values()).containsExactly("1", "one", "2", "two");
  }

  @Test public void multibindingProviderMap() {
    ignoreReflectionBackend();

    MultibindingProviderMap component = backend.create(MultibindingProviderMap.class);
    Provider<Map<String, String>> values = component.values();

    // Ensure the Provider is lazy in invoking and aggregating its backing @Provides methods.
    MultibindingProviderMap.Module1.oneCount.set(1);
    MultibindingProviderMap.Module1.twoCount.set(1);

    assertThat(values.get()).containsExactly("1", "one1", "2", "two1");
    assertThat(values.get()).containsExactly("1", "one2", "2", "two2");
  }

  @Test public void multibindingMapProvider() {
    ignoreReflectionBackend();

    MultibindingMapProvider component = backend.create(MultibindingMapProvider.class);
    Map<String, Provider<String>> values = component.values();
    assertThat(values.keySet()).containsExactly("1", "2");

    // Ensure each Provider is lazy in invoking its backing @Provides method.
    MultibindingMapProvider.Module1.twoValue.set("two");
    assertThat(values.get("2").get()).isEqualTo("two");

    MultibindingMapProvider.Module1.oneValue.set("one");
    assertThat(values.get("1").get()).isEqualTo("one");
  }

  private void ignoreReflectionBackend() {
    assumeTrue("Not yet implemented for reflection backend", backend != Backend.REFLECT);
  }

  private void ignoreCodegenBackend() {
    assumeTrue("Not supported for codegen backend", backend != Backend.CODEGEN);
  }
}
