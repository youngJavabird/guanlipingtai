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
 			/* url : '${ctx}' + '/refund/dataGrid',  */
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
				title : 'openid',
				 field : 'userid',
				sortable : true
			}, {
				width : '120',
				title : '微信订单号',
				 field : 'orderid', 
				sortable : true
			}  , {
				width : '120',
				title : '手机号',
				 field : 'phone', 
				sortable : true
			}  , {
				width : '100',
				title : '充值金额',
				 field : 'balance', 
				sortable : true
			} , {
				width : '100',
				title : '奖励金额',
				 field : 'pbalance', 
				sortable : true
			}   , {
				width : '60',
				title : '状态',
				field : 'state', 
				sortable : true
			} , {
				width : '120',
				title : '充值时间',
				 field : 'createdate', 
				sortable : true
			} , /* {
				width : '50',
				title : '退款额',
				field : 'remoney',
				sortable : true
			}  , {
				width : '50',
				title : '扣减返利',
				field : 'pbalance',
				sortable : true
			}  , {
				width : '50',
				title : '退款手续费',
				field : 'refee',
				sortable : true
			}  , {
				width : '120',
				title : '备注',
				field : 'remark',
				sortable : true
			}    , */{
				field : 'action',
				title : '操作',
				width : 130,
				formatter : function(value, row, index) {
					if(row.state=='充值'){
					  var  str = $.formatString("<a href='javascript:void(0)' onclick=editFun('"+row.orderid+"') >申请退款</a>", row.id);
					}
					if(row.state=='活动'){
						  var  str = $.formatString("<a href='javascript:void(0)' onclick=subCheck('"+row.userid+"','"+row.orderid+"','"+row.phone+"','"+row.balance+"','"+row.pbalance+"') >申请活动退款</a>", row.id);
						}
					return str;
				}
			}  ] ],
			toolbar : '#toolbar'
		});
	});
	
	var flag = true;
	function searchFun() {
		
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
		
		if (flag) {
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}' + '/refund/dataGrid' 
			});
			flag = false;
		}
		

		
	}
/* 	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	} */
	function editFun(orderid) {
/* 		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		} */
		parent.$.modalDialog({
			title : '编辑',
			width : 550,
			height : 500,
			href : '${ctx}/refund/editPage?orderid=' + orderid,
			buttons : [ {
				text : '提交',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#refundEditForm');
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
			width : 200,
			height : 200,
			href : '${ctx}/refund/viewPage?id=' + id
		});
	}
	function subCheck(userid,orderid,phone,balance,pbalance,createdate){
		  var sessionInfo_userName = '${sessionInfo.loginname}';	
/* 		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
			alert(id);
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		} */
		parent.$.messager.confirm('询问', '您是否要退款？', function(b) {
/* 			remoney = prompt("请输入退款金额", ""); 				
 			refee = prompt("请输入退款手续费", "");  
 			remark = prompt("请输入备注", "默认");   */
/*             if (remoney == null || remoney == "")  
            {  
                alert("请输入退款金额!");
                return;
            }
            if (refee == null || refee == "")  
            {  
                alert("请输入退款手续费!");
                return;
            } */
			if (b) {
				progressLoad();
				$.post('${ctx}/refund/check2', {
					userid : userid,
					orderid:orderid,
					phone : phone,
					balance:balance,
					pbalance : pbalance,
					createdate:createdate,
/* 					remoney:remoney,
					refee:refee,
					remark:remark, */
					opera:sessionInfo_userName
				}, function(result) {

						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
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