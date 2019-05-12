package android.wait_pos.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.commons.collections4.map.ListOrderedMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CallMemTask extends TimerTask {
	private String vendor_no;
	private Integer tbl_size;
	private Wait_Line wait_line;
	private String member_no;
	private ServletContext context;

	public CallMemTask(String vendor_no, Integer tbl_size, Wait_Line wait_line, String member_no, ServletContext context) {
		super();
		this.vendor_no = vendor_no;
		this.tbl_size = tbl_size;
		this.wait_line = wait_line;
		this.member_no = member_no;
		this.context = context;
	}



	@Override
	public void run() {
		
    	ListOrderedMap<String, PersonInLine> wait_line_queue = wait_line.getWait_line();
    	String result = null;
    	
    	synchronized(wait_line) {
    		PersonInLine pil = wait_line_queue.get(member_no);
    		if (pil == null || pil.getIsChecked() || pil.getIsCancel()) return; // 保險起見
    		// 倒數中清空
    		
    		if (pil.getHasPassed()) { // 過號過, 直接移除
    			wait_line_queue.remove(member_no);
            	result = (tbl_size * 2) + " 人桌 "+ pil.getNumberPlate() + " 號已從候位列表中移除";
            	SendToMember.beCanceledCall(vendor_no, tbl_size, member_no, context);
    		} else { // 沒有過號過, 往後移3號
    			pil.setHasPassed(true);
    			pil.setIsCall(false);
    			pil.setCallMemTimer(null);
    			pil.setDeadline(0);
    			wait_line_queue.remove(member_no);
    			if (wait_line_queue.size() >= 4) { // 隊伍有 4 人以上, 往後移 3 位
    				wait_line_queue.put(3, member_no, pil);
        			System.out.println(member_no + " has been put back 3 numbers!");
        			result = (tbl_size * 2) + " 人桌 "+ pil.getNumberPlate() + " 號已往後移 3 位";
        			
        			Map map = new HashMap<>();
    				for (int i = 0; i < wait_line.getWait_line().size(); i++) {
    					map.put(wait_line.getWait_line().get(i),
    							wait_line.getWait_line().indexOf(wait_line.getWait_line().get(i)));
    				}
    				Gson gson = new Gson();
    				StringBuilder jsonIn = new StringBuilder();
    				JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
    				String jsonStr = gson.toJson(map);
        			
        			
        			
        			SendToMember.renewGpBeforeCall("putBack", wait_line, jsonStr, vendor_no, 3, context);
    			} else { // 隊伍不足 4 人以上, 移到最後一位
    				wait_line_queue.put(member_no, pil);
    				System.out.println(member_no + " has been put back to last numbers! There is less than 3 numbers in line.");
    				result = (tbl_size * 2) + " 人桌 "+ pil.getNumberPlate() + " 號已移到最後一 位";
    				
    				Map map = new HashMap<>();
    				for (int i = 0; i < wait_line.getWait_line().size(); i++) {
    					map.put(wait_line.getWait_line().get(i),
    							wait_line.getWait_line().indexOf(wait_line.getWait_line().get(i)));
    				}
    				Gson gson = new Gson();
    				StringBuilder jsonIn = new StringBuilder();
    				JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
    				String jsonStr = gson.toJson(map);
    				
    				
    				SendToMember.renewGpBeforeCall("putBack", wait_line, jsonStr, vendor_no, wait_line_queue.size() - 1, context);
    			}
    			
    		}
    		
    		SendToVendor.refreshLine("putback", wait_line,  tbl_size,  vendor_no, result, context);

    	} // End of synchronized(wait_line)
    	
    	
	} // End of void run()

}
