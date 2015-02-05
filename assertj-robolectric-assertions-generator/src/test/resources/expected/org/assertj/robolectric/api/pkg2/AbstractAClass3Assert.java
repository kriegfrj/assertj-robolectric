package org.assertj.robolectric.api.pkg2;

import android.pkg2.AClass3;

import org.assertj.robolectric.api.AbstractRobolectricAssert;
import org.assertj.robolectric.api.pkg2.ShadowAClass3Assert;

import org.robolectric.shadows.ShadowAClass3;

public abstract class AbstractAClass3Assert<
  S extends AbstractAClass3Assert<S,A,AA,SA>,
  A extends AClass3,
  AA extends org.assertj.android.api.pkg2.AbstractAClass3Assert<AA,A>,
  SA extends AbstractShadowAClass3Assert<SA,? extends ShadowAClass3>> extends AbstractRobolectricAssert<S,A,AA,SA> {
  
  protected AbstractAClass3Assert(A actual,
                                  AA actualAssert,
                                  SA shadowAssert,
                                  Class<S> selfType) {
    super(actual, actualAssert, shadowAssert, selfType);
  }

  public S hasFloatProp(float property) {
    actualAssert.hasFloatProp(property);
    return myself;
  }

  public S hasClassProperty(android.util.UtilClass param) {
    actualAssert.hasClassProperty(param);
    return myself;
  }

  public S hasShadowProperty(double somethingElse) {
    shadowAssert.hasShadowProperty(somethingElse);
    return myself;
  }
}
