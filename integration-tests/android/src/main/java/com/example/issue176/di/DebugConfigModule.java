package com.example.issue176.di;

import com.example.issue176.DebugConfigContract;
import com.example.issue176.DebugConfigPresenter;
import dagger.Binds;
import dagger.Module;

@Module(includes = {AndroidApplicationModule.class})
public abstract class DebugConfigModule
{
  @Binds
  abstract DebugConfigContract.Presenter presenter(DebugConfigPresenter presenter);
}
