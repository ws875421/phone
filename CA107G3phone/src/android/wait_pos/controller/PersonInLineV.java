package android.wait_pos.controller;

import javax.websocket.Session;

public class PersonInLineV {
	private String mem_no;
	private int numberPlate;
	private Boolean isCall = false;
	private long deadline;
	private Boolean hasPassed = false;
	private int party_size;
	
	public PersonInLineV(PersonInLine personInLine) {
		this.mem_no = personInLine.getMem_no();
		this.numberPlate = personInLine.getNumberPlate();
		this.isCall = personInLine.getIsCall();
		this.hasPassed = personInLine.getHasPassed();
		this.party_size = personInLine.getParty_size();
		this.deadline = personInLine.getDeadline();
	}
	
	
}
