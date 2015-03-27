package org.robolectric.shadows;

import android.util.AbstractAnInterface;
import org.robolectric.annotation.Implements;

@Implements(AbstractAnInterface.class)
public class ShadowAbstractAnInterface {
  public String getSomething() {
    return "";
  }
}
