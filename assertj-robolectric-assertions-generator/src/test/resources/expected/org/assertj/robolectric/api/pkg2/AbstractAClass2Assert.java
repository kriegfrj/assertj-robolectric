package org.assertj.robolectric.api.pkg2;

import android.pkg2.AClass2;

import org.assertj.robolectric.api.AbstractRobolectricAssert;
import org.assertj.robolectric.api.pkg2.ShadowAClass2Assert;

public abstract class AbstractAClass2Assert<
  S extends AbstractAClass2Assert<S,A,AA,SA>,
  A extends AClass2,
  AA extends org.assertj.android.api.pkg2.AClass2Assert,
  SA extends ShadowAClass2Assert> extends AbstractRobolectricAssert<S,A,AA,SA> {
  
  public AbstractAClass2Assert(A actual,
                               AA actualAssert,
                               SA shadowAssert,
                               Class<S> selfType) {
    super(actual, actualAssert, shadowAssert, selfType);
  }

  public S hasStringProperty(java.lang.String property) {
    actualAssert.hasStringProperty(property);
    return myself;
  }

  public S hasClassProperty(android.util.UtilClass param) {
    actualAssert.hasClassProperty(param);
    return myself;
  }

  public S hasShadowProperty(int something) {
    shadowAssert.hasShadowProperty(something);
    return myself;
  }
}
