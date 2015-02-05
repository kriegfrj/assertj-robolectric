package org.assertj.robolectric.api.pkg2;

import org.assertj.core.api.AbstractAssert;
import org.robolectric.shadows.ShadowAClass3;

public class AbstractShadowAClass3Assert<S extends AbstractShadowAClass3Assert<S,A>, A extends ShadowAClass3>
  extends AbstractAssert<S,A>{

  public AbstractShadowAClass3Assert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasShadowProperty(double somethingElse) {
    return myself;
  }

  protected S protectedMethodsShouldBeIgnored(String things) {
    return myself;
  }
}
