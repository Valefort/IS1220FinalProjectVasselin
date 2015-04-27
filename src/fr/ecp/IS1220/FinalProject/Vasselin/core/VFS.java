package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.Paths;
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
	/**
	 * @return the name of the actual .vfs file in the host file system.
	 */
	public String getName(){
		return getActualFile().getName();
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
		
		try {
			save();
		} catch (MaxSizeExceededException e) { //this is not going to happen, except if the maxSize is about 10 bytes...
			System.out.println("Error : cannot create VFS : given maximum size is too low.");
			e.printStackTrace();
		}
	}

	//----------Saving functionalities-----------------------------------------

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
	 * The "root" at the beginning of the path may be omitted.
	 * @return the VItem which path points to
	 * @throws InvalidPathException is thrown if path doesn't point to any existing file.
	 */
	public VItem getPath(String path) throws InvalidPathException{
		VItem current = getRoot();
		if(path.startsWith("root"))
			path=path.substring(4);
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
	 * Checks the existence of a file/directory pointed at by path.
	 * @param path pointing at the file/directory whose existence is to be checked
	 * @return true if path is valid, false otherwise
	 */
	public boolean pathExists(String path){
		try{
			return getPath(path) != null;
		}catch(InvalidPathException e){
			return false;
		}
	}

	/**
	 * Finds all the VItems named name, using a simple breadth-first search. This is an overload of
	 * search(name, path) where path is "root"
	 * @param name : the name of the researched items
	 * @return a List of VItem, containing all the items of the VFS with the name "name"
	 */
	public List<VItem> search(String name){
		try{return search(name, "");}
		catch(InvalidPathException e){return null;}//This is never reached.
	}
	
	/**
	 * Finds all the VItems named name, using a simple breadth-first search.
	 * @param name : the name of the researched items
	 * @param path : the file/directory from which to search the items
	 * @return a List of VItem, containing all the items of the VFS with the name "name"
	 * @throws InvalidPathException if path is invalid
	 */
	public List<VItem> search(String name, String path) throws InvalidPathException{
		Stack<VItem> Q = new Stack<VItem>();
		Q.add(getPath(path));
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
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		rf.setLength(getMaxSpace());
		rf.close();
	}
	
	
	
	//-----------Generation of the vfs file used_test_vfs.vfs------------------	
	public static void main(String[] args) {
//		Path path = Paths.get("eval/host/test_used_vfs.vfs");
//		long maxSpace = 30000000;
//		try {
//			VFS test_used_vfs = new VFS(maxSpace, path);
//			test_used_vfs.getRoot().add(VItemFactory.importVItem(Paths.get("eval/host/toImport")));
//			test_used_vfs.save();
//			
//			//test_used_vfs.getRoot().exportVItem(Paths.get("eval/host/Exported"));
//			
//		} catch (Exception e) {
//			System.out.println("Error : impossible to create the vfs.");
//			e.printStackTrace();
//		}

		
		Path path = Paths.get("eval/host/example_music_storage.vfs");
		long maxSpace = 20000000;
		
		try {
		VFS example_music_storage = new VFS(maxSpace, path);
		example_music_storage.getRoot().add(VItemFactory.importVItem(Paths.get("eval/host/MusicToImport")));
		example_music_storage.save();
		
		//test_used_vfs.getRoot().exportVItem(Paths.get("eval/host/Exported"));
		
	} catch (Exception e) {
		System.out.println("Error : impossible to create the vfs.");
		e.printStackTrace();
	}
		
		
	}
	
		

}
