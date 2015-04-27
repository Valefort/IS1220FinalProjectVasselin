package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.nio.file.Paths;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;

public class CommandFREE extends Command {

	public CommandFREE(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "free";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			run(parser.getCurrentVFS());
		}else if(tk.countTokens()==1){
			String path = tk.nextToken();
			try{
				parser.setCurrentVFS(Paths.get(path));
				run(parser.getCurrentVFS());
			}catch(Exception e){
				System.out.println("Error : unable to get to the path " + path);
			}
		}else if(tk.hasMoreTokens()){
			System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}

	}

	public void run(VFS vfs){
		System.out.println("The free space of the vfs " + vfs.getName() + "is : " + vfs.getFreeSpace());

	}
	@Override
	public void help(){
		System.out.println("Usage :\nfree");
	}

}
