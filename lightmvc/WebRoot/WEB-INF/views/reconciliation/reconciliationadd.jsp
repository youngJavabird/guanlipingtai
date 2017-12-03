<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function() {
		
		$('#reconciliationAddForm').form({
			url : '${ctx}/reconciliation/add',
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
				if (file_typename != ".TXT"&&file_typename != ".txt") {
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
		<form enctype="multipart/form-data" id="reconciliationAddForm" method="post">
			<table class="grid">
				<tr>
                      <td style="text-align:center">卡券导入路径</td>
                       <td colspan="3"><input  id = "card_file"    name="card_file" type="file" data-options="required:true" style="width:315px"></td>
                </tr>
    
			

				
			</table>
		</form>
	</div>
	<div></div>
</div>