package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;

public class VFSTest {

	@Test
	public void testGetRoot() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxSpace() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFreeSpace() {
		fail("Not yet implemented");
	}

	@Test
	public void testVFSCreativeConstructor() {
		
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
		new VFS(30, "myVFS1.vfs");
		assertTrue(new File("myVFS1.vfs").exists());
		File f = new File("myVFS1.vfs");
		assertEquals(30,f.length());
	}
	
}
