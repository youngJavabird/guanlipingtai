<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	$(function() {
		$('#product_type_id').combotree({
			url : '${ctx}/goodstype/tree',
			parentField : 'id',
			lines : true,
			panelHeight : 'auto'
		});
		$('#suppliername').combotree({
			url : '${ctx}/goodssupplier/tree',
			parentField : 'suppliername',
			lines : true,
			panelHeight : 'auto'
		});
		$('#goodsEditForm').form({
			url : '${ctx}/goods/edit',
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

	function editActivity(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改',
			width : 660,
			height : 380,
			href : '${ctx}/goods/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsEditForm');
					f.submit();
				}
			} ]
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="goodsEditForm" enctype ="multipart/form-data" method="post">
		<input id="id" name="id" type="text" style="display:none" value="${product.id}">
		<input id="guid" name="guid" type="text" style="display:none" value="${product.guid}">
			<table class="grid">
				<tr>
					<td>商品名称</td>
					<td><input name="name" placeholder="请输入商品名称" class="easyui-validatebox" data-options="required:true" value="${product.name}"></td>
					<td>明细说明</td>
					<td><input name="detile"  placeholder="请输入明细说明" class="easyui-validatebox" data-options="required:true" value="${product.detile}"></td>
				</tr>
				<tr>				 
					<td>供货商价格</td>
					<td><input name="supplierprice" placeholder="请输入卡供货商价格" class="easyui-numberbox" data-options="required:true" value="${product.supplierprice}"></td>
					 <td>商品价格(分)</td>
					<td><input name="new_price" placeholder="请输入商品价格(分)" class="easyui-numberbox" data-options="required:true" value="${product.new_price}"></td>
				</tr>  
			   <tr>
					<!-- <td>商品图说明一</td>
					<td><input name="pictureone" type="file" placeholder="请输入商品图说明一"  data-options="required:true" ></td>
					<td>商品图说明二</td>
					<td><input name="picturetwo" type="file" placeholder="请输入商品图说明二" data-options="required:true" ></td>
				</tr>
			 	<tr>
					<td>商品图说明三</td>
					<td><input name="picturethree" type="file" placeholder="请输入商品图说明三" data-options="required:true" ></td> -->
					<td>商品详情图</td>
					<td><input name="longpicture" type="file" placeholder="请输入商品详情图" data-options="required:true" ></td>
				</tr>
				<tr>
			<!-- 	 <td>商品列表图</td>
					<td><input id="pictureshop" name="pictureshop" type="file"  data-options="required:true" ></td>  -->
					<td>原价</td>
					<td><input name="price" placeholder="请输入原价" class="easyui-numberbox" data-options="required:true" value="${product.price}"></td>
				</tr>
				<tr>
					<td>库存</td>
					<td><input name="num"  placeholder="请输入商品库存" class="easyui-numberbox" data-options="required:true" value="${product.num}"></td>
					<td>卡券数量</td>
					<td><input name="cardnum"  placeholder="请输入卡券数量" class="easyui-numberbox" data-options="required:true" value="${product.cardnum}"></td>
				</tr> 
				<tr>
						<td style="text-align:center">供货商名称</td>
					<td colspan="3">
						<input name="supplierid" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/productsupplierCombox',
	                    method:'get',
	                    valueField:'guid',
	                    textField:'suppliername',
	                    panelHeight:'auto',
	                    editable:false" value="${product.supplierid}">
					</td>
				</tr> 
				<tr>
				<td style="text-align:center">商品类型</td>
					<td colspan="3">
						<input name="product_type_id" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/producttypeCombox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false" value="${product.product_type_id}">
					</td>
				</tr>
				<tr>
				<td style="text-align:center">卡券类型</td>
					<td colspan="3">
						<input name="cardtypeid" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/cardtype/combobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    panelHeight:'auto',
	                    editable:false" value="${product.cardtypeid}">
					</td>
				</tr>
				 <tr>
                                 <td style="text-align:center"><label>销售状态：</label></td>
                                   	 <td><select name="state" id="id">
                                     <c:choose>
							           <c:when test="${product.state==1}">
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
</div>