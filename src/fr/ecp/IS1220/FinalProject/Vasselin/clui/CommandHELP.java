package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

public class CommandHELP extends Command {
	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Mettre un attribut parser et non plus parser � toutes les commandes ?
	
	public CommandHELP(Parser parser) {
		super(parser);
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
//		if(tk.countTokens()==0){
//			run();
//		}else if(tk.countTokens()==1){
//			run(tk.nextToken());
//		}else if(tk.countTokens()>=2){
//			run(tk.nextToken());
//			if(tk.hasMoreTokens())
//				System.out.println("Error : ignoring end of command starting from "+tk.nextToken());
//		}
	}

//	public void run(){
//		System.out.println(""); //General info about the CLUI
//		for(Command c: parser.getCommands()){
//			c.help();
//		}
//	}
//	public void run(String commandName){
//		Command c = parser.getCommand(commandName);
//		if(c.equals(null)){
//			System.out.println("Unknown command : the following command does not exists :"+commandName);
//		}else{
//			c.help();
//		}
//	}
	
	@Override
	public void help(){
		
	}

}
