package org.flyti.javaBuilderFlex;

import flex2.compiler.CompilationUnit;
import flex2.compiler.Source;
import flex2.compiler.Transcoder;
import flex2.compiler.as3.As3Configuration;
import flex2.compiler.as3.Extension;
import flex2.compiler.as3.reflect.TypeTable;
import flex2.compiler.extensions.IAsCompilerExtension;
import flex2.compiler.io.LocalFile;
import flex2.compiler.util.NameMappings;
import flex2.compiler.util.ThreadLocalToolkit;
import macromedia.asc.parser.ClassDefinitionNode;
import macromedia.asc.parser.Node;
import macromedia.asc.parser.NodeFactory;
import macromedia.asc.parser.ProgramNode;
import macromedia.asc.util.ObjectList;
import org.javabuilders.Builder;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class JavaBuilderFlexCompilerExtension implements IAsCompilerExtension, Extension {
  private FlexJavaBuilderConfig builderConfig;

  public void run(final List<Extension> asCompilerExtensions, final String gendir, final As3Configuration ascConfiguration, final NameMappings mappings, final Transcoder[] transcoders) {
    // before all, for reduce compilation unit set
//    asCompilerExtensions.add(0, this); мы почему-то в любом случае получаем main_s от embedextension
    asCompilerExtensions.add(this);
  }

  public void parse1(final CompilationUnit unit, final TypeTable typeTable) {
    final Source source = unit.getSource();
    if (!(source.getBackingFile() instanceof LocalFile)) {
      return;
    }

    final File ymlFile = new File(source.getParent(), source.getShortName() + ".yml");
    if (!ymlFile.exists()) {
      return;
    }

    try {
      build(ymlFile);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
//    final String r = FileUtil.

    final NodeFactory nodeFactory = unit.getContext().getAscContext().getNodeFactory();

    final ObjectList<Node> items = ((ProgramNode) unit.getSyntaxTree()).pkgdefs.get(0).statements.items;
    // ClassDefinition closer to end
    for (int i = items.size() - 1; i > -1; i--) {
      final Node node = items.get(i);
      if (node instanceof ClassDefinitionNode) {
        final String varName = "test".intern();
//        nodeFactory.statementList(((ClassDefinitionNode) node).statements, AbstractSyntaxTreeUtil.generateVariable(nodeFactory, varName, "Object", true, null));
        break;
      }
    }
  }

  public void parse2(final CompilationUnit unit, final TypeTable typeTable) {
  }

  public void analyze1(CompilationUnit unit, TypeTable typeTable) {

  }

  public void analyze2(CompilationUnit unit, TypeTable typeTable) {

  }

  public void analyze3(CompilationUnit unit, TypeTable typeTable) {

  }

  public void analyze4(CompilationUnit unit, TypeTable typeTable) {

  }

  public void generate(CompilationUnit unit, TypeTable typeTable) {
    
  }

  private Object build(final File file) throws FileNotFoundException {
    if (builderConfig == null) {
      builderConfig = new FlexJavaBuilderConfig(null, null, null);
    }

    SafeConstructor constructor = new SafeConstructor();
    Composer composer = new Composer(new ParserImpl(new StreamReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))), new Resolver());
    constructor.setComposer(composer);
    final Object data;
    try {
      data = constructor.getSingleData();
    }
    catch (YAMLException e) {
      ThreadLocalToolkit.logError(e.getMessage());
      e.printStackTrace();
      return null;
    }
    return data;

//		Builder.build(builderConfig, new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
  }
}
