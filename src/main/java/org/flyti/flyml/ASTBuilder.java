package org.flyti.flyml;

import macromedia.asc.parser.*;
import macromedia.asc.semantics.NamespaceValue;
import macromedia.asc.semantics.ReferenceValue;
import macromedia.asc.util.Context;
import macromedia.asc.util.Namespaces;
import macromedia.asc.util.ObjectList;
import org.flyti.flyml.util.ASTUtil;

import java.io.File;
import java.io.FileNotFoundException;

public class ASTBuilder extends AbstractBuilder {
  private static FunctionSignatureNode createChildrenFunctionSignature = new FunctionSignatureNode(null, null);

  private ObjectList<Node> functionStatements;

  static {
    createChildrenFunctionSignature.void_anno = true;
  }

  public ASTBuilder(boolean putBufferInMark, PropertyHandlerProvider propertyHandlerProvider) {
    super(putBufferInMark);

    this.propertyHandlerProvider = propertyHandlerProvider;
  }

  public void build(File file, ProgramNode syntaxTree, Context context) throws FileNotFoundException {
//    createChildrenFunctionSignature = new FunctionSignatureNode(null, null);
//    createChildrenFunctionSignature.void_anno = true;

    ObjectList<Node> items = syntaxTree.pkgdefs.get(0).statements.items;
    // ClassDefinition closer to end
    for (int i = items.size() - 1; i > -1; i--) {
      Node node = items.get(i);
      if (node instanceof ClassDefinitionNode) {
        NodeFactory nodeFactory = context.getNodeFactory();
        StatementListNode functionBody = new StatementListNode(null);

        // use nodeFactory, we need internal_name generation
        // fucked, fucked, fucked Adobe http://juick.com/develar/913000
        IdentifierNode functionName = new IdentifierNode("createChildren", false, 0);

        functionStatements = functionBody.items;

        PrimitivePropertyHandler defaultPropertyHandler = new PrimitivePropertyHandler();
        defaultPropertyHandler.setStatements(functionStatements);
        propertyHandlers.push(defaultPropertyHandler);
        build(file);

        ((ClassDefinitionNode) node).statements.items.add(new FunctionDefinitionNode(context, null, ASTUtil.createOverridePublicAttributeList(), new FunctionNameNode(Tokens.EMPTY_TOKEN, functionName),
                nodeFactory.functionCommon(context, functionName, createChildrenFunctionSignature, functionBody, 0)));
        break;
      }
    }
  }

  @Override
  public void setStringValue(String value) {
    functionStatements.add(new ExpressionStatementNode(new SetExpressionNode(new IdentifierNode(propertyName, true, 0), new ArgumentListNode(new LiteralStringNode(value), 0))));
  }

  private void createFunction(Context context, StatementListNode statements) {
    NodeFactory nodeFactory = context.getNodeFactory();

    StatementListNode functionBody = new StatementListNode(null);

    QualifiedIdentifierNode name = new QualifiedIdentifierNode(null, "v1", 0);
    IdentifierNode type = createResolvedIdentifier(context, "flash.text", "TextField");

    CallExpressionNode call = new CallExpressionNode(type, null);
    call.is_new = true;

    VariableBindingNode var = new VariableBindingNode(null, null, Tokens.VAR_TOKEN, new TypedIdentifierNode(name, createTypeMemberExpressionNode(type), 0), new MemberExpressionNode(null, call, 0));
    functionBody.items.add(new VariableDefinitionNode(null, null, Tokens.VAR_TOKEN, new ListNode(null, var, 0), 0));

    GetExpressionNode getExpressionNode = new GetExpressionNode(new IdentifierNode("v1", false, 0));
    getExpressionNode.setMode(Tokens.EMPTY_TOKEN);

    SetExpressionNode set = new SetExpressionNode(new IdentifierNode("text", 0), new ArgumentListNode(new LiteralStringNode("sss AEFLCWEHJCKSAX", false, false), 0));
    ExpressionStatementNode expressionStatementNode = new ExpressionStatementNode(new MemberExpressionNode(new MemberExpressionNode(null, getExpressionNode, 0), set, 0));
    functionBody.items.add(expressionStatementNode);

    CallExpressionNode callMethod = new CallExpressionNode(new IdentifierNode("addChild", false, 0), new ArgumentListNode(new MemberExpressionNode(null, getExpressionNode, 0), 0));
    callMethod.setMode(Tokens.EMPTY_TOKEN);

    functionBody.items.add(new ExpressionStatementNode(new MemberExpressionNode(null, callMethod, 0)));

    // use nodeFactory, we need internal_name generation
//    FunctionCommonNode functionCommon = nodeFactory.functionCommon(context, createChildrenFunctionName.identifier, createChildrenFunctionSignature, functionBody, 0);
//    statements.items.add(new FunctionDefinitionNode(context, nodeFactory.current_package, attributeListOverrideProtected, createChildrenFunctionName, functionCommon));
  }

  private static MemberExpressionNode createTypeMemberExpressionNode(IdentifierNode identifier) {
    GetExpressionNode getExpressionNode = new GetExpressionNode(identifier);
    getExpressionNode.setMode(Tokens.EMPTY_TOKEN);
    return new MemberExpressionNode(null, getExpressionNode, 0);
  }

  private static IdentifierNode createResolvedIdentifier(Context context, String namespace, String name) {
    NamespaceValue namespaceValue = new NamespaceValue();
    namespaceValue.name = namespace;
    Namespaces namespaces = new Namespaces(namespaceValue);

    IdentifierNode identifier = new IdentifierNode(name, false, 0);
    identifier.ref = new ReferenceValue(context, null, name, namespaces);
    return identifier;
  }
}
