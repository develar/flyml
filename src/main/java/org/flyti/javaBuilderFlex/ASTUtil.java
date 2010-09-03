package org.flyti.javaBuilderFlex;

import org.yaml.snakeyaml.Yaml;

import java.io.*;

public class ASTUtil {
  public static void dump(Object a, Object b) {
    Yaml yaml = new Yaml();
    try {
      yaml.dump(a, new FileWriter(new File("/Users/develar/a.yml")));
      yaml.dump(b, new FileWriter(new File("/Users/develar/b.yml")));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
