package com.example;

import dagger.reflect.test.Backend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

@RunWith(Parameterized.class)
public final class IntegrationTest {
  @Parameters(name = "{0}")
  public static Object[] parameters() {
    return Backend.values();
  }

  @Parameter public Backend backend;

  @Test public void bindsInstance() {
    InstanceBinding component = backend.builder(InstanceBinding.Builder.class)
        .string("foo")
        .build();
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void bindsInstanceCalledTwice() {
    InstanceBinding component = backend.builder(InstanceBinding.Builder.class)
        .string("foo")
        .string("bar")
        .build();
    assertThat(component.string()).isEqualTo("bar");
  }

  @Test public void bindsInstanceNull() {
    InstanceBindingNull component = backend.builder(InstanceBindingNull.Builder.class)
        .string(null)
        .build();
    assertThat(component.string()).isNull();
  }

  @Test public void builderImplicitModules() {
    BuilderImplicitModules component = backend.builder(BuilderImplicitModules.Builder.class)
        .value(3L)
        .build();

    assertThat(component.string()).isEqualTo("3");
  }

  @Test public void builderExplicitModules() {
    BuilderExplicitModules component = backend.builder(BuilderExplicitModules.Builder.class)
        .module1(new BuilderExplicitModules.Module1("3"))
        .build();

    assertThat(component.string()).isEqualTo("3");
  }

  @Test public void builderExplicitModulesSetTwice() {
    BuilderExplicitModules component = backend.builder(BuilderExplicitModules.Builder.class)
        .module1(new BuilderExplicitModules.Module1("3"))
        .module1(new BuilderExplicitModules.Module1("4"))
        .build();

    assertThat(component.string()).isEqualTo("4");
  }

  @Test public void builderExplicitModulesOmitted() {
    try {
      backend.builder(BuilderExplicitModules.Builder.class).build();
      fail();
    } catch (IllegalStateException e) {
      assertThat(e).hasMessageThat()
          .isEqualTo("com.example.BuilderExplicitModules.Module1 must be set");
    }
  }

  @Test public void builderDependency() {
    BuilderDependency component = backend.builder(BuilderDependency.Builder.class)
        .other(new BuilderDependency.Other("hey"))
        .build();

    assertThat(component.string()).isEqualTo("hey");
  }

  @Test public void builderDependencySetTwice() {
    BuilderDependency component = backend.builder(BuilderDependency.Builder.class)
        .other(new BuilderDependency.Other("hey"))
        .other(new BuilderDependency.Other("there"))
        .build();

    assertThat(component.string()).isEqualTo("there");
  }

  @Test public void builderDependencyOmitted() {
    try {
      backend.builder(BuilderDependency.Builder.class).build();
      fail();
    } catch (IllegalStateException e) {
      assertThat(e).hasMessageThat().isEqualTo("com.example.BuilderDependency.Other must be set");
    }
  }

  @Test public void moduleClass() {
    ModuleClass component = backend.create(ModuleClass.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleClassAndInterfaceHierarchy() {
    ModuleClassAndInterfaceHierarchy component =
        backend.create(ModuleClassAndInterfaceHierarchy.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleClassAndInterfaceDuplicatesHierarchy() {
    ModuleClassAndInterfaceDuplicatesHierarchy component =
        backend.create(ModuleClassAndInterfaceDuplicatesHierarchy.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleClassHierarchy() {
    ModuleClassHierarchy component = backend.create(ModuleClassHierarchy.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleClassHierarchyStatics() {
    ModuleClassHierarchyStatics component = backend.create(ModuleClassHierarchyStatics.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleInterface() {
    ModuleInterface component = backend.create(ModuleInterface.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void moduleInterfaceHierarchy() {
    ModuleInterfaceHierarchy component = backend.create(ModuleInterfaceHierarchy.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void nestedComponent() {
    NestedComponent.MoreNesting.AndMore.TheComponent component =
        backend.create(NestedComponent.MoreNesting.AndMore.TheComponent.class);
    assertThat(component.string()).isEqualTo("foo");
  }

  @Test public void nestedComponentBuilder() {
    NestedComponent.MoreNesting.AndMore.TheComponent component =
        backend.builder(NestedComponent.MoreNesting.AndMore.TheComponent.Builder.class).build();
    assertThat(component.string()).isEqualTo("foo");
  }

  private void ignoreReflectionBackend() {
    assumeTrue("Not yet implemented for reflection backend", backend != Backend.REFLECT);
  }

  private void ignoreCodegenBackend() {
    assumeTrue("Not supported for codegen backend", backend != Backend.CODEGEN);
  }
}
