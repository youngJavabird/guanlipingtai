<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	$(function() {
		$('#goodsAddForm').form({
			url : '${ctx}/goods/add',
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
		<form id="goodsAddForm" enctype ="multipart/form-data" method="post">
			<table class="grid">
				<tr>
					<td>商品名称</td>
					<td><input name="name" placeholder="请输入商品名称" class="easyui-validatebox" data-options="required:true" value=""></td>
					<td>明细说明</td>
					<td><input name="detile"  placeholder="请输入明细说明" class="easyui-validatebox" data-options="required:true" value=""></td>
				</tr>
				<tr>
					 <td>供货商价格</td>
					<td><input name="supplierprice" placeholder="请输入卡供货商价格" class="easyui-numberbox" data-options="required:true" value=""></td>
					 <td>商品价格(分)</td>
					<td><input name="new_price" placeholder="请输入商品价格(分)" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr>  
			   <tr>
					<td>商品图说明一</td>
					<td><input name="pictureone" type="file" placeholder="请输入商品图说明一"  data-options="required:true" ></td>
					<td>商品图说明二</td>
					<td><input name="picturetwo" type="file" placeholder="请输入商品图说明二" data-options="required:true" ></td>
				</tr>
			 	<tr>
					<td>商品图说明三</td>
					<td><input name="picturethree" type="file" placeholder="请输入商品图说明三" data-options="required:true" ></td>
					<td>商品详情图</td>
					<td><input name="longpicture" type="file" placeholder="请输入商品详情图" data-options="required:true" ></td>
				</tr>
				<tr>
				 <td>商品列表图</td>
					<td><input id="pictureshop" name="pictureshop" type="file"  data-options="required:true" ></td> 
					<td>原价</td>
					<td><input name="price" placeholder="请输入原价" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td>库存</td>
					<td><input name="num"  placeholder="请输入商品库存" class="easyui-numberbox" data-options="required:true" value=""></td>
					<td>卡券数量</td>
					<td><input name="cardnum"  placeholder="请输入卡券数量" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr> 
				<tr>
					<td>活跃度</td>
					<td><input name="liveness"  placeholder="请输入活跃度" class="easyui-numberbox" data-options="required:true" value=""></td>
				</tr> 
				<tr>
						<td style="text-align:center">供货商名称</td>
					<td colspan="3">
						<input name="supplierid"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/productsupplierCombox',
	                    method:'get',
	                    valueField:'guid',
	                    textField:'suppliername',
	                    panelHeight:'auto',
	                    editable:false" value="">
					</td>
				</tr> 
				<tr>
				<td style="text-align:center">商品类型</td>
					<td colspan="3">
						<input name="product_type_id"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/producttypeCombox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false" value="">
					</td>
				</tr>
				<tr>
				<td style="text-align:center">卡券类型</td>
					<td colspan="3">
						<input name="cardtypeid"   style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/cardtype/combobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    panelHeight:'auto',
	                    editable:false" value="">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>