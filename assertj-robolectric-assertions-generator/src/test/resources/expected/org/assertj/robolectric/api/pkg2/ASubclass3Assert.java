package org.assertj.robolectric.api.pkg2;

import static org.robolectric.Shadows.shadowOf;
import android.pkg2.ASubclass3;

import org.robolectric.shadows.ShadowASubclass3Assert;

public class ASubclass3Assert extends org.assertj.robolectric.api.pkg2.AbstractAClass3Assert<
  ASubclass3Assert,
  ASubclass3,
  org.assertj.android.api.pkg2.ASubclass3Assert,
  ShadowASubclass3Assert> {
  
  public ASubclass3Assert(ASubclass3 actual) {
    super(actual,
          new org.assertj.android.api.pkg2.ASubclass3Assert(actual),
          new ShadowASubclass3Assert(shadowOf(actual)),
          ASubclass3Assert.class);
  }

  public ASubclass3Assert hasAnotherStringProperty(java.lang.String property) {
    actualAssert.hasAnotherStringProperty(property);
    return this;
  }

  public ASubclass3Assert hasAnotherClassProperty(java.lang.Object param) {
    actualAssert.hasAnotherClassProperty(param);
    return this;
  }

  public ASubclass3Assert shadowHasSubclassShadowProperty(java.lang.String value) {
    shadowAssert.hasSubclassShadowProperty(value);
    return this;
  }
}
