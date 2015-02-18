package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class ShadowAClass2Assert
  extends AbstractAssert<ShadowAClass2Assert,ShadowAClass2>{

  public ShadowAClass2Assert(org.robolectric.shadows.ShadowAClass2 actual) {
    super(actual, ShadowAClass2Assert.class);
  }
  
  public ShadowAClass2Assert hasShadowProperty(int something) {
    return this;
  }

  public ShadowAClass2Assert hasDoubleProperty(double prop, double offst) {
    return this;
  }

  protected ShadowAClass2Assert protectedMethodsShouldBeIgnored(String things) {
    return this;
  }
}
