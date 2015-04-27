package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.FileTooLargeException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.NameConflictException;


/**
 * Usage :
 * impvfs hostPath vfsName vfsPath 
 * WARNING : vfsPath has to be absolute.
 * 
 * 
 * impvfs hostPath vfsPath
 * 		The vfs where the importation is performed will be the current one.
 * 
 * 
 * impvfs hostPath
 * 		The vfs where the importation is performed will be the current one.
 *		The virtual directory where the importation is performed will be the current one.
 * 
 */
public class CommandIMPVFS extends Command {

	public CommandIMPVFS(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "impvfs";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
		if(tk.countTokens()==0){
			System.out.println("Error : not enough arguments given to " + getName());
		}else if(tk.countTokens()==1){
			String hostPath = tk.nextToken();
			run(Paths.get(hostPath), user.getCurrentVFS().getName(),user.getCurrentPath());
		}else if(tk.countTokens()==2){
			String hostPath = tk.nextToken();
			String vfsPath = tk.nextToken();
			run(Paths.get(hostPath), user.getCurrentVFS().getName(),vfsPath);
		}else if(tk.countTokens()==3){
			String hostPath = tk.nextToken();
			String vfsName = tk.nextToken();
			String vfsPath = tk.nextToken();
			run(Paths.get(hostPath), vfsName,vfsPath);
		}else if(tk.countTokens()>=4){
			String hostPath = tk.nextToken();
			String vfsName = tk.nextToken();
			String vfsPath = tk.nextToken();
			run(Paths.get(hostPath), vfsName,vfsPath);
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}
	}

	public void run(Path hostPath, String vfsName, String vfsPath){
		try{
			user.importItem(hostPath, vfsName, vfsPath);
		}catch(FileTooLargeException e1){
			System.out.println("Error : the item to be imported is too large. The actual free space is "+ user.getVFS(vfsName).getFreeSpace());
		}catch(IOException e2){
			System.out.println("Error : importation failed, hostpath may be incorrect : "+ hostPath);
		}catch(NameConflictException e3){
			System.out.println("Error : an item with the same name already exists in the given virtual directory.");
		} catch (MisLeadingPathException e4) {
			System.out.println("Error : the vfsPath, "+vfsPath + " was expected to lead to a directory, whereas the given path lead to a file.");
		} catch (InvalidPathException e5) {
			System.out.println("Error : the vfsPath, "+vfsPath + " is invalid.");
		}
	}

}
