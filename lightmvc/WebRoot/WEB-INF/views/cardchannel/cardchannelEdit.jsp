<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	

	$(function() {
		$('#cardchannelEditForm').form({
			url : '${ctx}/cardchannel/edit',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				
	
				var file = document.getElementById('picture').files[0];
				if(file == undefined){
					progressClose();
					return isValid;
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
		<form enctype="multipart/form-data" id="cardchannelEditForm" method="post">
			
			<input id="id" name="id" type="text" style="display:none" value="${cardchannel.id}">
			<table class="grid">
			<tr>
					<td style="text-align:center">渠道名称</td>
					<td colspan="3"><input name="channel" type="text" style="width:311px;" maxlength="25" placeholder="请输入渠道名称" class="easyui-validatebox span2" data-options="required:true" value="${cardchannel.channel}"></td>
				</tr>
				<tr>
					<td style="text-align:center">渠道图片</td>
					<td colspan="3">
					<input id="picture" name="picture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>
				<tr>
					<td style="text-align:center">渠道banner图片</td>
					<td colspan="3">
					<input id="bannerpicture" name="bannerpicture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>	
				 <tr>
                                 <td style="text-align:center"><label>状态：</label></td>
                                   	 <td><select name="state" id="id">
                                     <c:choose>
							           <c:when test="${cardchannel.state==1}">
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
	<div></div>
</div>