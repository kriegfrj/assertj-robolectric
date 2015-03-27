package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAClassWithoutShadowOfAssert<S extends AbstractShadowAClassWithoutShadowOfAssert<S,A>, A extends ShadowAClassWithoutShadowOf>
  extends AbstractAssert<S,A> {

  public AbstractShadowAClassWithoutShadowOfAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasSomeProp(Object something) {
    return myself;
  }
}
