package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VFile implements Serializable, VItem {

	private static final long serialVersionUID = 7146324727556062468L;

	//Attributes

	private String name;
	private byte[] data;

	//Constructors

	/**
	 * Builds a VFile containing the data and named name.
	 * @param name : the name of this.
	 * @param data : the data contained by this. WARNING : the max size of this array is 2^31, which represents 2 GB of data.
	 */
	public VFile(String name, byte[] data) {
		super();
		this.name = name;
		this.data = data;
	}

	/**
	 * Builds an empty VFile named name.
	 * @param name : the name of this.
	 */
	public VFile(String name) {
		super();
		this.name = name;
		data = new byte[0];
	}

	/**
	 * Builds an empty VFile named "New file".
	 */
	public VFile() {
		super();
		name = "New file";
		data = new byte[0];
	}

	//Getters and Setters

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	//Implementation of VItem

	@Override
	public List<VItem> getSuccessors() {
		return new ArrayList<VItem>();
	}

	@Override
	public void add(VItem i) {
		//Defensive behaviour
	}

	@Override
	public VItem remove(VItem s) throws VItemNotFoundException {
		if(s!=null)
			throw new VItemNotFoundException();
		return null;
	}

	@Override
	public boolean contains(VItem i) {
		return (i==null);
	}

	@Override
	public long getSize() {
		return (long)data.length;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public void exportVItem(Path path) throws IOException{
		File f = path.toFile();
		if(!f.isDirectory())
			throw new IOException("The specified path is not a directory : "+f.getName());
		
		OutputStream out1 = new FileOutputStream(f);
		DataOutputStream out = new DataOutputStream(out1);
		
		out.write(getData());
		
		out.close();
		out1.close();
	}
	
}

