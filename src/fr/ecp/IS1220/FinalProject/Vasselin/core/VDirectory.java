package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VDirectory implements VItem, Serializable {
	
	private static final long serialVersionUID = 1632944914587944390L;
	
	//Attributes
	
	private List<VDirectory> directories;
	private List<VFile> files;
	private String name;
	
	//Constructors
	
	/**
	 * Creates a directory named name containing directories and files.
	 * @param name : the name of this directory
	 * @param directories : list of the direct successors directories of this.
	 * @param files : list of the direct successors files of this.
	 */
	public VDirectory(String name,List<VDirectory> directories, List<VFile> files) {
		super();
		this.name = name;
		this.directories = directories;
		this.files = files;
	}
	
	/**
	 * Creates an empty directory named name.
	 * @param name : the name of this directory
	 */
	public VDirectory(String name) {
		super();
		this.name = name;
		this.directories = new ArrayList<VDirectory>();
		this.files = new ArrayList<VFile>();
	}

	/**
	 * Creates an empty directory named "New directory".
	 */
	public VDirectory() {
		super();
		name = "New directory";
		directories = new ArrayList<VDirectory>();
		files = new ArrayList<VFile>();
	}

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
		List<VItem> res = new ArrayList<VItem>();
		res.addAll(directories);
		res.addAll(files);
		return res;
	}

	@Override
	public void add(VItem i) throws NameConflictException{
		for(VItem j : getSuccessors()){
			if(j.getName()==i.getName())
				throw new NameConflictException();
		}
		
		if(i instanceof VDirectory)
			directories.add((VDirectory)i);
		else if(i instanceof VFile)
			files.add((VFile)i);
	}

	@Override
	public VItem remove(VItem s) throws VItemNotFoundException {
		if(!contains(s))
			throw new VItemNotFoundException();
		if(s instanceof VDirectory){
			VDirectory res = (VDirectory)s;
			directories.remove(s);
			return res;
		}
		else if(s instanceof VFile){
			VFile res = (VFile)s;
			files.remove(s);
			return res;
		}
		return null;//never reached
	}

	@Override
	public boolean contains(VItem i) {
		if(i instanceof VDirectory)
			return directories.contains(i);
		else if(i instanceof VFile)
			return files.contains(i);
		else
			return false;//never reached
	}

	@Override
	public long getSize() {
		long res=0;
		for(VItem i : getSuccessors())
			res+=i.getSize();
		return res;
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
	public void exportVItem(Path path) throws IOException {
		File f = path.toFile();
		if(!f.isDirectory())
			throw new IOException("The specified path is not a directory : "+f.getName());
		
		File me = new File(f,getName());
		me.mkdir();
		if (!me.exists())
		    throw new IOException("The creation of the directory "+getName()+" failed.");
		Path myImage = me.toPath();
		for(VItem v : getSuccessors()){
			v.exportVItem(myImage);
		}
	}
	
	//for debugging purposes only...
	//used in VItemFactoryTest
	public void print(int l){
		String alinea = new String();
		for(int i=0;i<l;i++)
			alinea+="    ";
		System.out.println(alinea+"//"+getName());
		for(VFile f : getFiles())
			System.out.println(alinea+f.getName());
		for(VDirectory d : getDirectories())
			d.print(l+1);
	}

}
