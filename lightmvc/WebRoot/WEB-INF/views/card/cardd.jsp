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
<title>卡券信息</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/wnfcard/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			fit : true,
			sortOrder : 'asc',
			pageSize : 20,
			pageList : [ 5,10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				width : '200',
				title : '卡券名称',
				field : 'card_name',
				sortable : true
			}, {
				width : '160',
				title : '卡号',
				field : 'card_code'
			} , {
				width : '60',
				title : '类型',
				field : 'card_type'
			}, {
				width : '450',
				title : '说明',
				field : 'card_service_condition_one'
			} , {
				width : '60',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '未激活';
						break;
					case 1:
						return '已激活';
						break;
					case 2:
						return '作废';
						break;
					case 3:
						return '已使用';
						break;
					case 4:
						return '激活失败';
						break;
					case 5:
						return '壹钱包已激活卡券';
						break;
					}
				}
			},{
				width : '60',
				title : '金额',
				field : 'card_price'
			} ] ],
			toolbar : '#toolbar'
		});
	});

	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,	
			height : 450,
			href : '${ctx}/wnfcard/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#wnfcardAddForm');
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