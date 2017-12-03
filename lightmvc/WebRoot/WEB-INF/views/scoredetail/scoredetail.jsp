<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>卡券交易明细</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/scoredetail/dataGrid', 
			fit:true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [  {
				width : '220',
				title : 'openid(微信用户ID)',
				field : 'userid',
				sortable : true
			}, {
				width : '80',
				title : '手机号',
				field : 'phone',
				sortable : true
			} , {
				width : '120',
				title : '交易订单号',
				field : 'orderid',
				sortable : true
			}  , {
				width : '120',
				title : '交易时间',
				field : 'createdate',
				sortable : true
			}  , {
				width : '80',
				title : '交易场景',
				field : 'paytype',
				sortable : true
			} , {
				width : '60',
				title : '交易类型',
				field : 'type',
				sortable : true
			}  , {
				width : '60',
				title : '金额(元)',
				field : 'balance',
				sortable : true
			}  , {
				width : '60',
				title : '返现(元)',
				field : 'pbalance',
				sortable : true
			}  , {
				width : '120',
				title : '券名',
				field : 'typename',
				sortable : true
			}  , {
				width : '50',
				title : '市场价',
				field : 'p_price',
				sortable : true
			}  , {
				width : '50',
				title : '折后价',
				field : 'price',
				sortable : true
			}  ,{
				width : '50',
				title : '采购价',
				field : 'purchase',
				sortable : true
			}  , {
				width : '40',
				title : '数量',
				field : 'pro_num',
				sortable : true
			} , {
				width : '120',
				title : '卡券状态更新时间',
				field : 'update',
				sortable : true
			} , {
				width : '60',
				title : '卡券状态',
				field : 'state',
				sortable : true
			}  , {
				width : '100',
				title : '备注',
				field : 'remark',
				sortable : true
			}/* , {
				width : '100',
				title : '备注',
				field : 'typeid',
				sortable : true
			} *//* ,{
				field : 'action',
				title : '操作',
				width : 60,
				formatter : function(value, row, index) {
					var str = '';

					if(row.state=='预备卡券'){
						
							 str += $.formatString("<a href='javascript:void(0)' onclick=change('"+row.userid+"','"+row.typeid+"') >修改可使用</a>", row.id);
					}
					return str;
				}
			} */] ],
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
			width : 250,
			height : 200,
			href : '${ctx}/scoredetail/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#demoAddForm');
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
				$.post('${ctx}/scoredetail/delete', {
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
			title : '编辑',
			width : 250,
			height : 200,
			href : '${ctx}/scoredetail/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#demoEditForm');
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
	function download(){
		location.href='${ctx}/scoredetail/exportaccountinfo';
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
			href : '${ctx}/scoredetail/viewPage?id=' + id
		});
	}
	function change(userid,typeid){
		parent.$.messager.confirm('询问', '您是否要修改为可使用状态？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/scoredetail/change', {
					userid : userid,
					typeid:typeid
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
</head>
<script>
 var sendmessage="${sendmessage}"
 if(sendmessage!=""){
  alert(sendmessage);
 }
</script>
<body class="easyui-layout" data-options="fit:true,border:false">   
    <div  id="toolbar">
		<form id="searchForm" enctype="multipart/form-data">
			<table>
				<tr>
				    <th>OPENID:</th>
					<td><input name="userid" placeholder="请输入openid(选填)"/></td>
				    <th>手机号:</th>
					<td><input name="phone" placeholder="请输入手机号(选填)"/></td>
					<th>交易类型:</th>
                	<td> 	
                		<select class="easyui-combobox" name="type" label="type" labelPosition="top" style="width:100%;">
			                <option value="">全部</option>
			                <option value="5">充值</option>
			                <option value="6">消费</option>
			                <option value="8">退款</option>
                		</select>
                	</td>
					<th>创建时间:</th>
					<td><input name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至<input  name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a><a href="${ctx}/scoredetail/exportaccountinfo" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">导出</a></td>
				</tr>
			</table>
		</form>
	</div>	
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