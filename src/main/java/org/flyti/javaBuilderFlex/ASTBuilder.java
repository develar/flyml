package org.flyti.javaBuilderFlex;

import flash.swf.tools.as3.PrettyPrinter;
import flex2.compiler.as3.AbstractSyntaxTreeUtil;
import macromedia.asc.parser.*;
import macromedia.asc.semantics.NamespaceValue;
import macromedia.asc.semantics.ReferenceValue;
import macromedia.asc.util.Context;
import macromedia.asc.util.Namespaces;
import macromedia.asc.util.ObjectList;
import sun.tools.tree.ThisExpression;

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
    AttributeListNode attributeList = new AttributeListNode(null, -1);
    attributeList.items.remove(0);
//    attributeList.hasOverride = true;
//    attributeList.hasProtected = true;
    attributeList.hasPublic = true;

    StatementListNode functionBody = new StatementListNode(null);



//    TypedIdentifierNode type = new TypedIdentifierNode(new IdentifierNode("ss", false, -1), createTypeMemberExpressionNode(context, "flash.text", "TextField"), -1);
    TypedIdentifierNode type = new TypedIdentifierNode(new IdentifierNode("ss", false, -1), createTypeMemberExpressionNode(context, "flash.text", "TextField"), -1);


//    ApplyTypeExprNode apply = new ApplyTypeExprNode(expr, typeArgs);
//                apply.setRValue(true);

    CallExpressionNode call = new CallExpressionNode(new TypeExpressionNode(createTypeMemberExpressionNode(context, "flash.text", "TextField"), true, false), null);
       call.is_new = true;
    VariableBindingNode var = new VariableBindingNode(null, null, Tokens.VAR_TOKEN, type, new MemberExpressionNode(null, call, 0));
//    functionBody.items.add();


//    functionBody.items.add(new VariableDefinitionNode(null, null, Tokens.VAR_TOKEN, new ListNode(null, var, 0), 0));
    functionBody.items.add(AbstractSyntaxTreeUtil.generateVariableNew(nodeFactory, "s", "flash.text.TextField", 0));

    FunctionCommonNode functionCommon = nodeFactory.functionCommon(context, null, createChildrenFunctionSignature, functionBody, -1);
    functionCommon.setFunctionDefinition(true);
    FunctionDefinitionNode node = new FunctionDefinitionNode(context, nodeFactory.current_package, attributeList, createChildrenFunctionName, functionCommon);
    statements.items.add(node);

//    PrintWriter printWriter;
//    try {
//      printWriter = new PrintWriter(new File("/Users/develar/t.as"));
//    }
//    catch (FileNotFoundException e) {
//      e.printStackTrace();
//      return;
//    }
//    PrettyPrinter prettyPrinter = new PrettyPrinter(printWriter);
//    prettyPrinter.evaluate(context, statements);
//    printWriter.flush();
//    printWriter.close();
  }

  private static MemberExpressionNode createTypeMemberExpressionNode(Context context, String packageName, String unqualifiedTypeName) {
    GetExpressionNode getExpressionNode = new GetExpressionNode(generateResolvedIdentifier(context, packageName, unqualifiedTypeName));
    getExpressionNode.setMode(Tokens.EMPTY_TOKEN);
    return new MemberExpressionNode(null, getExpressionNode, 0);
  }

  private static IdentifierNode generateResolvedIdentifier(Context context, String namespace, String name) {
    IdentifierNode result = new IdentifierNode(name, false, 0);
    Namespaces namespaces = new Namespaces();
    NamespaceValue namespaceValue = new NamespaceValue();
    namespaceValue.name = namespace;
    namespaces.add(namespaceValue);
    ReferenceValue referenceValue = new ReferenceValue(context, null, name, namespaces);
    referenceValue.setIsAttributeIdentifier(false);
    result.ref = referenceValue;
    return result;
  }
}
