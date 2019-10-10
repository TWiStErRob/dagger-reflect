package com.example.issue176.di;

import android.content.ClipboardManager;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public interface AndroidApplicationModule
{
  @Provides
  static ClipboardManager provideClipboardManager(Context context) {
    return (ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
  }
}
