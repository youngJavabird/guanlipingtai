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
		
			url : '${ctx}/activityproduct/dataGrid',
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
			columns : [ [ {
				width : '70',
				title : '渠道产品序号',
				field : 'id',
				sortable : true,
				sortable : true
			}, {
				width : '100',
				title : '产品名称',
				field : 'name',
				sortable : true
			},{
				width : '160',
				title : '产品描述',
				field : 'detile',
				sortable : true
			},{
				width : '160',
				title : '产品图片',
				field : 'picture'
			},{
				width : '60',
				title : '产品状态',
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
				width : '80',
				title : '排序号',
				field : 'seq_id',
				sortable : true
			},   {
				field : 'action',
				title : '操作',
				width : 160,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
						if ($.canView) {
							str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >查看图片</a>', row.id);
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if(row.isdefault!=0){
							if ($.canEdit) {
								str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
							}
							}
						}
					return str;
				}
			}] ],
			toolbar : '#toolbar'
		});
	});
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 600,	
			height : 300,
			href : '${ctx}/activityproduct/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#activityproductAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function showImg(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '渠道产品图片',
			width : 660,
			height : 380,
			href : '${ctx}/activityproduct/showImgPage?id=' + id
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
			title : '修改渠道产品',
			width : 500,
			height : 600,
			href : '${ctx}/activityproduct/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#activityproductEditForm');
					f.submit();
				}
			} ]
		});
	}
	

	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data	-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/goods/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>