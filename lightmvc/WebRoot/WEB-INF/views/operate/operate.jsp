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
<title>操作记录管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/operate/dataGrid',
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
			title : '操作类型',
			field : 'state',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case '0':
					return '申请退券';
					break;
				case '1':
					return '通过退券审核';
					break;
				case '2':
					return '拒绝退券审核';
					break;
				case '3':
					return '申请退款';
					break;
				case '4':
					return '通过退款审核';
					break;
				case '5':
					return '拒绝退款审核';
					break;
				}
			}
		},  {
			width : '80',
			title : '操作人',
			field : 'opera',
			sortable : true
		},{
			width : '220',
			title : 'OPENID',
			field : 'userid',
			sortable : true
		},{
			width : '160',
			title : '券号',
			field : 'card_code',
			sortable : true
		},{
			width : '100',
			title : '券名',
			field : 'typename'
		},{
			width : '140',
			title : '手机号',
			field : 'phone'
		},{
			width : '60',
			title : '购买价格/金额',
			field : 'price',
			sortable : true,
		},{
			width : '120',
			title : '操作时间',
			field : 'date',
			sortable : true
		},{
			width : '140',
			title : '订单号',
			field : 'guid',
			sortable : true
		},{
			width : '100',
			title : '赠送金额',
			field : 'p_price',
			sortable : true
		}/* , {
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
		} */ ] ],
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
			href : '${ctx}/operate/showImgPage?id=' + id
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
		<form id="searchForm" enctype="multipart/form-data">
			<table>
				<tr>
				    <th>手机号:</th>
					<td><input name="phone" placeholder="请输入手机号(选填)"/></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>	
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/add')}">
<!-- 			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a> -->
		</c:if>
	</div>
</body>
</html>