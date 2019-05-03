package android.wait_pos.controller;

import javax.websocket.Session;

import org.apache.commons.collections4.map.ListOrderedMap;

public class Wait_Line {
	private Boolean open_wait = false;
	private ListOrderedMap<String, PersonInLine> wait_line = new ListOrderedMap<String, PersonInLine>();
	private int number_now = 1;
	
	public Boolean getOpen_wait() {
		return open_wait;
	}
	public void setOpen_wait(Boolean open_wait) {
		this.open_wait = open_wait;
	}
	public ListOrderedMap<String, PersonInLine> getWait_line() {
		return wait_line;
	}
	public void setWait_line(ListOrderedMap<String, PersonInLine> wait_line) {
		this.wait_line = wait_line;
	}
	public int getNumber_now() {
		return number_now;
	}
	public void setNumber_now(int number_now) {
		this.number_now = number_now;
	}
	public int getNumberPlate() {
		return number_now++;
	}
}

