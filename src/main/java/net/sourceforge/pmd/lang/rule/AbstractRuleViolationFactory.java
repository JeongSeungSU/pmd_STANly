package net.sourceforge.pmd.lang.rule;

import java.text.MessageFormat;

import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.rule.Violation;
import net.sourceforge.pmd.lang.java.rule.ViolationController;
import net.sourceforge.pmd.util.StringUtil;

public abstract class AbstractRuleViolationFactory implements RuleViolationFactory {

	private static final Object[] NO_ARGS = new Object[0];

	private String cleanup(String message, Object[] args) {

		if (message != null) {
			// Escape PMD specific variable message format, specifically the {
			// in the ${, so MessageFormat doesn't bitch.
			final String escapedMessage = StringUtil.replaceString(message,	"${", "$'{'");
			return MessageFormat.format(escapedMessage,	args != null ? args : NO_ARGS);
		} else {
			return message;
		}
	}
	
	public void addViolation(RuleContext ruleContext, Rule rule, Node node,	String message, Object[] args) {
		
		String formattedMessage = cleanup(message, args);
		
		if(rule.getRuleSetName().equals("Naming"))
		{
			ViolationController.AddViolation(Violation.NAMING, ruleContext, node, formattedMessage);
		}
		else if(rule.getRuleSetName().equals("Basic") || rule.getRuleSetName().equals("Unnecessary") || rule.getRuleSetName().equals("Empty Code"))
		{
			ViolationController.AddViolation(Violation.BASIC, ruleContext, node, formattedMessage);
		}
		
		ruleContext.getReport().addRuleViolation(createRuleViolation(rule, ruleContext, node, formattedMessage));

	}

	public void addViolation(RuleContext ruleContext, Rule rule, Node node,	String message, int beginLine, int endLine, Object[] args) {
		
		String formattedMessage = cleanup(message, args);
		
		ruleContext.getReport().addRuleViolation(
				createRuleViolation(rule, ruleContext, node, formattedMessage, beginLine, endLine)
				);
	}
	
	protected abstract RuleViolation createRuleViolation(Rule rule,	RuleContext ruleContext, Node node, String message);
	
	protected abstract RuleViolation createRuleViolation(Rule rule,	RuleContext ruleContext, Node node, String message, int beginLine, int endLine);
}
