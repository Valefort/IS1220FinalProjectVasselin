package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.Serializable;
import java.util.List;

public class VFile implements Serializable, VItem {

	private static final long serialVersionUID = 7146324727556062468L;
	
	//Attributes
	
	//Implementation of VItem

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
