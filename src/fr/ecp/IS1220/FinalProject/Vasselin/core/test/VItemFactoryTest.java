package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VDirectory;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class VItemFactoryTest {


	/**
	 * This is used only in the tests.
	 * It compares two VItem and returns true if they have the same content.
	 * @param a : a VItem
	 * @param b : another VItem
	 * @return true if a and b have the same content, false otherwise.
	 * Warning : set to public in order to be used in the clui tests
	 */
	public static boolean alike(VItem a, VItem b){
		if(a instanceof VDirectory){
			if(b instanceof VDirectory){//on est du même type...
				if(!a.getName().equals(b.getName())){
					System.out.println("Name mismatch between directories: "+a.getName()+"&"+b.getName());
					return false;
				}
				if(a.getSuccessors().size()!=b.getSuccessors().size())
					return false;
				for(VItem u : a.getSuccessors()){//on check que le contenu est le même.
					boolean foundSomethingWithSameName=false;
					for(VItem v : b.getSuccessors()){//on cherche un item avec le même nom
						if(u.getName().equals(v.getName())){
							foundSomethingWithSameName=true;
							if(!alike(u,v))
								return false;
						}
					}
					if(!foundSomethingWithSameName){
						System.out.println("File not found: "+u.getName());
						return false;
					}
					//if here, u is in b.getSuccessors().
				}
				return true;
			}else
				return false;
		}else{
			if(b instanceof VFile){//on est du même type...
				if(!a.getName().equals(b.getName())){
					System.out.println("File name mismatch: "+a.getName()+"&"+b.getName());
					return false;
				}
				byte[] t = ((VFile)a).getData();
				byte[] u = ((VFile)b).getData();
				if(t.length!=u.length)
					return false;
				for(int i=0;i<t.length;i++){
					if(t[i]!=u[i]){
						System.out.println("Content mismatch : "+a.getName()+"&"+b.getName());
						return false;
					}
				}
				return true;
			}
			else
				return false;
		}
	}

	/**
	 * This is used only in the test.
	 * @param file : a file in the host file system
	 * @param vfile : a VFile
	 * @return true if file and vfile have the same name and content.
	 */
	private static Boolean checkFileImported(File file,VFile vfile) throws IOException {
		if(!file.getName().equals(vfile.getName())){
			System.out.println("Filenames mismatch.");
			return false;
		}else{

			byte [] f1 = Files.readAllBytes(file.toPath());
			byte [] f2 = vfile.getData();
			if(f1.length != f2.length){
				System.out.println("File size mismatch.");
				return false;
			}else{
				for(int i=0; i<f1.length; i++){
					if(f1[i]!=f2[i]){
						System.out.println("File content mismatch.");
						return false;
					}
				}
			}
		}
		return true;
	}



	@Test
	public void testimportVItem() throws Exception{
		
		Random r = new Random();

		//generation of a content to be loaded.
		File testFolder = new File("importVItemTest");
		testFolder.mkdir();
		if(!testFolder.exists())
			fail("Impossible to create the test directory.");
		
		File f1 = new File("importVItemTest/file1.txt");
		byte[] f1data = new byte[80];
		r.nextBytes(f1data);
		FileOutputStream stream1 = new FileOutputStream(f1);
		DataOutputStream out1 = new DataOutputStream(stream1);
		out1.write(f1data);
		out1.close();
		stream1.close();
		
		File lilfolder = new File("importVItemTest/lilfolder");
		lilfolder.mkdir();
		if(!lilfolder.exists())
			fail("Impossible to create the lilfolder directory.");
		
		File f2 = new File("importVItemTest/lilfolder/file2.txt");
		byte[] f2data = new byte[80];
		r.nextBytes(f2data);
		FileOutputStream stream2 = new FileOutputStream(f2);
		DataOutputStream out2 = new DataOutputStream(stream2);
		out2.write(f2data);
		out2.close();
		stream2.close();
		
		//Testing the import
		VItem test = VItemFactory.importVItem(Paths.get("importVItemTest"));
		
		assertEquals(160,test.getSize());
		assertEquals(2, test.getSuccessors().size());
		
		assertTrue(((VDirectory)test).getDirectories().get(0).getName().equals("lilfolder"));
		assertTrue(checkFileImported(f1,((VDirectory)test).getFiles().get(0)));
		VDirectory lilfolderTest = (VDirectory)test.getSuccessors().get(0);
		
		assertEquals(1,lilfolderTest.getSuccessors().size());
		assertTrue(checkFileImported(f2,lilfolderTest.getFiles().get(0)));
	}

}


