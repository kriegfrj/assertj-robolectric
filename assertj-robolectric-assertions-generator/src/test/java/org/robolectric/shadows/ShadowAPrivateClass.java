package org.robolectric.shadows;

import org.robolectric.annotation.Implements;

@Implements(className = "android.pkg.APrivateClass")
public class ShadowAPrivateClass {
  public String getSomethingElse() {
    return "";
  }
}
