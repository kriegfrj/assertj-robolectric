package org.assertj.robolectric.api.pkg;

import android.pkg.AClassWithAbstractShadow;

import org.assertj.robolectric.api.AbstractRobolectricAssert;
import org.robolectric.shadows.ShadowAClassWithAbstractShadowAssert;

public abstract class AbstractAClassWithAbstractShadowAssert<
  S extends AbstractAClassWithAbstractShadowAssert<S,SA>,
  SA extends ShadowAClassWithAbstractShadowAssert> extends AbstractRobolectricAssert<S,AClassWithAbstractShadow,org.assertj.android.api.pkg.AClassWithAbstractShadowAssert,SA> {
  
  public AbstractAClassWithAbstractShadowAssert(AClassWithAbstractShadow actual,
                                                org.assertj.robolectric.api.pkg.AClassWithAbstractShadowAssert actualAssert,
                                                SA shadowAssert,
                                                Class<S> selfType) {
    super(actual, actualAssert, shadowAssert, selfType);
  }

  public S hasProperty(int property) {
    actualAssert.hasProperty(property);
    return myself;
  }

  public S hasShadowProperty(String shadowProperty) {
    shadowAssert.hasShadowProperty(shadowProperty);
    return myself;
  }
}
