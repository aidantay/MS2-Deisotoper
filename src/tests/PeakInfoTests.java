package tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.PeakInfo;
import model.PeakList;
import model.PeakSet;
import model.impl.PeakInfoImpl;
import model.impl.PeakListImpl;
import model.impl.PeakSetImpl;

public class PeakInfoTests {
	
	private PeakInfo p1;
	private PeakInfo p2;
	private PeakInfo p3;
	private PeakInfo p4;
	
	@Before
	public void setUp() {
		this.p1 = new PeakInfoImpl(504.7857, 2310.8);
		this.p2 = new PeakInfoImpl(505.7915, 1096.2);
		this.p3 = new PeakInfoImpl(522.7976, 8897.6);
		this.p4 = new PeakInfoImpl(523.8019, 4637.8);
	}
	
	private PeakList createNewList() {
		PeakList peakList = new PeakListImpl();
		peakList.add(p1);
		peakList.add(p2);
		peakList.add(p3);
		peakList.add(p4);
		return peakList;
	}
	
	@Test
	public void testAdd() {
		PeakList peakList = createNewList();
		assertEquals(4, peakList.size());
		assertEquals(p1, peakList.get(0));
		assertEquals(p2, peakList.get(1));
		assertEquals(p3, peakList.get(2));
		assertEquals(p4, peakList.get(3));
	}
	
	@Test
	public void testEquality() {
		PeakList peakList1 = createNewList();
		PeakList peakList2 = createNewList();
		PeakList peakList3 = new PeakListImpl(peakList1);

		assertEquals(peakList1, peakList2);
		assertEquals(peakList1, peakList3);
		assertEquals(peakList2, peakList3);
		
		Set<PeakList> distributions = new HashSet<PeakList>();
		distributions.add(peakList1);
		distributions.add(peakList2);
		assertEquals(1, distributions.size());
		distributions.add(peakList3);
		assertEquals(1, distributions.size());
	}
	
	@Test
	public void testRemove() {		
		PeakList peakList = createNewList();
		peakList.remove(p2);
		peakList.remove(p3);
		assertEquals(2, peakList.size());
		assertEquals(p1, peakList.get(0));
		assertEquals(p4, peakList.get(1));
	}

	@Test
	public void testRemoveAll() {
		PeakList peakList = createNewList();
		PeakSet peakSubSet = new PeakSetImpl();
		peakSubSet.add(p2);
		peakSubSet.add(p3);
		
		peakList.removeAll(peakSubSet);
		assertEquals(2, peakList.size());
		assertEquals(p1, peakList.get(0));
		assertEquals(p4, peakList.get(1));
	}
	
	@Test
	public void testRetainAll() {
		PeakList peakList = createNewList();
		PeakSet peakSubSet = new PeakSetImpl();
		peakSubSet.add(p2);
		peakSubSet.add(p3);
		
		peakList.retainAll(peakSubSet);
		assertEquals(2, peakList.size());
		assertEquals(p2, peakList.get(0));
		assertEquals(p3, peakList.get(1));
	}
	
}

