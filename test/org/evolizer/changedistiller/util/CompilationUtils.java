package org.evolizer.changedistiller.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.core.util.CommentRecorderParser;
import org.evolizer.changedistiller.java.Comment;
import org.evolizer.changedistiller.java.CommentCollector;

public final class CompilationUtils {

    private static final String TEST_DATA_BASE_DIR = "resources/testdata/";

    private CompilationUtils() {}

    public static Compilation compileSource(String source) {
        CompilerOptions options = getDefaultCompilerOptions();
        Parser parser = createCommentRecorderParser(options);
        ICompilationUnit cu = createCompilationunit(source, "");
        CompilationResult compilationResult = createDefaultCompilationResult(cu, options);
        return new Compilation(parser.parse(cu, compilationResult), parser.scanner);
    }

    private static CompilationResult createDefaultCompilationResult(ICompilationUnit cu, CompilerOptions options) {
        CompilationResult compilationResult = new CompilationResult(cu, 0, 0, options.maxProblemsPerUnit);
        return compilationResult;
    }

    private static ICompilationUnit createCompilationunit(String source, String filename) {
        ICompilationUnit cu = new CompilationUnit(source.toCharArray(), filename, null);
        return cu;
    }

    private static Parser createCommentRecorderParser(CompilerOptions options) {
        Parser parser =
                new CommentRecorderParser(new ProblemReporter(
                        DefaultErrorHandlingPolicies.proceedWithAllProblems(),
                        options,
                        new DefaultProblemFactory()), false);
        return parser;
    }

    private static CompilerOptions getDefaultCompilerOptions() {
        CompilerOptions options = new CompilerOptions();
        options.docCommentSupport = true;
        options.complianceLevel = ClassFileConstants.JDK1_6;
        options.sourceLevel = ClassFileConstants.JDK1_6;
        options.targetJDK = ClassFileConstants.JDK1_6;
        return options;
    }

    public static Compilation compileFile(String filename) {
        CompilerOptions options = getDefaultCompilerOptions();
        Parser parser = createCommentRecorderParser(options);
        ICompilationUnit cu = createCompilationunit(getContentOfFile(TEST_DATA_BASE_DIR + filename), filename);
        CompilationResult compilationResult = createDefaultCompilationResult(cu, options);
        return new Compilation(parser.parse(cu, compilationResult), parser.scanner);
    }

    private static String getContentOfFile(String filename) {
        char[] b = new char[1024];
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(new File(filename));
            int n = reader.read(b);
            while (n > 0) {
                sb.append(b, 0, n);
                n = reader.read(b);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<Comment> extractComments(Compilation sCompilationUnit) {
        CommentCollector collector =
                new CommentCollector(sCompilationUnit.getCompilationUnit(), sCompilationUnit.getSource());
        collector.collect();
        return collector.getComments();
    }

    public static AbstractMethodDeclaration findMethod(CompilationUnitDeclaration cu, String methodName) {
        for (TypeDeclaration type : cu.types) {
            for (AbstractMethodDeclaration method : type.methods) {
                if (String.valueOf(method.selector).equals(methodName)) {
                    return method;
                }
            }
        }
        return null;
    }

}