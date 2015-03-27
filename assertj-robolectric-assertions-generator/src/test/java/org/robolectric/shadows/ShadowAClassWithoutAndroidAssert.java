package org.robolectric.shadows;

import android.pkg.AClassWithoutAndroidAssert;
import org.robolectric.annotation.Implements;

@Implements(AClassWithoutAndroidAssert.class)
public class ShadowAClassWithoutAndroidAssert {
  public String getSomething() {
    return "";
  }
}
