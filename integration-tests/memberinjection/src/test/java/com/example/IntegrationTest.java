package com.example;

import dagger.reflect.test.Backend;
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

  @Test public void memberInjectionEmptyClass() {
    MemberInjectionEmpty component = backend.create(MemberInjectionEmpty.class);
    MemberInjectionEmpty.Target target = new MemberInjectionEmpty.Target();
    component.inject(target);
    // No state, nothing to verify, except it didn't throw.
  }

  @Test public void memberInjectionEmptyAbstractClass() {
    MemberInjectionEmptyAbstract component = backend.create(MemberInjectionEmptyAbstract.class);
    MemberInjectionEmptyAbstract.Target target = new MemberInjectionEmptyAbstract.Target() {};
    component.inject(target);
    // No state, nothing to verify, except it didn't throw.
  }

  @Test public void memberInjectionEmptyInterface() {
    MemberInjectionEmptyInterface component = backend.create(MemberInjectionEmptyInterface.class);
    MemberInjectionEmptyInterface.Target target = new MemberInjectionEmptyInterface.Target() {};
    component.inject(target);
    // No state, nothing to verify, except it didn't throw.
  }

  @Test public void memberInjectionInterface() {
    MemberInjectionInterface component = backend.create(MemberInjectionInterface.class);
    class Target implements MemberInjectionInterface.Target {
      boolean called;
      @Override public void method(String foo) {
        called = true;
      }
    }
    Target target = new Target();
    component.inject(target);

    assertThat(target.called).isFalse();
  }

  @Test public void memberInjectionReturnInstance() {
    MemberInjectionReturnInstance component = backend.create(MemberInjectionReturnInstance.class);
    MemberInjectionReturnInstance.Target in = new MemberInjectionReturnInstance.Target();
    MemberInjectionReturnInstance.Target out = component.inject(in);
    assertThat(out.foo).isEqualTo("foo");
    assertThat(out).isSameAs(in);
  }

  @Test public void memberInjectionNoInjects() {
    MemberInjectionNoInjects component = backend.create(MemberInjectionNoInjects.class);
    MemberInjectionNoInjects.Target target = new MemberInjectionNoInjects.Target();
    component.inject(target);
    assertThat(target.one).isNull();
    assertThat(target.two).isNull();
    assertThat(target.three).isNull();
    assertThat(target.count).isEqualTo(0);
  }

  @Test public void memberInjectionFieldBeforeMethod() {
    MemberInjectionFieldBeforeMethod component =
        backend.create(MemberInjectionFieldBeforeMethod.class);
    MemberInjectionFieldBeforeMethod.Target target = new MemberInjectionFieldBeforeMethod.Target();
    component.inject(target);
    assertThat(target.fieldBeforeMethod).isTrue();
  }

  @Test public void memberInjectionFieldVisibility() {
    MemberInjectionFieldVisibility component = backend.create(MemberInjectionFieldVisibility.class);
    MemberInjectionFieldVisibility.Target target = new MemberInjectionFieldVisibility.Target();
    component.inject(target);
    assertThat(target.one).isEqualTo("one");
    assertThat(target.two).isEqualTo(2L);
    assertThat(target.three).isEqualTo(3);
  }

  @Test public void memberInjectionHierarchy() {
    MemberInjectionHierarchy component = backend.create(MemberInjectionHierarchy.class);
    MemberInjectionHierarchy.Subtype target = new MemberInjectionHierarchy.Subtype();
    component.inject(target);
    assertThat(target.baseOne).isEqualTo("foo");
    assertThat(target.baseCalled).isTrue();
    assertThat(target.subtypeOne).isEqualTo("foo");
    assertThat(target.subtypeCalled).isTrue();
  }

  @Test public void memberInjectionOrder() {
    MemberInjectionOrder component = backend.create(MemberInjectionOrder.class);
    MemberInjectionOrder.SubType target = new MemberInjectionOrder.SubType();
    component.inject(target);
    assertThat(target.calls)
        .containsExactly(
            // @Inject specification: Constructors are injected first
            "instantiation: baseField=null, subField=null",
            // followed by fields, and then methods.
            "baseMethod(foo): baseField=foo, subField=null",
            // Fields and methods in superclasses are injected before those in subclasses.
            "subMethod(foo): baseField=foo, subField=foo"
        )
        .inOrder();
  }

  @Test public void memberInjectionMethodVisibility() {
    MemberInjectionMethodVisibility component =
        backend.create(MemberInjectionMethodVisibility.class);
    MemberInjectionMethodVisibility.Target target = new MemberInjectionMethodVisibility.Target();
    component.inject(target);
    assertThat(target.count).isEqualTo(3);
    assertThat(target.one).isEqualTo("one");
    assertThat(target.two).isEqualTo(2L);
    assertThat(target.three).isEqualTo(3);
  }

  @Test public void memberInjectionMethodMultipleParams() {
    MemberInjectionMethodMultipleParams component =
        backend.create(MemberInjectionMethodMultipleParams.class);
    MemberInjectionMethodMultipleParams.Target target =
        new MemberInjectionMethodMultipleParams.Target();
    component.inject(target);
    assertThat(target.one).isEqualTo("one");
    assertThat(target.two).isEqualTo(2L);
    assertThat(target.two2).isEqualTo(2L);
    assertThat(target.three).isEqualTo(3);
  }

  @Test public void memberInjectionMethodReturnTypes() {
    MemberInjectionMethodReturnTypes component =
        backend.create(MemberInjectionMethodReturnTypes.class);
    MemberInjectionMethodReturnTypes.Target target = new MemberInjectionMethodReturnTypes.Target();
    component.inject(target);
    assertThat(target.count).isEqualTo(3);
  }

  @Test public void memberInjectionQualified() {
    MemberInjectionQualified component = backend.create(MemberInjectionQualified.class);
    MemberInjectionQualified.Target target = new MemberInjectionQualified.Target();
    component.inject(target);
    assertThat(target.fromField).isEqualTo("foo");
    assertThat(target.fromMethod).isEqualTo("foo");
  }

  private void ignoreReflectionBackend() {
    assumeTrue("Not yet implemented for reflection backend", backend != Backend.REFLECT);
  }

  private void ignoreCodegenBackend() {
    assumeTrue("Not supported for codegen backend", backend != Backend.CODEGEN);
  }
}
