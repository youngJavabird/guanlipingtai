<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>退款</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/jtrefund/dataGrid',
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
				width : '20',
				title : 'id',
				field : 'id',
				sortable : false
			}, {
				width : '220',
				title : 'openid',
				field : 'openid',
				sortable : true
			}, {
				width : '90',
				title : '账户需扣款金额',
				field : 'moneyaccount',
				sortable : true
			}  , {
				width : '80',
				title : '账户可退金额',
				field : 'money',
				sortable : true
			}  , {
				width : '80',
				title : '可退金额说明',
				field : 'refundexplain',
				sortable : true
			} , {
				width : '100',
				title : '手机号',
				field : 'phone',
				sortable : true
			}   , {
				width : '60',
				title : '姓名',
				field : 'name',
				sortable : true
			} , {
				width : '120',
				title : '退款原因',
				field : 'reason',
				sortable : true
			} , {
				width : '70',
				title : '状态',
				field : 'state',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case '1':
						return '已提交';
						break;
					case '2':
						return '审核中';
						break;
					case '3':
						return '审核失败';
						break;
					case '4':
						return '退款成功';
						break;
					case '5':
						return '退款失败';
						break;
					}
				}
			} , {
				width : '120',
				title : '创建时间',
				field : 'createdate',
				sortable : true
			}  ,  {
				width : '120',
				title : '备注',
				field : 'remark',
				sortable : true
			}    ,{
				field : 'action',
				title : '操作',
				width : 180,
				formatter : function(value, row, index) {
					var str = '';
					if(row.state=='1'){
				      str = $.formatString("<a href='javascript:void(0)' onclick=subCheck('"+row.id+"','"+row.remark+"','"+row.refundexplain+"') >允许审核</a>", row.id);
				  	  str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					  str += $.formatString("<a href='javascript:void(0)' onclick=resubCheck('"+row.id+"','"+row.remark+"','"+row.openid+"') >拒绝审核</a>", row.id);
					}
					if(row.state=='2'){
						  str += $.formatString("<a href='javascript:void(0)' onclick=resubCheck('"+row.id+"','"+row.remark+"','"+row.openid+"') >拒绝审核</a>", row.id);
					  	  str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						  str += $.formatString("<a href='javascript:void(0)' onclick=onCheck('"+row.id+"','"+row.openid+"') >确认退款</a>", row.id);
						}
				      return str;
				}
			}  ] ],
			toolbar : '#toolbar'
		});
	});
			
	function subCheck(id,remark,refundexplain){
		parent.$.messager.confirm('询问', '您是否允许审核？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/jtrefund/check', 
				{
					id:id,
					remark:remark,
					refundexplain:refundexplain
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
	function resubCheck(id,remark,openid){
		parent.$.messager.confirm('询问', '您是否拒绝审核？', function(b) {
			remoney = prompt("请输入拒绝原因", ""); 	
	          if (remoney == null || remoney == "")  
	            {  
	                alert("请输入拒绝原因!");
	                return;
	            }
			if (b) {
				progressLoad();
				$.post('${ctx}/jtrefund/refusecheck', {
					id:id,
					remark:remoney,
					openid:openid
				}, function(result) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					progressClose();
				}, 'JSON');
			}
		});
	}
	function onCheck(id,openid){
		parent.$.messager.confirm('询问', '您是否确认退款？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/jtrefund/oncheck', {
					id:id,
					openid:openid
				}, function(result) {

						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
			
					progressClose();
				}, 'JSON');
			}
		});
	}
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
			href : '${ctx}/refund/viewPage?id=' + id
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
					<td><input name="openid" placeholder="请输入openid(选填)"/></td>
				    <th>手机号:</th>
					<td><input name="phone" placeholder="请输入手机号(选填)"/></td>
					<th>创建时间:</th>
					<td><input name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至<input  name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a></td>
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