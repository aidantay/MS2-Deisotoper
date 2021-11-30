package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import task.impl.DeisotopingTaskImpl;

public class MS2Deisotoper {
	
	public static void main(String[] args) throws Exception {		
		Options options         = generateOptions();
		CommandLine commandLine = generateCommandLine(options, args);

		List<String> spectraFilePathList = getFilePathList(commandLine, "f");
		List<Double> ppmList             = getPpmList(commandLine, "m");
		List<Double> intensityList       = getIntensityList(commandLine, "i");
		
		for (String spectraFilePath : spectraFilePathList) {
			// Add a deisotoping task to the list of tasks
			DeisotopingTaskImpl task 
				= new DeisotopingTaskImpl(spectraFilePath, ppmList, intensityList, false, false);
			task.run();	// Set withoutIntensity and removedPeaks to false for now
		}
	}
	
	private static Options generateOptions() {
		Option fileOption      = Option.builder("f")
								 .required()
								 .hasArgs()
								 .argName("MS2SpectraFile")
								 .desc("Input files")
								 .build();
								
		Option massOption      = Option.builder("m")
								 .hasArgs()
								 .argName("massTolerance")
								 .desc("Mass tolerance (default: 10)")
								 .build();
		
		Option intensityOption = Option.builder("i")
								 .hasArgs()
								 .argName("relativeIntensity")
								 .desc("Relative intensity (default: 100)")
								 .build();

		Options options = new Options();
		options.addOption(fileOption);
		options.addOption(massOption);
		options.addOption(intensityOption);		
		return options;
	}
	
	private static CommandLine generateCommandLine(Options options, String[] args) {
		CommandLineParser cmdLineParser = new DefaultParser();  
		CommandLine commandLine = null;
		
		try {  
			commandLine = cmdLineParser.parse(options, args);
			
		} catch (ParseException pe) {
			System.err.println(pe);
			printUsage(options);
			System.exit(1);
		}  
		return commandLine;
	}
	
	private static void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("MS2-Deisotoper", options, true);		
	}

	private static List<String> getFilePathList(CommandLine commandLine, String shortOpt) {
		String[] filePaths        = commandLine.getOptionValues(shortOpt);
		List<String> filePathList = Arrays.asList(filePaths);
		return filePathList;
	}
	
	private static List<Double> getPpmList(CommandLine commandLine, String shortOpt) {
		String[] ppmField = commandLine.getOptionValues(shortOpt);
		if (ppmField == null) {
			ppmField    = new String[1];
			ppmField[0] = "10";
		}
		return getParameterList(ppmField);
	}
	
	private static List<Double> getIntensityList(CommandLine commandLine, String shortOpt) {
		String[] intensityField = commandLine.getOptionValues(shortOpt);
		if (intensityField == null) {
			intensityField    = new String[1];
			intensityField[0] = "100";
		}
		return getParameterList(intensityField);
	}
	
	private static List<Double> getParameterList(String[] parameterField) {
		List<Double> parameterList = new ArrayList<Double>();
		
		for (String parameter : parameterField) {
			double parameterValue = Double.valueOf(parameter);
			parameterList.add(parameterValue);
		}
		
		return parameterList;
	}

}
