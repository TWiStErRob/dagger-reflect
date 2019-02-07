package com.example;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Component(modules = ProvidesNull.Module1.class)
public interface ProvidesNull {
  String string();

  @Module
  abstract class Module1 {
    @Provides static String foo() {
      return provideWrong();
    }
    static String provideWrong() {
      return null;
    }
  }
}
