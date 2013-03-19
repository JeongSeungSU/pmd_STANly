package net.sourceforge.pmd.lang.java.rule.stanly.relation;

/**
 * 각 도메인사이의 관계를 나타냄...
 * @since 2013. 1. 29.오후 11:18:42
 * @author JeongSeungsu
 */
public enum Relations {
	EXTENDS,
	IMPLEMENTS,
	CONTAINS,
	RETURNS,
	HASPARAM,
	THROWS,
	CALLS,
	ACCESSES,
	ISOFTYPE,
	REFERENCES,
	UNKNOWN
}
