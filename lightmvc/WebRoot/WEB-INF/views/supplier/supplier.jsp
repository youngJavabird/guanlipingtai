<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>供货商信息管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {
	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/supplier/dataGrid',
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
			width : '80',
			title : '地址序号',
			field : 'id',
			sortable : true,
			hidden : true
		},{
			width : '160',
			title : '供货商名称',
			field : 'suppliername',
			sortable : true
		},{
			width : '200',
			title : '发货地',
			field : 'sendaddress',
			sortable : true
		},{
			width : '200',
			title : '退换货地',
			field : 'recvaddress',
			sortable : true
		},{
			width : '80',
			title : '联系人',
			field : 'linkman',
			sortable : true
		},{
			width : '120',
			title : '联系电话',
			field : 'phone',
			sortable : true
		},{
			width : '60',
			title : '状态',
			field : 'state',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case "00":
					return '可用';
					break;
				case "01":
					return '禁用';
					break;
				}
			}
		},{
			width : '140',
			title : '创建时间',
			field : 'createdate',
			sortable : true
		},{
			width : '140',
			title : '修改时间',
			field : 'upddate',
			sortable : true
		},{
			field : 'action',
			title : '操作',
			width : 60,
			formatter : function(value, row, index) {
				var str = '';
				if(row.isdefault!=0){
					if (true) {
						str += $.formatString('<a href="javascript:void(0)" onclick="edit(\'{0}\');" >修改</a>', row.id);
					}
				}
				return str;
			}
		}] ],
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
			title : '添加供货商信息',
			width : 660,
			height : 380,
			href : '${ctx}/supplier/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#supplierAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function edit(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改供货商信息',
			width : 660,
			height : 380,
			href : '${ctx}/supplier/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#supplierEditForm');
					f.submit();
				}
			} ]
		});
		
	}

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm" enctype="multipart/form-data">
			<table>
				<tr>
					<th>供货商名称:</th>
					<td><input name="suppliername" placeholder="请输入供货商名称(选填)"/></td>
					<th>联系人:</th>
					<td><input name="linkman" placeholder="请输入联系人名称(选填)"/></td>
					<th>供货商状态:</th>
					<td><select id="state" name="state" style="width:200px;">
							<option selected="" value="-1">全部</option>
							<option value="00">可用</option>
							<option value="01">禁用</option>
					</select></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'活动列表'" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/supplier/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>