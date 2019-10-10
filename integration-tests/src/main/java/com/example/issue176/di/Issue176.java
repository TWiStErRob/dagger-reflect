package com.example.issue176.di;

import com.example.issue176.DebugConfigContract;
import dagger.Component;

@Component(modules = DebugConfigModule.class)
public interface Issue176 {
  DebugConfigContract.Presenter presenter();
}
