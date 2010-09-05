package org.flyti.flyml.util;

import macromedia.asc.parser.AttributeListNode;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

// fucked, fucked, fucked Adobe http://juick.com/develar/913000
public final class ASTUtil {
  public static void dump(Object a) {
    Yaml yaml = new Yaml();
    try {
      yaml.dump(a, new FileWriter(new File("/Users/develar/a.yml")));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static AttributeListNode createOverridePublicAttributeList() {
    AttributeListNode attributeList = new AttributeListNode(null, 0);
    attributeList.items.remove(0);
    attributeList.hasProtected = true;
    attributeList.hasOverride = true;
    return attributeList;
  }
}
