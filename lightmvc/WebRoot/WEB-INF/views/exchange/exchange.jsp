<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>卡券退券</title>
	<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			/* url : '${ctx}' + '/exchange/dataGrid', */
			fit:true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100 ],
			columns : [ [  {
				width : '220',
				title : 'openid',
				field : 'userid',
				sortable : true
			}, {
				width : '120',
				title : '手机号',
				field : 'phone',
				sortable : true
			}  , {
				width : '60',
				title : '购买价格',
				field : 'price',
				sortable : true
			}  , {
				width : '60',
				title : '券面金额',
				field : 'p_price',
				sortable : true
			}  , {
				width : '100',
				title : '券号',
				field : 'card_code',
				sortable : true
			} , {
				width : '120',
				title : '卡券名称',
				field : 'typename',
				sortable : true
			}  , {
				width : '60',
				title : '卡券状态',
				field : 'state',
				sortable : true
			}  ,{
				width : '120',
				title : '更新时间',
				field : 'update',
				sortable : true
			}, {
				width : '140',
				title : '订单号',
				field : 'orderid',
				sortable : true
			},/* {
				width : '120',
				title : '卡券类型',
				field : 'sendid',
				sortable : true
			},  */ {
				field : 'action',
				title : '操作',
				width : 60,
				formatter : function(value, row, index) {
					var str = '';

					if(row.state=='未使用' || row.state=='未激活'|| row.state=='预备卡券'){
						
							 str += $.formatString("<a href='javascript:void(0)' onclick=subCheck('"+row.userid+"','"+row.guid+"','"+row.cardtype+"','"+row.state+"','"+row.card_code+"','"+row.typename+"','"+row.phone+"','"+row.price+"','"+row.orderid+"','"+row.sendid+"') >申请退券</a>", row.id);
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar'
		});
	});
			
	function searchFun() {
		 dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/exchange/dataGrid'
		}); 
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	 
	/*var flag = true;?
	function searchFun() {
		
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
		
		if (flag) {
			dataGrid = $('#dataGrid').datagrid({
				url : '${ctx}' + '/exchange/dataGrid'
			});
			flag = false;
		}
	}*/
		
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
			href : '${ctx}/exchange/viewPage?id=' + id
		});
	}
	function subCheck(userid,guid,cardtype,state,card_code,typename,phone,price,orderid,sendid){
		  var sessionInfo_userName = '${sessionInfo.loginname}';	  
/* 		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
			alert(id);
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		} */
		parent.$.messager.confirm('询问', '您是否要提交退券审核？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/exchange/check', {
					userid : userid,
					guid:guid,
					cardtype:cardtype,
					state:state,
					card_code:card_code,
					typename:typename,
					phone:phone,
					price:price,
					orderid:orderid,
					sendid:sendid,
					opera:sessionInfo_userName
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
					<th>卡券号:</th>
					<td><input name="card_code" placeholder="请输入卡券号(选填)"/></td>
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