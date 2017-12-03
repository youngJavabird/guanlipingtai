<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<script type="text/javascript">

	$(function() {
		$('#orderinfoEditForm').form({
			url : '${ctx}/orderinfo/edit',
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
		<form enctype="multipart/form-data" id="orderinfoEditForm" method="post">
			
			<input id="id" name="id" type="text" style="display:none" value="${orderinfo.id}">
			<table class="grid">
				<tr>
					<td style="text-align:center">物流公司</td>
					<td colspan="3"><input name="logistics" type="text" style="width:311px;" maxlength="50" placeholder="请输入物流公司" class="easyui-validatebox span2" value="${orderinfo.logistics}"></td>
				</tr>
				<tr>
					<td style="text-align:center">物流单号</td>
					<td colspan="3"><input name="logisticsnum" type="text" style="width:311px;" maxlength="25" placeholder="请输入物流单号" class="easyui-numberbox" value="${orderinfo.logisticsnum}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div></div>
</div>