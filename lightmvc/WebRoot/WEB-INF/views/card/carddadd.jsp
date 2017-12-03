<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function() {
		
		$('#wnfcardAddForm').form({
			url : '${ctx}/wnfcard/add',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				
		
				var file = document.getElementById('card_file').files[0];
				if(file == undefined){
					progressClose();
					parent.$.messager.alert('提示', '请选择卡券导入路径', 'info');
					return false;
				}
				var fileName = file.name;
				var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length).toUpperCase();  
				//"JPEG","PNG","JPG"  判断图片的格式
				if (file_typename != ".XLS"&&file_typename != ".XLSX") {
					progressClose();
					parent.$.messager.alert('提示', '格式错误或不支持该格式', 'info');
					return false;
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
		<form enctype="multipart/form-data" id="wnfcardAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">卡券名称</td>
					<td colspan="3"><input id = "card_name" name="card_name" type="text" style="width:311px;" maxlength="25" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
                      <td style="text-align:center">卡券导入路径</td>
                       <td colspan="3"><input  id = "card_file"    name="card_file" type="file" data-options="required:true" style="width:315px"></td>
                </tr>
                <tr>
					<td style="text-align:center">图标路径</td>
					<td colspan="3">
					<input id = "card_pictureurl"  name="card_picture"type="file" data-options="required:true" style="width:315px"></td>
				</tr>	
			
					<input style="display:none" id = "card_type"  name="card_type" type="text" style="width:311px;" maxlength="25" placeholder="请输入详情" class="easyui-validatebox span2" data-options="required:false" value="抵用券">

				<tr>
				<td style="text-align:center" >卡券金额类型</td>
					<td colspan="3">
						<input name="cardtypeid"  id="" style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/cardtype/combobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    panelHeight:'auto',
	                    editable:false,
	                     panelHeight:200" value="">
					</td>
				</tr>
				<tr>
					<td style="text-align:center">卡券左侧底色</td>
					<td colspan="3"><input id = "card_colour"  name="card_colour" type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:false" value="#FFF"></td>
				</tr>
				<tr>
					<td style="text-align:center">卡券说明</td>
					<td colspan="3"><input id = "desc1"  name="card_service_condition_one" type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>

                <input style="display:none" id = "offsetdesc"  name="card_offset_description" type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:false" value="无门槛使用">

				<tr>
					<td style="text-align:center">卡券详细说明</td>
					<td colspan="3"><input id = "card_detile"  name="card_detile"  type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">操作员代码</td>
					<td colspan="3"><input id = "card_userid" name="card_userid"type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:false" value="0001"></td>
				</tr>
				
			</table>
		</form>
	</div>
	<div></div>
</div>