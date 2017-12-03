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
<title>活动管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/activity/dataGrid',
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
			title : '活动序号',
			field : 'id',
			sortable : true,
			hidden : true
		}, {
			width : '160',
			title : '活动名称',
			field : 'name',
			sortable : true
		},{
			width : '0',
			title : '活动图片',
			field : 'picture',
			hidden : true
		},{
			width : '160',
			title : '活动描述',
			field : 'detile'
		},{
			width : '80',
			title : '活动状态',
			field : 'state',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case "0":
					return '已上架';
					break;
				case "1":
					return '已下架';
					break;
				case "2":
					return '新活动';
					break;
				case "3":
					return '审核未通过';
					break;
				case "4":
					return '待审核';
					break;
				}
			}
		},{
			width : '80',
			title : '是否热门',
			field : 'ishot',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 0 :
					return '是';
					break;
				case 1:
					return '否';
					break;
				}
			}
		},{
			width : '60',
			title : '活动积分',
			field : 'score',
			sortable : true
		},  {
			width : '60',
			title : '活跃度',
			field : 'liveness',
			sortable : true
		},  {
			width : '140',
			title : '活动开始时间',
			field : 'stime',
			sortable : true
		}, {
			width : '140',
			title : '活动结束时间',
			field : 'etime',
			sortable : true
		} , {
			field : 'action',
			title : '操作',
			width : 300,
			formatter : function(value, row, index) {
				var str = '';
				if(row.isdefault!=0){
					if (row.state == 0) {
						str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >活动图片预览</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="editActivity(\'{0}\');" >修改活动</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="subAudit(\'{0}\');" >下架</a>', row.id);
					}
					else if(row.state == 1){
						str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >活动图片预览</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="subOut(\'{0}\');" >上架</a>', row.id);
					}
					else if(row.state == 3){
						str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >活动图片预览</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="showMemo(\'{0}\');" >查看原因</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="editActivity(\'{0}\');" >修改活动</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="subAudit(\'{0}\');" >下架</a>', row.id);
					}
					else if(row.state == 2){
						str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >活动图片预览</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="editHerf(\'{0}\');" >修改活动链接</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="subCheck(\'{0}\');" >提交审核</a>', row.id);
					} 
					else if(row.state == 4){
						str += $.formatString('<a href="javascript:void(0)" onclick="showImg(\'{0}\');" >活动图片预览</a>', row.id);
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
			title : '添加活动',
			width : 660,
			height : 500,
			href : '${ctx}/activity/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#activityAddForm');
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
			title : '活动图片',
			width : 660,
			height : 380,
			href : '${ctx}/activity/showImgPage?id=' + id
		});
	}
	
	function showMemo(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '拒绝审核原因',
			width : 660,
			height : 380,
			href : '${ctx}/activity/showReason?id=' + id
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
			title : '修改活动',
			width : 660,
			height : 500,
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
	
	function subAudit(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要下架此活动？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/activity/audit', {
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

	function subOut(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要上架此活动？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/activity/up', {
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
	function subCheck(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要提交该活动审核？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/activity/check', {
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
	function editHerf(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '活动链接',
			width : 660,
			height : 380,
			href : '${ctx}/activity/showHerfPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#activityEditHerfForm');
					f.submit();
				}
			} ]
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
					<th>活动名称:</th>
					<td><input name="name" placeholder="请输入活动名称(选填)"/></td>
 					<th>活动状态:</th>
					<td><select id="state" name="state" style="width:200px;">		
							<option selected=""   value="">全部</option>
	                        <option value="0">已上架</option>
							<option value="1">已下架</option>
							<option value="2">新活动</option>
							<option value="3">审核未通过</option>
							<option value="4">待审核</option>
					</select></td>
					<th>活动结束时间:</th>
					<td><input name="stime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至
					<input  name="etime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'信息列表'" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/activity/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</body>
</html>