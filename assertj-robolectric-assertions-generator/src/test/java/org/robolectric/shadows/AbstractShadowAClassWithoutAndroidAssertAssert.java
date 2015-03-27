package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAClassWithoutAndroidAssertAssert<S extends AbstractShadowAClassWithoutAndroidAssertAssert<S,A>, A extends ShadowAClassWithoutAndroidAssert>
  extends AbstractAssert<S,A>{

  public AbstractShadowAClassWithoutAndroidAssertAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasSomething(String something) {
    return myself;
  }
}
