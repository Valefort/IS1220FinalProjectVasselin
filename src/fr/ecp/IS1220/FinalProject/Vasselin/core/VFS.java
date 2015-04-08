package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	private File actualFilePath;

	//----------Getters---------------
	public VDirectory getRoot() {
		return root;
	}
	public long getMaxSpace() {
		return maxSpace;
	}
	public File getActualFilePath() {
		return actualFilePath;
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
	 * @throws if the file location given by path cannot be written, an IOException will be thrown.
	 */
	public VFS(long maxSpace, String path) throws IOException{
		this.root = new VDirectory(); 
		this.maxSpace = maxSpace;

		//Generating the path to create the vfs, function of the OS.
		Path filePath = FileSystems.getDefault().getPath(path);
		//Writing the vfs in a file calling the generate method.
		this.generate(filePath); 
		actualFilePath = filePath.toFile();
	}

	//----------Saving functionalities

	/**
	 * Saves the content of this VFS to the hard-drive.
	 * @throws IOException may be thrown if the location getActualFilePath() can no longer be written.
	 */
	public void save() throws IOException{
		OutputStream out1 = new FileOutputStream(getActualFilePath());
		ObjectOutputStream out = new ObjectOutputStream(out1);
		
		out.writeObject(getRoot());
		
		out.close();
		out1.close();
	}
	
	/**
	 * Creates a VFS from a .vfs file in the host file system.
	 * @param path : must point to the .vfs file (including the ".vfs")
	 * @return a VFS corresponding to the one written on the hard-drive targeted by path
	 * @throws IOException if the path is invalid or the location can't be read.
	 * @throws NotAVFSException if path doesn't point to a VFS.
	 */
	public static VFS load(String path) throws IOException, NotAVFSException{
		File target = new File(path);
		if(!path.endsWith(".vfs"))
			throw new NotAVFSException();
		InputStream in1 = new FileInputStream(target);
		ObjectInputStream in = new ObjectInputStream(in1);
		
		VFS res=null;
		try{res = (VFS)in.readObject();}
		catch(ClassNotFoundException e){throw new NotAVFSException();}
		finally{
			in.close();
			in1.close();
		}
		return res;
	}

	//----------Private methods----------

	private void generate(Path filePath) throws IOException{
		File file = filePath.toFile();
		//file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		byte n[]={0};
		for(long i=0;i<this.maxSpace;i++)
			out.write(n);
		out.close();
	}

}
