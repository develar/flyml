package org.flyti.flyml;

import flex2.compiler.CompilationUnit;
import flex2.compiler.Source;
import flex2.compiler.Transcoder;
import flex2.compiler.as3.As3Configuration;
import flex2.compiler.as3.Extension;
import flex2.compiler.as3.reflect.TypeTable;
import flex2.compiler.extensions.IAsCompilerExtension;
import flex2.compiler.extensions.IConfigurableExtension;
import flex2.compiler.io.LocalFile;
import flex2.compiler.util.NameMappings;
import flex2.compiler.util.ThreadLocalToolkit;
import macromedia.asc.parser.ProgramNode;
import org.flyti.flyml.cocoa.PropertyHandlerCocoaProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class FlymlCompilerExtension implements IAsCompilerExtension, Extension, IConfigurableExtension {
  private ASTBuilder builder;

  public void configure(List<String> parameters) {
  }

  public void run(List<Extension> asCompilerExtensions, String gendir, As3Configuration ascConfiguration, NameMappings mappings, Transcoder[] transcoders) {
    // before all, for reduce compilation unit set
    asCompilerExtensions.add(0, this);

    builder = new ASTBuilder(true, new PropertyHandlerCocoaProvider());
  }

  public void parse1(CompilationUnit unit, TypeTable typeTable) {
    final Source source = unit.getSource();
    if (!(source.getBackingFile() instanceof LocalFile)) {
      return;
    }

    final File ymlFile = new File(source.getParent(), source.getShortName() + ".yml");
    if (!ymlFile.exists()) {
      return;
    }

    try {
      builder.build(ymlFile, ((ProgramNode) unit.getSyntaxTree()), unit.getContext().getAscContext());
    }
    catch (FileNotFoundException e) {
      ThreadLocalToolkit.logError(e.getMessage());
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
}
