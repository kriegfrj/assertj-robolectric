package org.assertj.robolectric.api.pkg2;

import org.assertj.core.api.AbstractAssert;
import org.robolectric.shadows.ShadowAClass2;

public class ShadowAClass2Assert
  extends AbstractAssert<ShadowAClass2Assert,ShadowAClass2>{

  public ShadowAClass2Assert(org.robolectric.shadows.ShadowAClass2 actual) {
    super(actual, ShadowAClass2Assert.class);
  }
  
}
