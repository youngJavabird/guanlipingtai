<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	$(function() {
		$('#goodstypeEditForm').form({
			url : '${ctx}/goodstype/edit',
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
			href : '${ctx}/goodstype/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodstypeEditForm');
					f.submit();
				}
			} ]
		});
	}

</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form id="goodstypeEditForm" enctype ="multipart/form-data" method="post">
		<input id="id" name="id" type="text" style="display:none" value="${producttype.id}">
			<table class="grid">
				<tr>
					<td>商品品类名称</td>
					<td><input name="name" placeholder="请输入商品名称" class="easyui-validatebox" data-options="required:true" value="${producttype.name}"></td>
				</tr>
			   <tr>
					<td>商品品类图图说明</td>
					<td><input name="picture" type="file" placeholder="请输入商品品类图说明一"  data-options="required:true" ></td>
				</tr>

				 <tr>
                                 <td style="text-align:center"><label>状态：</label></td>
                                   	 <td><select name="state" id="id">
                                     <c:choose>
							           <c:when test="${producttype.state==1}">
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