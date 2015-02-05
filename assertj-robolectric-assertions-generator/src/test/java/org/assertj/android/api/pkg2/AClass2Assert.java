package org.assertj.android.api.pkg2;
import org.assertj.core.api.AbstractAssert;

import android.util.UtilClass;

public class AClass2Assert extends AbstractAssert<AClass2Assert,android.pkg2.AClass2> {

  public AClass2Assert(android.pkg2.AClass2 actual) {
    super(actual, AClass2Assert.class);
  }
    
  public AClass2Assert hasStringProperty(String property) {
    return this;
  }

  public static void hasStaticMethod(int something) {
  }

  public AClass2Assert hasClassProperty(UtilClass param) {
    return this;
  }
}
