package org.assertj.android.api.pkg;

import org.assertj.core.api.AbstractAssert;

public class AClassWithoutShadowAssertAssert
  extends AbstractAssert<AClassWithoutShadowAssertAssert,
                         android.pkg.AClassWithoutShadowAssert> {

  public AClassWithoutShadowAssertAssert(android.pkg.AClassWithoutShadowAssert actual) {
    super(actual, AClassWithoutShadowAssertAssert.class);
  }
  
  public AClassWithoutShadowAssertAssert hasFloat(int other) {
    return this;
  }
}
