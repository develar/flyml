package org.flyti.flyml;

public interface PropertyHandler {
  void createObject(String type);

  void startMap();

  void endMap();
  
  // init for set property, value will be set later
  void setProperty(String name);

  void setStringValue(String value);
}
