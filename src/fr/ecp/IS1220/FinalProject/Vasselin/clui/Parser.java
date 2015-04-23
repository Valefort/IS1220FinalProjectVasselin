package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Parser extends User {

	//Attributes

	private List<Command> commands;

	//Constructors

	public Parser(){
		super();
		commands=new ArrayList<Command>();
		//Add new commands here
		commands.add(new CommandLS(this));
		commands.add(new CommandCD(this));
		
	}

	//Commands

	/**
	 * Executes the command line com. WARNING : DOES NOT SUPPORT PATHES/NAMES WITH SPACES IN IT
	 * @param com : a command line.
	 * @throws InvalidCommandException if com is not a valid command
	 */
	public void parseCommand(String com) throws InvalidCommandException{
		StringTokenizer tk = new StringTokenizer(com," ");
		String id = tk.nextToken();
		for(Command c : commands ){
			if(c.getName().equals(id)){
				c.parse(com.substring(c.getName().length()));
				return;
			}
		}
		System.out.println("Error : unknown command : "+id);
	}

	/**
	 * Executes all the commands of the given script.
	 * @param path : points to the script file (.vfss)
	 * @throws InvalidCommandException if a command in the script file is invalid
	 * @throws IOException if the script file cannot be read
	 */
	public void runScript(Path path) throws InvalidCommandException, IOException{
		File f = path.toFile();
		FileReader fr = new FileReader(f);
		BufferedReader in = new BufferedReader(fr);
		String line=in.readLine();
		while(line!=null){
			parseCommand(line);
		}
		in.close();
		fr.close();
	}

}
