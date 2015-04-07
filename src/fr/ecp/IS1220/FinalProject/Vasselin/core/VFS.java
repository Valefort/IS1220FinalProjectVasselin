package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class VFS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448298108902955253L;
	//----------Attributes------------
	private VDirectory root;
	private long maxSpace;


	//----------Getters---------------
	public VDirectory getRoot() {
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
	 * @param maxSpace : the maximum storage space of the VFS, i.e the size of the VFS file on the host file system.
	 * 
	 * This constructor creates a new VFS in the concrete file system with the specified maxSpace size, and initialize it.
	 * The initialisation process implies :
	 *     - adaptation of the the path
	 *     - creation of the root VDirectory
	 */
	public VFS(long maxSpace, String path) {
		this.root = new VDirectory(); 
		this.maxSpace = maxSpace;

		//Generating the path to create the vfs, function of the OS.
		Path filePath = FileSystems.getDefault().getPath(path);

		//Writing the vfs in a file calling the generate method.
		this.generate(filePath); //Exceptions are managed in the generate method.
	}

	/**
	 * Creates a file containing zeros, of the size maxSpace.
	 * Warning : this.maxSpace MUST BE initialised before calling generate.
	 * @param filePath : the place where the file will be created.
	 */
	private void generate(Path filePath){
		try {
			File file = filePath.toFile();
			//file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			byte n[]={0};
			for(long i=0;i<this.maxSpace;i++)
				out.write(n);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
