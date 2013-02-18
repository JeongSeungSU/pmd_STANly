package net.sourceforge.pmd.lang.java.rule.stanly.Util;

public final class MacroFunctions {

	/**
	 *
	 * @since 2013. 2. 15.오전 2:35:57
	 * @author JeongSeungsu
	 * @param obj
	 * @return NULL->true 아니면 false
	 */
	final public static boolean NULLChecking(Object obj)
	{
		if(obj == null)
			return false;
		else
			return true;
	}
}
