package org.flyti.javaBuilderFlex;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class TestYAML {
  public static void main(String[] args) throws FileNotFoundException {
    Yaml yaml = new Yaml();
    Object data = yaml.load(new InputStreamReader(new FileInputStream(new File("/Users/develar/workspace/idea-test/src/TestForm.yml"))));
    data.toString();
  }
}
