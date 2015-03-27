package org.robolectric;

import android.pkg.AClassWithAbstractShadow;
import android.pkg.AClassWithoutAndroidAssert;
import android.pkg.AClassWithoutShadowAssert;
import android.pkg2.AClass2;
import android.pkg2.AClass3;
import android.pkg2.ASubclass3;
import android.util.AbstractAnInterface;
import android.widget.AnInterfaceImpl;

import org.robolectric.shadows.ShadowAClass2;
import org.robolectric.shadows.ShadowAClass3;
import org.robolectric.shadows.ShadowAClassWithAbstractShadow;
import org.robolectric.shadows.ShadowAClassWithoutAndroidAssert;
import org.robolectric.shadows.ShadowAClassWithoutShadowAssert;
import org.robolectric.shadows.ShadowASubclass3;
import org.robolectric.shadows.ShadowAbstractAnInterface;
import org.robolectric.shadows.ShadowAnInterfaceImpl;

public class Shadows {
    public static ShadowAClass2 shadowOf(AClass2 solid) {
      return null;
    }

    public static ShadowAClassWithAbstractShadow shadowOf(AClassWithAbstractShadow solid) {
      return null;
    }

    public static ShadowAClassWithoutAndroidAssert shadowOf(AClassWithoutAndroidAssert solid) {
      return null;
    }

    public static ShadowAClassWithoutShadowAssert shadowOf(AClassWithoutShadowAssert solid) {
      return null;
    }

    public static ShadowAClass3 shadowOf(AClass3 solid) {
      return null;
    }

    public static ShadowASubclass3 shadowOf(ASubclass3 solid) {
      return null;
    }

    public static ShadowAnInterfaceImpl shadowOf(AnInterfaceImpl solid) {
      return null;
    }
    
    public static ShadowAbstractAnInterface shadowOf(AbstractAnInterface solid) {
      return null;
    }
}
