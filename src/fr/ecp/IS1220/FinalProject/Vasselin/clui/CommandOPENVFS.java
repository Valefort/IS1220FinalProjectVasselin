package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.NameConflictException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NotAVFSException;


/**
 * Open an existing VFS
 * 
 * Usage :
 * openvfs path 
 *
 */
public class CommandOPENVFS extends Command {

	public CommandOPENVFS(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "openvfs";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to "+ getName());
		}else if(tk.countTokens()==1){
			run(tk.nextToken());
		}else if(tk.countTokens()>=2){
			run(tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}

	}

	@Override
	public void help() {
		// TODO Auto-generated method stub

	}
	
	public void run(String path){
		Path realPath = Paths.get(path);
		try {
			parser.openVFS(realPath);
		} catch (IOException e) {
			System.out.println("I/O Error : impossible to reach the vfs.");
			e.printStackTrace();
		} catch (NotAVFSException e) {
			System.out.println("Error : the selected file is not a vfs : "+path);
			e.printStackTrace();
		} catch (NameConflictException e) {
			System.out.println("Error : a vfs with the same name is already opened.");
			e.printStackTrace();
		}
	}

}
