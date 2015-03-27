package org.robolectric.shadows;

import org.assertj.core.api.AbstractAssert;

public class ShadowAClass2ShadowAnInnerClassAssert
  extends AbstractAssert<ShadowAClass2ShadowAnInnerClassAssert,ShadowAClass2.ShadowAnInnerClass>{

  public ShadowAClass2ShadowAnInnerClassAssert(org.robolectric.shadows.ShadowAClass2.ShadowAnInnerClass actual) {
    super(actual, ShadowAClass2ShadowAnInnerClassAssert.class);
  }
  
  public ShadowAClass2ShadowAnInnerClassAssert hasShadowInnerProp(long something) {
    return this;
  }
}
