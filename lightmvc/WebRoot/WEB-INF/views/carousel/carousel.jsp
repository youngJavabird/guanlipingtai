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
<title>轮播图管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/carousel/dataGrid',
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
		columns : [ [{
			width : '30',
			title : 'id',
			field : 'id',
			sortable : true
		}, {
			width : '80',
			title : '轮播图类型',
			field : 'type',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 1:
					return 'app活动轮播图';
					break;
				case 2:
					return 'app商城轮播图';
					break;
				case 3:
					return '微信活动轮播图';
					break;
				case 4:
					return '微信商城轮播图';
					break;
				case 5:
					return 'app广告轮播图';
					break;
				case 6:
					return 'app首页轮播图';
					break;
				}
			}
		},  {
			width : '80',
			title : '轮播图名称',
			field : 'name',
			sortable : true
		},{
			width : '160',
			title : '轮播banner图片',
			field : 'bannerpicture',
			sortable : true
		},{
			width : '160',
			title : '轮播图内容图片',
			field : 'picture',
			sortable : true
		},{
			width : '100',
			title : '轮播图详情',
			field : 'detail'
		},{
			width : '140',
			title : '创建时间',
			field : 'createtime'
		},{
			width : '60',
			title : '状态',
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
			width : '100',
			title : '链接',
			field : 'href',
			sortable : true
		}, {
			field : 'action',
			title : '操作',
			width : '160',
			formatter : function(value, row, index) {
				var str = '&nbsp;';
				if(row.isdefault!=0){
					if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
							str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >查看图片预览</a>', row.id);
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
	function showImg(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '轮播图片',
			width : 660,
			height : 380,
			href : '${ctx}/carousel/showImgPage?id=' + id
		});
	}
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加活动',
			width : 660,
			height : 380,
			href : '${ctx}/carousel/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#carouselAddForm');
					f.submit();
				}
			} ]
		});
	}

	function editFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改轮播图',
			width : 660,
			height : 380,
			href : '${ctx}/carousel/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#carouselEditForm');
					f.submit();
				}
			} ]
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加活动',
			width : 660,
			height : 380,
			href : '${ctx}/carousel/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#carouselAddForm');
					f.submit();
				}
			} ]
		});
	}

	function editActivity(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改轮播图',
			width : 660,
			height : 380,
			href : '${ctx}/activity/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#activityEditForm');
					f.submit();
				}
			} ]
		});
	}


</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>