package org.flyti.flyml;

import java.io.File;
import java.io.FileNotFoundException;

public interface Builder extends PropertyHandler {
  void build(File file) throws FileNotFoundException;
}
