package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

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
		
		ArrayList<VDirectory> dirList = new ArrayList<VDirectory>();
		dirList.add(new VDirectory("D1"));
		dirList.add(new VDirectory("D2"));
		dirList.add(new VDirectory("D3"));
		
		ArrayList<VFile> fileList = new ArrayList<VFile>();
		fileList.add(new VFile("F1"));
		fileList.add(new VFile("F2"));
		fileList.add(new VFile("F3"));		

		ArrayList<VItem> successors = new ArrayList<VItem>();
		
		successors.addAll(dirList);
		successors.addAll(fileList);

		VDirectory containerDir = new VDirectory("containerDir", dirList, fileList);
		
		assertTrue(successors.equals(containerDir.getSuccessors()));
		
	}

	@Test
	public void testAdd() {
		VDirectory dir1 = new VDirectory("D1");
		VDirectory dir2 = new VDirectory("D2");	
		VFile file1 = new VFile("F1");
		VFile file2 = new VFile("F2");

		VDirectory containerDir = new VDirectory("containerDir");
		
		containerDir.add(dir1);
		containerDir.add(dir2);
		
		containerDir.add(file1);
		containerDir.add(file2);

		assertTrue(containerDir.contains(dir1) && containerDir.contains(dir2) && containerDir.contains(file1) && containerDir.contains(file2));
		
	}

	@Test
	public void testRemove() {
		ArrayList<VDirectory> dirList = new ArrayList<VDirectory>();
		VDirectory dir1 = new VDirectory("D1");
		VDirectory dir2 = new VDirectory("D2");	
		dirList.add(dir1);
		dirList.add(dir2);
		
		ArrayList<VFile> fileList = new ArrayList<VFile>();
		VFile file1 = new VFile("F1");
		VFile file2 = new VFile("F2");
		fileList.add(file1);
		fileList.add(file2);

		VDirectory containerDir = new VDirectory("containerDir", dirList, fileList);

		try{
			containerDir.remove(dir1);
			containerDir.remove(file1);
		}
		catch(Exception e){fail("File to remove not found.");}
		
		assertTrue(!containerDir.contains(dir1) && containerDir.contains(dir2) && !containerDir.contains(file1) && containerDir.contains(file2));
	}

	@Test
	public void testContains() {
		ArrayList<VDirectory> dirList = new ArrayList<VDirectory>();
		VDirectory dir1 = new VDirectory("D1");
		VDirectory dir2 = new VDirectory("D2");	
		dirList.add(dir1);
		dirList.add(dir2);
		
		ArrayList<VFile> fileList = new ArrayList<VFile>();
		VFile file1 = new VFile("F1");
		VFile file2 = new VFile("F2");
		fileList.add(file1);
		fileList.add(file2);

		VDirectory containerDir = new VDirectory("containerDir", dirList, fileList);
		
		assertTrue(containerDir.contains(dir1) && containerDir.contains(dir2) && containerDir.contains(file1) && containerDir.contains(file2));
	}

	@Test
	public void testGetSize() {
		//import functions need to be tested before getSize()
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
