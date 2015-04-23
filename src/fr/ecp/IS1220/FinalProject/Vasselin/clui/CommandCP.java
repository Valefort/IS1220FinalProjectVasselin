package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.*;

public class CommandCP extends Command {

	public CommandCP(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "cp";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to cp");
		}else if(tk.countTokens()==1){
			run(user.getCurrentVFS().getName(),user.getCurrentPath(),tk.nextToken());
		}else if(tk.countTokens()==2){
			run(user.getCurrentVFS().getName(),tk.nextToken(),tk.nextToken());
		}else if(tk.countTokens()>=3){
			run(tk.nextToken(),tk.nextToken(), tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}

	private void run(String vfsname, String sourcepath, String targetpath){
		VFS vfs = user.getVFS(vfsname);
		if(vfs==null){
			System.out.println("Error : unknown VFS name in mv : "+vfsname);
			return;
		}
		
		user.setCurrentVFS(vfs);
		
		try{user.setClipboard(deepCopy(vfs.getPath(sourcepath)));
		}catch(InvalidPathException e){
			System.out.println("Error : item to be copied not found : "+sourcepath);
			return;
		}
		
		try{user.setCurrentPath(user.parentDirectory(targetpath, false));}
		catch(InvalidPathException e){
			System.out.println("Error : invalid destination in cp : "+targetpath);
			return;
		}

		user.getClipboard().setName(targetpath.substring(targetpath.lastIndexOf("/")+1));

		try{user.getCurrentVItem().add(user.getClipboard());}
		catch(NameConflictException e){
			System.out.println("Error : there is already an item with this name : "+targetpath);
			return;
		}
	}
	
	protected VItem deepCopy(VItem item){
		byte[] data = null;
		VItem res=null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out=null;
		try{out = new ObjectOutputStream(baos);}catch(Exception e){}//Does not happen
		try{out.writeObject(item);}catch(IOException e){}//Does not happen
		data = baos.toByteArray();
		try{out.close();
		baos.close();}catch(IOException e){}//Does not happen
		InputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream in=null;
		try{in = new ObjectInputStream(bais);}catch(Exception e){}//Does not happen
		try{res=(VItem)in.readObject();}catch(Exception e){}//Does not happen
		try{in.close();
		bais.close();}catch(IOException e){}//Does not happen
		
		return res;
	}

}
