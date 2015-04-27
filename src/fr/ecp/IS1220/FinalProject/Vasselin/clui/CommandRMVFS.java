package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;


/**
 * Usage :
 * rmvfs vfsName
 *
 */
public class CommandRMVFS extends Command {

	public CommandRMVFS(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "rmvfs";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==1){
			String vfsName = tk.nextToken();
			run(vfsName);
		}else if(tk.hasMoreTokens()){
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}
	
	public void run(String vfsName){
		try{
		user.removeVFS(user.getVFS(vfsName));
		}
		catch(SecurityException e){
			System.out.println("Error : error during the deletion of the vfs.");
		}
	}

}
