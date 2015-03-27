package org.robolectric.shadows;

public final class Assertions {
  public static ShadowAbstractAnInterfaceAssert assertThat(
      ShadowAbstractAnInterface actual) {
    return new ShadowAbstractAnInterfaceAssert(actual);
  }

  public static ShadowAClass2Assert assertThat(
      ShadowAClass2 actual) {
    return new ShadowAClass2Assert(actual);
  }

  public static ShadowAClass2ShadowAnInnerClassAssert assertThat(
      ShadowAClass2.ShadowAnInnerClass actual) {
    return new ShadowAClass2ShadowAnInnerClassAssert(actual);
  }

  public static ShadowAClass3Assert assertThat(
      ShadowAClass3 actual) {
    return new ShadowAClass3Assert(actual);
  }

  public static ShadowAClassWithAbstractShadowAssert assertThat(
      ShadowAClassWithAbstractShadow actual) {
    return new ShadowAClassWithAbstractShadowAssert(actual);
  }

  public static ShadowAClassWithoutAndroidAssertAssert assertThat(
      ShadowAClassWithoutAndroidAssert actual) {
    return new ShadowAClassWithoutAndroidAssertAssert(actual);
  }

  public static ShadowAClassWithoutShadowOfAssert assertThat(
      ShadowAClassWithoutShadowOf actual) {
    return new ShadowAClassWithoutShadowOfAssert(actual);
  }

  public static ShadowAnInterfaceImplAssert assertThat(
      ShadowAnInterfaceImpl actual) {
    return new ShadowAnInterfaceImplAssert(actual);
  }

  public static ShadowAPrivateClassAssert assertThat(
      ShadowAPrivateClass actual) {
    return new ShadowAPrivateClassAssert(actual);
  }

  public static ShadowASubclass3Assert assertThat(
      ShadowASubclass3 actual) {
    return new ShadowASubclass3Assert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
