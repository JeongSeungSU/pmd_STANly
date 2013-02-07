package net.sourceforge.pmd.lang.java.ast;

public abstract class AbstractJavaAccessTypeNode extends AbstractJavaAccessNode implements TypeNode {

	public AbstractJavaAccessTypeNode(int i) {
		super(i);
	}

	public AbstractJavaAccessTypeNode(JavaParser parser, int i) {
		super(parser, i);
	}

	private Class<?> type;

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
	
	private String qualifiedName;
	
	public void setQualifiedName(String name)
	{
		qualifiedName = name;
	}
	
	public String getQualifiedName()
	{
		return qualifiedName;
	}
}
