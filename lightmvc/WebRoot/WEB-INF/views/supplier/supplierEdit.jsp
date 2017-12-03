<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

		$('#supplierEditForm').form({
			url : '${pageContext.request.contextPath}/supplier/edit',
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
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="supplierEditForm" method="post">
			<input id="id" name="id" type="text" style="display:none" value="${supplier.id}">
			<table class="grid">
				<tr>
					<td style="text-align:center">供货商名称</td>
					<td colspan="3"><input name="suppliername" type="text" style="width:311px;" maxlength="50" placeholder="请输入供货商名称" class="easyui-validatebox span2" data-options="required:true" value="${supplier.suppliername}"></td>
				</tr>
				<tr>
					<td style="text-align:center">发货地址</td>
					<td colspan="3"><input name="sendaddress" type="text" style="width:311px;" maxlength="100" placeholder="请输入发货地址" class="easyui-validatebox span2" data-options="required:true" value="${supplier.sendaddress}"></td>
				</tr>
				<tr>
					<td style="text-align:center">退换货地址</td>
					<td colspan="3"><input name="recvaddress" type="text" style="width:311px;" maxlength="100" placeholder="请输入退换货地址" class="easyui-validatebox span2" data-options="required:true" value="${supplier.recvaddress}"></td>
				</tr>
				<tr>
					<td style="text-align:center">联系人</td>
					<td colspan="3"><input name="linkman" type="text" style="width:311px;" maxlength="10" placeholder="请输入联系人" class="easyui-validatebox span2" data-options="required:true" value="${supplier.linkman}"></td>
				</tr>
				<tr>
					<td style="text-align:center">联系电话</td>
					<td colspan="3"><input name="phone" type="text" style="width:311px;" maxlength="15" placeholder="请输入联系电话" class="easyui-validatebox span2" data-options="required:true" value="${supplier.phone}"></td>
				</tr>
				<tr>
					<td style="text-align:center">活动状态</td>
					<td><select id="state" name="state" style="width:315px;">
							<option selected="" value="00">可用</option>
							<option value="01">禁用</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</div>