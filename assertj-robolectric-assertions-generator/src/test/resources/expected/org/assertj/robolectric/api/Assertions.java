package org.assertj.robolectric.api;

public final class Assertions {
  public static org.assertj.android.api.pkg.AClass1Assert assertThat(
      android.pkg.AClass1 actual) {
    return new org.assertj.android.api.pkg.AClass1Assert(actual);
  }

  public static org.robolectric.shadows.ShadowAClassWithAbstractShadowAssert assertThat(
      org.robolectric.shadows.ShadowAClassWithAbstractShadow actual) {
    return new org.robolectric.shadows.ShadowAClassWithAbstractShadowAssert(actual);
  }

  public static org.assertj.robolectric.api.pkg.AClassWithAbstractShadowAssert assertThat(
      android.pkg.AClassWithAbstractShadow actual) {
    return new org.assertj.robolectric.api.pkg.AClassWithAbstractShadowAssert(actual);
  }

  public static org.robolectric.shadows.ShadowAClass2Assert assertThat(
      org.robolectric.shadows.ShadowAClass2 actual) {
    return new org.robolectric.shadows.ShadowAClass2Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.AClass2Assert assertThat(
      android.pkg2.AClass2 actual) {
    return new org.assertj.robolectric.api.pkg2.AClass2Assert(actual);
  }

  public static <T,V> org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert<T,V> assertThat(
      android.pkg.AClassWithoutShadowAssert<T,V> actual) {
    return new org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert<T,V>(actual);
  }

  public static org.robolectric.shadows.ShadowAClass3Assert assertThat(
      org.robolectric.shadows.ShadowAClass3 actual) {
    return new org.robolectric.shadows.ShadowAClass3Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.AClass3Assert assertThat(
      android.pkg2.AClass3 actual) {
    return new org.assertj.robolectric.api.pkg2.AClass3Assert(actual);
  }

  public static org.robolectric.shadows.ShadowASubclass3Assert assertThat(
      org.robolectric.shadows.ShadowASubclass3 actual) {
    return new org.robolectric.shadows.ShadowASubclass3Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ASubclass3Assert assertThat(
      android.pkg2.ASubclass3 actual) {
    return new org.assertj.robolectric.api.pkg2.ASubclass3Assert(actual);
  }

  public static org.assertj.android.api.util.AnInterfaceAssert assertThat(
      android.util.AnInterface actual) {
    return new org.assertj.android.api.util.AnInterfaceAssert(actual);
  }

  public static org.robolectric.shadows.ShadowAnInterfaceImplAssert assertThat(
      org.robolectric.shadows.ShadowAnInterfaceImpl actual) {
    return new org.robolectric.shadows.ShadowAnInterfaceImplAssert(actual);
  }

  public static org.assertj.robolectric.api.widget.AnInterfaceImplAssert assertThat(
      android.widget.AnInterfaceImpl actual) {
    return new org.assertj.robolectric.api.widget.AnInterfaceImplAssert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
