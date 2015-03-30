package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.FileOutputStream;
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
	 * The initialisation process implies :
	 *     - adaptation of the the path
	 *     - creation of the root VDirectory
	 *     
	 * /!\ No memory is allowed on the hard drive, the file that is created weights 0kB
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
			File file = new File(filePath.toString());
			//file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			byte n[]={0};
			for(int i=0;i<this.maxSpace;i++)
				out.write(n);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//Import
	
	/**
	 * Creates a VItem from the file/directory targeted by path.
	 * Note : internally uses the importVItem from VItem on the right VItem type (directory/file)
	 * @param path
	 * @return a VItem created from the target of the path.
	 * Note : internally uses the importVItem from VItem on the right VItem type (directory/file)
	 */
	public VItem importVItem(Path path){
		return null;
	}

}
