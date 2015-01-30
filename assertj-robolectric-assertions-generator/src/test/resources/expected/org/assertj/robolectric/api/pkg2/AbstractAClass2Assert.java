package org.assertj.robolectric.api.pkg2;

import android.pkg2.AClass2;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;
import org.assertj.robolectric.api.pkg2.ShadowAClass2Assert;

public abstract class AbstractAClass2Assert<
  S extends AbstractAClass2Assert<S,A,AA,SA>,
  A extends AClass2,
  AA extends org.assertj.android.api.pkg2.AClass2Assert,
  SA extends ShadowAClass2Assert> extends AbstractAssert<S,A> {
  
  protected AA actualAssert;
  protected SA shadowAssert;
  
  public AbstractAClass2Assert(A actual,
                               AA actualAssert,
                               SA shadowAssert,
                               Class<S> selfType) {
    super(actual, selfType);
    this.actualAssert = actualAssert;
    this.shadowAssert = shadowAssert;
  }

  /** {@inheritDoc} */
  @Override
  public S describedAs(String description, Object... args) {
    super.describedAs(description, args);
    actualAssert.describedAs(description, args);
    shadowAssert.describedAs(description, args);
    return myself;
  }

  /** {@inheritDoc} */
  @Override
  public S describedAs(Description description) {
    super.describedAs(description);
    actualAssert.describedAs(description);
    shadowAssert.describedAs(description);
    return myself;
  }

}
