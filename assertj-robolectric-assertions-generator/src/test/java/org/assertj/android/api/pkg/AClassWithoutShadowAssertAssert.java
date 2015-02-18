package org.assertj.android.api.pkg;

import org.assertj.core.api.AbstractAssert;

public class AClassWithoutShadowAssertAssert<T, V>
  extends AbstractAssert<AClassWithoutShadowAssertAssert<T,V>,
                         android.pkg.AClassWithoutShadowAssert<T,V>> {

  public AClassWithoutShadowAssertAssert(android.pkg.AClassWithoutShadowAssert<T,V> actual) {
    super(actual, AClassWithoutShadowAssertAssert.class);
  }
  
  public AClassWithoutShadowAssertAssert<T,V> hasFloat(int other) {
    return this;
  }
}
