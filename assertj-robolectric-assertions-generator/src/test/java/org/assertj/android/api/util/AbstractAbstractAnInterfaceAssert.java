package org.assertj.android.api.util;

import android.util.AbstractAnInterface;

public abstract class AbstractAbstractAnInterfaceAssert<
  S extends AbstractAbstractAnInterfaceAssert<S,A>,
  A extends AbstractAnInterface> extends AbstractAnInterfaceAssert<S,A> {

  protected AbstractAbstractAnInterfaceAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasInt(int expected) {
    return myself;
  }
}
