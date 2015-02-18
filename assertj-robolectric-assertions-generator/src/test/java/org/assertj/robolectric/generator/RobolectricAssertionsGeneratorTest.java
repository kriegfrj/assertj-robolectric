package org.assertj.robolectric.generator;

import static org.truth0.Truth.ASSERT;
import static com.google.testing.compile.JavaFileObjects.*;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Test;

public class RobolectricAssertionsGeneratorTest {

  @Test
  public void unannotatedSource_shouldCompile() {
    ASSERT.about(javaSource())
      .that(forSourceString("HelloWorld", "final class HelloWorld {}"))
      .processedWith(new RobolectricAssertionsGenerator())
      .compilesWithoutError()
      .and().generatesSources(
          forResource("expected/org/assertj/robolectric/api/Assertions.java"),
          forResource("expected/org/assertj/robolectric/api/pkg/AClassWithAbstractShadowAssert.java"),
          forResource("expected/org/assertj/robolectric/api/pkg2/AClass2Assert.java"),
          forResource("expected/org/assertj/robolectric/api/pkg2/AClass3Assert.java"),
          forResource("expected/org/assertj/robolectric/api/pkg2/AbstractAClass3Assert.java"),
          forResource("expected/org/assertj/robolectric/api/pkg2/ASubclass3Assert.java")
          );
  }
}
