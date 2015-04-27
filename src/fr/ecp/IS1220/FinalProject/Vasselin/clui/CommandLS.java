package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFile;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;


/**
 * Usage :
 *
 */
public class CommandLS extends Command {

	public CommandLS(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "ls";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(!tk.hasMoreTokens()){
			run(parser.getCurrentVFS().getName(),"", parser.getCurrentPath());
			return;
		}
		String vfsname=tk.nextToken();
		if(tk.hasMoreTokens()){
			String args=tk.nextToken();
			if(tk.hasMoreTokens()){
				String pathname=tk.nextToken();
				if(tk.hasMoreTokens()){
					System.out.println("Warning : end of command ignored starting from "+tk.nextToken());
				}
				run(vfsname,args,pathname);
			}else{
				run(vfsname,"",args);
			}
		}else{
			run(parser.getCurrentVFS().getName(),"", vfsname);
		}
	}
	
	private void run(String vfsname, String args, String pathname) {
		VFS vfs = parser.getVFS(vfsname);
		if(vfsname.equals(""))
			vfs=parser.getCurrentVFS();
		if(vfs==null){
			System.out.println("Error : invalid VFS name in command ls : "+vfsname);
			return;
		}
		
		boolean getSizes=false;
		if(args.equals("-l"))
			getSizes=true;
		else if(!args.equals("")){
			System.out.println("Error : invalid argument given to ls : "+args);
			return;
		}
		
		VItem vitem = null;
		try{vitem=vfs.getPath(pathname);}
		catch(InvalidPathException e){System.out.println("Error : invalid path given to ls : "+pathname);return;}
		
		List<String> names = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<String> sizes = new ArrayList<String>();
		
		for(VItem i : vitem.getSuccessors()){
			names.add(i.getName());
			if(i instanceof VFile)
				types.add("f");
			else
				types.add("d");
			if(getSizes)
				sizes.add(String.valueOf(i.getSize()));
		}
		
		int tab=0;
		for(String n : names){
			if(n.length()>tab)
				tab=n.length();
		}
		
		String res = new String();
		for(int i=0;i<names.size();i++){
			String line=String.format("%1$"+tab+"s", names.get(i));
			line+="  "+types.get(i);
			if(getSizes)
				line+=" "+sizes.get(i);
			res+=line+'\n';
		}
		System.out.print(res);
	}
	
	@Override
	public void help(){
		
	}

}
