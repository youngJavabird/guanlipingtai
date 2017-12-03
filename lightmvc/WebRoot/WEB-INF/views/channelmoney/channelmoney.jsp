<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
  <head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>渠道备份金查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript" type="text/javascript">
	

  </script>
  </head>
  
<script>

</script>
 <body>
	 <form action="${ctx}/channelmoney/check" method="post" onsubmit="return check1()" enctype="multipart/form-data">
                       <table  width="440" border="0">
                             <tr>
                                <select style="width: 180px;"  name="渠道编号" id="channelid">      
                                   <option value="1">银统</option>
                                    <!-- <option value="2">银统2</option> -->
                                  </select>                                
                             </tr>
                             <tr>
                                 <td colspan="2" align="center"><input type="submit"  class="sub" value="查     询"></td>
                             </tr>
                       </table>
                       </form>

 </body>
</html>
