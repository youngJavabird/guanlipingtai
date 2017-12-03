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
<title>父文本管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}' + '/smstemp/dataGrid',
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
			title : '序号',
			field : 'id',
			sortable : true,
			hidden : true
		}, {
			width : '60',
			title : '模板ID',
			field : 'msgid',
			sortable : true
		}, {
			width : '500',
			title : '模板内容',
			field : 'smstemplet',
			sortable : true
		}, {
			width : '80',
			title : '交易类型',
			field : 'transtype',
			sortable : true
		}, {
			width : '120',
			title : '活动名称',
			field : 'activity_id',
			sortable : true
		}, {
			width : '140',
			title : '创建时间',
			field : 'createtime',
			sortable : true
		}, {
			width : '140',
			title : '修改时间',
			field : 'upddate',
			sortable : true
		} , {
			field : 'action',
			title : '操作',
			width : 100,
			formatter : function(value, row, index) {
				var str = '';
				if(row.isdefault!=0){
					if (true) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="delFun(\'{0}\');" >删除</a>', row.id);
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
			title : '添加短信模板',
			width : 660,
			height : 380,
			href : '${ctx}/smstemp/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#smstempAddForm');
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
			title : '修改短信模板',
			width : 660,
			height : 380,
			href : '${ctx}/smstemp/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#smstempEditForm');
					f.submit();
				}
			} ]
		});
	}
	function delFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前记录？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/smstemp/drop', {
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

</script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?bb372b90f1bbb70c160bb3c920cfcf08";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
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
						url:'${ctx}/smstemp/transCombox',
	                    method:'get',
	                    valueField:'transtype',
	                    textField:'transtypename',
	                    panelHeight:'auto',
	                    editable:false" value="全部">
					</td>
					<th>活动类型:</th>
					<td><input name="activity_id" class="easyui-combobox" data-options="
						required:false,
						url:'${ctx}/smstemp/activityCombox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:200,
	                    editable:false" value="-1"/>
	                </td>
	                <td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'列表'" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/smstemp/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>