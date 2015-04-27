package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NameConflictException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemNotFoundException;

public class CommandMV extends Command {

	public CommandMV(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "mv";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to mv");
		}else if(tk.countTokens()==1){
			run(parser.getCurrentVFS().getName(),parser.getCurrentPath(),tk.nextToken());
		}else if(tk.countTokens()==2){
			run(parser.getCurrentVFS().getName(),tk.nextToken(),tk.nextToken());
		}else if(tk.countTokens()>=3){
			run(tk.nextToken(),tk.nextToken(), tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Warning : ignoring end of command starting from "+tk.nextToken());
		}
	}
	
	private void run(String vfsname, String oldpath, String newpath){
		VFS vfs = parser.getVFS(vfsname);
		if(vfs==null){
			System.out.println("Error : unknown VFS name in mv : "+vfsname);
			return;
		}
		
		parser.setCurrentVFS(vfs);
		
		try{parser.move(oldpath, newpath);}
		catch(VItemNotFoundException e){
			System.out.println("Error : item not found in mv : "+oldpath);
		}
		catch(InvalidPathException e){
			System.out.println("Error : invalid destination in mv : "+newpath);
		}
		catch(NameConflictException e){
			System.out.println("Error : there is already an item with this name : "+newpath);
		}
	}
	
	@Override
	public void help(){
		
	}

}
