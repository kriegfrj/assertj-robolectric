package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAbstractAnInterfaceAssert<S extends AbstractShadowAbstractAnInterfaceAssert<S,A>, A extends ShadowAbstractAnInterface>
  extends AbstractAssert<S,A> {

  public AbstractShadowAbstractAnInterfaceAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasSomething(String something) {
    return myself;
  }
}
