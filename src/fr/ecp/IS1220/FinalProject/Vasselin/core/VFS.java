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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class VFS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1448298108902955253L;
	//----------Attributes------------
	private VDirectory root;
	private long maxSpace;
	private File actualFile;

	//----------Getters---------------
	/**
	 * @return the root VDIrectory, containing all the elements of the VFS.
	 */
	public VDirectory getRoot() {
		return root;
	}
	/**
	 * @return the maximum authorised total size of the .vfs file in the host file system, in bytes.
	 * Note that due to the storage of the data structure, the total amount of actual data stored in
	 * this VFS is smaller than getMaxSpace().
	 */
	public long getMaxSpace() {
		return maxSpace;
	}
	/**
	 * @return the file where the .vfs file corresponding to this is stored (in the hosting file system)
	 */
	public File getActualFile() {
		return actualFile;
	}
	/**
	 * @param actualFile : the new file in the hosting file system where the .vfs file will be stored when save() is called.
	 */
	public void setActualFile(File actualFile) {
		this.actualFile = actualFile;
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
	 * @param path : the path pointing to the future emplacement of the .vfs file (including the name.vfs)
	 * 
	 * This constructor creates a new VFS in the concrete file system with the specified maxSpace size, and initialize it.
	 * The initialisation process implies :
	 *     - adaptation of the the path
	 *     - creation of the root VDirectory
	 * @throws if the file location given by path cannot be written, an IOException will be thrown.
	 */
	public VFS(long maxSpace, Path path) throws IOException{
		this.root = new VDirectory("root"); 
		this.maxSpace = maxSpace;

		//Writing the vfs in a file calling the generate method.
		this.generate(path); 
		actualFile = path.toFile();
	}

	//----------Saving functionalities

	/**
	 * Saves the content of this VFS to the hard-drive.
	 * The length of the actual .vfs file in the hosting file system may be larger than getMaxSpace(),
	 * because of the space taken by the data structure.
	 * @throws IOException may be thrown if the location getActualFilePath() can no longer be written.
	 * @throws MaxSizeExceededException is thrown if the current size of the VFS is larger than getMaxSize().
	 */
	public void save() throws IOException, MaxSizeExceededException{
		OutputStream out1 = new FileOutputStream(getActualFile());
		ObjectOutputStream out = new ObjectOutputStream(out1);

		out.writeObject(this);

		out.close();
		out1.close();

		if(this.getActualFile().length()>getMaxSpace()){
			this.getActualFile().delete();
			generate(getActualFile().toPath());

			throw new MaxSizeExceededException();
		}
	}

	/**
	 * Creates a VFS from a .vfs file in the host file system.
	 * @param path : must point to the .vfs file (including the ".vfs")
	 * @return a VFS corresponding to the one written on the hard-drive targeted by path
	 * @throws IOException if the path is invalid or the location can't be read.
	 * @throws NotAVFSException if path doesn't point to a VFS.
	 */
	public static VFS load(Path path) throws IOException, NotAVFSException{
		File target = path.toFile();
		if(!path.toString().endsWith(".vfs"))
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
		res.setActualFile(target);
		return res;
	}

	/**
	 * Deletes the .vfs file in the host file system.
	 * @throws SecurityException is thrown if the .vfs file is no longer accessible.
	 */
	public void delete() throws SecurityException{
		actualFile.delete();
	}

	//Paths and searches

	/**
	 * Returns the VItem at the location designated by path in the VFS.
	 * @param path : the path to the wanted file/directory in the VFS. The directories should be separated by "/".
	 * The "root/" at the beginning of the path may be omitted.
	 * @return the VItem which path points to
	 * @throws InvalidPathException is thrown if path doesn't point to any existing file.
	 */
	public VItem getPath(String path) throws InvalidPathException{
		VItem current = getRoot();
		if(path.startsWith("root/"))
			path=path.substring(5);
		StringTokenizer tk = new StringTokenizer(path, "/");
		while(tk.hasMoreTokens()){
			boolean found=false;
			String name = tk.nextToken();
			for(VItem i : current.getSuccessors()){
				if(name.equals(i.getName())){
					found=true;
					current=i;
					break;
				}
			}
			if(!found)
				throw new InvalidPathException();
		}
		return current;
	}

	/**
	 * Finds all the VItems named name, using a simple breadth-first search.
	 * @param name : the name of the researched items
	 * @return a List of VItem, containing all the items of the VFS with the name "name"
	 */
	public List<VItem> search(String name){
		Stack<VItem> Q = new Stack<VItem>();
		Q.add(getRoot());
		VItem current=null;
		List<VItem> res = new ArrayList<VItem>();
		while(!Q.isEmpty()){
			current=Q.pop();
			for(VItem i : current.getSuccessors()){
				if(i.getName().equals(name))
					res.add(i);
				Q.add(i);
			}
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
