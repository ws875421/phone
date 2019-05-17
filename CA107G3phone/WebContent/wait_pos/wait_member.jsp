<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ page import="android.wait_pos.model.*" %>
<%@ page import="java.util.*" %>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>wait_member</title>
</head>
<body>

<form action="<%=request.getContextPath()%>/wait_pos/wait_pos.do">
	
		廠商編號<input type="text" name="vendor_no"> <br>
		
		會員編號<input type="text" name="mem_no"> <br>
		
		人數<input type="number"  name="party_size" min="1" max="10" value="1"> <br>
		
		<button type="submit" class="getPosNum">候位取號</button>
		<input type="hidden" name="action" value="insert">
</form>


<br><br>

<!-- Cancel Wait -->
<form action="<%=request.getContextPath()%>/wait_pos/wait_pos.do">

		廠商編號<input type="text" name="vendor_no"> <br>
		
		會員編號<input type="text" name="mem_no"> <br>
		
		桌位<input type="number"  name="tbl_size" min="1" max="5" value="1"> <br>
		
		<button type="submit" class="cancel">取消候位</button>
		<input type="hidden" name="action" value="cancel">
</form>

</body>
</html>