<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>流量统计</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/statistics/activity',
      		columns : [ [ {
				width : '100',
				title : '时间',
				field : 'dete',
				sortable : true
			}, {
				width : '200',
				title : '模板名称',
				field : 'userid',
				sortable : true
			}, {
				width : '160',
				title : '类型',
				field : 'name',
				sortable : true
			}, {
				width : '80',
				title : 'PV',
				field : 'pv',
				sortable : true
			} , {
				width : '80',
				title : 'UV',
				field : 'uv',
				sortable : true
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
			href : '${ctx}/starwin/viewPage?id=' + id
		});
	}
	</script>
</head>
<script>
 var sendmessage="${sendmessage}"
 if(sendmessage!=""){
  alert(sendmessage);
 }
</script>
<body class="easyui-layout" data-options="fit:true,border:false">   
    <div  id="toolbar" >
		<form id="searchForm" >
			<table>
				<tr>
					<th>选择时间:</th>
					<td><input name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至<input  name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					<th>名称:</th>
					<td> 
                  	<input class="easyui-combobox"  name="identification" style="width:100%;" data-options="
	                    url:'name_combox',
	                    method:'get',
	                    valueField:'identification',
	                    textField:'neme',
	                    panelHeight:300"> 
                	</td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a><a href="${ctx}/statistics/exportactivityinfo">导出</a></td>
				</tr>
			</table>
		</form>
	</div>	
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>