package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import static org.junit.Assert.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NameConflictException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NotAVFSException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VDirectory;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;

public class VFSTest {
	
	/**
	 * This is only used in the tests.
	 * @return a small randomly-generated VDirectory composed of 5 files and another VDIrectory, itself
	 * composed of 5 files.
	 */
	protected VDirectory generateRandomVDirectory() {
		byte[] data;
		Random r = new Random();
		VDirectory tested = new VDirectory("thisIsGreat");
		for(int i=0;i<5;i++){
			int increment = Math.abs(r.nextInt())%50;
			data = new byte[increment];
			try{tested.add(new VFile("whatever"+i, data));}catch(NameConflictException e){}
		}

		VDirectory subdir = new VDirectory();
		for(int i=0;i<5;i++){
			int increment = Math.abs(r.nextInt())%50;
			data = new byte[increment];
			try{subdir.add(new VFile("whatever"+(i+5), data));}catch(NameConflictException e){}
		}

		try{tested.add(subdir);}catch(NameConflictException e){}
		return tested;
	}

	@Test
	public void testGetMaxSpace() throws IOException{
		VFS vfs = new VFS(1564, FileSystems.getDefault().getPath("myVFS3.vfs"));
		assertEquals(1564, vfs.getMaxSpace());
	}

	@Test
	public void testGetFreeSpace() throws IOException, NameConflictException{
		VFS vfs = new VFS(1564, FileSystems.getDefault().getPath("myVFS4.vfs"));
		byte[] fakeData = new byte[564];
		VFile file = new VFile("Poney.trol", fakeData);
		vfs.getRoot().add(file);
		assertEquals(1000, vfs.getFreeSpace());
	}
	
	@Test
	public void testGetActualFile() throws IOException{
		VFS vfs = new VFS(1564, FileSystems.getDefault().getPath("myVFS5.vfs"));
		File f = new File("myVFS5.vfs");
		assertEquals(f, vfs.getActualFile());
	}
	
	@Test
	public void testSetActualFile() throws IOException{
		VFS vfs = new VFS(1564, FileSystems.getDefault().getPath("myVFS6.vfs"));
		File f = new File("myVFS7.vfs");
		vfs.setActualFile(f);
		assertEquals(f, vfs.getActualFile());
	}

	@Test
	public void testVFS() throws IOException{
		
		//Does not work
//		new VFS(30, "/Final Project Vasselin/testVFS_Constructor/myVFS1.vfs");
//		assertTrue(new File("/Final Project Vasselin/testVFS_Constructor/myVFS1.vfs").exists());
	
		
		//Does work
//		new VFS(30, "C:\\Users\\Malo MARREC\\Documents\\Boulot\\Progra\\Projet final\\testsVFS_Constructor\\myVFS1.vfs");
//		assertTrue(new File("C:\\Users\\Malo MARREC\\Documents\\Boulot\\Progra\\Projet final\\testsVFS_Constructor\\myVFS1.vfs").exists());
		
		//Does not work
//		new VFS(30, "testsVFS_Constructor\\myVFS1.vfs");
//		assertTrue(new File("testsVFS_Constructor\\myVFS1.vfs").exists());
		
		//Does work
		Path filePath = FileSystems.getDefault().getPath("myVFS1.vfs");
		new VFS(30, filePath);
		assertTrue(new File("myVFS1.vfs").exists());
		File f = new File("myVFS1.vfs");
		assertEquals(30,f.length());
	}
	
	@Test
	public void testSaveLoad() throws Exception{
		//generating a VDirectory.
		VDirectory tested = generateRandomVDirectory();
		//generating a VFS
		VFS vfs = new VFS(2000, FileSystems.getDefault().getPath("myVFS2.vfs"));
		vfs.getRoot().add(tested);
		
		//save
		vfs.save();
		
		//load
		VFS vfs2 = VFS.load(Paths.get("myVFS2.vfs"));
		//Note that I SHOULD NOT be doing that, i.e opening two VFS instances targeting the same file.
		
		assertTrue(VItemFactoryTest.alike(vfs.getRoot(), vfs2.getRoot()));
	}
	
	@Test(expected = NotAVFSException.class)
	public void testLoadThrowsNotAVFSException() throws IOException, NotAVFSException{
		File f = new File("trol.lol");
		FileOutputStream out1 = new FileOutputStream(f);
		DataOutputStream out = new DataOutputStream(out1);
		
		Random r = new Random();
		byte[] fakeData = new byte[55];
		r.nextBytes(fakeData);
		out.write(fakeData);
		
		out.close();
		out1.close();
		
		@SuppressWarnings("unused")
		VFS vfs = VFS.load(Paths.get(f.getPath()));
		
		fail("No exception was thrown.");
	}
	
	@Test
	public void testDelete() throws IOException{
		VFS vfs = new VFS(50,Paths.get("myVFS8.vfs"));
		File f = new File("myVFS8.vfs");
		assertTrue(f.exists());
		vfs.delete();
		assertTrue(!f.exists());
	}
	
	@Test
	public void testGetPathWorks() throws Exception{
		VFS vfs = new VFS(1000, Paths.get("myVFS9.vfs"));
		vfs.getRoot().add(generateRandomVDirectory());
		assertEquals("whatever3",vfs.getPath("root/thisIsGreat/whatever3").getName());
	}
	
	@Test(expected = InvalidPathException.class)
	public void testGetPathThrowException() throws InvalidPathException, IOException{
		VFS vfs = new VFS(1000, Paths.get("myVFS10.vfs"));
		vfs.getPath("root/Hell/YourWorstNightmare.troll");
		fail("No exception was thrown.");
	}
	
	@Test
	public void testSearch() throws Exception{
		VFS vfs = new VFS(1000, Paths.get("myVFS11.vfs"));
		vfs.getRoot().add(generateRandomVDirectory());
		assertEquals("whatever5", vfs.search("whatever5").get(0).getName());
	}
}
