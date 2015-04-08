package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface VItem {

	/**
	 * @return the list of the successors of this.
	 */
	public List<VItem> getSuccessors();

	/**
	 * @param adds the VItem i to the successors of this.
	 * @throws NameConflictException is thrown if i has the same name as one of the successors of this.
	 */
	public void add(VItem i) throws NameConflictException;

	/**
	 * @param s is the direct successor of this that will be removed and returned.
	 * @return s is the direct successor of this that was removed.
	 * May throw a VItemNotFoundException if s is not a direct successor of this.
	 */
	public VItem remove(VItem s) throws VItemNotFoundException;

	/**
	 * @param i - the VItem we want to find in the direct successors of this.
	 * @return true if i is among the direct successors of this, false otherwise.
	 */
	public boolean contains(VItem i);

	/**
	 * @return the size of this, in bytes.
	 */
	public long getSize();

	/**
	 * @return the name of this (which is used in the paths internal to the VSF) (which should contain
	 * the extension of the file if this is a file)
	 */
	public String getName();

	/**
	 * @param name : the new name of this.
	 */
	public void setName(String name);
	
	/**
	 * exports this to a file or directory outside of the VFS.
	 * @param path : the path where to write this VItem. The path must target a directory,
	 * where the VItem will be exported (if not, exportVItem will throw an IOException). It must not contain the name of the written file, which will be
	 * the same as this.getName(). Of you want to change this name, you can either rename the file before
	 * or after exporting it.
	 */
	public void exportVItem(Path path) throws IOException;
}
