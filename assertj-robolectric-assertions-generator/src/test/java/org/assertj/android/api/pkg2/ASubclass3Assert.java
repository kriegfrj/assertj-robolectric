package org.assertj.android.api.pkg2;

public class ASubclass3Assert extends AbstractAClass3Assert<ASubclass3Assert,android.pkg2.ASubclass3> {

  public ASubclass3Assert(android.pkg2.ASubclass3 actual) {
    super(actual, ASubclass3Assert.class);
  }
    
  public ASubclass3Assert hasAnotherStringProperty(String property) {
    return this;
  }

  public ASubclass3Assert hasAnotherClassProperty(Object param) {
    return this;
  }
}
