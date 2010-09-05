package org.flyti.flyml.cocoa;

import org.flyti.flyml.ContentComplexPropertyHandler;
import org.flyti.flyml.PropertyHandler;
import org.flyti.flyml.PropertyHandlerProvider;

public class PropertyHandlerCocoaProvider implements PropertyHandlerProvider {
  private ContentComplexPropertyHandler contentComplexPropertyHandler;

  public PropertyHandler findPrimitive(String propertyName) {
    return null;
  }

  public PropertyHandler findComplex(String propertyName) {
    if (propertyName.equals("content")) {
      if (contentComplexPropertyHandler == null) {
        contentComplexPropertyHandler = new ContentComplexPropertyHandler();
      }
      return contentComplexPropertyHandler;
    }

    return null;
  }
}
