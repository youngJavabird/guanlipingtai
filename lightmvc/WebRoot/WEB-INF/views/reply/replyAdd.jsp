<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function() {
		
		$('#replyAddForm').form({
			url : '${ctx}/reply/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
		<form enctype="multipart/form-data" id="replyAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">回复序号</td>
					<td colspan="3"><input name="name" type="text" style="width:311px;" maxlength="25" placeholder="请输入回复序号" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">回复内容</td>
					<td colspan="3"><input name="detail" type="text" style="width:311px;" placeholder="请输入回复内容" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
			</table>
		</form>
	</div>
	<div></div>
</div>