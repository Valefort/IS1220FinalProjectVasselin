package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser extends User {

	//Attributes

	private List<Command> commands;

	//To get available commands in the help command

	public List<Command> getCommands(){
		return commands;
	}

	public Command getCommand(String commandName){
		for(Command c: commands){
			if(c.getName().equals(commandName)){
				return c;
			}
		}
		return null;
	}

	//Constructors

	public Parser(){
		super();
		commands=new ArrayList<Command>();
		//Add new commands here
		commands.add(new CommandLS(this));
		commands.add(new CommandCD(this));
		commands.add(new CommandMV(this));
		commands.add(new CommandCP(this));
		commands.add(new CommandRM(this));
		commands.add(new CommandFREE(this));
		commands.add(new CommandFIND(this));
		commands.add(new CommandCRVFS(this));
		commands.add(new CommandIMPVFS(this));
		commands.add(new CommandEXPVFS(this));
		commands.add(new CommandHELP(this));
		commands.add(new CommandRMVFS(this));
		commands.add(new CommandOPENVFS(this));

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


	public static void main(String[] args) {
		Parser mainUser = new Parser();
		String com = new String();
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to our VFS interface !");

		do{
			System.out.println("Type command (type \"help\" for further informations), or type \"stop\" to stop using the Interface.");
			com = sc.nextLine();
			if(com.equals("stop")){
				System.out.println("Goodbye !");
				sc.close();
				break;
			}else{
				try {
					mainUser.parseCommand(com);
				} catch (InvalidCommandException e) {System.out.println("Error : invalid command : "+com);}
			}	
		}while(true);
	}


}
