package org.flyti.javaBuilderFlex;

import flex2.compiler.as3.AbstractSyntaxTreeUtil;
import macromedia.asc.parser.*;
import macromedia.asc.semantics.NamespaceValue;
import macromedia.asc.semantics.ReferenceValue;
import macromedia.asc.util.Context;
import macromedia.asc.util.Namespaces;
import macromedia.asc.util.ObjectList;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ASTBuilder extends AbstractBuilder {
  private static final FunctionSignatureNode createChildrenFunctionSignature = new FunctionSignatureNode(null, null);
  private static final FunctionNameNode createChildrenFunctionName = new FunctionNameNode(Tokens.EMPTY_TOKEN, new IdentifierNode("createChildren", false, -1));
//  private static final FunctionNameNode createChildrenQualifiedIdentifierNode = new FunctionNameNode(Tokens.EMPTY_TOKEN, new IdentifierNode("createChildren", false, -1));

  static {
    createChildrenFunctionSignature.void_anno = true;
  }

  public ASTBuilder(boolean putBufferInMark) {
    super(putBufferInMark);
  }

  private static boolean s = true;

  public void build(File file, ProgramNode syntaxTree, Context context) throws FileNotFoundException {
    ObjectList<Node> items = syntaxTree.pkgdefs.get(0).statements.items;
    // ClassDefinition closer to end
    for (int i = items.size() - 1; i > -1; i--) {
      Node node = items.get(i);
      if (node instanceof ClassDefinitionNode) {
        createFunction(context, ((ClassDefinitionNode) node).statements);
//        nodeFactory.statementList(((ClassDefinitionNode) node).statements, AbstractSyntaxTreeUtil.generateVariable(nodeFactory, varName, "Object", true, null));
        break;
      }
    }

    //build(file);
  }

  private void createFunction(Context context, StatementListNode statements) {
    NodeFactory nodeFactory = context.getNodeFactory();

//    NodeFactory nodeFactory = context.getNodeFactory();


    AttributeListNode attributeList = new AttributeListNode(null, -1);
    attributeList.items.remove(0);
//    attributeList.hasOverride = true;
//    attributeList.hasProtected = true;
    attributeList.hasPublic = true;

    StatementListNode functionBody = new StatementListNode(null);

    QualifiedIdentifierNode name = new QualifiedIdentifierNode(null, "v1", 0);
    IdentifierNode type = createResolvedIdentifier(context, "flash.text", "TextField");

    CallExpressionNode call = new CallExpressionNode(type, null);
    call.is_new = true;

    VariableBindingNode var = new VariableBindingNode(null, null, Tokens.VAR_TOKEN, new TypedIdentifierNode(name, createTypeMemberExpressionNode(type), 0), new MemberExpressionNode(null, call, 0));
    functionBody.items.add(new VariableDefinitionNode(null, null, Tokens.VAR_TOKEN, new ListNode(null, var, 0), 0));


    GetExpressionNode getExpressionNode = new GetExpressionNode(new IdentifierNode("v1", false, 0));
    getExpressionNode.setMode(Tokens.EMPTY_TOKEN);

    GetExpressionNode getExpressionNode2 = new GetExpressionNode(new IdentifierNode("addChild", false, 0));
    getExpressionNode.setMode(Tokens.EMPTY_TOKEN);

    SetExpressionNode set = new SetExpressionNode(new IdentifierNode("text", 0), new ArgumentListNode(new LiteralStringNode("sss AEFLCWEHJCKSAX", false, false), 0));
//    ExpressionStatementNode expressionStatementNode = new ExpressionStatementNode(new ListNode(null, new MemberExpressionNode(new MemberExpressionNode(null, getExpressionNode, 0), set, 0), 0));
    ExpressionStatementNode expressionStatementNode = new ExpressionStatementNode(new MemberExpressionNode(new MemberExpressionNode(null, getExpressionNode, 0), set, 0));
    functionBody.items.add(expressionStatementNode);

    CallExpressionNode callMethod = new CallExpressionNode(new IdentifierNode("addChild", false, 0), new ArgumentListNode(new MemberExpressionNode(null, getExpressionNode, 0), 0));
//    CallExpressionNode callMethod = new CallExpressionNode(getExpressionNode2, new ArgumentListNode(new MemberExpressionNode(null, getExpressionNode, 0), 0));
    callMethod.setMode(Tokens.EMPTY_TOKEN);
    callMethod.setRValue(true);


//    if (statements.items.size() == 2  && s) {
//      s = false;
    functionBody.items.add(new ExpressionStatementNode(new ListNode(null, new MemberExpressionNode(null, callMethod, 0), 0)));
//      functionBody.items.add(((FunctionDefinitionNode) statements.items.get(1)).fexpr.body.items.get(2));
//      ASTUtil.dump(functionBody.items.last(), ((FunctionDefinitionNode) statements.items.get(1)).fexpr.body.items.get(2));
//    }

//    FunctionCommonNode functionCommon = nodeFactory.functionCommon(context, null, createChildrenFunctionSignature, functionBody, -1);
//    functionCommon.setFunctionDefinition(true);
//    FunctionDefinitionNode node = new FunctionDefinitionNode(context, nodeFactory.current_package, attributeList, createChildrenFunctionName, functionCommon);

//    IdentifierNode identifier = new IdentifierNode("createChildren", false, -1);
    FunctionCommonNode functionCommon = nodeFactory.functionCommon(context, createChildrenFunctionName.identifier, createChildrenFunctionSignature, functionBody, -1);
    FunctionDefinitionNode node = new FunctionDefinitionNode(context, nodeFactory.current_package, attributeList, createChildrenFunctionName, functionCommon);
    statements.items.add(node);
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
