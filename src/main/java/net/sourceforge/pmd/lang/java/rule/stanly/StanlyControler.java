package net.sourceforge.pmd.lang.java.rule.stanly;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSetFactory;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RulesetsFactoryUtils;
import net.sourceforge.pmd.benchmark.Benchmark;
import net.sourceforge.pmd.benchmark.Benchmarker;
import net.sourceforge.pmd.cli.PMDParameters;
import net.sourceforge.pmd.lang.Language;
import net.sourceforge.pmd.lang.LanguageFilenameFilter;
import net.sourceforge.pmd.lang.LanguageVersion;
import net.sourceforge.pmd.lang.LanguageVersionDiscoverer;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.processor.MonoThreadProcessor;
import net.sourceforge.pmd.processor.MultiThreadProcessor;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.util.FileUtil;
import net.sourceforge.pmd.util.IOUtil;
import net.sourceforge.pmd.util.SystemUtils;
import net.sourceforge.pmd.util.datasource.DataSource;

public class StanlyControler {

	static private List<DomainRelation> RelationList;
	static private ElementNode	RootNode;
	static private String RootPath = "";
	
	
	public static String getRootPath() {
		return RootPath;
	}

	public static void setRootPath(String rootPath) {
		RootPath = rootPath;
	}

	public static List<DomainRelation> getRelationList() {
		return RelationList;
	}

	public static void setRelationList(List<DomainRelation> relationList) {
		RelationList = relationList;
	}

	public static ElementNode getRootNode() {
		return RootNode;
	}

	public static void setRootNode(ElementNode rootNode) {
		RootNode = rootNode;
	}

	
	public StanlyControler()
	{
		RelationList = null;
		RootNode 	 = null;
	}
	
	public static StanlyAnalysisData StartAnalysis(String Path)
	{
		StanlyControler.setRootPath(Path);
		PMDParameters pmdparameter = new PMDParameters();
		PMDConfiguration configuration = transformParametersIntoConfiguration(pmdparameter,Path);
		
		doPMD(configuration);
		
		StanlyAnalysisData analysisdata = new StanlyAnalysisData();
		analysisdata.setRelationList(StanlyControler.getRelationList());
		analysisdata.setRootNode(StanlyControler.getRootNode());
		
		return analysisdata;
	}
	
	public static void doPMD(PMDConfiguration configuration) {

		// Load the RuleSets
		long startLoadRules = System.nanoTime();
		RuleSetFactory ruleSetFactory = RulesetsFactoryUtils
				.getRulesetFactory(configuration);

		RuleSets ruleSets = RulesetsFactoryUtils.getRuleSets(
				configuration.getRuleSets(), ruleSetFactory, startLoadRules);
		if (ruleSets == null)
			return;

		Set<Language> languages = getApplicableLanguages(configuration, ruleSets);
		List<DataSource> files = getApplicableFiles(configuration, languages);

		long reportStart = System.nanoTime();
		try {			
			Renderer renderer = configuration.createRenderer();
			List<Renderer> renderers = new LinkedList<Renderer>();
			renderers.add(renderer);

			renderer.setWriter(IOUtil.createWriter(configuration.getReportFile()));
			renderer.start();

			Benchmarker.mark(Benchmark.Reporting, System.nanoTime() - reportStart, 0);

			RuleContext ctx = new RuleContext();

			processFiles(configuration, ruleSetFactory, files, ctx, renderers);

			reportStart = System.nanoTime();
			renderer.end();
			renderer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public static void processFiles(final PMDConfiguration configuration,
			final RuleSetFactory ruleSetFactory, final List<DataSource> files,
			final RuleContext ctx, final List<Renderer> renderers) {

		sortFiles(configuration, files);

		/*
		 * Check if multithreaded support is available. ExecutorService can also be
		 * disabled if threadCount is not positive, e.g. using the "-threads 0"
		 * command line option.
		 */
		if (SystemUtils.MT_SUPPORTED && configuration.getThreads() > 0) {
			new MultiThreadProcessor(configuration).processFiles(ruleSetFactory, files, ctx, renderers);
		} else {
			new MonoThreadProcessor(configuration).processFiles(ruleSetFactory, files, ctx, renderers);
		}
	}
	
	private static void sortFiles(final PMDConfiguration configuration, final List<DataSource> files) {
		if (configuration.isStressTest()) {
			// randomize processing order
			Collections.shuffle(files);
		} else {
			final boolean useShortNames = configuration.isReportShortNames();
			final String inputPaths = configuration.getInputPaths();
			Collections.sort(files, new Comparator<DataSource>() {
				public int compare(DataSource left, DataSource right) {
					String leftString = left.getNiceFileName(useShortNames, inputPaths);
					String rightString = right.getNiceFileName(useShortNames, inputPaths);
					return leftString.compareTo(rightString);
				}
			});
		}
	}
	
	
	public static List<DataSource> getApplicableFiles(
			PMDConfiguration configuration, Set<Language> languages) {
		long startFiles = System.nanoTime();
		LanguageFilenameFilter fileSelector = new LanguageFilenameFilter(languages);
		List<DataSource> files = FileUtil.collectFiles(
				configuration.getInputPaths(), fileSelector);
		long endFiles = System.nanoTime();
		Benchmarker.mark(Benchmark.CollectFiles, endFiles - startFiles, 0);
		return files;
	}
	
	private static Set<Language> getApplicableLanguages(PMDConfiguration configuration, RuleSets ruleSets) {
		Set<Language> languages = new HashSet<Language>();
		LanguageVersionDiscoverer discoverer = configuration.getLanguageVersionDiscoverer();
		
		for (Rule rule : ruleSets.getAllRules()) {
			Language language = rule.getLanguage();
			if (languages.contains(language))
				continue;
			LanguageVersion version = discoverer.getDefaultLanguageVersion(language);
			if (RuleSet.applies(rule, version)) {
				languages.add(language);
			}
		}
		return languages;
	}
	
	public static PMDConfiguration transformParametersIntoConfiguration(PMDParameters params,String path)
	{
    	PMDConfiguration configuration = new PMDConfiguration();
    	configuration.setInputPaths(path);
    	configuration.setReportFormat("text");
    	configuration.setBenchmark(params.isBenchmark());
    	configuration.setDebug(params.isDebug());
    	configuration.setMinimumPriority(params.getMinimumPriority());
    	configuration.setReportFile(params.getReportfile());
    	configuration.setReportProperties(params.getProperties());
    	configuration.setReportShortNames(params.isShortnames());
    	configuration.setRuleSets("java-stanly,java-basic,java-naming");
    	configuration.setShowSuppressedViolations(params.isShowsuppressed());
    	configuration.setSourceEncoding(params.getEncoding());
    	configuration.setStressTest(params.isStress());
    	configuration.setSuppressMarker(params.getSuppressmarker());
    	configuration.setThreads(params.getThreads()); 
    	for ( LanguageVersion language : LanguageVersion.findVersionsForLanguageTerseName( params.getLanguage() ) ) {
        	configuration.getLanguageVersionDiscoverer().setDefaultLanguageVersion(language.getLanguage().getVersion(params.getVersion()));    		
    	}
        try {
            configuration.prependClasspath(params.getAuxclasspath());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid auxiliary classpath: " + e.getMessage(), e);
        }
		return configuration;
	}
}
