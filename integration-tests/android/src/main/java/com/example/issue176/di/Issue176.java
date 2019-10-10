package com.example.issue176.di;

import android.content.Context;
import com.example.issue176.DebugConfigContract;
import dagger.BindsInstance;
import dagger.Component;

@Component(modules = DebugConfigModule.class)
public interface Issue176 {
  DebugConfigContract.Presenter presenter();

  @Component.Builder
  interface Builder {
    @BindsInstance Builder context(Context instance);
    Issue176 build();
  }
}
