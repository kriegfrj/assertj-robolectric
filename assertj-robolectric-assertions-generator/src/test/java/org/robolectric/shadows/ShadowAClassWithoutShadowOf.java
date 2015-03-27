package org.robolectric.shadows;

import libcore.pkg.AClassWithoutShadowOf;
import org.robolectric.annotation.Implements;

@Implements(AClassWithoutShadowOf.class)
public class ShadowAClassWithoutShadowOf {
  public Object getShadowProp() {
    return null;
  }
}
