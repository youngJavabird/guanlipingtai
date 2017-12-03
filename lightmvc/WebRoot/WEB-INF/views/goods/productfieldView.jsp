<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<title>商品属性详情</title>
<script type="text/javascript">
$(function() {
	
	
$("#description").val('${demo.description}');
});

 


function deleteFun(id) {

	parent.$.messager.confirm('询问', '您是否要删除当前属性？', function(b) {
		if (b) {
			progressLoad();
			$.post('${ctx}/goods/delete', {
				id : id
			}, function(result) {
				if (result.success) {
					parent.$.messager.alert('提示', result.msg, 'info');					
				//	$(parent.window.sss).dialog('close')
					 $(parent.window.sss).dialog('refresh','${ctx}/goods/fieldviewPage?id='+${	product.id})
		
				}
				progressClose();
			}, 'JSON');
		}
	});
}






</script>
</head>
<script>
 var sendmessage="${sendmessage}"
 if(sendmessage!=""){
  alert(sendmessage);
 }
</script>

<div class="easyui-layout" data-options="fit:true,border:false"  id="aldk">
	<input id="id" name="id" type="text" style="display:none" value="${	product.id}">
			<table class="grid">
				<tr>
					<td>商品名称</td>
					<td>${product.name}</td>
				</tr>
				<tr>
				 <td>一级属性</td>
					<td><c:forEach items="${fieldname}" var="fllist">	
					${fllist.name} 库存：${fllist.num}
							            		<input id="id" name="id" type="text" style="display:none" value="${	fllist.id}">						  
							            		 <a href="javascript:void(0)"  onclick="deleteFun(${fllist.id})" >删除</a>							            		 			             
							 </c:forEach>
					</td> 		
						</tr>
						<tr>
					<td>二级属性</td>
					<td><c:forEach items="${fieldnametwo}" var="fllist">
							            	     ${fllist.name} 库存：${fllist.num}							             
							            		<input id="id" name="id" type="text" style="display:none" value="${	fllist.id}">						  				
							                     	 <a href="javascript:void(0)"  onclick="deleteFun(${fllist.id})" >删除</a>			       		     
							 </c:forEach></td>	
				</tr>
			</table>		
</div>