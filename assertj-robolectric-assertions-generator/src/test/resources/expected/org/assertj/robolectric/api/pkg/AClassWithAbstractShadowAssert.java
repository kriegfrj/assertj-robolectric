package org.assertj.robolectric.api.pkg;

import static org.robolectric.Shadows.shadowOf;
import android.pkg.AClassWithAbstractShadow;

import org.robolectric.shadows.ShadowAClassWithAbstractShadowAssert;

public class AClassWithAbstractShadowAssert extends org.assertj.robolectric.api.AbstractRobolectricAssert<
  AClassWithAbstractShadowAssert,
  AClassWithAbstractShadow,
  org.assertj.android.api.pkg.AClassWithAbstractShadowAssert,
  ShadowAClassWithAbstractShadowAssert> {
  
  public AClassWithAbstractShadowAssert(AClassWithAbstractShadow actual) {
    super(actual,
          new org.assertj.android.api.pkg.AClassWithAbstractShadowAssert(actual),
          new ShadowAClassWithAbstractShadowAssert(shadowOf(actual)),
          AClassWithAbstractShadowAssert.class);
  }


  public AClassWithAbstractShadowAssert hasProperty(int property) {
    actualAssert.hasProperty(property);
    return this;
  }

  public AClassWithAbstractShadowAssert shadowHasShadowProperty(java.lang.String shadowProperty) {
    shadowAssert.hasShadowProperty(shadowProperty);
    return this;
  }
}
