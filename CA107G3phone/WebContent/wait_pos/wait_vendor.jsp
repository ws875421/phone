<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ page import="android.wait_pos.controller.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.collections4.map.ListOrderedMap" %>
<%@ page import="javax.websocket.Session" %>

<% 
String vendor_no = null;
if (request.getParameter("vendor_no") == null) {
	vendor_no = "V000001";
} else {
	vendor_no = request.getParameter("vendor_no");
}
Map<String, Map<Integer, Wait_Line>> wait_line_all = (Map) application.getAttribute("wait_line_all");

Map<Integer, Wait_Line> wait_line_vendor = (Map) wait_line_all.get(vendor_no);				
if (wait_line_vendor == null) {
	
	wait_line_vendor = new HashMap<Integer, Wait_Line>();
	wait_line_all.put(vendor_no, wait_line_vendor);
	
	wait_line_vendor.put(1, new Wait_Line());
	wait_line_vendor.put(2, new Wait_Line());
	wait_line_vendor.put(3, new Wait_Line());
	wait_line_vendor.put(4, new Wait_Line());
	wait_line_vendor.put(5, new Wait_Line());
}
Wait_Line wait_line;
ListOrderedMap<String, PersonInLine> wait_line_queue;

Map<String, Set<Session>> vendor_wait_sessions = (Map) application.getAttribute("vendor_wait_sessions");
Set<Session> vendor_wait_session = (Set) vendor_wait_sessions.get(vendor_no);
if (vendor_wait_session == null) {	
	vendor_wait_session = Collections.synchronizedSet(new HashSet<Session>());
	vendor_wait_sessions.put(vendor_no, vendor_wait_session);
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>候位管理</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


<!-- Side Nav -->
<style type="text/css">
	#sidenavOverlay {
	    display: none;

	    position: fixed;
	    bottom: 0;
	    left: 0;
	    right: 0;
   		top: 0;

   		z-index: 998;

	    background: rgba(0, 0, 0, 0.5);
	}
	#sidenavOverlay.active {
	    display: block;
	}


	#sidenav {
		position: fixed;
	    top: 0;
	    bottom: 0;

	    width: 280px;

	    left: -280px;

	    z-index: 999;
	    background: #fff;
	    color: #000;

	    box-shadow: 8px 0 6px -6px #333;
	}
	#sidenav.active {
	    left: 0;
	}
	
	.navbar {
		box-shadow: 0 8px 6px -6px #333;
	}

</style>

<!-- switch toggle -->
<style>
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 34px;
  height: 19px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 15px;
  width: 15px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196F3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(15px);
  -ms-transform: translateX(15px);
  transform: translateX(15px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 19px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>

<style type="text/css">
    .divAlert {
   		position: fixed;
   		bottom: 0;
   		left: 0;
   		display: flex;
   	}
   	
   	.divTitle {
   		text-align: center;
   		display: flex;
   	}
   	
   	
   	/* Card ===========================================*/
   	.divPersonInLine {
		width: 100%;
		box-shadow: 0 1px 1px 0 rgba(60,64,64,.08), 0 1px 3px 1px rgba(60,64,64,.16);
		border-radius: 3px;
		padding: 8px 15px 10px 15px;
		margin-bottom: 3px;		
	}	

	.divPilTop {
		display: flex;
	}

	.divNumberPlate {
		background-color: #eee;
		width: 50px;
		height: 50px;
		border-radius: 50%;
		text-align: center;
		line-height: 50px;
		font-size: 30px;
	}

	.divPersonInfo {
		margin-left: 20px;
	}
	.divMem_name {
		margin: 0 0 8px 0;
	}
	.divParty_size {
		margin: 0 0 5px 0;
	}
	
	.divTimer {
		border-top: 1px solid #dadce0;
		padding-top: 8px;
		display: flex;
		justify-content: space-between;
	}
	.spanTimer {
		margin: 0 0 0 70px;
		display: flex;
	}
	
	.btnChkMem {
	  all: unset;
	}
	
	/* wait line*/
	.divW_line {
		height: 610px;
		overflow: auto;
	}
	
	body {
		background-color: #aaa;
	}
</style>

</head>
<body>

<!-- Navbar -->
<nav class="navbar  bg-dark navbar-dark">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar" id="btnSidenav">
    <span class="navbar-toggler-icon"></span>
  </button>
  <span class="navbar-brand">Seek Food Table</span>
</nav>

<!-- Side Nav -->
<div id="sidenavOverlay"></div>

   <nav class="nav navbar-nav bg-dark" id="sidenav">
  <a class="nav-link text-white p-3" href="#">基本資料</a>
  <a class="nav-link text-white p-3" href="#">訂位管理</a>
  <a class="nav-link text-white p-3" href="#">菜單管理</a>
  <a class="nav-link text-white p-3" href="#">桌位管理</a>
</nav>

<div class="container-fluid">
<div class="row justify-content-around p-3 flex-nowrap">
<% 
for (int j = 1; j <= 5; j++){
	wait_line = wait_line_vendor.get(j);
%>
<!-- <%=j * 2%> 人桌 -->
<div class="divWaitTblSize bg-light mr-1" id="divWaitTblSize<%=j%>">
<input type="hidden" name="tbl_size" value="<%=j%>">

<div class="divBasicPanel mb-1" id="divBasicPanel<%=j%>">

<div class="divTitle mb-1 text-light bg-secondary p-2">
	<div style="margin: 0 auto;"><%=j * 2%> &nbsp; 人桌</div>
	<div class="divOpenWait d-flex flex-column justify-content-center">
		<label class="switch mb-0">
		  <input type="checkbox" class="chkOpenWait" id="chkOpenWait<%=j%>" value="<%=j%>" <%= wait_line.getOpen_wait() ? "checked" : ""  %>>
		  <span class="slider round"></span>
		</label>
	</div>
</div>
<div class="input-group mb-1 w-100 input-group-sm">
	<div class="input-group-prepend">
	    <span class="input-group-text bg-secondary text-white">目前號碼</span>   
	</div>
	<input type="text" id="inputNumberNow<%=j%>" value="<%= wait_line.getNumber_now() %>" class="form-control" aria-label="number now" style="text-align: center;" readonly>
	<div class="input-group-append">
		<button type="button" class="btnReturnZero btn btn-secondary" id="btnReturnZero<%=j%>">歸零</button>
	</div>
</div>

<div class="btn-group w-100 btn-group-sm" role="group" aria-label="Basic example">	
	<button type="button" class="btnClearLine btn btn-secondary" id="btnClearLine<%=j%>">清空</button>	
	<button type="button" class="btnCallMem btn btn-secondary" id="btnCallMem<%=j%>">叫號</button>
</div>



</div> <!-- end of divBasicPanel -->


<div class="divW_line" id="divW_line<%=j%>">
<% 
wait_line_queue = wait_line.getWait_line();
for (int i = 0; i < wait_line_queue.size(); i++){
%>

<div class="divPersonInLine">
<input type="hidden" name="mem_no" value="<%= wait_line_queue.get(i)%>">

 <div class="divPilTop">
 
	<div class="divNumberPlate">
		<%= wait_line_queue.getValue(i).getNumberPlate()%>
	</div>
	
	
	<div class="divPersonInfo">
		<h5 class="divMem_name">
		<%= wait_line_queue.get(i)%>
		</h5>
		<p class="divParty_size">	
		<%= wait_line_queue.getValue(i).getParty_size()%> &nbsp; 人
		</p>
	</div>

</div>

<%-- visibility:<%= wait_line_queue.getValue(i).getIsCall() ? "visible" : "hidden"%> --%>
<div class="divTimer" style='display: <%= wait_line_queue.getValue(i).getIsCall() ? "flex" : "none"%>'>
	<input type="hidden" name="deadline" value="<%= wait_line_queue.getValue(i).getDeadline()%>">
	<div class="spanTimer"><div class="spanM"></div>:<div class="spanS"></div>:<div class="spanMs"></div></div>
	<button type="button" class="btnChkMem">驗證</button>
</div>


</div> <!-- end of divPersonInLine -->
	
<% } %>

</div> <!-- end of divW_line -->

</div> <!-- end of divWaitTblSize -->
<% } %>
</div> <!-- end of row -->
</div> <!-- end of container -->

<!-- alert -->
<div id="result" class="divAlert"></div>


<!-- ======================= Ajax =========================== -->
<script type="text/javascript">
$(document).ready(function(){

// open wait function (開啟候位功能)
$(".chkOpenWait").change(function(){
	$.ajax({
	    url: "<%=request.getContextPath()%>/wait_pos/wait_pos.do",
	    type: 'post',
	    chkOpenWait : $(this),
	    tbl_size : $(this).val(),
	    open_wait : $(this).prop("checked"),
	    data: {
	      action : "open_wait_fun",
	      vendor_no : "<%= vendor_no %>",
	      tbl_size : $(this).val(),
	      open_wait : $(this).prop("checked")
	    },
	    dataType: "text",
	    success: function(response) {	    	
	    	//showAlert("alert-success", (this.tbl_size * 2) + " 人桌候位" + (this.open_wait ? "開放" : "關閉") + "成功");
	    },	    
	    error: function(xhr) {
	    	showAlert("alert-danger", (this.tbl_size * 2) + " 人桌候位" + (this.open_wait ? "開放" : "關閉") + "失敗");
	   		$(this.chkOpenWait).prop("checked", !$(this.chkOpenWait).prop("checked"));
	    }

	});
});  // End of $(".chkOpenWait").change

// 叫號
$(".btnCallMem").click(function(){
	var divWaitTblSize = $(this).parents("div.divWaitTblSize");
	var tbl_size_val = $(divWaitTblSize).find("input[name=tbl_size]").val();
	$.ajax({
	    url: "<%=request.getContextPath()%>/wait_pos/wait_pos.do",
	    type: 'post',
	    tbl_size : tbl_size_val,
	    data: {
	      action : "call_member",
	      vendor_no : "<%= vendor_no %>",
	      tbl_size : tbl_size_val
	    },
	    dataType: "text",
	    success: function(response) {
	    	if (response) {
	    		showAlert("alert-info", (this.tbl_size * 2) + " 人桌 " + response);
	    	}	 
	    },	    
	    error: function(xhr) {
	    	showAlert("alert-danger", (this.tbl_size * 2) + " 人桌叫號失敗");
	    }

	});
}); // End of $(".btnCallMem").click

//歸零
$(".btnReturnZero").click(function(){
	var divWaitTblSize = $(this).parents("div.divWaitTblSize");
	var tbl_size_val = $(divWaitTblSize).find("input[name=tbl_size]").val();
	debugger;
	$.ajax({
	    url: "<%=request.getContextPath()%>/wait_pos/wait_pos.do",
	    type: 'post',
	    tbl_size : tbl_size_val,
	    data: {
	      action : "return_zero",
	      vendor_no : "<%= vendor_no %>",
	      tbl_size : tbl_size_val
	    },
	    dataType: "text",
	    success: function(response) {
	    	 
	    },	    
	    error: function(xhr) {
	    	showAlert("alert-danger", (this.tbl_size * 2) + " 人桌歸零失敗");
	    }

	});	
}); // End of $(".btnChkMem").click

//清空
$(".btnClearLine").click(function(){
	var divWaitTblSize = $(this).parents("div.divWaitTblSize");
	var tbl_size_val = $(divWaitTblSize).find("input[name=tbl_size]").val();
	$.ajax({
	    url: "<%=request.getContextPath()%>/wait_pos/wait_pos.do",
	    type: 'post',
	    tbl_size : tbl_size_val,
	    data: {
	      action : "clear_line",
	      vendor_no : "<%= vendor_no %>",
	      tbl_size : tbl_size_val
	    },
	    dataType: "text",
	    success: function(response) {
	    	 
	    },	    
	    error: function(xhr) {
	    	showAlert("alert-danger", (this.tbl_size * 2) + " 人桌清空失敗");
	    }

	});	
}); // End of $(".btnClearLine").click

// 驗證 (check member)
$(".btnChkMem").click(function(){
	checkMember(this);
}); // End of $(".btnChkMem").click


}); // end of document.ready

//驗證 (check member)
function checkMember(btnChkMem){

	var divWaitTblSize = $(btnChkMem).parents("div.divWaitTblSize");
	var tbl_size_val = $(divWaitTblSize).find("input[name=tbl_size]").val();
	
	var divPersonInLine = $(btnChkMem).parents("div.divPersonInLine");
	var mem_no_val = $(divPersonInLine).find("input[name=mem_no]").val();
	
	var numberPlate = $(divPersonInLine).find("div.divNumberPlate").html();
	
	var spanTimer = $(btnChkMem).siblings("div.spanTimer")[0];
	
	$.ajax({
	    url: "<%=request.getContextPath()%>/wait_pos/wait_pos.do",
	    type: 'post',
	    tbl_size : tbl_size_val,	      
	    mem_no : mem_no_val,
	    numberPlate : numberPlate,
	    data: {
	      action : "check_member",
	      vendor_no : "<%= vendor_no %>",
	      tbl_size : tbl_size_val,	      
	      mem_no : mem_no_val
	    },
	    spanTimer: spanTimer,
	    dataType: "text",
	    success: function(response) {
 			if (response) {
 				showAlert("alert-warning", response);
 			}
	    },	    
	    error: function(xhr) {
	    	showAlert("alert-danger", (this.tbl_size * 2) + " 人桌  " + this.numberPlate + " 號驗證失敗");
	    }

	});	

} // End of checkMember

</script>

<!-- ======================= Web Socket =========================== -->
<script>

	var vendorWaitMgmtWS;
	
	// connect
	window.addEventListener("load", function() {
		var MyPoint = "/VendorWS/<%= vendor_no %>"; // servlet ServerEndpoint
		var path = window.location.pathname; // /WebSocketChatWeb/index.html
		var webCtx = path.substring(0, path.indexOf('/', 1)); // /WebSocketChatWeb
		var endPointURL = "ws://" + window.location.host + webCtx + MyPoint; 
		
		// create a websocket
		vendorWaitMgmtWS = new WebSocket(endPointURL); // connect ot server ServerEndpoint servlet

		
		// onopen
		vendorWaitMgmtWS.onopen = function(event) {
			showAlert("alert-success", "Web Socket 已連線");
		};
		
		// onmessage
		vendorWaitMgmtWS.onmessage = function(event) {
			
			var jsonObj = JSON.parse(event.data);
			switch (jsonObj.action) {
				case "openWaitFun": // 變更候位功能狀態
					showAlert("alert-info", (jsonObj.tbl_size * 2) + " 人桌候位功能已" + (jsonObj.open_wait ? "開啟" : "關閉"));										
					openWaitFun(jsonObj.tbl_size, jsonObj.open_wait);
					break;
				case "setDeadline": // 叫號
					showAlert("alert-info", (jsonObj.tbl_size * 2) + " 人桌 " + jsonObj.numberPlate + " 號叫號成功");
					setDeadline(jsonObj.tbl_size, jsonObj.mem_no, jsonObj.deadline);
					break;
				case "returnZero": // 刷新隊伍
					showAlert("alert-info", (jsonObj.tbl_size * 2) + " 人桌候位號碼已歸零");										
					setNumberNow(jsonObj.tbl_size, 1);
					break;
				case "refreshLine": // 刷新隊伍
					if (jsonObj.event == "insert") {
						var jsonObj2 = JSON.parse(jsonObj.result);
						showAlert("alert-info", jsonObj2.result);
						setNumberNow(jsonObj.tbl_size, jsonObj2.number_now);
					} else {
						showAlert("alert-info", jsonObj.result);
					}															
					refreshLine(jsonObj.tbl_size, jsonObj.w_line);
					break;
				case "clearLine": // 清空隊伍
					showAlert("alert-info", (jsonObj.tbl_size * 2) + " 人桌候位列表已清空");										
					clearLine(jsonObj.tbl_size);
					break;
				//case "renewNumberNow": // 更新號碼牌
				//	setNumberNow(jsonObj.tbl_size, jsonObj.number_now);
				//	break;
					
			}
		};
		
		// onclose
		vendorWaitMgmtWS.onclose = function(event) {
			showAlert("alert-danger", "Web Socket 已斷線");
		};
	} ,false);
	
	// disconnect
	window.addEventListener("unload", function() {
		vendorWaitMgmtWS.close();
	},false);
	
// ========================================================================
	// 變更候位功能狀態
	function openWaitFun(tbl_size, open_wait) {
		var chkOpenWait = document.getElementById("chkOpenWait" + tbl_size);
		chkOpenWait.checked = open_wait;
	}
	
	// 叫號 設定期限
	function setDeadline(tbl_size, mem_no, deadline){
		var divW_line = document.getElementById("divW_line" + tbl_size);
		var divPersonInLine = $(divW_line).find("input[name=mem_no][value="+ mem_no +"]").parents("div.divPersonInLine");
		var divTimer = $(divPersonInLine).find("div.divTimer");
		$(divTimer).css("display", "flex");
		var spanTimer = $(divTimer).find("div.spanTimer")[0];
		
		setTimeout(function(){countdown(spanTimer, deadline)}, 1);
	}
	
	function countdown(spanTimer, deadline){
		var timeRemain = deadline - new Date().getTime();
		if (timeRemain > 0 && !spanTimer.classList.contains("stop")) {
			var ms = timeRemain % 1000;
			var total_s = Math.floor(timeRemain / 1000);
			var s = total_s % 60;
			var m = Math.floor(total_s / 60);
			$(spanTimer).find("div.spanM").html(m);
			$(spanTimer).find("div.spanS").html(s);
			$(spanTimer).find("div.spanMs").html(ms);
			setTimeout(function(){countdown(spanTimer, deadline)}, 1);
		}
	}

	// init Timer	
	$(document).ready(function(){
		var divTimers = document.getElementsByClassName("divTimer");
		var len = divTimers.length;
		for(var i = 0; i < len; i++) {
			if (divTimers[i].style.display == "flex") {
				(function(n){					
					var spanTimer = $(divTimers[n]).find("div.spanTimer")[0];
					var deadline = $(divTimers[n]).find("input[name=deadline]").val();
					setTimeout(function(){countdown(spanTimer, deadline)}, 1);									
				})(i);
				
			} // End of if
				
		} // End of for
	
	}); // End of document.ready
	
	// 號碼牌設定
	function setNumberNow(tbl_size, number_now) {
		var inputNumberNow = document.getElementById("inputNumberNow" + tbl_size);
		inputNumberNow.value = number_now;
	}
	
	// 清空隊伍
	function clearLine(tbl_size) {
		var divW_line = document.getElementById("divW_line" + tbl_size);
		$(divW_line).find("div.spanTimer").addClass("stop"); // stop timer
		$(divW_line).empty();
	}
	
	// 重載 line
	function refreshLine(tbl_size, w_line) {

		var divW_line = document.getElementById("divW_line" + tbl_size);
		$(divW_line).find("div.spanTimer").addClass("stop"); // stop timer
		$(divW_line).empty();
		var len = w_line.length;
		for (var i = 0; i < len; i++){
		(function(n) {
			var divPersonInLine = document.createElement("div");
				divPersonInLine.className = "divPersonInLine"; 
				divPilTop
				
			var divPilTop = document.createElement("div");
				divPilTop.className = "divPilTop"; 	
				
			var divNumberPlate = document.createElement("div");
				divNumberPlate.className = "divNumberPlate";
				divNumberPlate.innerHTML = w_line[n].numberPlate;
			
			// ================================================
			var divPersonInfo = document.createElement("div");
				divPersonInfo.className = "divPersonInfo";
			
				
			var divMem_name = document.createElement("h5");
				divMem_name.className = "divMem_name";
				divMem_name.innerHTML = w_line[n].mem_no;
				
			var divParty_size = document.createElement("p");
				divParty_size.className = "divParty_size";
				divParty_size.innerHTML = w_line[n].party_size + " 人";
				
			divPersonInfo.appendChild(divMem_name);
			divPersonInfo.appendChild(divParty_size);
			// ================================================
				
			var divTimer = document.createElement("div");
				divTimer.className = "divTimer";
				divTimer.style.display = w_line[n].isCall ? "flex" : "none";
				
			var spanTimer = document.createElement("div");
				spanTimer.className = "spanTimer";
			var spanM = document.createElement("div");
				spanM.className = "spanM";
			var textSep1 = document.createTextNode(":");
			var spanS = document.createElement("div");
				spanS.className = "spanS";
			var textSep2 = document.createTextNode(":");
			var spanMs = document.createElement("div");
				spanMs.className = "spanMs";
			spanTimer.appendChild(spanM);
			spanTimer.appendChild(textSep1);
			spanTimer.appendChild(spanS);
			spanTimer.appendChild(textSep2);
			spanTimer.appendChild(spanMs);
				
			var btnChkMem = document.createElement("button");
				btnChkMem.className = "btnChkMem";
				btnChkMem.innerHTML = "驗證";
				btnChkMem.addEventListener("click", function(){checkMember(this);}, false);
			
			var inputDeadline = document.createElement("input");
			inputDeadline.setAttribute('type', 'hidden');
			inputDeadline.setAttribute('name', 'deadline');
			inputDeadline.setAttribute('value', w_line[n].deadline);
				
			divTimer.appendChild(spanTimer);
			divTimer.appendChild(btnChkMem);
			divTimer.appendChild(inputDeadline);
			// ================================================	
			var inputMem_no = document.createElement("input");
			inputMem_no.setAttribute('type', 'hidden');
			inputMem_no.setAttribute('name', 'mem_no');
			inputMem_no.setAttribute('value', w_line[n].mem_no);
			// ================================================
			divPilTop.appendChild(divNumberPlate);
			divPilTop.appendChild(divPersonInfo);
			divPersonInLine.appendChild(divPilTop);
			divPersonInLine.appendChild(divTimer);			
			divPersonInLine.appendChild(inputMem_no);
			$(divW_line).append(divPersonInLine);
			
			// start Timer
			if (w_line[n].isCall) {
				setTimeout(function(){countdown(spanTimer, w_line[n].deadline)}, 1);
			}
		})(i);
		} // End of for
		
	} // End of refreshLine

	//var jsonObj = {
	//	"userName" : userName,
	//	"message" : message
	//};
	//vendorWaitMgmtWS.send(JSON.stringify(jsonObj));

</script>

<!-- alert -->
<script type="text/javascript">

//alert-primary
//alert-secondary
//alert-success
//alert-danger
//alert-warning
//alert-info
//alert-light
//alert-dark
//showAlert("alert-info", msg)
function showAlert(alertType, msg) {
	var divAlert = createAlert(alertType, msg);
    $("#result").prepend(divAlert);

    window.setTimeout(function() {
        $(divAlert).fadeTo(500, 0, function(){
            $(this).remove(); 
        });
            
    }, 4000);
}

function createAlert(alertType, msg) {
	var divAlert = document.createElement("div");
	
	divAlert.className = "alert alert-dismissible fade show";
	divAlert.classList.add(alertType);
	divAlert.setAttribute("role", "alert");

	var textMsg = document.createTextNode(msg);

	var btnClose = document.createElement("button");
	btnClose.setAttribute("type", "button");
	btnClose.setAttribute("class", "close");
	btnClose.setAttribute("data-dismiss", "alert");
	btnClose.setAttribute("aria-label", "Close");
	
	var spanClose = document.createElement("span");
	spanClose.setAttribute("aria-hidden","true");
	spanClose.innerHTML = "&times;";

	btnClose.appendChild(spanClose);

	divAlert.appendChild(textMsg);
	divAlert.appendChild(btnClose);

	return divAlert;
} 

$(document).ready(function(){
	$('#btnSidenav').on('click', function () {
        $('#sidenavOverlay').addClass('active');
        $('#sidenav').addClass('active');             
    });
    $('#sidenavOverlay').on('click', function () {
        $('#sidenavOverlay').removeClass('active'); 
        $('#sidenav').removeClass('active');              
    });
});
</script>
</body>
</html>