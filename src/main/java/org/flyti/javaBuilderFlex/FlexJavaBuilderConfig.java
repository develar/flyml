package org.flyti.javaBuilderFlex;

import org.javabuilders.BuilderConfig;
import org.javabuilders.ICustomCommand;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.handler.validation.IValidationMessageHandler;

public class FlexJavaBuilderConfig extends BuilderConfig {
  public FlexJavaBuilderConfig(IBackgroundProcessingHandler backgroundProcessingHandler, IValidationMessageHandler validationMessageHandler, ICustomCommand<Boolean> confirmCommand) {
    super(backgroundProcessingHandler, validationMessageHandler, confirmCommand);
  }
}
