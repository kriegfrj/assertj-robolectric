package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAClassWithAbstractShadowAssert<
  S extends AbstractShadowAClassWithAbstractShadowAssert<S,A>,
  A extends ShadowAClassWithAbstractShadow
  > extends AbstractAssert<S,A> {

  public AbstractShadowAClassWithAbstractShadowAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasShadowProperty(String shadowProperty) {
    return myself;
  }
}
