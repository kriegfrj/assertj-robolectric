package org.robolectric.shadows;

public class ShadowAClassWithAbstractShadowAssert extends AbstractShadowAClassWithAbstractShadowAssert<ShadowAClassWithAbstractShadowAssert,ShadowAClassWithAbstractShadow> {
  
  public ShadowAClassWithAbstractShadowAssert(ShadowAClassWithAbstractShadow actual) {
    super(actual, ShadowAClassWithAbstractShadowAssert.class);
  }
}
