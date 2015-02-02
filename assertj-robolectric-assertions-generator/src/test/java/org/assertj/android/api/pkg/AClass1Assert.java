package org.assertj.android.api.pkg;

import org.assertj.core.api.AbstractAssert;

public class AClass1Assert extends AbstractAssert<AClass1Assert,android.pkg.AClass1> {

  public AClass1Assert(android.pkg.AClass1 actual) {
    super(actual, AClass1Assert.class);
  }
  
  public AClass1Assert hasProperty(int property) {
    return this;
  }
}
