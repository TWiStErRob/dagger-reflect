package com.example;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.inject.Qualifier;

@Component(modules = ProvidesQualified.Module1.class)
interface ProvidesQualified {
  @Foo String string();

  @Module
  abstract class Module1 {
    @Provides static @Foo String string() {
      return "foo";
    }
  }

  @Qualifier
  @interface Foo {
  }
}
