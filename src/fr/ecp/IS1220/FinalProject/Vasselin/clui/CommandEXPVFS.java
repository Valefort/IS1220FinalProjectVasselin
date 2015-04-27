package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * Usage :
 * expvfs vfsName hostpath
 * 
 * expvfs hostpath
 * 		The exported vfs will be the current one.
 *
 * WARNING : hostpath must be absolute. 
 * WARNING : To suit the requirements of the project, this usage does not respect the order convention used in impvfs
 */
public class CommandEXPVFS extends Command {

	public CommandEXPVFS(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "expvfs";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==1){
			String hostPath = tk.nextToken();
			run(Paths.get(hostPath), user.getCurrentVFS().getName());
			
		}else if(tk.countTokens()==2){
			//WARNING : the order convention here is not respected, to suit the specifications.
			String vfsName = tk.nextToken();
			String hostPath = tk.nextToken();
			run(Paths.get(hostPath), vfsName);
		}else if(tk.countTokens()>=3){
			//WARNING : the order convention here is not respected, to suit the specifications.
			String vfsName = tk.nextToken();
			String hostPath = tk.nextToken();
			run(Paths.get(hostPath), vfsName);
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}

	public void run(Path hostPath, String vfsName){
		try{
			user.exportVFS(hostPath, user.getVFS(vfsName));
		}catch(IOException e2){
			System.out.println("Error : exportation failed, hostPath may be incorrect : "+ hostPath);
		}
	}
	
	@Override
	public void help(){
		
	}
}
