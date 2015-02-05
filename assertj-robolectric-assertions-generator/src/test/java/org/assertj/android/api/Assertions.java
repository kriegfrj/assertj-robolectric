package org.assertj.android.api;

public final class Assertions {
  public static org.assertj.android.api.pkg.AClass1Assert assertThat(
      android.pkg.AClass1 actual) {
    return new org.assertj.android.api.pkg.AClass1Assert(actual);
  }

  public static org.assertj.android.api.pkg2.AClass2Assert assertThat(
      android.pkg2.AClass2 actual) {
    return new org.assertj.android.api.pkg2.AClass2Assert(actual);
  }

  // There was a bug in the template generator that if an assertion without
  // a shadow appeared after one with a shadow, then the shadow from the previous
  // one would be used instead. Putting this assertThat after the previous
  // checks for this condition.
  public static org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert assertThat(
      android.pkg.AClassWithoutShadowAssert actual) {
    return new org.assertj.android.api.pkg.AClassWithoutShadowAssertAssert(actual);
  }

  public static org.assertj.android.api.pkg2.AClass3Assert assertThat(
      android.pkg2.AClass3 actual) {
    return new org.assertj.android.api.pkg2.AClass3Assert(actual);
  }

  public static org.assertj.android.api.pkg2.ASubclass3Assert assertThat(
      android.pkg2.ASubclass3 actual) {
    return new org.assertj.android.api.pkg2.ASubclass3Assert(actual);
  }

  private Assertions() {
    throw new AssertionError("No instances.");
  }
}
