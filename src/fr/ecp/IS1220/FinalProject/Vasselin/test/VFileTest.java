package fr.ecp.IS1220.FinalProject.Vasselin.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemNotFoundException;

public class VFileTest {
	
	private boolean compareByteArray(byte[] a, byte[] b){
		if(a.length==b.length){
			for(int i=0;i<a.length;i++){
				if(a[i]!=b[i])
					return false;
			}
			return true;
		}
		else
			return false;
	}

	@Test
	public void testVFileStringByteArray() {
		byte[] a = new byte[10];
		for(int i=0;i<10;i++)
			a[i]=(byte)(i*i);
		VFile f = new VFile("Yolo", a);
		
		assertEquals("Yolo", f.getName());
		assertTrue(compareByteArray(a, f.getData()));
	}

	@Test
	public void testVFileString() {
		VFile test = new VFile("azerty is not qwerty");
		assertEquals("azerty is not qwerty", test.getName());
		assertTrue(compareByteArray(new byte[0], test.getData()));
	}

	@Test
	public void testVFile() {
		VFile test = new VFile();
		assertEquals("New file", test.getName());
		assertTrue(compareByteArray(new byte[0], test.getData()));
	}

	@Test
	public void testGetData() {
		Random r = new Random();
		byte[] data = new byte[256];
		r.nextBytes(data);
		byte[] witness = data.clone();
		VFile test = new VFile("yolo", data);
		
		assertTrue(compareByteArray(witness, test.getData()));
	}

	@Test
	public void testSetData() {
		Random r = new Random();
		byte[] data = new byte[256];
		r.nextBytes(data);
		byte[] witness = data.clone();
		VFile test = new VFile();
		
		test.setData(data);
		
		assertTrue(compareByteArray(witness, test.getData()));
	}

	@Test
	public void testGetSuccessors() {
		assertTrue((new VFile()).getSuccessors().isEmpty());
	}

	@Test
	public void testAdd() {
		//add is supposed NOT to do anything...
		//Therefore, there is nothing to test...
		assertTrue(true);
	}

	@Test(expected = VItemNotFoundException.class)
	public void testRemove() throws VItemNotFoundException {
		VFile a = new VFile();
		VFile b = new VFile();
		a.remove(b);
		fail("No exception raised.");
	}

	@Test
	public void testContains() {
		VFile a = new VFile();
		VFile b = new VFile();
		assertFalse(a.contains(b));
	}

	@Test
	public void testGetSize() {
		Random r = new Random();
		int s = r.nextInt()%2048;
		if(s<0)
			s=-s;
		byte[] data= new byte[s];
		r.nextBytes(data);
		VFile test = new VFile("yolo",data);
		assertEquals((long)s,test.getSize());
	}

	@Test
	public void testGetName() {
		VFile test = new VFile("trololo");
		assertEquals("trololo",test.getName());
	}

	@Test
	public void testSetName() {
		VFile test = new VFile();
		test.setName("This is a test.");
		assertEquals("This is a test.",test.getName());
	}
	
	@Test
	public void testExportVItem() {
		fail("Not yet implemented");
	}

}
