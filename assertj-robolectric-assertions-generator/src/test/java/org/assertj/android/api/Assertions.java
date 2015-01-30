package org.assertj.android.api;

public final class Assertions {
  public static org.assertj.android.api.pkg.AClass1Assert assertThat(
      android.pkg.AClass1 actual) {
    return new org.assertj.android.api.pkg.AClass1Assert(actual);
  }

  public static org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert assertThat(
      android.pkg.AClassWithoutShadowAssert actual) {
    return new org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert(actual);
  }

  public static org.assertj.android.api.pkg2.AClass2Assert assertThat(
      android.pkg2.AClass2 actual) {
    return new org.assertj.android.api.pkg2.AClass2Assert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
