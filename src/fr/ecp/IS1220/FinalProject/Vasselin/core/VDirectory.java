package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.Serializable;
import java.util.List;

public class VDirectory implements VItem, Serializable {
	
	private List<VDirectory> directories;
	private List<VFile> files;
	
	//Constructors
	
	
	
	//Getters & Setters
	
	public List<VDirectory> getDirectories() {
		return directories;
	}

	public void setDirectories(List<VDirectory> directories) {
		this.directories = directories;
	}

	public List<VFile> getFiles() {
		return files;
	}

	public void setFiles(List<VFile> files) {
		this.files = files;
	}
	
	//VItem implementation

	@Override
	public List<VItem> getSuccessors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(VItem i) {
		// TODO Auto-generated method stub

	}

	@Override
	public VItem remove(VItem s) throws VItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(VItem i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
