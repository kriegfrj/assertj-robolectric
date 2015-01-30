package org.assertj.robolectric.api;

public final class Assertions {
  public static org.assertj.android.api.pkg.AClass1Assert assertThat(
      android.pkg.AClass1 actual) {
    return new org.assertj.android.api.pkg.AClass1Assert(actual);
  }

  public static org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert assertThat(
      android.pkg.AClassWithoutShadowAssert actual) {
    return new org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.ShadowAClass2Assert assertThat(
      org.robolectric.shadows.ShadowAClass2 actual) {
    return new org.assertj.robolectric.api.pkg2.ShadowAClass2Assert(actual);
  }

  public static org.assertj.robolectric.api.pkg2.AClass2Assert assertThat(
      android.pkg2.AClass2 actual) {
    return new org.assertj.robolectric.api.pkg2.AClass2Assert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
