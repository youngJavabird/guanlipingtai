<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
$(function() {
		
		$('#activityproductEditForm').form({
			url : '${ctx}/activityproduct/edit',
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
		<form enctype="multipart/form-data" id="activityproductEditForm" method="post">
		<input id="id" name="id" type="text" style="display:none" value="${activityProduct.id}">
			<table class="grid">
				<tr>
					<td style="text-align:center">产品名称</td>
					<td colspan="3"><input name="name" type="text" style="width:311px;" maxlength="25" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${activityProduct.name}"></td>
				</tr>
				<tr>
					<td style="text-align:center">产品详情</td>
					<td colspan="3"><input name="detile" type="text" style="width:311px;" maxlength="25" placeholder="请输入详情" class="easyui-validatebox span2" data-options="required:true" value="${activityProduct.detile}"></td>
				</tr>
				<tr>
					<td style="text-align:center">排序号</td>
					<td colspan="3"><input name="seq_id" type="text" style="width:311px;" maxlength="25" placeholder="" class="easyui-validatebox span2" data-options="required:false" value="${activityProduct.seq_id}"></td>
				</tr>
				<tr>
					<td style="text-align:center">产品图片</td>
					<td colspan="3">
					<input id="picture" name="picture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>	
				<tr>
						<td style="text-align:center">渠道类型</td>
					<td colspan="3">
						<input name="type_id"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/activityproduct/combobox_data',
	                    method:'get',
	                    valueField:'id',
	                    textField:'channel',
	                    panelHeight:'auto',
	                    editable:false" value="${activityProduct.type_id}">
					</td>
				</tr> 
				  <tr>
                                 <td style="text-align:center"><label>状态：</label></td>
                                   	 <td><select name="state" id="id">
                                     <c:choose>
							           <c:when test="${activityProduct.state==1}">
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