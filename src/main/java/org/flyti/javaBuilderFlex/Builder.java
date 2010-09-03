package org.flyti.javaBuilderFlex;

import java.io.File;
import java.io.FileNotFoundException;

public interface Builder {
  void build(File file) throws FileNotFoundException;

  void createObject(String type);

  void setProperty(String name);

  void setStringValue(String value);
}
