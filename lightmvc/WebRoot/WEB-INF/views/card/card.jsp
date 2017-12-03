<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/card/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/card/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/card/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<title>卡券管理</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/card/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			
			sortOrder : 'asc',
			pageSize : 20,
			pageList : [ 5,10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '200',
				title : '名称',
				field : 'typename',
				sortable : true
			}, {
				width : '60',
				title : '数量',
				field : 'num'
			} , {
				width : '60',
				title : '金额',
				field : 'price'
			}, {
				width : '80',
				title : '总金额',
				field : 'total'
			} , {
				width : '100',
				title : '状态',
				field : 'remark'
			} ] ],
			toolbar : '#toolbar'
		});
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 250,
			height : 200,
			href : '${ctx}/card/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#cardAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前DEMO？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/demo/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 250,
			height : 200,
			href : '${ctx}/demo/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#demoEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '詳情',
			width : 200,
			height : 200,
			href : '${ctx}/demo/viewPage?id=' + id
		});
	}
	
	
	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	
	
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">   
    <div  id="toolbar">
		<form id="searchForm">
			<table>
				<tr>
					<th>发券方:</th>
					<td><!-- <input name="name" placeholder="请输入名称"/> --></td>
					<th>券类型:</th>
					<td> 				  
                  	<input class="easyui-combobox"  name="type_id" style="width:100%;" data-options="
	                    url:'card_type_combox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    panelHeight:'auto' "> 
                	</td>
                	<th>券状态:</th>
                	<td> 	
                		<select class="easyui-combobox" name="state" label="State:" labelPosition="top" style="width:100%;">
			                <option value="">全部</option>
			                <option value="0">未激活</option>
			                <option value="1">已激活</option>
			                <option value="2">作废</option>
			                <option value="3">已使用</option>
			                <option value="4">激活失败</option>
			                <option value="5">壹钱包已激活卡券</option>
                		</select>
                	</td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>	
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</body>

</html>