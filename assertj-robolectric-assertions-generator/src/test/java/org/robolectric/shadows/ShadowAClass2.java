package org.robolectric.shadows;

import android.pkg2.AClass2;
import org.robolectric.annotation.Implements;

@Implements(AClass2.class)
public class ShadowAClass2 {
  public String getShadowProperty() {
    return "";
  }
}
