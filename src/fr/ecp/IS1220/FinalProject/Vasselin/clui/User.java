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
		currentPath="";
		if(!openedVFS.contains(currentVFS))
			openedVFS.add(currentVFS);
	}
	/**
	 * An overloaded version of setCurrentVFS(VFS currentVFS). Opens the VFS if it has not yet been opened.
	 * @param path : the path pointing to the .vfs file in the host file system
	 * @throws IOException if path cannot be read
	 * @throws NotAVFSException if path doesn't point to a VFS.
	 */
	public void setCurrentVFS(Path path) throws IOException, NotAVFSException, NameConflictException{
		String name=path.toFile().getName();
		VFS test=getVFS(name);
		if(test==null){//we have to open the new path !
			openVFS(path);
			test=getVFS(name);//no longer null, or an exception was thrown before that
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
	 * @return null if no VFS with this name has been opened yet, or an opened VFS with this name.
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
	public User(Path vfs) throws IOException, NotAVFSException{
		super();
		this.clipboard=null;
		this.openedVFS = new ArrayList<VFS>();
		try{setCurrentVFS(vfs);}
		catch(NameConflictException e){}//Does not happen.
	}

	//Canonical methods
	/**
	 * Loads in memory the specified .vfs file and adds the corresponding VFS object to openedVFS.
	 * @param path points to the .vfs file in the host file system
	 * @throws IOException thrown if path cannot be read
	 * @throws NotAVFSException thrown if path doesn't point to a VFS
	 * @throws NameConflictException if there is already an opened VFS with the same name in this User
	 */
	public void openVFS(Path path) throws IOException, NotAVFSException, NameConflictException{
		String name = path.toFile().getName();
		for(VFS v : openedVFS){
			if(v.getName().equals(name))
				throw new NameConflictException();
		}
		VFS test = VFS.load(path);
		openedVFS.add(test);
	}
	/**
	 * A basic method to retrieve the directory containing a file/directory in the currentVFS.
	 * @param path : the file/directory whose parent directory must be determined, in the currentVFS
	 * @return the path to the parent directory, or "" if path points to the root of currentVFS
	 * @throws InvalidPathException if the argument path is invalid
	 */
	public String parentDirectory(String path) throws InvalidPathException{
		return parentDirectory(path, true);
	}
	private String parentDirectory(String path, boolean checkExistence) throws InvalidPathException{
		if(currentVFS.pathExists(path) || !checkExistence){
			int i = path.lastIndexOf('/');
			if(i>0)
				return path.substring(0, i);
			else
				return "";
		}
		else
			throw new InvalidPathException();
	}
	/**
	 * An overload of parentDirectory(String path) with the default argument currentPath.
	 * @return the parent directory of the current path, or the root path if the current directory is root.
	 */
	public String parentDirectory(){
		try{return parentDirectory(currentPath);}
		catch(InvalidPathException e){return "";}//Does NOT happen
	}
	/**
	 * Set the currentPath to its parent directory.
	 */
	public void goToParentDirectory(){
		try{setCurrentPath(parentDirectory());}
		catch(InvalidPathException e){}//Does NOT happen
	}
	/**
	 * Takes a relative path, returns an absolute path.
	 * @param relativePath : a path that is RELATIVE to referencePath
	 * @param referencePath : the ABSOLUTE path of reference to which relativePath is relative
	 * @return the absolute path corresponding to the argument path
	 * @throws InvalidPathException if one of the given path is invalid
	 */
	public String toAbsolutePath(String relativePath, String referencePath) throws InvalidPathException{
		if(!currentVFS.pathExists(referencePath))
			throw new InvalidPathException();
		if(relativePath.startsWith("."))
			relativePath=relativePath.substring(1);
		if(relativePath.startsWith("."))
			return toAbsolutePath(relativePath, parentDirectory(referencePath));
		if(relativePath.startsWith("/")){
			if(referencePath.endsWith("/"))
				relativePath=relativePath.substring(1);
		}
		else{
			if(!referencePath.endsWith("/") && !referencePath.isEmpty() && !relativePath.isEmpty())
				relativePath="/"+relativePath;
		}
		String res=referencePath+relativePath;
		if(currentVFS.pathExists(res))
			return res;
		else
			throw new InvalidPathException();
	}
	/**
	 * An overloaded version of toAbsolutePath(String relativePath, String referencePath) using currentPath as the ReferencePath.
	 * @param relativePath : a path relative to currentPath
	 * @return the absolute path corresponding to relativePath
	 * @throws InvalidPathException if the given relativePath is invalid
	 */
	public String toAbsolutePath(String relativePath) throws InvalidPathException{
		return toAbsolutePath(relativePath, currentPath);
	}
	/**
	 * Sets the currentPath to the new specified path (same as setCurrentPath, but using relative path)
	 * @param relativePath : a path relative to currentPath pointing to the new working path
	 * @throws InvalidPathException if the given relativePath is invalid
	 */
	public void goTo(String relativePath) throws InvalidPathException{
		String newpath=toAbsolutePath(relativePath);
		setCurrentPath(newpath);
	}
	/**
	 * Sets the currentPath to the position of the given VItem.
	 * @param i : a VItem THAT MUST BE A DIRECT SUCCESSOR OF CURRENTVITEM, and which will become the new currentVItem
	 * @throws VItemNotFoundException if i is not a direct successor of currentVItem
	 */
	public void goTo(VItem i) throws VItemNotFoundException{
		if(currentVItem.getSuccessors().contains(i)){
			try{goTo(i.getName());}
			catch(InvalidPathException e){}//Does not happen.
		}
		else
			throw new VItemNotFoundException();
	}
	/**
	 * Changes the absolute path of a VItem in the currentVFS. Won't do anything if trying to move root.
	 * @param oldpath : the current position of the moved VItem
	 * @param newPath : the future position of the moved VItem, including its new name.
	 * The position (except for its new name) must exist.
	 * @throws VItemNotFoundException : if oldpath is invalid
	 * @throws InvalidPathException : if newPath is in a non-existent directory
	 * @throws NameConflictException : if there is already a VItem at newPath
	 */
	public void move(String oldpath, String newPath) throws VItemNotFoundException, InvalidPathException, NameConflictException{
		VItem test=null;
		try{test=currentVFS.getPath(oldpath);
		}catch(InvalidPathException e){throw new VItemNotFoundException();}
		
		if(test==currentVFS.getRoot())
			return;
		
		VItem parent=null;
		try{parent=currentVFS.getPath(parentDirectory(oldpath));
		}catch(InvalidPathException e){}//Does not happen since oldpath is valid
		
		if(currentVFS.pathExists(newPath))
			throw new NameConflictException();//We have to check there, because if any exception happens,
		//we don't want to damage anything (i.e don't do any remove/setName/...)
		
		VItem target=null;
		target=currentVFS.getPath(parentDirectory(newPath, false));//Any InvalidPathException here will come
		//from getPath, meaning the newPath is not valid
		
		//Here, we are good. Let's proceed.
		parent.remove(test);
		test.setName(newPath.substring(newPath.lastIndexOf("/")+1));
		target.add(test);//No NameConflictException should be thrown here
	}
}
