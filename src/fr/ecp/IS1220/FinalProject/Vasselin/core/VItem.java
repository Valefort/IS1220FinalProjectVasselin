package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.util.List;

public interface VItem {
	
	/**
	 * @return the list of the successors of this.
	 */
	public List<VItem> getSuccessors();
	
	/**
	 * @param adds the VItem i to the successors of this.
	 */
	public void add(VItem i);
	
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
	 * @return the name of this (which is used in the pathes internal to the VSF).
	 */
	public String getName();
	
	/**
	 * @param name : the new name of this.
	 */
	public void setName(String name);
}
