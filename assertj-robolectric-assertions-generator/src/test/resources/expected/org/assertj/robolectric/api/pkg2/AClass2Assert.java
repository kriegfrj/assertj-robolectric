package org.assertj.robolectric.api.pkg2;

import static org.robolectric.Shadows.shadowOf;
import android.pkg2.AClass2;
import org.robolectric.shadows.ShadowAClass2Assert;

public class AClass2Assert extends org.assertj.robolectric.api.AbstractRobolectricAssert<
  AClass2Assert,
  AClass2,
  org.assertj.android.api.pkg2.AClass2Assert,
  ShadowAClass2Assert> {
  
  public AClass2Assert(AClass2 actual) {
    super(actual,
          new org.assertj.android.api.pkg2.AClass2Assert(actual),
          new ShadowAClass2Assert(shadowOf(actual)),
          AClass2Assert.class);
  }
  
  public AClass2Assert hasStringProperty(java.lang.String property) {
    actualAssert.hasStringProperty(property);
    return this;
  }

  public AClass2Assert hasClassProperty(android.util.UtilClass param) {
    actualAssert.hasClassProperty(param);
    return this;
  }
  
  public AClass2Assert hasFloatProperty(float value, float offset) {
    actualAssert.hasFloatProperty(value, offset);
    return this;
  }

  public AClass2Assert shadowHasShadowProperty(int something) {
    shadowAssert.hasShadowProperty(something);
    return this;
  }

  public AClass2Assert shadowHasDoubleProperty(double prop, double offst) {
    shadowAssert.hasDoubleProperty(prop, offst);
    return this;
  }
}
