package net.sourceforge.pmd;

import net.sourceforge.pmd.benchmark.Benchmark;
import net.sourceforge.pmd.benchmark.Benchmarker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public final class RulesetsFactoryUtils {

	//private static final Log LOG = LogFactory.getLog(RulesetsFactoryUtils.class);
	private static final Logger LOG = Logger.getLogger(RulesetsFactoryUtils.class.getName());

	private RulesetsFactoryUtils() {}

	public static RuleSets getRuleSets(String rulesets, RuleSetFactory factory, long loadRuleStart) {
		RuleSets ruleSets = null;

		try {
			ruleSets = factory.createRuleSets(rulesets);
			factory.setWarnDeprecated(false);
			printRuleNamesInDebug(ruleSets);
			long endLoadRules = System.nanoTime();
			Benchmarker.mark(Benchmark.LoadRules, endLoadRules - loadRuleStart, 0);
		} catch (RuleSetNotFoundException rsnfe) {
			LOG.debug("Ruleset not found", rsnfe);
			throw new IllegalArgumentException(rsnfe);
		}
		return ruleSets;
	}

	public static RuleSetFactory getRulesetFactory(PMDConfiguration configuration) {
		RuleSetFactory ruleSetFactory = new RuleSetFactory();
		ruleSetFactory.setMinimumPriority(configuration.getMinimumPriority());
		ruleSetFactory.setWarnDeprecated(true);
		return ruleSetFactory;
	}

	/**
	 * If in debug modus, print the names of the rules.
	 *
	 * @param rulesets     the RuleSets to print
	 */
	private static void printRuleNamesInDebug(RuleSets rulesets) {
		if (LOG.isDebugEnabled()) {
			for (Rule r : rulesets.getAllRules()) {
				LOG.debug("Loaded rule " + r.getName());
			}
		}
	}
}
