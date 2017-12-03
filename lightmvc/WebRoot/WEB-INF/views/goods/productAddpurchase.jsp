<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	$(function() {

		$('#goodsAddpurchaseForm').form({
			url : '${ctx}/goods/addpurchase',
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
		
		
		$("#description").val('${demo.description}');

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="goodsAddpurchaseForm" enctype ="multipart/form-data" method="post">
		<input id="product_id" name="product_id" type=+"text" style="display:none" value="${product.id}">
		<input name="picture" type="file" style="display:none" placeholder="请输入商品属性图"  data-options="required:false" >
			<table class="grid">
				<tr>
					<td style="text-align:center">时间</td>
					<td><input name="shelfbegintime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'HH:mm:ss'})" readonly="readonly" />至
					<input  name="shelfendtime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'HH:mm:ss'})" readonly="readonly" />
					
					</td>
				</tr>
				<tr>
					<td>库存</td>
					<td><input name="shelfnum"  placeholder="请输入商品库存" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr> 
				<tr>
					<td>比例</td>
					<td><input name="scale"  placeholder="请输入商品库存比例" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr> 
			</table>
		</form>
	</div>
</div>