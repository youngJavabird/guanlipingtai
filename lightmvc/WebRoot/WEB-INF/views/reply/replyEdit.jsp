<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	

	$(function() {
		$('#replyEditForm').form({
			url : '${ctx}/reply/edit',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				
	
		
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.messager.alert('提示', result.msg, 'info');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
	});
	
	
</script>
<style>
	table td{
		width:120px;
		hight:30px;
	};
</style>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form enctype="multipart/form-data" id="replyEditForm" method="post">
		<input id="id" name="id" type="text" style="display:none" value="${reply.id}">
			<table class="grid">
				<tr>
					<td style="text-align:center">回复序号</td>
					<td colspan="3"><input name="name" type="text" style="width:311px;" maxlength="25" placeholder="请输入轮播图名称" class="easyui-validatebox span2" data-options="required:true" value="${reply.name}"></td>
				</tr>
				<tr>
					<td style="text-align:center">回复内容</td>
					<td colspan="3"><input name="detail" type="text" style="width:311px;"  placeholder="请输入轮播图详情" class="easyui-validatebox span2" data-options="required:true" value="${reply.detail}"></td>
				</tr>
				  <tr>
                                 <td style="text-align:center"><label>状态：</label></td>
                                   	 <td><select name="state" id="id">
                                     <c:choose>
							           <c:when test="${reply.state==1}">
							                <option id="0" value="0" >正常</option>
							               <option id="1" value="1" selected="selected">禁用</option>
							           </c:when>
							           <c:otherwise>
							               <option id="0" value="0" >正常</option>
							               <option id="1" value="1" >禁用</option>
							           </c:otherwise>
							           </c:choose> 
                                    
                                 </select></td>
                             </tr>	
			</table>
		</form>

	</div>
	<div></div>
</div>