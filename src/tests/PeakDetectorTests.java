package tests;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.PeakInfo;
import model.PeakList;
import model.PeakSet;
import model.impl.PeakInfoImpl;
import model.impl.PeakListImpl;
import task.PeakAnalyser;
import task.PeakDetector;
import task.impl.PeakAnalyserImpl;
import task.impl.PeakDetectorImpl;

public class PeakDetectorTests {
	
	private PeakInfo p1;
	private PeakInfo p2;
	private PeakInfo p3;
	private PeakInfo p4;
	private PeakInfo p5;
	private PeakInfo p6;
	private PeakInfo p7;
		
	private PeakList peakList;
	private PeakList isotopicDistribution1;
	private PeakList isotopicDistribution2;
	private PeakList isotopicDistribution3;

	@Before
	public void setUp() {
		p1 = new PeakInfoImpl(504.7857, 2310.8);
		p2 = new PeakInfoImpl(505.7915, 1096.2);
		p3 = new PeakInfoImpl(522.7976, 8897.6);
		p4 = new PeakInfoImpl(523.8019, 4637.8);
		p5 = new PeakInfoImpl(706.628, 645.1);
		p6 = new PeakInfoImpl(723.6672, 898.8);
		p7 = new PeakInfoImpl(986.1047, 588.9);
		
		peakList = new PeakListImpl();
		peakList.add(p1);
		peakList.add(p2);
		peakList.add(p3);
		peakList.add(p4);
		peakList.add(p5);
		peakList.add(p6);
		peakList.add(p7);
		
		isotopicDistribution1 = new PeakListImpl();
		isotopicDistribution1.add(p1);
		isotopicDistribution1.add(p2);
		
		isotopicDistribution2 = new PeakListImpl();
		isotopicDistribution2.add(p3);
		isotopicDistribution2.add(p4);
		
		isotopicDistribution3 = new PeakListImpl();
		isotopicDistribution3.add(p5);
		isotopicDistribution3.add(p6);
		isotopicDistribution3.add(p7);
	}
	
	@Test
	public void testIsotopicClusters() {	// Set withoutIntensity and removedPeaks to false for now
		PeakAnalyser peakAnalyser = new PeakAnalyserImpl(peakList);
		peakAnalyser.analysePeakList();

		PeakDetector peakDetector      = new PeakDetectorImpl(peakAnalyser, 10, 100);
		Set<PeakList> isotopicClusters = peakDetector.getIsotopicClusters(peakAnalyser.getNeutronTable());
		
		assertEquals(true, isotopicClusters.contains(isotopicDistribution1));
		assertEquals(true, isotopicClusters.contains(isotopicDistribution2));
		assertEquals(false, isotopicClusters.contains(isotopicDistribution3));
	}
	
	@Test
	public void testIsotopicPeaks() {
		PeakAnalyser peakAnalyser = new PeakAnalyserImpl(peakList);
		peakAnalyser.analysePeakList();

		PeakDetector peakDetector      = new PeakDetectorImpl(peakAnalyser, 10, 100);
		Set<PeakList> isotopicClusters = peakDetector.getIsotopicClusters(peakAnalyser.getNeutronTable());
		PeakSet isotopicPeaksToRemove  = peakDetector.getIsotopicPeaks(isotopicClusters);

		assertEquals(true, isotopicPeaksToRemove.contains(p2));
		assertEquals(true, isotopicPeaksToRemove.contains(p4));
		assertEquals(false, isotopicPeaksToRemove.contains(p5));
	}
	
}
