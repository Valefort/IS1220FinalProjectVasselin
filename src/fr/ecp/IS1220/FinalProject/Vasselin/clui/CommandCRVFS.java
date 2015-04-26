package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.NameConflictException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NotAVFSException;

/**
 *Usage :
 * crvfs  concreteVFSPath maxSpace
 */

public class CommandCRVFS extends Command {
	
	public CommandCRVFS(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "crvfs";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()<=1){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==2){
			String concreteVFSPath = tk.nextToken();
			String maxSpaceString = tk.nextToken();
			long maxSpace = Long.valueOf(maxSpaceString).longValue();
			run(concreteVFSPath,maxSpace);
		}else if(tk.countTokens()>=3){
			String concreteVFSPath = tk.nextToken();
			String maxSpaceString = tk.nextToken();
			long maxSpace = Long.valueOf(maxSpaceString).longValue();
			run(concreteVFSPath, maxSpace);
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}
	
	/**
	 * Creates a new vfs at the specified path, and opens it.
	 * @param concreteVFSPath : the path of the newly created VFS. It also determines the vfs name. It is a string and not a path object.
	 * @param maxSpace : the maximum reachable size of the newly created vfs.
	 */
	public void run(String concreteVFSPath, long maxSpace){
		try{
			user.createVFS(Paths.get(concreteVFSPath), maxSpace);
		}catch(IOException e){
			System.out.println("Error : cannot create the vfs at the specified path : " + concreteVFSPath.toString());
		}
		try{
			user.openVFS(Paths.get(concreteVFSPath));
		}catch(IOException e){
			System.out.println("Error : cannot open the newly created VFS : IOException occured");
			e.printStackTrace();
		} catch (NotAVFSException e) {
			System.out.println("Error : the created file is not a VFS.");
			e.printStackTrace();
		} catch (NameConflictException e) {
			System.out.println("Error : there is already an opened VFS with the same name.");
			e.printStackTrace();
		}
		
	}

}
