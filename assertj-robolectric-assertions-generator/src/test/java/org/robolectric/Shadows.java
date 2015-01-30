package org.robolectric;

import android.pkg.AClassWithoutShadowAssert;
import android.pkg2.AClass2;

import org.robolectric.shadows.ShadowAClass2;
import org.robolectric.shadows.ShadowAClassWithoutShadowAssert;

public class Shadows {
    public static ShadowAClass2 shadowOf(AClass2 solid) {
      return null;
    }

    public static ShadowAClassWithoutShadowAssert shadowOf(AClassWithoutShadowAssert solid) {
      return null;
    }
}
