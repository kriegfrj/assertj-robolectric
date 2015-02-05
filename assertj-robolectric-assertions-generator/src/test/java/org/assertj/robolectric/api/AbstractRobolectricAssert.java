package org.assertj.robolectric.api;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.description.Description;

public abstract class AbstractRobolectricAssert<
  S extends AbstractRobolectricAssert<S,A,AA,SA>,
  A,
  AA extends AbstractAssert<AA,A>,
  SA extends AbstractAssert<SA,?>> extends AbstractAssert<S,A> {

  protected AA actualAssert;
  protected SA shadowAssert;
  
  protected AbstractRobolectricAssert(A actual,
                                      AA actualAssert,
                                      SA shadowAssert,
                                      Class<S> selfType) {
    super(actual, selfType);
    this.actualAssert = actualAssert;
    this.shadowAssert = shadowAssert;
  }

  /** {@inheritDoc} */
  @Override
  public S as(String description, Object... args) {
    super.as(description, args);
    actualAssert.as(description, args);
    shadowAssert.as(description, args);
    return myself;
  }

  /** {@inheritDoc} */
  @Override
  public S as(Description description) {
    super.as(description);
    actualAssert.as(description);
    shadowAssert.as(description);
    return myself;
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

  /** {@inheritDoc} */
  @Override
  public S overridingErrorMessage(String newErrorMessage, Object... args) {
    super.overridingErrorMessage(newErrorMessage, args);
    actualAssert.overridingErrorMessage(newErrorMessage, args);
    shadowAssert.overridingErrorMessage(newErrorMessage, args);
    return myself;
  }
}
