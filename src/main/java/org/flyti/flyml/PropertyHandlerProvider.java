package org.flyti.flyml;

public interface PropertyHandlerProvider {
  PropertyHandler findPrimitive(String propertyName);

  PropertyHandler findComplex(String propertyName);
}
