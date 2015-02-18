package org.assertj.android.api.util;

import android.util.AnInterface;
import org.assertj.core.api.AbstractAssert;

public abstract class AbstractAnInterfaceAssert<
  S extends AbstractAnInterfaceAssert<S,A>,
  A extends AnInterface> extends AbstractAssert<S,A> {

  protected AbstractAnInterfaceAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasInterfaceProperty(String interfaceProperty) {
    return myself;
  }
}
