package dagger.reflect.test;

import dagger.internal.DaggerCodegen;
import dagger.reflect.DaggerReflect;

public enum Backend {
  REFLECT {
    @Override public <C> C create(Class<C> componentClass) {
      return DaggerReflect.create(componentClass);
    }

    @Override public <B> B builder(Class<B> builderClass) {
      return DaggerReflect.builder(builderClass);
    }
  },
  CODEGEN {
    @Override public <C> C create(Class<C> componentClass) {
      return DaggerCodegen.create(componentClass);
    }

    @Override public <B> B builder(Class<B> builderClass) {
      return DaggerCodegen.builder(builderClass);
    }
  };

  public abstract <C> C create(Class<C> componentClass);
  public abstract <B> B builder(Class<B> builderClass);
}
