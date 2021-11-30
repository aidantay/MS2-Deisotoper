package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import task.DeisotopingTask;
import task.impl.DeisotopingTaskImpl;

public class SpectraTests {
	
	private static final String DIRECTORY = System.getProperty("user.dir") + "/resources/SpectraTests/";

	private static final double PPM_0         = 0;
	private static final double PPM_10        = 10;
	private static final double PPM_20        = 20;
	private static final double INTENSITY_0   = 0;
	private static final double INTENSITY_50  = 50;
	private static final double INTENSITY_100 = 100;
	
	private List<Double> getZeroPpmList() {
		return new ArrayList<Double>(Arrays.asList(PPM_0));
	}
	
	private List<Double> getDefaultPpmList() {
		return new ArrayList<Double>(Arrays.asList(PPM_10));
	}
	
	private List<Double> getZeroIntensityList() {
		return new ArrayList<Double>(Arrays.asList(INTENSITY_0));
	}
	
	private List<Double> getDefaultIntensityList() {
		return new ArrayList<Double>(Arrays.asList(INTENSITY_100));
	}
	
	private List<Double> getMultiplePpmList() {
		return new ArrayList<Double>(Arrays.asList(PPM_10, PPM_20));
	}

	private List<Double> getMultipleIntensityList() {
		return new ArrayList<Double>(Arrays.asList(INTENSITY_50, INTENSITY_100));
	}
	
	private String getTestFilePath(String inputFileName) {
		return DIRECTORY + inputFileName + ".mgf";
	}
	
	private static void deleteOutputFile(File outputFile) {
		if (outputFile.exists()) {
			outputFile.delete();
		}		
	}
	
	@Test
	public void testSingleSpectraDefaultPpmDefaultIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath1 = DIRECTORY + "single_spectra_01" + "_10m100i" + ".mgf"; 
		String outputFilePath2 = DIRECTORY + "single_spectra_01" + "_10mNoi" + ".mgf";
		String outputFilePath3 = DIRECTORY + "single_spectra_01" + "_removedPeaks" + "_10m100i" + ".mgf"; 
		String outputFilePath4 = DIRECTORY + "single_spectra_01" + "_removedPeaks" + "_10mNoi" + ".mgf"; 
		
		String expectedFilePath1 = DIRECTORY + "expected_single_spectra_01" + "_10m100i" + ".mgf"; 
		String expectedFilePath2 = DIRECTORY + "expected_single_spectra_01" + "_10mNoi" + ".mgf";
		String expectedFilePath3 = DIRECTORY + "expected_single_spectra_01" + "_removedPeaks" + "_10m100i" + ".mgf"; 
		String expectedFilePath4 = DIRECTORY + "expected_single_spectra_01" + "_removedPeaks" + "_10mNoi" + ".mgf"; 
		
		try {
			DeisotopingTask task = new DeisotopingTaskImpl(testFilePath, getDefaultPpmList(), getDefaultIntensityList(), true, true);
			task.run();
			
			File outputFile1    = new File(outputFilePath1);
			File outputFile2    = new File(outputFilePath2);
			File outputFile3    = new File(outputFilePath3);
			File outputFile4    = new File(outputFilePath4);
			
			File expectedFile1 = new File(expectedFilePath1);		
			File expectedFile2 = new File(expectedFilePath2);		
			File expectedFile3 = new File(expectedFilePath3);		
			File expectedFile4 = new File(expectedFilePath4);		

			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile1, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile2, "UTF-8"), FileUtils.readFileToString(outputFile2, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile3, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile4, "UTF-8"), FileUtils.readFileToString(outputFile4, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteOutputFile(new File(outputFilePath1));
		deleteOutputFile(new File(outputFilePath2));
		deleteOutputFile(new File(outputFilePath3));
		deleteOutputFile(new File(outputFilePath4));
	}
	
	@Test
	public void testSingleSpectraDefaultPpmZeroIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath1 = DIRECTORY + "single_spectra_01" + "_10m0i" + ".mgf"; 
		String outputFilePath2 = DIRECTORY + "single_spectra_01" + "_removedPeaks" + "_10m0i" + ".mgf"; 
		
		String expectedFilePath1 = DIRECTORY + "expected_single_spectra_01" + "_10m0i" + ".mgf"; 
		String expectedFilePath2 = DIRECTORY + "expected_single_spectra_01" + "_removedPeaks" + "_10m0i" + ".mgf"; 
		String expectedFilePath3 = DIRECTORY + "expected_single_spectra_01" + ".mgf"; 
		
		try {
			DeisotopingTask task = new DeisotopingTaskImpl(testFilePath, getDefaultPpmList(), getZeroIntensityList(), false, true);
			task.run();
			
			File outputFile1    = new File(outputFilePath1);
			File outputFile2    = new File(outputFilePath2);
			
			File expectedFile1 = new File(expectedFilePath1);		
			File expectedFile2 = new File(expectedFilePath2);
			File expectedFile3 = new File(expectedFilePath3);

			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile1, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile2, "UTF-8"), FileUtils.readFileToString(outputFile2, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteOutputFile(new File(outputFilePath1));
		deleteOutputFile(new File(outputFilePath2));
	}

	@Test
	public void testSingleSpectraZeroPpmDefaultIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath1 = DIRECTORY + "single_spectra_01" + "_0m100i" + ".mgf"; 
		String outputFilePath2 = DIRECTORY + "single_spectra_01" + "_removedPeaks" + "_0m100i" + ".mgf"; 
		
		String expectedFilePath1 = DIRECTORY + "expected_single_spectra_01" + "_0m100i" + ".mgf"; 
		String expectedFilePath2 = DIRECTORY + "expected_single_spectra_01" + "_removedPeaks" + "_0m100i" + ".mgf"; 
		String expectedFilePath3 = DIRECTORY + "expected_single_spectra_01" + ".mgf"; 

		try {
			DeisotopingTask task = new DeisotopingTaskImpl(testFilePath, getZeroPpmList(), getDefaultIntensityList(), false, true);
			task.run();
			
			File outputFile1    = new File(outputFilePath1);
			File outputFile2    = new File(outputFilePath2);
			
			File expectedFile1 = new File(expectedFilePath1);		
			File expectedFile2 = new File(expectedFilePath2);		
			File expectedFile3 = new File(expectedFilePath3);

			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(outputFile1, "UTF-8"), FileUtils.readFileToString(expectedFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(outputFile2, "UTF-8"), FileUtils.readFileToString(expectedFile2, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteOutputFile(new File(outputFilePath1));
		deleteOutputFile(new File(outputFilePath2));
	}
	
	@Test
	public void testSingleSpectraZeroPpmZeroIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath   = DIRECTORY + "single_spectra_01" + "_0m0i" + ".mgf"; 

		String expectedFilePath = DIRECTORY + "expected_single_spectra_01" + ".mgf"; 

		try {
			DeisotopingTask task = new DeisotopingTaskImpl(testFilePath, getZeroPpmList(), getZeroIntensityList(), false, false);
			task.run();
			
			File outputFile   = new File(outputFilePath);
			File expectedFile = new File(expectedFilePath);

			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile, "UTF-8"), FileUtils.readFileToString(outputFile, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteOutputFile(new File(outputFilePath));
	}

	@Test
	public void testSingleSpectraMultiplePpmMultipleIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath1 = DIRECTORY + "single_spectra_01" + "_10m50i" + ".mgf"; 
		String outputFilePath2 = DIRECTORY + "single_spectra_01" + "_10m100i" + ".mgf"; 
		String outputFilePath3 = DIRECTORY + "single_spectra_01" + "_20m50i" + ".mgf"; 
		String outputFilePath4 = DIRECTORY + "single_spectra_01" + "_20m100i" + ".mgf"; 
		
		String expectedFilePath1 = DIRECTORY + "expected_single_spectra_01" + "_10m50i" + ".mgf"; 
		String expectedFilePath2 = DIRECTORY + "expected_single_spectra_01" + "_10m100i" + ".mgf"; 
		String expectedFilePath3 = DIRECTORY + "expected_single_spectra_01" + "_20m50i" + ".mgf"; 
		String expectedFilePath4 = DIRECTORY + "expected_single_spectra_01" + "_20m100i" + ".mgf"; 
	
		try {
			DeisotopingTaskImpl task = new DeisotopingTaskImpl(testFilePath, getMultiplePpmList(), getMultipleIntensityList(), false, false);
			task.run();

			File outputFile1   = new File(outputFilePath1);
			File outputFile2   = new File(outputFilePath2);
			File outputFile3   = new File(outputFilePath3);
			File outputFile4   = new File(outputFilePath4);
			
			File expectedFile1 = new File(expectedFilePath1);		
			File expectedFile2 = new File(expectedFilePath2);
			File expectedFile3 = new File(expectedFilePath3);
			File expectedFile4 = new File(expectedFilePath4);
			
			
			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile1, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile2, "UTF-8"), FileUtils.readFileToString(outputFile2, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile3, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile4, "UTF-8"), FileUtils.readFileToString(outputFile4, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		deleteOutputFile(new File(outputFilePath1));
		deleteOutputFile(new File(outputFilePath2));
		deleteOutputFile(new File(outputFilePath3));
		deleteOutputFile(new File(outputFilePath4));
	}

	@Test
	public void testSingleSpectraMultiplePpmZeroIntensity() {
		String testFilePath = getTestFilePath("single_spectra_01");
		
		String outputFilePath1 = DIRECTORY + "single_spectra_01" + "_10m0i" + ".mgf"; 
		String outputFilePath2 = DIRECTORY + "single_spectra_01" + "_20m0i" + ".mgf"; 
		
		String expectedFilePath1 = DIRECTORY + "expected_single_spectra_01" + "_10m0i" + ".mgf"; 
		String expectedFilePath2 = DIRECTORY + "expected_single_spectra_01" + "_20m0i" + ".mgf"; 
		String expectedFilePath3 = DIRECTORY + "expected_single_spectra_01" + ".mgf"; 

		try {
			DeisotopingTaskImpl task = new DeisotopingTaskImpl(testFilePath, getMultiplePpmList(), getZeroIntensityList(), false, false);
			task.run();

			File outputFile1   = new File(outputFilePath1);
			File outputFile2   = new File(outputFilePath2);
			
			File expectedFile1 = new File(expectedFilePath1);		
			File expectedFile2 = new File(expectedFilePath2);
			File expectedFile3 = new File(expectedFilePath3);
			
			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile1, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile2, "UTF-8"), FileUtils.readFileToString(outputFile2, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile1, "UTF-8"));
			assertEquals(FileUtils.readFileToString(expectedFile3, "UTF-8"), FileUtils.readFileToString(outputFile2, "UTF-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		deleteOutputFile(new File(outputFilePath1));
		deleteOutputFile(new File(outputFilePath2));
	}

	@Test
	public void testSingleSpectraSciNotation() {
		String testFilePath = getTestFilePath("single_spectra_02");
		
		String outputFilePath = DIRECTORY + "single_spectra_02" + "_0m0i" + ".mgf"; 
		
		String expectedFilePath = DIRECTORY + "expected_single_spectra_02" + ".mgf"; 

		try {
			DeisotopingTask task = new DeisotopingTaskImpl(testFilePath, getZeroPpmList(), getZeroIntensityList(), false, false);
			task.run();
			
			File outputFile    = new File(outputFilePath);

			File expectedFile = new File(expectedFilePath);		

			// Deisotoped file (outputFile1) should be the same as the test file and therefore, removed peaks file (outputFile2) should have no peaks
			assertEquals(FileUtils.readFileToString(expectedFile, "UTF-8"), FileUtils.readFileToString(outputFile, "UTF-8"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteOutputFile(new File(outputFilePath));
	}

}
