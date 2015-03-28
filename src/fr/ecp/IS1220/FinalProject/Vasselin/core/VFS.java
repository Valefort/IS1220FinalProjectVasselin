package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	 * The initialization process implies :
	 *     - creating the root VDirectory
	 *     - 
	 *     - 
	 */
	public VFS(long maxSpace, String path) {
		this.root = new VDirectory(); 
		this.maxSpace = maxSpace;

		//Generating the path to create the vfs, function of the OS.
		String filePath = new String();

		//Writing the vfs in a file calling the generate method.

		this.generate(filePath); //Exceptions are managed in the generate method.
	}


	private void generate(String filePath){
		try {
			File file = new File(filePath);
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
