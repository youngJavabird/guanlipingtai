<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
  <head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>活动送券导入</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" type="text/javascript">
	

  </script>
  </head>
  
<script>
function check(){
var file = $("#file").val();
if(file==null||file==""){
	alert("请选择文件！");
	return false;
}else{
	return true;
}
}
var msg="${msg}"
	 if(msg!=""){
		 alert(msg);
	 }
</script>
 <body>
	 <form action="${ctx}/message/importFile" method="post" onsubmit="return check()" enctype="multipart/form-data">
                       <table  width="440" border="0">                            
                             <tr>
                             <br/>
                             <br/>
                             选择文件：<input type = "file" name = "file" id = "file" style = "width:200px;height:30px;">
                             <br/>
                             <br/>
                             <br/>
                                选择活动：<select style="width: 180px;"  name="id" id="active">      
                               	 <c:forEach items="${list}" var="list">
                                  <option value = "${list.activitycode}-${list.cardtypeid}">${list.name}</option>
                                  </c:forEach>
                                  </select>                                
                             </tr>
                             <br/>
                             <br/>
                             <br/>
                             <tr>
                                 <td colspan="2" align="center"><input type="submit"  class="sub" value="导     入"></td>
                             </tr>
                       </table>
                       </form>

 </body>
</html>
