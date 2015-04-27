package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.*;

public class CommandCD extends Command {

	public CommandCD(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "cd";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to cd");
		}else if(tk.countTokens()==1){
			run(parser.getCurrentVFS().getName(),tk.nextToken());
		}else if(tk.countTokens()>=2){
			run(tk.nextToken(),tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}

	private void run(String vfsname, String pathname){
		VFS vfs = parser.getVFS(vfsname);
		if(vfs==null){
			System.out.println("Error : unknown VFS name in cd : "+vfsname);
			return;
		}

		String oldPath = parser.getCurrentPath();
		VFS oldVFS = parser.getCurrentVFS();

		parser.setCurrentVFS(vfs);
		try{parser.setCurrentPath(pathname);}
		catch(InvalidPathException e){
			try{parser.setCurrentPath(oldPath);
				parser.setCurrentPath(parser.toAbsolutePath(pathname));}
			catch(InvalidPathException e2){
				System.out.println("Error : invalid path in cd : "+pathname);
				parser.setCurrentVFS(oldVFS);
				try{parser.setCurrentPath(oldPath);}catch(InvalidPathException e3){
					System.out.println("Warning : impossible to recover the previous working location : "+oldPath);
				}
			}
		}
	}
	
	@Override
	public void help(){
		System.out.println("Usage :\n"
				+ "cd virtualPath");
	}

}
