package org.flyti.javaBuilderFlex;

import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public abstract class AbstractBuilder implements Builder {
  private static final Charset YAML_CHARSET = Charset.forName("UTF-8");

  private final Composer composer;

  private final boolean putBufferInMark;

  private String propertyName;

  public AbstractBuilder(boolean putBufferInMark) {
    this.putBufferInMark = putBufferInMark;

    composer = new Composer(new Resolver(), this);
  }

  public void build(File file) throws FileNotFoundException {
    composer.setParser(new ParserImpl(new StreamReader(new InputStreamReader(new FileInputStream(file), YAML_CHARSET), putBufferInMark)));
    composer.compose();
  }

  public void createObject(String type) {
    
  }

  public void setProperty(String name) {
    propertyName = name;
  }

  public void setStringValue(String value) {
    
  }
}
