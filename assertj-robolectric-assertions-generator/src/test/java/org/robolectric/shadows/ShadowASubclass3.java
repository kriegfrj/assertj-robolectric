package org.robolectric.shadows;

import android.pkg2.ASubclass3;
import org.robolectric.annotation.Implements;

@Implements(ASubclass3.class)
public class ShadowASubclass3 extends ShadowAClass3 {
  public String getSubclassShadowProperty() {
    return "";
  }
}
