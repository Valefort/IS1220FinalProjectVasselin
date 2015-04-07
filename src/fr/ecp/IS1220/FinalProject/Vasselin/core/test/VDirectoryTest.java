package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VDirectory;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;

public class VDirectoryTest {

	@Test
	public void testVDirectoryStringListOfVDirectoryListOfVFile() {
		VDirectory dir1 = new VDirectory();
		VDirectory dir2 = new VDirectory();
		ArrayList<VDirectory> list1 = new ArrayList<VDirectory>();
		list1.add(dir1);
		list1.add(dir2);

		VFile file1 = new VFile();
		VFile file2 = new VFile();
		ArrayList<VFile> list2 = new ArrayList<VFile>();
		list2.add(file1);
		list2.add(file2);

		VDirectory containerDir = new VDirectory("containerDir", list1, list2);
		assertTrue("containerDir".equals(containerDir.getName()) && list1.equals(containerDir.getDirectories()) && list2.equals(containerDir.getFiles()));
	}

	@Test
	public void testVDirectoryString() {
		VDirectory dir1 = new VDirectory("dir1");
		assertTrue(dir1.getName().equals("dir1") && dir1.getDirectories().isEmpty() && dir1.getFiles().isEmpty());
	}

	@Test
	public void testVDirectory() {
		VDirectory dir1 = new VDirectory();
		assertTrue(dir1.getName().equals("New directory") && dir1.getDirectories().isEmpty() && dir1.getFiles().isEmpty());
	}

	@Test
	public void testGetDirectories() {
		ArrayList<VDirectory> list1 = new ArrayList<VDirectory>();
		list1.add(new VDirectory("1"));
		list1.add(new VDirectory("2"));
		list1.add(new VDirectory("3"));		
		VDirectory dir1 = new VDirectory("dir1", list1, new ArrayList<VFile>());
		assertTrue(list1.equals(dir1.getDirectories()));
	}

	@Test
	public void testSetDirectories() {
		ArrayList<VDirectory> list1 = new ArrayList<VDirectory>();
		list1.add(new VDirectory("1"));
		list1.add(new VDirectory("2"));
		list1.add(new VDirectory("3"));		
		VDirectory dir1 = new VDirectory("dir1");
		dir1.setDirectories(list1);
		assertTrue(list1.equals(dir1.getDirectories()));
	}

	@Test
	public void testGetFiles() {
		ArrayList<VFile> list1 = new ArrayList<VFile>();
		list1.add(new VFile("1"));
		list1.add(new VFile("2"));
		list1.add(new VFile("3"));		
		VDirectory dir1 = new VDirectory("dir1",new ArrayList<VDirectory>(), list1);
		assertTrue(list1.equals(dir1.getFiles()));
	}

	@Test
	public void testSetFiles() {
		ArrayList<VFile> list1 = new ArrayList<VFile>();
		list1.add(new VFile("1"));
		list1.add(new VFile("2"));
		list1.add(new VFile("3"));		
		VDirectory dir1 = new VDirectory("dir1");
		dir1.setFiles(list1);
		assertTrue(list1.equals(dir1.getFiles()));
	}

	@Test
	public void testGetSuccessors() {
		ArrayList<VItem> successors = new ArrayList<VItem>();
		successors.add(new VFile("F1"));
		successors.add(new VFile("F2"));
		successors.add(new VFile("F3"));		
		successors.add(new VDirectory("D1"));
		successors.add(new VDirectory("D2"));
		successors.add(new VDirectory("D3"));	
		assertTrue(successors.equals(successors.getSuccessors()));
		
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	public void testContains() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		VDirectory dir1 = new VDirectory("dir1");
		assertTrue(dir1.getName().equals("dir1"));
	}

	@Test
	public void testSetName() {
		VDirectory dir1 = new VDirectory("dir1");
		System.out.println("The name of the directory equals to dir1 ? " + dir1.getName().equals("dir1"));
		dir1.setName("dirRenamed");
		System.out.println("The name of the directory equals to dirRenamed ? " + dir1.getName().equals("dirRenamed"));
		assertTrue(dir1.getName().equals("dirRenamed"));
	}

	@Test
	public void testExportVItem() {
		fail("Not yet implemented");
	}

}
