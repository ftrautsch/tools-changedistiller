package org.evolizer.changedistiller.structuredifferencing.java;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ASTNode;

/**
 * Node for Java structure differencing.
 * 
 * @author Beat Fluri
 */
public class JavaStructureNode {

    private Type fType;
    private String fName;
    private ASTNode fASTNode;
    private List<JavaStructureNode> fChildren;

    /**
     * Creates a new Java structure node
     * 
     * @param type
     *            of the node
     * @param name
     *            of the node
     * @param astNode
     *            representing the structure node
     */
    public JavaStructureNode(Type type, String name, ASTNode astNode) {
        fType = type;
        fName = name;
        fASTNode = astNode;
        fChildren = new LinkedList<JavaStructureNode>();
    }

    /**
     * Adds a new {@link JavaStructureNode} child.
     * 
     * @param node
     *            to add as child
     */
    public void addChild(JavaStructureNode node) {
        fChildren.add(node);
    }

    public List<JavaStructureNode> getChildren() {
        return fChildren;
    }

    @Override
    public String toString() {
        return fType.name() + ": " + fName;
    }

    /**
     * Java structure node types.
     * 
     * @author Beat Fluri
     */
    public enum Type {
        CU,
        FIELD,
        CONSTRUCTOR,
        METHOD,
        INTERFACE,
        CLASS,
        ANNOTATION,
        ENUM
    }

    public Type getType() {
        return fType;
    }

    public String getName() {
        return fName;
    }

}