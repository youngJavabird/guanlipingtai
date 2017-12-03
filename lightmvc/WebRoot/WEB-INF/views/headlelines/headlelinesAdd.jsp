<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<script type="text/javascript">
	
$(function() {
		
		$('#headlelinesAddForm').form({
			url : '${ctx}/headlelines/add',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				
				var file = document.getElementById('picture').files[0];
				if(file == undefined){
					progressClose();
					parent.$.messager.alert('提示', '请选择图片', 'info');
					return false;
				}
				var fileName = file.name;
				var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length).toUpperCase();  
				//"JPEG","PNG","JPG"  判断图片的格式
				if (file_typename != ".JPG" && file_typename != ".JPEG" && file_typename != ".PNG") {
					progressClose();
					parent.$.messager.alert('提示', '图片格式错误或不支持该格式', 'info');
					return false;
				}
				else{
					if (parseInt(file.size) > 1024 * 150){
						progressClose();
						parent.$.messager.alert('提示', '图片大小不能超过150KB', 'info');
						return false;
					}
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					$('#headlelinesAddForm input').val('');
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
		<form enctype="multipart/form-data" id="headlelinesAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">头条描述</td>
					<td colspan="3"><input name="describes" type="text" style="width:311px;" maxlength="25" placeholder="请输入头条描述" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">标签</td>
					<td colspan="3"><input id="title" name="title" type="text" style="width:311px;" maxlength="100" placeholder="请输入标签" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">头条链接</td>
					<td colspan="3"><input id="href" name="href" type="text" style="width:311px;" maxlength="100" placeholder="请输入头条链接" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">头条序号</td>
					<td colspan="3"><input name="sort" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:999999999" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">点击量</td>
					<td colspan="3"><input name="virclicktime" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:999999999" value="0"></td>
				</tr>
				<tr>
					<td style="text-align:center">活动图片</td>
					<td colspan="3">
					<input id="picture" name="picture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div></div>
</div>