package com.example;

import dagger.reflect.test.Backend;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

public final class IntegrationTest {

  @Parameter public Backend backend = Backend.REFLECT;

  @Test public void optionalBindingNullable() {

    OptionalBindingNullable component = backend.create(OptionalBindingNullable.class);
    try {
      component.string();
      fail();
    } catch (NullPointerException e) {
      assertThat(e).hasMessageThat()
          .isEqualTo("@Provides[com.example.OptionalBindingNullable$Module1.foo(…)] "
              + "returned null which is not allowed for optional bindings");
    }
  }

  private void ignoreReflectionBackend() {
    assumeTrue("Not yet implemented for reflection backend", backend != Backend.REFLECT);
  }
}
