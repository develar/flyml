package org.flyti.flyml;

import java.io.File;
import java.io.FileNotFoundException;

public interface Builder {
  void build(File file) throws FileNotFoundException;

  void createObject(String type);

  // init for set property, value will be set later
  void setProperty(String name);

  void setStringValue(String value);
}
