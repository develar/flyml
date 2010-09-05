package org.flyti.flyml;

import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.util.ArrayStack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public abstract class AbstractBuilder implements Builder {
  private static final Charset YAML_CHARSET = Charset.forName("UTF-8");

  private final Composer composer;
  protected PropertyHandlerProvider propertyHandlerProvider;

  private final boolean putBufferInMark;

  protected String propertyName;
  protected ArrayStack<PropertyHandler> propertyHandlers = new ArrayStack<PropertyHandler>(4);
  protected PropertyHandler propertyHandler;

  public AbstractBuilder(boolean putBufferInMark) {
    this.putBufferInMark = putBufferInMark;

    composer = new Composer(new Resolver(), this);
  }

  public void build(File file) throws FileNotFoundException {
    composer.setParser(new ParserImpl(new StreamReader(new InputStreamReader(new FileInputStream(file), YAML_CHARSET), putBufferInMark)));
    composer.compose();
  }

  public void createObject(String type) {
    propertyName = null;
    propertyHandler.createObject(type);
  }

  public void startMap() {
    // null after createObject
    if (propertyName != null) {
      PropertyHandler newPropertyHandler = propertyHandlerProvider.findComplex(propertyName);
      if (newPropertyHandler != null) {
        propertyHandler = newPropertyHandler;
        propertyHandlers.push(newPropertyHandler);
      }
    }

    propertyHandler.startMap();
  }

  public void endMap() {
    propertyHandler.endMap();
  }

  public void setProperty(String name) {
    propertyName = name;
  }

  public void setStringValue(String value) {
    PropertyHandler propertyHandler = propertyHandlerProvider.findPrimitive(propertyName);
    if (propertyHandler != null) {
      this.propertyHandler = propertyHandler;
    }
    this.propertyHandler.setStringValue(value);
  }
}
