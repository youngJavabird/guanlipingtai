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
<title>订单管理</title>
<style type="text/css">
	table td{
		text-align:center;
	}
</style>
<script type="text/javascript">
var dataGrid;
$(function() {

	dataGrid = $('#dataGrid').datagrid({
	
		url : '${ctx}/orderinfo/dataGrid',
		fit : true,
		striped : true,
		rownumbers : true,
		pagination : true,
		singleSelect : true,
		idField : 'id',
		sortName : 'upddate',
		sortOrder : 'desc',
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
			title : '订单号',
			field : 'orderid',
			sortable : true
		},{
			width : '220',
			title : '商品名称',
			field : 'proname',
		},{
			width : '80',
			title : '订单状态',
			field : 'state',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case '00':
					return '待支付';
					break;
				case '01':
					return '待发货';
					break;
				case '02':
					return '待收货';
					break;
				case '03':
					return '换货中';
					break;
				case '04':
					return '退货中';
					break;
				case '05':
					return '已退货';
					break;
				case '06':
					return '已收货';
					break;
				}
			}
		},{
			width : '80',
			title : '订单数量',
			field : 'num'
		},{
			width : '120',
			title : '单价',
			field : 'new_price',
			sortable : true
		},  {
			width : '120',
			title : '总金额',
			field : 'countprice',
			sortable : true
		}, {
			width : '80',
			title : '收货人姓名',
			field : 'username',
		},{
			width : '140',
			title : '下单时间',
			field : 'createdate',
			sortable : true
		} , {
			width : '140',
			title : '最后修改时间',
			field : 'upddate',
			sortable : true
		} , {
			width : '200',
			title : '收货地址',
			field : 'address',
			sortable : true
		} , {
			width : '80',
			title : '商品库存',
			field : 'pronum',
			sortable : true
		} , {
			width : '80',
			title : '商品原价',
			field : 'price',
			sortable : true
		} , {
			width : '80',
			title : '是否需要发票',
			field : 'isinvoice',
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case '00':
					return '无';
					break;
				case '01':
					return '个人';
					break;
				case '02':
					return '公司';
					break;
				}
			}
		} , {
			width : '130',
			title : '发票抬头',
			field : 'invoicename',
			sortable : true
		} , {
			field : 'action',
			title : '操作',
			width : 220,
			formatter : function(value, row, index) {
				var str = '';
				if(row.isdefault!=0){
					if(row.state=='00' || row.state=='01'){
						str += $.formatString('<a href="javascript:void(0)" onclick="editSupplier(\'{0}\');" >(添加)修改物流</a>', row.id);
						
					}
					if (row.state=='03') {
						str += $.formatString('<a href="javascript:void(0)" onclick="operstarState(\'{0}\',2);" >换货</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="operstarState(\'{0}\',3);" >撤销换货</a>', row.id);
					}
					else if(row.state == '04'){
						str += $.formatString('<a href="javascript:void(0)" onclick="operstarState(\'{0}\',0);" >退货</a>', row.id);
						str += "&nbsp;&nbsp;|&nbsp;&nbsp;"
						str += $.formatString('<a href="javascript:void(0)" onclick="operstarState(\'{0}\',1);" >撤销退货</a>', row.id);
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
	
	function editSupplier(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改物流信息',
			width : 660,
			height : 380,
			href : '${ctx}/orderinfo/editPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#orderinfoEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function operstarState(id, state){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		var b = false;
		var remark = "";
		if(state == 0){
			parent.$.messager.confirm('询问', '确定要进行退货操作吗?', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/orderinfo/updateorderstate', {
						id : id,
						state : state
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						else{
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
		else if(state == 1){
			parent.$.messager.confirm('询问', '确定要撤销退货吗?', function(b) {
				remark = prompt("请输入撤销理由", ""); 
	            if (remark == null || remark == "")  
	            {  
	                alert("请输入撤销理由!");
	                return;
	            }
	    		if (b) {
	    			progressLoad();
	    			$.post('${ctx}/orderinfo/updateorderstate', {
	    				id : id,
	    				state : state,
	    				remark : remark
	    			}, function(result) {
	    				if (result.success) {
	    					parent.$.messager.alert('提示', result.msg, 'info');
	    					dataGrid.datagrid('reload');
	    				}
	    				else{
	    					parent.$.messager.alert('提示', result.msg, 'info');
	    					dataGrid.datagrid('reload');
	    				}
	    				progressClose();
	    			}, 'JSON');
	    		}
			});
		}
		else if(state == 2){
			parent.$.messager.confirm('询问', '确定要进行换货操作吗?', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/orderinfo/updateorderstate', {
						id : id,
						state : state
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						else{
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
		else if(state == 3){
			parent.$.messager.confirm('询问', '确定要撤销换货吗?', function(b) {
	    		remark = prompt("请输入撤销理由", ""); 
	            if (remark == null || remark == "")  
	            {  
	                alert("请输入撤销理由!");
	                return;
	            }
	    		if (b) {
	    			progressLoad();
	    			$.post('${ctx}/orderinfo/updateorderstate', {
	    				id : id,
	    				state : state
	    			}, function(result) {
	    				if (result.success) {
	    					parent.$.messager.alert('提示', result.msg, 'info');
	    					dataGrid.datagrid('reload');
	    				}
	    				else{
	    					parent.$.messager.alert('提示', result.msg, 'info');
	    					dataGrid.datagrid('reload');
	    				}
	    				progressClose();
	    			}, 'JSON');
	    		}
			});
		}
		
	}
	
	function download(){
		location.href='${ctx}/orderinfo/download';
	}
	
	function download1(){
		location.href='${ctx}/orderinfo/downloadsupplier';
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
	<div data-options="region:'north',border:false" style="height: 80px; overflow: hidden;background-color: #fff">
		<form id="searchForm" enctype="multipart/form-data">
			<table>
				<tr>
					<th>订单号:</th>
					<td><input name="orderid" placeholder="请输入订单号(选填)"/></td>
					<th>订单状态:</th>
					<td><select id="state" name="state" style="width:180px;">
							<option selected="selected" value="-1">全部</option>
							<option value="00">待支付</option>
							<option value="01">待发货</option>
							<option value="02">待收货</option>
							<option value="03">换货中</option>
							<option value="04">退货中</option>
							<option value="05">已退货</option>
							<option value="06">已收货</option>
					</select></td>
					<th>用户平台:</th>
					<td><select id="usertype" name="usertype" style="width:180px;">
							<option selected="selected" value="-1">全部</option>
							<option value="1">APP用户</option>
							<option value="2">微信用户</option>
					</select></td>
					<th>最后修改时间:</th>
					<td><input name="upddate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 00:00:00'})" readonly="readonly" />至
					<input  name="createdate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd 23:59:59'})" readonly="readonly" />
					
					</td>
				</tr>
				<tr>
					<td colspan="7"></td>
					<td>&nbsp;&nbsp;
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
						<input type ="button" id = "excbtn" value = "导出信息明细" onclick="download()" >&nbsp;&nbsp;&nbsp;&nbsp;
						<input type ="button" id = "excbtn1" value = "导出订单信息" onclick="download1()" ></td>
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