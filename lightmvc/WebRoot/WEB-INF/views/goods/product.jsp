<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<title>商品管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/goods/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [{
				width : '180',
				title : '名称',
				field : 'name',
				sortable : true
			} , {
				width : '300',
				title : '描述',
				field : 'detile'
			} , {
				width : '80',
				title : '价格(分)',
				field : 'new_price'
			} , {
				width : '60',
				title : '库存',
				field : 'num'
			} , {
				width : '75',
				title : '赠送卡券数量',
				field : 'cardnum'
			} ,{
				width : '50',
				title : '活跃度',
				field : 'liveness'
			} ,{
				width : '80',
				title : '销售状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '正常';
						break;
					case 1:
						return '禁用';
						break;
					}
				}
			},{
				field : 'action',
				title : '操作',
				width : 320,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
						if ($.canView) {
							str += $.formatString('<a href="javascript:void(0)" onclick="viewFun(\'{0}\');" >查看</a>', row.id);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if(row.isdefault!=0){
							if ($.canEdit) {
								str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
								str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
				/* 				str += $.formatString('<a href="javascript:void(0)" onclick="addfieldFun(\'{0}\');" >添加一级属性</a>', row.id);
								str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
								str += $.formatString('<a href="javascript:void(0)" onclick="addfieldtwoFun(\'{0}\');" >添加二级属性</a>', row.id);
								str += '&nbsp;&nbsp;|&nbsp;&nbsp;'; */
								str += $.formatString('<a href="javascript:void(0)" onclick="addpurchaseFun(\'{0}\');" >设置限购</a>', row.id);
								str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
								str += $.formatString('<a href="javascript:void(0)" onclick="purchsaeFun(\'{0}\');" >限购管理</a>', row.id);
							}
							}
						}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
	});
	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 600,	
			height : 600,
			href : '${ctx}/goods/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsAddForm');
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
				$.post('${ctx}/goods/delete', {
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
			title : '修改商品',
			width : 600,
			height : 600,
			href : '${ctx}/goods/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsEditForm');
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
			width : 600,
			height : 670,
			href : '${ctx}/goods/viewPage?id=' + id
		});
		
		
	}
	function fieldFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.window.sss=parent.$.modalDialog({
			title : '商品属性詳情',
			width : 300,
			height : 400,
			href : '${ctx}/goods/fieldviewPage?id=' + id,
			buttons : [ {
				text : '删除',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsxfForm');
					f.submit();
				}
			} ]
		}  );
	}
	function purchsaeFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.window.sss=parent.$.modalDialog({
			title : '限购信息詳情',
			width : 300,
			height : 400,
			href : '${ctx}/goods/purchaseviewPage?id=' + id,
			buttons : [ {
				text : '删除',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsxfForm');
					f.submit();
				}
			} ]
		}  );
	}
	function downFun(id){
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要下架该商品？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/goods/down', {
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
	
	function addfieldFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '增加一级商品属性',
			width : 300,
			height : 200,
			href : '${ctx}/goods/addfieldpage?id=' + id,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsAddfieldForm');
					f.submit();
				}
			} ]
		});
	}
	function addfieldtwoFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '增加二级商品属性',
			width : 300,
			height : 200,
			href : '${ctx}/goods/addfieldtwopage?id=' + id,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsAddfieldtwoForm');
					f.submit();
				}
			} ]
		});
	}
	function addpurchaseFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '设置限购信息',
			width : 300,
			height : 200,
			href : '${ctx}/goods/addpurchasepage?id=' + id,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#goodsAddpurchaseForm');
					f.submit();
				}
			} ]
		});
	}
	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm" >
			<table>
				<tr>
					<th>商品名称:</th>
					<td><input name="name" placeholder="请输入商品名称(选填)"/></td>
					<th>商品类型:</th>				
					<td>
						<input name="product_type_id"  style="width:100%;" class="easyui-combobox" data-options="
						url:'producttypeCombox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                      panelHeight:'auto',
	                    editable:false"  value=""></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid" data	-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/goods/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>