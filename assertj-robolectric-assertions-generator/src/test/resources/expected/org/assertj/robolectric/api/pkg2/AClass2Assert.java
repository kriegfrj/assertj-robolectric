package org.assertj.robolectric.api.pkg2;

import static org.robolectric.Shadows.shadowOf;
import android.pkg2.AClass2;
import org.assertj.robolectric.api.pkg2.ShadowAClass2Assert;

public class AClass2Assert extends
  AbstractAClass2Assert<AClass2Assert,
                        AClass2,
                        org.assertj.android.api.pkg2.AClass2Assert,
                        ShadowAClass2Assert> {
  
  public AClass2Assert(AClass2 actual) {
    super(actual,
          new org.assertj.android.api.pkg2.AClass2Assert(actual),
          new ShadowAClass2Assert(shadowOf(actual)),
          AClass2Assert.class);
  }
}
