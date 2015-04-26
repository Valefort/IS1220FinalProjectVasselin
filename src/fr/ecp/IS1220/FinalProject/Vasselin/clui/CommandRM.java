package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

import fr.ecp.IS1220.FinalProject.Vasselin.core.InvalidPathException;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VFS;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItem;
import fr.ecp.IS1220.FinalProject.Vasselin.core.VItemNotFoundException;

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
			run(user.getCurrentVFS().getName(),tk.nextToken());
		}else if(tk.countTokens()>=2){
			run(tk.nextToken(),tk.nextToken());
			if(tk.hasMoreTokens())
				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
		}

	}
	
	private void run(String vfsName, String path){
		VFS vfs = user.getVFS(vfsName);
		if(vfs==null){
			System.out.println("Error : unknown VFS name in cd : "+vfsName);
			return;
		}	
		
		try{ //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Plutôt dégueu : on fait comme si le chemin donné était relatif, en postulant que toAbsolutePath ne modifie pas le chemin s'il est déjà absolu
			user.goTo(path);
		}catch(InvalidPathException e){
			System.out.println("Error : invalid path " + path);
		}
		VItem toRemove = user.getCurrentVItem();
		user.goToParentDirectory();
		
		try{
			user.getCurrentVItem().remove(toRemove);
		}catch(VItemNotFoundException e){
			System.out.println("Error : tried to remove the root or itemto be removed not found.");
		}
		
	}

}
