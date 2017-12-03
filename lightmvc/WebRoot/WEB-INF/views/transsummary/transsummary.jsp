<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/demo/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<title>交易汇总查询</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {
	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/transsummary/dataGrid',
		fit : true,
		striped : true,
		rownumbers : true,
		pagination : true,
		singleSelect : true,
		idField : 'transtype',
		sortName : 'transtype',
		sortOrder : 'asc',
		pageSize : 50,
		pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
		columns : [ [ {
			width : '80',
			title : '渠道编号',
			field : 'channel_id',
			sortable : true
		},{
			width : '80',
			title : '交易类型',
			field : 'transtype',
			sortable : true
		},{
			width : '80',
			title : '费种',
			field : 'code',
			sortable : true
		},{
			width : '80',
			title : '交易金额',
			field : 'amount',
			sortable : true
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
	
	function download(){
		/* progressLoad();
		$.post('${ctx}/transdetail/download', {
			id : 1
		}, function(result) {
			if (!result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
			}
			progressClose();
		}, 'JSON'); */
		location.href='${ctx}/transsummary/download';
	}

</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchForm" enctype="multipart/form-data">
			<table>
				<tr>
					<th>交易类型:</th>
					<td>
						<input name="transtype" class="easyui-combobox" data-options="
						required:false,
						url:'${ctx}/transsummary/combobox_data',
	                    method:'get',
	                    valueField:'transtype',
	                    textField:'transtypename',
	                    panelHeight:'auto',
	                    editable:false,
	                    panelHeight:200" value="全部">
	                </td>
					<td><input name="trsdate_send" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至
					<input  name="trsdate_center" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					<input type="button" style="margin:0 50px;width:80px" value="导&nbsp;&nbsp;&nbsp;&nbsp;出" onclick="download()"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'信息列表'" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>