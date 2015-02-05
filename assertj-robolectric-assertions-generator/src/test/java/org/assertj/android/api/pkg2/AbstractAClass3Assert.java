package org.assertj.android.api.pkg2;
import org.assertj.core.api.AbstractAssert;

import android.util.UtilClass;

public abstract class AbstractAClass3Assert<S extends AbstractAClass3Assert<S,A>,A extends android.pkg2.AClass3> extends AbstractAssert<S,A> {

  public AbstractAClass3Assert(A actual, Class<S> selfType) {
    super(actual,selfType);
  }
    
  public S hasFloatProp(float property) {
    return myself;
  }

  public static void hasSomeOtherStaticMethod(int something) {
  }

  public S hasClassProperty(UtilClass param) {
    return myself;
  }
}
