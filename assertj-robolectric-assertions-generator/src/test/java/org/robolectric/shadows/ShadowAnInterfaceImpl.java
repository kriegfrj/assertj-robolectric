package org.robolectric.shadows;

import android.widget.AnInterfaceImpl;
import org.robolectric.annotation.Implements;

@Implements(AnInterfaceImpl.class)
public class ShadowAnInterfaceImpl {
  public String getUrl() {
    return "";
  }
}
