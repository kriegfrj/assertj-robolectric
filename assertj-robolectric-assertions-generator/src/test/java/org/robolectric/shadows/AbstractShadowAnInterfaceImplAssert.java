package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class AbstractShadowAnInterfaceImplAssert<S extends AbstractShadowAnInterfaceImplAssert<S,A>, A extends ShadowAnInterfaceImpl>
  extends AbstractAssert<S,A>{

  public AbstractShadowAnInterfaceImplAssert(A actual, Class<S> selfType) {
    super(actual, selfType);
  }
  
  public S hasUrl(String url) {
    return myself;
  }

  protected S protectedMethodsShouldBeIgnored(String things) {
    return myself;
  }
}
