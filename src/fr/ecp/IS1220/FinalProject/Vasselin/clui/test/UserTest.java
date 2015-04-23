package fr.ecp.IS1220.FinalProject.Vasselin.clui.test;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.clui.*;
import fr.ecp.IS1220.FinalProject.Vasselin.core.*;

public class UserTest {

	@Test
	public void testGetSetClipboard() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals(null, test.getClipboard());
		VItem hoax = new VFile("hoax.txt");
		test.setClipboard(hoax);
		assertEquals(hoax, test.getClipboard());
	}

	@Test
	public void testOpenVFSGetOpenedVFS() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals(1, test.getOpenedVFS().size());
		assertEquals("test_used_vfs.vfs",test.getOpenedVFS().get(0).getName());
		test.openVFS(Paths.get("eval/host/example_music_storage.vfs"));
		assertEquals(2, test.getOpenedVFS().size());
		assertEquals("example_music_storage.vfs",test.getOpenedVFS().get(1).getName());
	}

	@Test
	public void testGetSetCurrentVFS() throws Exception{
		User test = new User();
		assertEquals(null,test.getCurrentVFS());
		VFS bidon = VFS.load(Paths.get("eval/host/test_used_vfs.vfs"));
		test.setCurrentVFS(bidon);
		assertEquals(bidon, test.getCurrentVFS());
		test.setCurrentVFS(Paths.get("eval/host/example_music_storage.vfs"));
		assertEquals("example_music_storage.vfs",test.getCurrentVFS().getName());
	}

	@Test
	public void testGetCurrentVItem() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals(test.getCurrentVFS().getRoot(),test.getCurrentVItem());
		fail("A terminer avec GetSetCurrentPath");
	}

	@Test
	public void testGetSetCurrentPath() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals("",test.getCurrentPath());
		test.setCurrentPath("toImport");
		assertEquals("toImport",test.getCurrentPath());
		test.setCurrentPath("toImport/Portal");
		assertEquals("toImport/Portal",test.getCurrentPath());
		try{test.setCurrentPath("Portal");
			fail("No exception was thrown");
		}catch(InvalidPathException e){}
	}

	@Test
	public void testGetVFS() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		
	}

	@Test
	public void testUserDefaultConstructor() {
		User test = new User();
		assertEquals(null,test.getClipboard());
		assertEquals(null,test.getCurrentVFS());
		assertEquals("",test.getCurrentPath());
		assertEquals(null,test.getCurrentVItem());
		assertTrue(test.getOpenedVFS().isEmpty());
	}

	@Test
	public void testUserOverloadedConstructor() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals(null,test.getClipboard());
		assertEquals("test_used_vfs.vfs",test.getCurrentVFS().getName());
		assertEquals("",test.getCurrentPath());
		assertEquals(test.getCurrentVFS().getRoot(),test.getCurrentVItem());
		assertEquals(1,test.getOpenedVFS().size());
	}

	@Test
	public void testUserPath() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testOpenVFS() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testParentDirectoryString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testParentDirectory() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testGoToParentDirectory() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testToAbsolutePathStringString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testToAbsolutePathString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testGoToString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testGoToVItem() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

	@Test
	public void testMove() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		fail("Not yet implemented");
	}

}
