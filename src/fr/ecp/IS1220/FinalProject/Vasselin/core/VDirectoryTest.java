package fr.ecp.IS1220.FinalProject.Vasselin.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class VDirectoryTest {

	@Test
	public void testVDirectoryStringListOfVDirectoryListOfVFile() {
		VDirectory dir1 = new VDirectory("dir1" );
		System.out.println();
		dir1.getDirectories();
		dir1.getFiles();
		dir1.getName();
		dir1.getSuccessors();
	}

	@Test
	public void testVDirectoryString() {
		VDirectory dir1 = new VDirectory("dir1");
		System.out.println();
		dir1.getDirectories();
		dir1.getFiles();
		dir1.getName();
		dir1.getSuccessors();
	}

	@Test
	public void testVDirectory() {
		VDirectory dir1 = new VDirectory("dir1");
		System.out.println();
		dir1.getDirectories();
		dir1.getFiles();
		dir1.getName();
		dir1.getSuccessors();
	}

	@Test
	public void testGetDirectories() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDirectories() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFiles() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSuccessors() {
		fail("Not yet implemented");
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

}
