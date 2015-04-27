package fr.ecp.IS1220.FinalProject.Vasselin.clui;

public abstract class Command {
	
	//Attributes
	protected Parser parser;
	
	//Constructor
	public Command(Parser parser) {
		super();
		this.parser = parser;
	}
	
	//Abstract Methods
	
	/**
	 * @return the name of the command
	 */
	abstract public String getName();
	
	/**
	 * Parses com and executes it, or display an error message if com isn't a valid command
	 * @param com : the command line to be parsed.
	 */
	abstract public void parse(String com);
	
	abstract public void help();
}
