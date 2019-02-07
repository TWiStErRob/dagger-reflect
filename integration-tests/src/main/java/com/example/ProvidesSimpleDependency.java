package com.example;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(modules = ProvidesSimpleDependency.Module1.class)
interface ProvidesSimpleDependency {

  String string();

  @Module
  abstract class Module1 {
    @Provides static long number() {
      return 42;
    }
    @Provides static String string(long number) {
      return "foo" + number;
    }
  }
}
