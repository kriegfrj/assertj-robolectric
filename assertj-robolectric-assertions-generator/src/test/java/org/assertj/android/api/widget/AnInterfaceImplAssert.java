package org.assertj.android.api.widget;

import org.assertj.android.api.util.AbstractAnInterfaceAssert;

import android.widget.AnInterfaceImpl;

public class AnInterfaceImplAssert extends AbstractAnInterfaceAssert<AnInterfaceImplAssert, AnInterfaceImpl> {

  public AnInterfaceImplAssert(AnInterfaceImpl actual) {
    super(actual, AnInterfaceImplAssert.class);
  }
  
  public AnInterfaceImplAssert hasInterfaceByte(byte interfaceByte) {
    return this;
  }
}
