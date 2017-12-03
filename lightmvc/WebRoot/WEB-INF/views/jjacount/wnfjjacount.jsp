<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
  <head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>久久券号核销</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" type="text/javascript">
	

  </script>
  </head>
  
<script>
 var sendmessage="${sendmessage}"
 if(sendmessage!=""){
 	 var message=sendmessage.substr(0,8);
 	 var qid=sendmessage.substring(8);
	if(message=="确定核销此券号？"){
		if(window.confirm(message)){
			window.location.href="/jjacount/check2?jjaccount="+qid+"";
			
		};
	}else{
	  alert(sendmessage);
	}
 }
</script>
 <body>
 
	 <form action="${ctx}/jjacount/check" method="post" onsubmit="return check1()" enctype="multipart/form-data">
                       <table  width="440" border="0">
                             <tr>
                                
                                 <td><input  type="text"  maxlength="50" value="${jjacount.jjaccount}"  name="jjaccount"> </td>
                             </tr>
                             <tr>
                                 <td colspan="2" align="center"><input type="submit"  class="sub" value="核     销"></td>
                             </tr>
                       </table>
                       </form>

 </body>
</html>
