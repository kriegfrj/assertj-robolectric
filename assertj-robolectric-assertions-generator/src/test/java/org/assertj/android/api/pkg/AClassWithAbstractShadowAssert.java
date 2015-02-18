package org.assertj.android.api.pkg;

import org.assertj.core.api.AbstractAssert;

public class AClassWithAbstractShadowAssert extends AbstractAssert<AClassWithAbstractShadowAssert,android.pkg.AClassWithAbstractShadow> {

  public AClassWithAbstractShadowAssert(android.pkg.AClassWithAbstractShadow actual) {
    super(actual, AClassWithAbstractShadowAssert.class);
  }
  
  public AClassWithAbstractShadowAssert hasProperty(int property) {
    return this;
  }
}
