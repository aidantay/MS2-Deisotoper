package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.PeakInfo;
import model.PeakList;
import model.impl.PeakInfoImpl;
import model.impl.PeakListImpl;
import task.PeakAnalyser;
import task.impl.PeakAnalyserImpl;

public class PeakAnalyserTests {
	
	private PeakList peakList;
	private PeakList isotopicDistribution1;
	private PeakList isotopicDistribution2;
	private PeakList isotopicDistribution3;

	@Before
	public void setUp() {
		PeakInfo p1 = new PeakInfoImpl(504.7857, 2310.8);
		PeakInfo p2 = new PeakInfoImpl(505.7915, 1096.2);
		PeakInfo p3 = new PeakInfoImpl(522.7976, 8897.6);
		PeakInfo p4 = new PeakInfoImpl(523.8019, 4637.8);
		PeakInfo p5 = new PeakInfoImpl(706.628, 645.1);
		PeakInfo p6 = new PeakInfoImpl(723.6672, 898.8);
		PeakInfo p7 = new PeakInfoImpl(986.1047, 588.9);
		
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
	public void testMemory() {
		PeakAnalyser peakAnalyser = new PeakAnalyserImpl(peakList);
		peakAnalyser.analysePeakList();
		assertEquals(peakList.size() - 1, peakAnalyser.getNeutronTable().getMaxRowSize());
		assertEquals(peakList.size() - 1, peakAnalyser.getHalfNeutronTable().getMaxRowSize());
		assertEquals(peakList.size() - 1, peakAnalyser.getIntensityTable().getMaxRowSize());
	}

	@Test
	public void testMassShift() {
		PeakAnalyser peakAnalyser = new PeakAnalyserImpl(peakList);
		peakAnalyser.analysePeakList();
		
		assertEquals(4.8439, peakAnalyser.getNeutronTable().getValue(0, 1), 0.0001);
		assertEquals(1.8136, peakAnalyser.getNeutronTable().getValue(2, 3), 0.0001);
		assertEquals(997.6995, peakAnalyser.getHalfNeutronTable().getValue(0, 1), 0.0001);
		assertEquals(960.4924, peakAnalyser.getHalfNeutronTable().getValue(2, 3), 0.0001);
	}

	@Test
	public void testIntensity() {
		PeakAnalyser peakAnalyser = new PeakAnalyserImpl(peakList);
		peakAnalyser.analysePeakList();
		
		assertEquals(47.4381, peakAnalyser.getIntensityTable().getValue(0, 1), 0.0001);
		assertEquals(52.1241, peakAnalyser.getIntensityTable().getValue(2, 3), 0.0001);
	}
	
}
