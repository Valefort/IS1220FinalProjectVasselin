package fr.ecp.IS1220.FinalProject.Vasselin.clui.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.clui.Parser;

public class ParserTest {

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
	public void testRunScript() {
		fail("Not yet implemented");
	}

}
