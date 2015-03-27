package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAPrivateClassAssert<S extends AbstractShadowAPrivateClassAssert<S,A>, A extends ShadowAPrivateClass>
  extends AbstractAssert<S,A>{

  public AbstractShadowAPrivateClassAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasSomethingElse(String something) {
    return myself;
  }
}
