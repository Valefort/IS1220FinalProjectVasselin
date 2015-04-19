package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import fr.ecp.IS1220.FinalProject.Vasselin.core.*;

public class User {

	//Attributes
	private VItem clipboard;
	private List<VFS> openedVFS;
	private VFS currentVFS;
	private VItem currentVItem;
	private String currentPath;

	//Getters and Setters
	/**
	 * @return the VItemp contained in the clipboard (typically the item being copied).
	 * May return a null value if no item has been put in the clipboard using setClipboard.
	 */
	public VItem getClipboard() {
		return clipboard;
	}
	/**
	 * Set the content of the clipboard (typically when copying a VItem)
	 * @param clipboard : the new VItem returned by getClipboard
	 */
	public void setClipboard(VItem clipboard) {
		this.clipboard = clipboard;
	}
	/**
	 * @return the list of the VFS this user has already opened and loaded into memory.
	 */
	public List<VFS> getOpenedVFS() {
		return openedVFS;
	}
	/**
	 * @return the VFS in which the currentPath is defined.
	 */
	public VFS getCurrentVFS() {
		return currentVFS;
	}
	/**
	 * Changes the current working VFS, reseting the currentPath and currentVItem to its root.
	 * @param currentVFS : the new working VFS. If it is not in the OpenedVFS of this User, it will be added.
	 */
	public void setCurrentVFS(VFS currentVFS) {
		this.currentVFS = currentVFS;
		currentVItem=currentVFS.getRoot();
		currentPath="/";
		if(openedVFS.contains(currentVFS))
			openedVFS.add(currentVFS);
	}
	/**
	 * An overloaded version of setCurrentVFS(VFS currentVFS). Opens the VFS if it has not yet been opened.
	 * @param path : the path pointing to the .vfs file in the host file system
	 * @throws IOException if path cannot be read
	 * @throws NotAVFSException if path doesn't point to a VFS.
	 */
	public void setCurrentVFS(Path path) throws IOException, NotAVFSException{
		String name=path.toFile().getName();
		VFS test=getVFS(name);
		if(test==null){//we have to open the new path !
			test=VFS.load(path);
		}
		setCurrentVFS(test);
	}
	/**
	 * @return the VItem pointed at by currentPath.
	 */
	public VItem getCurrentVItem() {
		return currentVItem;
	}
	/**
	 * @return the current working location in the currentVFS, pointing at the currentVItem.
	 */
	public String getCurrentPath() {
		return currentPath;
	}
	/**
	 * Defines the new current position.
	 * @param currentPath : the new current position.
	 * @throws InvalidPathException is thrown if currentPath is not a valid path.
	 */
	public void setCurrentPath(String currentPath) throws InvalidPathException{
		VItem test = currentVFS.getPath(currentPath);//just to avoid ruining the currentVItem in case of an invalid path
		currentVItem=test;
		this.currentPath=currentPath;
	}
	/**
	 * A String-based search among the opened VFS.
	 * @param name : the name of the researched .vfs file. The ".vfs" suffix may be omitted.
	 * @return null if no VFS with this name has been opened yet, or a VFS with this name.
	 */
	public VFS getVFS(String name){
		String myName = new String(name);
		if(!name.endsWith(".vfs"))
			myName+=".vfs";
		for(VFS v : this.openedVFS){
			if(v.getName().equals(myName))
				return v;
		}
		return null;
	}
	
	//Constructors
	/**
	 * The default constructor. Does not open any VFS.
	 */
	public User() {
		super();
		this.clipboard=null;
		this.currentPath="";
		this.currentVFS=null;
		this.currentVItem=null;
		this.openedVFS = new ArrayList<VFS>();
	}

	/**
	 * An overloaded constructor, opening a VFS targeted by the argument.
	 * @param vfs : the path pointing to the .vfs file to be opened
	 * @throws IOException when the destination cannot be read.
	 * @throws NotAVFSException when the destination is not a VFS.
	 */
	public User(Path vfs) throws IOException, NotAVFSException{//NOT FINISHED YET. USE THE SETTERS/OPENVFS
		super();
		this.clipboard=null;
//		this.currentVFS = VFS.load(vfs);
//		try{setCurrentPath("");}catch(InvalidPathException e){}//does not happen.
	}
	
	//Canonical methods
	
	public void openVFS(Path path) throws IOException, NotAVFSException{
		//to be implemented.
	}
	
}
