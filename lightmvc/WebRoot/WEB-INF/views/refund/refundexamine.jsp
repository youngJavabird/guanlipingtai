<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>退款审核</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/refundexamine/dataGrid',
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
			columns : [ [  {field:'checked',formatter:function(value,row,index){
				if(row.checked){
				return '<input type="checkbox" name="DataGridCheckbox" checked="checked">';
				}else{
				return '<input type="checkbox" name="DataGridCheckbox">';
				}
				}},{
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
					width : '60',
					title : '充值金额',
					field : 'balance',
					sortable : true
				} , {
					width : '60',
					title : '奖励金额',
					field : 'pbalance',
					sortable : true
				}   , {
					width : '100',
					title : '状态',
					field : 'state',
					sortable : true
				} , {
					width : '120',
					title : '充值时间',
					field : 'createdate',
					sortable : true
				} , {
					width : ' 50',
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
				}  ,  {
					width : '50',
					title : '申请人',
					field : 'opera',
					sortable : true
				}  ,  {
					width : '120',
					title : '申请时间',
					field : 'update',
					sortable : true
				}  , {
					width : '60',
					title : '备注',
					field : 'remark',
					sortable : true
				}, {
				field : 'action',
				title : '操作',
				width : 120,
				formatter : function(value, row, index) {
					var str = '';

					if(row.state=='退款审核中'){
							 str += $.formatString("<a href='javascript:void(0)' onclick=subCheck('"+row.userid+"','"+row.orderid+"','"+row.state+"','"+row.phone+"','"+row.balance+"','"+row.pbalance+"') >审核退款</a>", row.id);
							 str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						     str += $.formatString("<a href='javascript:void(0)' onclick=subrefuseCheck('"+row.userid+"','"+row.orderid+"','"+row.state+"','"+row.phone+"','"+row.balance+"','"+row.pbalance+"') >拒绝审核</a>", row.id);
					}
					return str;
				}
			} ] ],
			singleSelect: true,
			toolbar : '#toolbar'
		});
	});
	
	  function ButonGetCheckFun(){
		  var sessionInfo_userName = '${sessionInfo.loginname}';	  
			parent.$.messager.confirm('询问', '您是否要提交退款审核？', function(b) {
				if (b) {
/* 					var opera = prompt("请输入您的ID", ""); 
		            if (opera == null || opera == "")  
		            {  
		                alert("请输入您的ID!");
		                return false;
		            } */
					progressLoad(); 
		var checkedItems =  $('#dataGrid').datagrid('getChecked');
		var userid = [];
		var orderid = [];
		var state = [];	
		var phone = [];
		var balance = [];
		var pbalance = [];
		var createdate = [];
		var checkedItemsLength = checkedItems.length-0,
				successNum = 0,
				forNum = 0;
		$.each(checkedItems, function(index, item){			
			$.post('${ctx}/refundexamine/check', {
				userid : item.userid,
				orderid:item.orderid,
				state:item.state,
				phone : item.phone,
				balance:item.balance,
				pbalance : item.pbalance,
				createdate:item.createdate,
				opera:sessionInfo_userName
			}, function(result) {
				forNum++;
				if (result.success) {
					successNum++;
					dataGrid.datagrid('reload');
				}
				progressClose();			
				if (forNum == checkedItemsLength) {
					parent.$.messager.alert('提示', "操作成功" +successNum+"条，失败"+(checkedItemsLength-successNum)+"条", 'info');
				}				
			}, 'JSON');						
		});	
		
		

		
		
		
	    };
	});
}
	
 	$.extend($.fn.datagrid.methods, {
		getChecked: function (jq) {
		var rr = [];
		var rows = jq.datagrid('getRows');
		jq.datagrid('getPanel').find('div.datagrid-cell input:checked').each(function () {
		var index = $(this).parents('tr:first').attr('datagrid-row-index');
		rr.push(rows[index]);
		});
		return rr;
		}
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
			href : '${ctx}/refundexamine/viewPage?id=' + id
		});
	}
	function subCheck(userid,orderid,state,phone,balance,pbalance,createdate){
		  var sessionInfo_userName = '${sessionInfo.loginname}';	 
		parent.$.messager.confirm('询问', '您是否要提交退款审核？', function(b) {
			if (b) {
/* 				var opera = prompt("请输入您的ID", ""); 
	            if (opera == null || opera == "")  
	            {  
	                alert("请输入您的ID!");
	                return false;
	            } */
				progressLoad();
				$.post('${ctx}/refundexamine/check', {
					userid : userid,
					orderid : orderid,
					state:state,
					phone : phone,
					balance:balance,
					pbalance : pbalance,
					createdate:createdate,
					opera:sessionInfo_userName
				}, function(result) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					progressClose();
				}, 'JSON');
			}
		});
	}
	function subrefuseCheck(userid,orderid,state,phone,balance,pbalance,createdate){
		  var sessionInfo_userName = '${sessionInfo.loginname}';	 
				parent.$.messager.confirm('询问', '您是否要拒绝退款审核？', function(b) {
					if (b) {
/* 						var opera = prompt("请输入您的ID", ""); 
			            if (opera == null || opera == "")  
			            {  
			                alert("请输入您的ID!");
			                return false;
			            } */
						progressLoad();
						$.post('${ctx}/refundexamine/refusecheck', {
							userid : userid,
							orderid:orderid,
							state:state,
							phone : phone,
							balance:balance,
							pbalance : pbalance,
							createdate:createdate,
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
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="ButonGetCheckFun();">批量退款</a></td>
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