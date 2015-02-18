package org.assertj.robolectric.api.pkg2;

import static org.robolectric.Shadows.shadowOf;
import android.pkg2.AClass3;
import org.robolectric.shadows.ShadowAClass3Assert;

public class AClass3Assert extends
  AbstractAClass3Assert<AClass3Assert,
                        AClass3,
                        org.assertj.android.api.pkg2.AClass3Assert,
                        ShadowAClass3Assert> {
  
  public AClass3Assert(AClass3 actual) {
    super(actual,
          new org.assertj.android.api.pkg2.AClass3Assert(actual),
          new ShadowAClass3Assert(shadowOf(actual)),
          AClass3Assert.class);
  }
}
