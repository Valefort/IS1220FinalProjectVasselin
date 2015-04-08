package fr.ecp.IS1220.FinalProject.Vasselin.core.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ecp.IS1220.FinalProject.Vasselin.core.FileTooLargeException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VDirectory;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemFactory;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VItemFactoryTest {
	
	/*
	 * Des tests sur la création et l'édition de fichiers et dossiers ainsi que 
	 * la gestion des chemins sont à réaliser afin de débugguer tout ça...
	 * 
	 */
	
	//il faudra mettre du contenu dans les fichiers
	
	private Boolean checkFileImported(File file,VFile vfile) {
		Boolean result = new Boolean(false);
		result = file.getName().equals(vfile.getName());
		
		//ici on vérifie que le contenu en terme de bits est le même
		
		//Et il reste un bout à coder pour les dossiers...
		
		return result;
	}
	
	//Mouais, je préferre ça (le vire pas, je l'utilise dans VDirectoryTest) :
	protected static boolean alike(VItem a, VItem b){
		if(a instanceof VDirectory){
			if(b instanceof VDirectory){//on est du même type...
				VDirectory a2 = (VDirectory)a;
				VDirectory b2 = (VDirectory)b;
				for(int i=0; i<a2.getSuccessors().size();i++){//on check que le contenu est le même.
					if(!alike(a2.getSuccessors().get(i), b2.getSuccessors().get(i)))
						return false;
				}
				return true;
			}else
				return false;
		}else{
			if(b instanceof VFile){//on est du même type...
				return (a.getName()==b.getName()) && (((VFile)a).getData()==((VFile)b).getData());
			}
			else
				return false;
		}
	}

	@Test
	/**
	 * This function in particular is hard to test : as the data is manipulated 
	 * as bits, without the export function, there is no way to check that the 
	 * import is correct.
	 * Without export functions, one can test :
	 *  - effective importation of the files with contain() and getSize()
	 *  - recursive importation of folders, with conservation of the tree structure
	 *  
	 *  /!\ Does only support windows path notations for the moment
	 */
	public void testimportVItem() throws IOException{
		Boolean bool1, bool2; //The test succeed if bool1 && bool2
		bool1 = new Boolean(false);
		bool2 = new Boolean(false);
		
		VFS myVFS = new VFS(100000000, "myVFS.vfs"); //creating a new VFS
		
		File myFile = new File("myFile.file"); // creating a new file
		
		/*
		 * This first test check the most basic importation of a file.
		 * Integrity checking remains to be implemented.
		 */
		try{
			VItem vitem1 = VItemFactory.importVItem(myFile.toPath()); //loading the file in a VItem
			myVFS.getRoot().add(vitem1); //add the file in the root directory
			bool1 = myVFS.getRoot().contains(vitem1) && vitem1 !=null;
		}catch(Exception e){fail("File too large or io exception raised.");}
		
		File dir1 = new File("dir1"); // creating a new directory
		if(!dir1.exists()){
			try{
				dir1.mkdir();
			}catch(SecurityException e){
				fail("Folder dir1 already exists.");
			}
		}
		
		//adding files and directory in the directory
		/*
		 * dir1
		 * |-> file1.file
		 * |-> file2.file
		 * |-> dir2
		 * 		|-> file3.file
		 */
		File file1 = new File("dir1\\file1.file");
		File file2 = new File("dir1\\file2.file");
		File dir2 = new File("dir1\\dir2");
		if(!dir2.exists()){
			try{
				dir2.mkdir();
			}catch(SecurityException e){
				fail("Folder dir2 already exists.");
			}
		}
		File file3 = new File("dir1\\dir2\\file3.file");
		
		try{
			VItem dir1Imported = VItemFactory.importVItem(dir1.toPath());
			myVFS.getRoot().add(dir1Imported);
			
			bool2 = checkFileImported(file1, myVFS.getRoot().getDirectories().get(0).getFiles().get(0)); //check if file1 is correctly imported
			bool2 = bool2 && checkFileImported(file2, myVFS.getRoot().getDirectories().get(0).getFiles().get(1)); //check if file2 is correctly imported
			bool2 = bool2 && checkFileImported(file3, myVFS.getRoot().getDirectories().get(0).getDirectories().get(0).getFiles().get(0)); //check if file3 is correctly imported
			
			
		}catch(Exception e){fail("File too large or io exception raised.");}
		
		assertTrue(bool1 && bool2);
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
	}

}
