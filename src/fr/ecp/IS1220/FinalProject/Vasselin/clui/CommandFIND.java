package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;


/**
 * Usage :
 * find fvsName filename
 * find fileName
 */
public class CommandFIND extends Command {

	public CommandFIND(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "find";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==1){
			String filename = tk.nextToken();
			run(user.getCurrentVFS().getName(),filename);
		}else if(tk.countTokens()==2){
			String vfsName = tk.nextToken();
			String filename = tk.nextToken();
			run(vfsName,filename);
		}else if(tk.countTokens()>=3){
			run(tk.nextToken(),tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}

	public void run(String vfsName, String filename){
		String result = user.search(vfsName, filename);
		if(result == null){
			System.out.println("File "+filename+" not found in the vfs " + vfsName);
		}
		else{
			System.out.println("The file +"+filename+" in the vfs "+vfsName+ " is at the following path :\n"+result);
		}
	}

}
