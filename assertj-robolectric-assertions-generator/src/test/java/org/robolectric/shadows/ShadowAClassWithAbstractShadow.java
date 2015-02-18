package org.robolectric.shadows;

import android.pkg.AClassWithAbstractShadow;
import org.robolectric.annotation.Implements;

@Implements(AClassWithAbstractShadow.class)
public class ShadowAClassWithAbstractShadow {
  public String getShadowProperty() {
    return "";
  }
}
