package org.flyti.flyml;

import macromedia.asc.parser.*;
import macromedia.asc.util.ObjectList;

public class PrimitivePropertyHandler implements PropertyHandler {
  private ObjectList<Node> statements;

  public void setStatements(ObjectList<Node> statements) {
    this.statements = statements;
  }

  public void setProperty(String name) {

  }

  public void setStringValue(String value) {
//    statements.add(new ExpressionStatementNode(new SetExpressionNode(new IdentifierNode(propertyName, true, 0), new ArgumentListNode(new LiteralStringNode(value), 0))));
  }
}
