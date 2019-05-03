package android.wait_pos.controller;


import java.util.Timer;

import javax.websocket.Session;

public class PersonInLine {
	private String mem_no;
	private int numberPlate;
	private Boolean isCall = false;
	private long deadline;
	private Boolean isCancel = false;
	private Boolean hasPassed = false;
	private Boolean isChecked = false;
	private Session memberSession;
	private int party_size;
	private Timer callMemTimer;
	
	public PersonInLine(String mem_no, int party_size, int numberPlate) {
		this.mem_no = mem_no;
		this.party_size = party_size;
		this.numberPlate = numberPlate;
	}
	
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public int getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(int numberPlate) {
		this.numberPlate = numberPlate;
	}
	public Boolean getIsCall() {
		return isCall;
	}
	public void setIsCall(Boolean isCall) {
		this.isCall = isCall;
	}
	
	public Boolean getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Boolean getHasPassed() {
		return hasPassed;
	}

	public void setHasPassed(Boolean hasPassed) {
		this.hasPassed = hasPassed;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Session getMemberSession() {
		return memberSession;
	}

	public void setMemberSession(Session memberSession) {
		this.memberSession = memberSession;
	}

	public int getParty_size() {
		return party_size;
	}

	public void setParty_size(int party_size) {
		this.party_size = party_size;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public Timer getCallMemTimer() {
		return callMemTimer;
	}

	public void setCallMemTimer(Timer callMemTimer) {
		this.callMemTimer = callMemTimer;
	}
	

}

