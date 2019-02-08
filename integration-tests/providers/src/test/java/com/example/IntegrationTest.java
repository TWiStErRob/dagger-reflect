package com.example;

import dagger.reflect.test.Backend;
import java.util.Optional;
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

  @Test public void componentProvider() {
    ComponentProvider component = backend.create(ComponentProvider.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void componentProviderQualified() {
    ComponentProviderQualified component = backend.create(ComponentProviderQualified.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void staticProvider() {
    StaticProvider component = backend.create(StaticProvider.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void bindsProvider() {
    BindsProvider component = backend.create(BindsProvider.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void optionalBinding() {
    OptionalBinding component = backend.create(OptionalBinding.class);
    assertThat(component.string()).isEqualTo(Optional.of("foo"));
  }

  @Test public void optionalBindingAbsent() {
    OptionalBindingAbsent component = backend.create(OptionalBindingAbsent.class);
    assertThat(component.string()).isEqualTo(Optional.empty());
  }

  @Test public void optionalGuavaBinding() {
    ignoreReflectionBackend();

    OptionalGuavaBinding component = backend.create(OptionalGuavaBinding.class);
    assertThat(component.string()).isEqualTo(com.google.common.base.Optional.of("foo"));
  }

  @Test public void optionalGuavaBindingAbsent() {
    ignoreReflectionBackend();

    OptionalGuavaBindingAbsent component = backend.create(OptionalGuavaBindingAbsent.class);
    assertThat(component.string()).isEqualTo(com.google.common.base.Optional.absent());
  }

  @Test public void justInTimeConstructor() {
    JustInTimeConstructor component = backend.create(JustInTimeConstructor.class);
    assertThat(component.thing()).isNotNull();
  }

  @Test public void scoped() {
    ignoreReflectionBackend();

    Scoped component = backend.create(Scoped.class);
    assertThat(component.value()).isEqualTo(1);
    assertThat(component.value()).isEqualTo(1);
  }

  public void ignoreReflectionBackend() {
    assumeTrue("Not yet implemented for reflection backend", backend != Backend.REFLECT);
  }

  public void ignoreCodegenBackend() {
    assumeTrue("Not supported for codegen backend", backend != Backend.CODEGEN);
  }
}
