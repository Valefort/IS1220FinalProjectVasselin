package fr.ecp.IS1220.FinalProject.Vasselin.core;

public class VFS {
	//----------Attributes------------
	private VItem root;
	private long space;
	private long maxSpace;
	
	
	//----------Getters---------------
	public VItem getRoot() {
		return root;
	}
	public long getSpace() {
		return space;
	}
	public long getMaxSpace() {
		return maxSpace;
	}

	//----------Constructors----------
	
	
	
	/**
	 * @param root
	 * @param space
	 * @param maxSpace
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public VFS(VItem root, long space, long maxSpace) {
		super();
		this.root = root;
		this.space = space;
		this.maxSpace = maxSpace;
	}
	
	
	
	

}
