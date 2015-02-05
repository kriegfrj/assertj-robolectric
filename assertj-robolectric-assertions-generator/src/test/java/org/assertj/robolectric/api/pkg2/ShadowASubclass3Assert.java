package org.assertj.robolectric.api.pkg2;

import org.robolectric.shadows.ShadowASubclass3;

public class ShadowASubclass3Assert extends AbstractShadowAClass3Assert<ShadowASubclass3Assert, ShadowASubclass3> {

  public ShadowASubclass3Assert(ShadowASubclass3 actual) {
    super(actual, ShadowASubclass3Assert.class);
  }
  
  public ShadowASubclass3Assert hasSubclassShadowProperty(String value) {
    return this;
  }
}
