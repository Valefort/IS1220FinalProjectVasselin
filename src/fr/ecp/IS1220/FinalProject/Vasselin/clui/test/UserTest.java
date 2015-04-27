package fr.ecp.IS1220.FinalProject.Vasselin.clui.test;

import static org.junit.Assert.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.clui.*;
import fr.ecp.IS1220.FinalProject.Vasselin.core.*;
import fr.ecp.IS1220.FinalProject.Vasselin.core.test.VItemFactoryTest;

public class UserTest {
	
	private boolean compareByteArray(byte[] a, byte[] b){
		if(a.length != b.length)
			return false;
		for(int i=0; i<a.length;i++){
			if(a[i]!=b[i])
				return false;
		}
		return true;
	}

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
		test.setCurrentPath("toImport/Shakespeare");
		assertEquals("Shakespeare",test.getCurrentVItem().getName());
		assertTrue(test.getCurrentVItem() instanceof VDirectory);
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
		assertEquals("test_used_vfs.vfs",test.getVFS("test_used_vfs").getName());
		assertEquals(null, test.getVFS("example_music_storage"));
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
	public void testParentDirectoryString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals("toImport/Portal", test.parentDirectory("toImport/Portal/PieceOfCake.txt"));
		assertEquals("", test.parentDirectory("toImport"));
		assertEquals("toImport",test.parentDirectory("toImport/Shakespeare"));
	}

	@Test
	public void testParentDirectory() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals("", test.parentDirectory());
		test.setCurrentPath("toImport/Portal");
		assertEquals("toImport", test.parentDirectory());
	}

	@Test
	public void testGoToParentDirectory() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		test.setCurrentPath("toImport/Portal");
		test.goToParentDirectory();
		assertEquals("toImport",test.getCurrentPath());
	}

	@Test
	public void testToAbsolutePathStringString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals("toImport/Latin.txt",test.toAbsolutePath("Latin.txt","toImport"));
		assertEquals("toImport/Portal", test.toAbsolutePath("Portal","toImport"));
		assertEquals("toImport/Portal", test.toAbsolutePath(".","toImport/Portal"));
		assertEquals("toImport", test.toAbsolutePath("..","toImport/Shakespeare"));
		assertEquals("toImport/Portal", test.toAbsolutePath("..","toImport/Portal/PieceOfCake.txt"));
	}

	@Test
	public void testToAbsolutePathString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		assertEquals("toImport/Portal/WantYouGone.txt", test.toAbsolutePath("toImport/Portal/WantYouGone.txt"));
		test.setCurrentPath("toImport/Shakespeare");
		assertEquals("toImport/Shakespeare/ToBe.txt", test.toAbsolutePath("ToBe.txt"));
		assertEquals("toImport/Shakespeare",test.toAbsolutePath("."));
	}

	@Test
	public void testGoToString() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		test.goTo("toImport");
		assertEquals("toImport",test.getCurrentPath());
		test.goTo("Portal");
		assertEquals("toImport/Portal",test.getCurrentPath());
		test.goTo("..");
		assertEquals("toImport",test.getCurrentPath());
	}

	@Test
	public void testGoToVItem() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		test.goTo(test.getCurrentVFS().getRoot().getSuccessors().get(0));
		assertEquals("toImport", test.getCurrentPath());
		test.goTo(test.getCurrentVItem().getSuccessors().get(0));
		List<String> bidon = new ArrayList<String>();
		bidon.add("Portal");bidon.add("Shakespeare");bidon.add("Latin.txt");
		assertTrue(bidon.contains(test.getCurrentVItem().getName()));
	}

	@Test
	public void testMove() throws Exception{
		User test = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		VItem moved = test.getCurrentVFS().search("Latin.txt").get(0);
		test.move("toImport/Latin.txt", "toImport/Portal/text.txt");
		assertTrue(!test.getCurrentVFS().getRoot().getSuccessors().get(0).getSuccessors().contains(moved));
		test.setCurrentPath("toImport/Portal");
		assertTrue(test.getCurrentVItem().getSuccessors().contains(moved));
		test.move("toImport/Portal/text.txt", "toImport/Latin.txt");
	}
		
	@Test
	public void testCloseVFS() throws Exception{
		User user = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		user.closeVFS("test_used_vfs.vfs");		
		assertTrue(user.getOpenedVFS().isEmpty());
	}
	
	@Test
	public void testCreateVFS() throws Exception{
		User user = new User();
		Path concreteVFSPath = Paths.get("eval/host/test_create_vfs.vfs");
		long maxSpace = 42000;
		
		user.createVFS(concreteVFSPath, maxSpace);
		assertTrue(new File("eval/host/test_create_vfs.vfs").exists());
		File f = new File("eval/host/test_create_vfs.vfs");
		assertEquals("test_create_vfs.vfs",f.getName());
		
	}
	
	@Test
	public void testRemoveVFS() throws Exception{
		User user = new User();
		Path concreteVFSPath = Paths.get("eval/host/test_create_vfs.vfs");
		long maxSpace = 42000;		
		user.createVFS(concreteVFSPath, maxSpace);
		user.openVFS(concreteVFSPath);
		
		VFS vfsToRemove = user.getVFS("test_create_vfs.vfs");
		user.removeVFS(vfsToRemove);

		assertTrue(user.getOpenedVFS().isEmpty());
		assertTrue(!(new File("eval/host/test_create_vfs.vfs").exists()));
	}
	
	@Test
	public void testImportItem() throws Exception{
		
		fail("Not implemented yet.");
//		User user = new User(Paths.get("eval/host/test_used_vfs.vfs"));
//		Path hostPath = Paths.get("eval/host/toImport/Portal");
//		String vfsPath = "root/toImport/Shakespeare";
//		String vfsName = "test_used_vfs.vfs";
//		String referencePath = "root/toImport/Portal";
//		
//		user.importItem(hostPath, vfsName, vfsPath);
//		user.setCurrentPath(vfsPath);
//		VItem a = user.getCurrentVItem();
//		user.setCurrentPath(referencePath);
//		VItem b = user.getCurrentVItem();
//		
//		assertTrue(a.getName().equals(b.getName()));
		
	}
	
	@Test
	public void testExportVFS() throws Exception{
		fail("Not implemented yet.");
		
//		User user = new User(Paths.get("eval/host/test_used_vfs.vfs"));
//		Path hostPath = Paths.get("eval/host/Exported");
//		VFS vfsToExport = user.getVFS("test_used_vfs.vfs");
//		
//		user.exportVFS(hostPath, vfsToExport);
//		
//		File f = new File("eval/host/Exported/root");
//		assertTrue(f.exists());		
//		File f2 = new File("eval/host/Exported/root/toImport");
//		assertTrue(f2.exists());
//		File f3 = new File("eval/host/Exported/root/toImport/Portal");
//		assertTrue(f3.exists());
//		File f4 = new File("eval/host/Exported/root/toImport/Portal/Latin.txt");
//		assertTrue(f4.exists());
//		File f5 = new File("eval/host/Exported/root/toImport/Portal/PieceOfCake.txt");
//		assertTrue(f5.exists());

		//Does not work : comparison byte by byte between the imported and exported folder.
//		File g = new File("eval/host/toImport");
//		File h = new File("eval/host/Exported/root/toImport");
//
//		byte[] data1=null;
//		try{data1=new byte[(int)g.length()];}
//		catch(ClassCastException e){throw new FileTooLargeException();}
//		InputStream in1 = new FileInputStream(g);
//		DataInputStream in = new DataInputStream(in1);
//		in.read(data1);
//		in.close();
//		in1.close();
//		
//		byte[] data2=null;
//		try{data2=new byte[(int)g.length()];}
//		catch(ClassCastException e){throw new FileTooLargeException();}
//		InputStream iN1 = new FileInputStream(h);
//		DataInputStream iN = new DataInputStream(iN1);
//		iN.read(data2);
//		iN.close();
//		iN1.close();
//		
//
//		assertTrue(compareByteArray(data1, data2));
		
		
	}
	
	@Test
	public void testSearch() throws Exception{
		User user = new User(Paths.get("eval/host/test_used_vfs.vfs"));
		String vfsName = "test_used_vfs.vfs";
		String filename = "PieceOfCake.txt";
		
		System.out.println(user.search(vfsName, filename));
		
		assertTrue("root/toImport/Portal/PieceOfCake.txt\n".equals(user.search(vfsName, filename)));
		
	}
	
}
