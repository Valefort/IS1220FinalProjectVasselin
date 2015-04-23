package fr.ecp.IS1220.FinalProject.Vasselin.clui;

import java.util.StringTokenizer;

public class CommandCD extends Command {

	public CommandCD(User user) {
		super(user);
	}

	@Override
	public String getName() {
		return "cd";
	}

	@Override
	public void parse(String com) {
		StringTokenizer tk = new StringTokenizer(com);
	}

}
