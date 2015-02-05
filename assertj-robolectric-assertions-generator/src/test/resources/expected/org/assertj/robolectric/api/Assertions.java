package org.assertj.robolectric.api;

public final class Assertions {
  public static org.assertj.android.api.pkg.AClass1Assert assertThat(
      android.pkg.AClass1 actual) {
    return new org.assertj.android.api.pkg.AClass1Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ShadowAClass2Assert assertThat(
      org.robolectric.shadows.ShadowAClass2 actual) {
    return new org.assertj.robolectric.api.pkg2.ShadowAClass2Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.AClass2Assert assertThat(
      android.pkg2.AClass2 actual) {
    return new org.assertj.robolectric.api.pkg2.AClass2Assert(actual);
  }

  public static org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert assertThat(
      android.pkg.AClassWithoutShadowAssert actual) {
    return new org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ShadowAClass3Assert assertThat(
      org.robolectric.shadows.ShadowAClass3 actual) {
    return new org.assertj.robolectric.api.pkg2.ShadowAClass3Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.AClass3Assert assertThat(
      android.pkg2.AClass3 actual) {
    return new org.assertj.robolectric.api.pkg2.AClass3Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ShadowASubclass3Assert assertThat(
      org.robolectric.shadows.ShadowASubclass3 actual) {
    return new org.assertj.robolectric.api.pkg2.ShadowASubclass3Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ASubclass3Assert assertThat(
      android.pkg2.ASubclass3 actual) {
    return new org.assertj.robolectric.api.pkg2.ASubclass3Assert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
