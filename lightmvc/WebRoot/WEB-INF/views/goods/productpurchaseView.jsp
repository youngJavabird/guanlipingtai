<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<title>商品限购详情</title>
<script type="text/javascript">
$(function() {
	
	
$("#description").val('${demo.description}');
});

 


function deleteFun(id) {

	parent.$.messager.confirm('询问', '您是否要删除当前限购？', function(b) {
		if (b) {
			progressLoad();
			$.post('${ctx}/goods/deletepurchase', {
				id : id
			}, function(result) {
				if (result.success) {
					parent.$.messager.alert('提示', result.msg, 'info');					
				   //$(parent.window.sss).dialog('close')
					 $(parent.window.sss).dialog('refresh','${ctx}/goods/purchaseviewPage?id='+${	product.id})
		
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
				 <td>详情信息</td>
					<td><c:forEach items="${purchase}" var="fllist">	
					            起始时间：${fllist.shelfbegintime}</br>
					            结束时间：${fllist.shelfendtime}</br>
					            库存：${fllist.shelfnum}</br>
					            库存比例：${fllist.scale}</br>
							            		<input id="id" name="id" type="text" style="display:none" value="${	fllist.id}">						  
							            		 <a href="javascript:void(0)"  onclick="deleteFun(${fllist.id})" >删除</a>	</br>						            		 			             
							 </c:forEach>
					</td> 		
						</tr>
			</table>		
</div>