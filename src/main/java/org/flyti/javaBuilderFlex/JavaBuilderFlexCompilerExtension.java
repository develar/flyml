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

import java.io.File;
import java.util.List;

public class JavaBuilderFlexCompilerExtension implements IAsCompilerExtension, Extension {
  public void run(List<Extension> asCompilerExtensions, String gendir, As3Configuration ascConfiguration, NameMappings mappings, Transcoder[] transcoders) {
    // before all, for reduce compilation unit set
//    asCompilerExtensions.add(0, this); мы почему-то в любом случае получаем main_s от embedextension
    asCompilerExtensions.add(0, this);
  }

  public void parse1(CompilationUnit unit, TypeTable typeTable) {
    final Source source = unit.getSource();
    if (!(source.getBackingFile() instanceof LocalFile)) {
      return;
    }

    final File ymlFile = new File(source.getParent(), source.getShortName() + ".yml");
    if (ymlFile.exists()) {
      System.out.print("yes");
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
