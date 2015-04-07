package fr.ecp.IS1220.FinalProject.Vasselin.core;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class VItemFactory {

	/**
	 * Creates a VItem from the file/directory targeted by path.
	 * @param path : the path from which to import the VItem
	 * @return a VItem created from the target of the path.
	 * @throws FileTooLargeException is thrown if a file whose size is more than 2GB is encountered.
	 * @throws IOException encountered while trying to read files are thrown too.
	 * @see VItem exportVItem
	 */
	public static VItem importVItem(Path path) throws FileTooLargeException, IOException{
		File f = path.toFile();
		if(f.isFile()){
			byte[] data=null;
			try{data=new byte[(int)f.length()];}
			catch(ClassCastException e){throw new FileTooLargeException();}
			InputStream in1 = new FileInputStream(f);
			DataInputStream in = new DataInputStream(in1);
			in.read(data);
			in.close();
			in1.close();
			return new VFile(f.getName(),data);
		}else{//here, f is a directory
			VItem res = new VDirectory(f.getName());
			for(String s : f.list()){
				res.add(importVItem(FileSystems.getDefault().getPath(s)));
			}
			return res;
		}
	}

}
