<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	$(function() {
		
		$('#smstempAddForm').form({
			url : '${ctx}/smstemp/add',
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
					$('#smstempAddForm input').val('');
					$('#smstempAddForm textarea').val('');
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
		<form enctype="multipart/form-data" id="smstempAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">短信模板ID</td>
					<td colspan="3"><input name="msgid" style="width:311px;" class="easyui-numberbox" data-options="required:true,min:0,max:999999999" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">模板内容</td>
					<td colspan="3"><textarea name="smstemplet" rows="" cols="" maxlength="250" style="width:309px;hight:260px;" ></textarea></td>
				</tr>
				<tr>
					<td style="text-align:center">活动</td>
					<td colspan="3">
						<input name="activity_id"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/smstemp/activityComboxs',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    editable:false,
	                    panelHeight:200" value="">
					</td>
				</tr>
				<tr>
					<td style="text-align:center">交易类型</td>
					<td colspan="3">
						<input name="transtype"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/smstemp/transComboxs',
	                    method:'get',
	                    valueField:'transtype',
	                    textField:'transtypename',
	                    editable:false,
	                    panelHeight:200" value="">
					</td>
				</tr>
				<tr>
					<td style="text-align:center">备注</td>
					<td colspan="3"><textarea name="remark" rows="" cols="" maxlength="100" style="width:309px;hight:60px;" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>