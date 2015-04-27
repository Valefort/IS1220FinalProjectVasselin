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

	public CommandIMPVFS(Parser parser) {
		super(parser);
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
			run(Paths.get(hostPath), parser.getCurrentVFS().getName(),parser.getCurrentPath());
		}else if(tk.countTokens()==2){
			String hostPath = tk.nextToken();
			String vfsPath = tk.nextToken();
			run(Paths.get(hostPath), parser.getCurrentVFS().getName(),vfsPath);
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
			parser.importItem(hostPath, vfsName, vfsPath);
		}catch(FileTooLargeException e1){
			System.out.println("Error : the item to be imported is too large. The actual free space is "+ parser.getVFS(vfsName).getFreeSpace());
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
	
	@Override
	public void help(){
		System.out.println("Usage :\n"
				+ "impvfs hostPath vfsName vfsPath\n"
				+ "impvfs hostPath vfsPath\n"
				+"impvfs hostPath\n WARNING hostPath has to be absolute.");
	}

}
