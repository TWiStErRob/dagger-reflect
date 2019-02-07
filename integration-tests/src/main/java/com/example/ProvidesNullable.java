package com.example;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import javax.annotation.Nullable;

@Component(modules = ProvidesNullable.Module1.class)
public interface ProvidesNullable {
  @Nullable String string();

  @Module
  abstract class Module1 {
    @Provides static @Nullable String foo() {
      return null;
    }
  }
}
