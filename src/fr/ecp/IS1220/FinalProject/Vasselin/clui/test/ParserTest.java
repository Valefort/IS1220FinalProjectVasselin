package fr.ecp.IS1220.FinalProject.Vasselin.clui.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.clui.*;
import fr.ecp.IS1220.FinalProject.Vasselin.core.*;

public class ParserTest {
	
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
	public void testLS() throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);

		Parser test = new Parser();
		test.openVFS(Paths.get("eval/host/test_used_vfs.vfs"));
		test.parseCommand("ls test_used_vfs.vfs toImport/Portal");
		test.parseCommand("ls test_used_vfs.vfs -l toImport");

		System.out.flush();
		System.setOut(old);

		assertEquals("       PieceOfCake.txt  f\n"+
					 "portal_still_alive.mp3  f\n"+
					 "       WantYouGone.txt  f\n"+
					 "     Portal  d 5651158\n"+
					 "Shakespeare  d 1871\n"+
					 "  Latin.txt  f 3088\n",baos.toString());
	}
	
	@Test
	public void testCD() throws Exception{
		Parser test = new Parser();
		test.openVFS(Paths.get("eval/host/test_used_vfs.vfs"));
		test.parseCommand("cd test_used_vfs.vfs toImport/Portal");
		assertEquals("toImport/Portal",test.getCurrentPath());
		test.parseCommand("cd toImport/Shakespeare");
		assertEquals("toImport/Shakespeare",test.getCurrentPath());
		test.parseCommand("cd .");
		assertEquals("toImport/Shakespeare",test.getCurrentPath());
		test.parseCommand("cd ..");
		assertEquals("toImport",test.getCurrentPath());
		test.parseCommand("cd Portal");
		assertEquals("toImport/Portal",test.getCurrentPath());
		test.parseCommand("cd ..");
		test.parseCommand("cd ..");
		assertEquals("",test.getCurrentPath());
		test.parseCommand("cd ..");
		assertEquals("",test.getCurrentPath());
	}
	
	@Test
	public void testMV() throws Exception{
		Parser test = new Parser();
		test.openVFS(Paths.get("eval/host/test_used_vfs.vfs"));
		test.parseCommand("mv test_used_vfs.vfs toImport/Portal root/newPortal");
		assertTrue(test.getCurrentVFS().pathExists("newPortal"));
		test.parseCommand("mv newPortal/PieceOfCake.txt toImport/aaa.bbb");
		assertTrue(test.getCurrentVFS().pathExists("toImport/aaa.bbb"));
	}
	
	@Test
	public void testCP() throws Exception{
		Parser test = new Parser();
		test.openVFS(Paths.get("eval/host/test_used_vfs.vfs"));
		test.parseCommand("cp test_used_vfs.vfs toImport/Portal/WantYouGone.txt toImport/copy.txt");
		assertTrue(test.getCurrentVFS().pathExists("toImport/copy.txt"));
		assertTrue(compareByteArray(
				((VFile)test.getCurrentVFS().getPath("toImport/Portal/WantYouGone.txt")).getData(),
				((VFile)test.getCurrentVFS().getPath("toImport/copy.txt")).getData()));
	}

	@Test
	public void testRunScript() {
		fail("Not yet implemented");
	}

}
