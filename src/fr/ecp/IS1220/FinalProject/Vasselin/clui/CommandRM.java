package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemNotFoundException;

/**Usage :
 * rm vfsName absoluteVirtualPath
 * rm relativeVirtualPath
 */
public class CommandRM extends Command {


	public CommandRM(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "rm";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==1){
			String pathToRemove = tk.nextToken();
			try{
				run(user.getCurrentVFS().getName(),user.toAbsolutePath(pathToRemove));
			}catch(InvalidPathException e){
				System.out.println("Error : the following path " + pathToRemove + "was expected to be relative and was absolute instead, or invalid.");
			}
		}else if(tk.countTokens()==2){
			String vfsName = tk.nextToken();
			String pathToRemove = tk.nextToken();
			run(vfsName,pathToRemove);
		}else if(tk.countTokens()>=3){
			run(tk.nextToken(),tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}

	}

	private void run(String vfsName, String absolutePath){
		VFS vfs = user.getVFS(vfsName);
		if(vfs==null){
			System.out.println("Error : unknown VFS name in cd : "+vfsName);
			return;
		}	

		try{
			//setCurentPath is used there, because the given path is absolute.
			user.setCurrentPath(absolutePath);
		}catch(InvalidPathException e){
			System.out.println("Error : invalid path " + absolutePath);
		}
		VItem toRemove = user.getCurrentVItem();
		user.goToParentDirectory();

		try{
			user.getCurrentVItem().remove(toRemove);
		}catch(VItemNotFoundException e){
			System.out.println("Error : tried to remove the root or item to be removed not found.");
		}

	}

}
