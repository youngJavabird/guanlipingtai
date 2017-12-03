<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function() {
		
		$('#carouselAddForm').form({
			url : '${ctx}/carousel/add',
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
					if (parseInt(file.size) > 1024 * 100){
						progressClose();
						parent.$.messager.alert('提示', '图片大小不能超过100KB', 'info');
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
					$('#carouselAddForm input').val('');
					$('#carouselAddForm textarea').val('');
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
		<form enctype="multipart/form-data" id="carouselAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">轮播图名称</td>
					<td colspan="3"><input name="name" type="text" style="width:311px;" maxlength="25" placeholder="请输入轮播图名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">轮播图详情</td>
					<td colspan="3"><input name="detail" type="text" style="width:311px;" maxlength="25" placeholder="请输入轮播图详情" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">轮播图链接</td>
					<td colspan="3"><input name="href" type="text" style="width:311px;" placeholder="请输入轮播图链接" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">轮播图片</td>
					<td colspan="3">
					<input id="picture" name="picture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>
				<tr>
					<td style="text-align:center">轮播banner图片</td>
					<td colspan="3">
					<input id="bannerpicture" name="bannerpicture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>		
				 <tr>
                                 <td style="text-align:center"><label>轮播图类型：</label></td>
                                   	 <td><select name="type" id="id">
							               <option id="1" value="1" >APP活动轮播图</option>
							               <option id="2" value="2" >APP商城轮播图</option>  
							               <option id="3" value="3" >微信活动轮播图</option>
							               <option id="4" value="4" >微信商城轮播图</option>  
							               <option id="5" value="5" >APP广告轮播图</option>  
							               <option id="6" value="6" >APP首页轮播图</option>  
                                     </td>
                 </tr>	
			</table>
		</form>
	</div>
	<div></div>
</div>