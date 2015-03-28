package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class VFS {
	//----------Attributes------------
	private VItem root;
	private long maxSpace;


	//----------Getters---------------
	public VItem getRoot() {
		return root;
	}
	public long getMaxSpace() {
		return maxSpace;
	}	
	/**
	 * @return getFreeSpace COMPUTES the free space of this VFS.
	 */
	public long getFreeSpace(){
		return maxSpace - root.getSize();
	}

	//----------Constructors----------
	/**
	 * @param maxSpace
	 * 
	 * This constructor creates a new VFS in the concrete file system with the specified maxSpace size, and initialize it.
<<<<<<< HEAD
	 * The initialisation process implies :
	 *     - adaptation of the the path
	 *     - creation of the root VDirectory
	 *     
	 * /!\ No memory is allowed on the hard drive, the file that is created weights 0kB
=======
	 * The initialisation process implies :
	 *     - creating the root VDirectory
	 *     - 
	 *     - 
>>>>>>> branch 'master' of https://github.com/Valefort/IS1220FinalProjectVasselin.git
	 */
	public VFS(long maxSpace, String path) {
		this.root = new VDirectory(); 
		this.maxSpace = maxSpace;

		//Generating the path to create the vfs, function of the OS.
		Path filePath = FileSystems.getDefault().getPath(path);

		//Writing the vfs in a file calling the generate method.
		this.generate(filePath); //Exceptions are managed in the generate method.
	}


	private void generate(Path filePath){
		try {
			File file = new File(filePath.toString());
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
