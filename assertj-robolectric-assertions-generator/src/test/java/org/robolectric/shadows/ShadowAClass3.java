package org.robolectric.shadows;

import android.pkg2.AClass3;
import org.robolectric.annotation.Implements;

@Implements(AClass3.class)
public class ShadowAClass3 {
  public String getShadowProperty() {
    return "";
  }
}
