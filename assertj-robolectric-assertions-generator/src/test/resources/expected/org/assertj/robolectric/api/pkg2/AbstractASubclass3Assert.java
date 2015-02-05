package org.assertj.robolectric.api.pkg2;

import android.pkg2.ASubclass3;

import org.assertj.android.api.pkg2.ASubclass3Assert;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;
import org.assertj.robolectric.api.pkg2.ShadowAClass3Assert;
import org.assertj.robolectric.api.pkg2.ShadowASubclass3Assert;

public abstract class AbstractASubclass3Assert<
  S extends AbstractASubclass3Assert<S,A,AA,SA>,
  A extends ASubclass3,
  AA extends org.assertj.android.api.pkg2.ASubclass3Assert,
  SA extends ShadowAClass3Assert> extends AbstractAClass3Assert<S,A,AA,SA> {
  
  public AbstractASubclass3Assert(A actual,
                                  AA actualAssert,
                                  SA shadowAssert,
                                  Class<S> selfType) {
    super(actual, actualAssert, shadowAssert, selfType);
  }

  public S hasAnotherStringProperty(String property) {
    actualAssert.hasAnotherStringProperty(property);
    return myself;
  }

  public S hasAnotherClassProperty(Object param) {
    actualAssert.hasAnotherClassProperty(param);
    return myself;
  }

  public S hasSubclassShadowProperty(String value) {
    shadowAssert.hasSubclassShadowProperty(value);
    return myself;
  }
}
