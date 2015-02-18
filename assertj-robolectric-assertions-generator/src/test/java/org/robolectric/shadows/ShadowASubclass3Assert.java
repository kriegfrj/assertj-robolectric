package org.robolectric.shadows;


public class ShadowASubclass3Assert extends AbstractShadowAClass3Assert<ShadowASubclass3Assert, ShadowASubclass3> {

  public ShadowASubclass3Assert(ShadowASubclass3 actual) {
    super(actual, ShadowASubclass3Assert.class);
  }
  
  public ShadowASubclass3Assert hasSubclassShadowProperty(String value) {
    return this;
  }
}
