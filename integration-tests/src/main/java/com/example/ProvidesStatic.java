package com.example;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(modules = ProvidesStatic.Module1.class)
interface ProvidesStatic {
  String string();

  @Module
  abstract class Module1 {
    @Provides static String string() {
      return "foo";
    }
  }
}
