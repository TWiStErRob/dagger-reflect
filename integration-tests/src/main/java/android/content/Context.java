package android.content;

public class Context {
  public static final String CLIPBOARD_SERVICE = "";
  public Context getApplicationContext() { return this; }
  public Object getSystemService(String name) { return new ClipboardManager(); }
}
