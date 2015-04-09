package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VDirectory;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class VItemFactoryTest {

	//Used in VDirectoryTest.
	//Ensure that two VItem are the same.
	protected static boolean alike(VItem a, VItem b){
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

	/*
	 * Des tests sur la création et l'édition de fichiers et dossiers ainsi que 
	 * la gestion des chemins sont à réaliser afin de débugguer tout ça...
	 * 
	 */

	//il faudra mettre du contenu dans les fichiers

	private Boolean checkFileImported(File file,VFile vfile) throws IOException {
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
/**
 *This test has to ensure that the imported store the good data. It does not
 *need the export function.
 * This excepted, one have to test the recursive importation of folders, 
 * with conservation of the tree structure.
 *  
 *  /!\ Does only support windows path notations for the moment
 */
public void testimportVItem() throws IOException{

	//Creation of the folder to be imported

	//To be implemented. Actually using the TestFolder

	//Importation of the folder in a new VDirectory

	Path path = FileSystems.getDefault().getPath("C:\\Users\\Malo MARREC\\Documents\\Boulot\\Progra\\Environnement\\Final Project Vasselin\\TestFolder\\Test_VItemFactoryTest_1\\Folder1");

	try{
		VDirectory rootDir = (VDirectory)VItemFactory.importVItem(path);
		rootDir.print(0);
		//Check the integrity of the imported file by comparison between the 
		// original file and the imported then exported file.

		File dirContainer = new File("dirContainer"); // creating a new directory
		if(!dirContainer.exists()){
			try{
				dirContainer.mkdir();
			}catch(SecurityException e){
				fail("Folder dirContainer already exists.");
			}
		}

		//The directory is exported in a new directory
		try{
			rootDir.exportVItem(dirContainer.toPath());

		}catch(Exception e){fail("Exception thrown...");}

	}catch(Exception e){fail("Exception thrown...");}

}

}
//		Boolean bool1, bool2; //The test succeed if bool1 && bool2
//		bool1 = new Boolean(false);
//		bool2 = new Boolean(false);
//
//		VFS myVFS = new VFS(100000000, "myVFS.vfs"); //creating a new VFS
//
//		File myFile = new File("myFile.file"); // creating a new file
//
//		/*
//		 * This first test check the most basic importation of a file.
//		 * Integrity checking remains to be implemented.
//		 */
//		try{
//			VItem vitem1 = VItemFactory.importVItem(myFile.toPath()); //loading the file in a VItem
//			myVFS.getRoot().add(vitem1); //add the file in the root directory
//			bool1 = myVFS.getRoot().contains(vitem1) && vitem1 !=null;
//		}catch(Exception e){fail("File too large or io exception raised.");}
//
//		File dir1 = new File("dir1"); // creating a new directory
//		if(!dir1.exists()){
//			try{
//				dir1.mkdir();
//			}catch(SecurityException e){
//				fail("Folder dir1 already exists.");
//			}
//		}
//
//		//adding files and directory in the directory
//		/*
//		 * dir1
//		 * |-> file1.file
//		 * |-> file2.file
//		 * |-> dir2
//		 * 		|-> file3.file
//		 */
//		File file1 = new File("dir1\\file1.file");
//		File file2 = new File("dir1\\file2.file");
//		File dir2 = new File("dir1\\dir2");
//		if(!dir2.exists()){
//			try{
//				dir2.mkdir();
//			}catch(SecurityException e){
//				fail("Folder dir2 already exists.");
//			}
//		}
//		File file3 = new File("dir1\\dir2\\file3.file");
//
//		try{
//			VItem dir1Imported = VItemFactory.importVItem(dir1.toPath());
//			myVFS.getRoot().add(dir1Imported);
//
//			bool2 = checkFileImported(file1, myVFS.getRoot().getDirectories().get(0).getFiles().get(0)); //check if file1 is correctly imported
//			bool2 = bool2 && checkFileImported(file2, myVFS.getRoot().getDirectories().get(0).getFiles().get(1)); //check if file2 is correctly imported
//			bool2 = bool2 && checkFileImported(file3, myVFS.getRoot().getDirectories().get(0).getDirectories().get(0).getFiles().get(0)); //check if file3 is correctly imported
//
//
//		}catch(Exception e){fail("File too large or io exception raised.");}
//
//		assertTrue(bool1 && bool2);
//		File theDir = new File("new folder");
//
//		// if the directory does not exist, create it
//		if (!theDir.exists()) {
//		    System.out.println("creating directory: " + directoryName);
//		    boolean result = false;
//
//		    try{
//		        theDir.mkdir();
//		        result = true;
//		    } 
//		    catch(SecurityException se){
//		        //handle it
//		    }        
//		    if(result) {    
//		        System.out.println("DIR created");  
//		    }
//		}



//		Path path = file.getPath();
//		File f = new File(path.toString());
//		if(myFile.isFile()){
//			byte[] data=null;
//			try{data=new byte[(int)myFile.length()];}
//			catch(ClassCastException e){throw new FileTooLargeException();}
//			InputStream in1 = new FileInputStream(myFile);
//			DataInputStream in = new DataInputStream(in1);
//			in.read(data);
//			in.close();
//			in1.close();
//			return new VFile(myFile.getName(),data);
//		}else{//here, f is a directory
//			VItem res = new VDirectory(myFile.getName());
//			for(String s : myFile.list()){
//				res.add(importVItem(Paths.get(s)));
//			}

