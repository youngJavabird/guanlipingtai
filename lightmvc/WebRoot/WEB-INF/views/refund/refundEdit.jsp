<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		
		$('#refundEditForm').form({
			url : '${ctx}/refund/check',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
				/* 	parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close'); */
					parent.$.messager.alert('提示', result.msg, 'info');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
		
		$("#description").val('${refund.description}');
		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="refundEditForm" method="post">
			<table class="grid">
			
				<tr>
				<td>openid</td>
				<td><input name="userid" type="hidden"  value="${scoredetail.userid}">${scoredetail.userid}</td>
			   </tr>
				<tr>
					<td>微信订单号</td>
					<td><input name="orderid" type="hidden"  value="${scoredetail.orderid}">${scoredetail.orderid}</td>
				</tr>
				<tr>
					<td>手机号</td>
					<td><input name="phone" type="hidden"  value="${scoredetail.phone}">${scoredetail.phone}</td>
				</tr>
				<tr>
					<td>充值金额</td>
					<td><input name="balance" type="hidden"  value="${scoredetail.balance}">${scoredetail.balance}</td>
				</tr>
				<tr>
					<td>奖励金额</td>
					<td><input name="pbalance" type="hidden"  value="${scoredetail.pbalance}">${scoredetail.pbalance}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td><input name="state" type="hidden"  value="${scoredetail.state}">${scoredetail.state}</td>
				</tr>
				<tr>
					<td>充值时间</td>
					<td><input name="update" type="hidden"  value="${scoredetail.createdate}">${scoredetail.createdate}</td>
				</tr>
				<tr>
				<td>退款额</td>
				<td><input name="remoney" type="text" placeholder="请输入退款额" class="easyui-validatebox" data-options="required:true" value="${scoredetail.remoney}"></td>
				</tr>
					<tr>
					<td>扣减返利</td>
					<td>${scoredetail.pbalance}</td>
				</tr>
				<tr>
				<td>退款手续费</td>
					<td><input name="refee" type="text" placeholder="请输入退款手续费" class="easyui-validatebox" data-options="required:true" value="${scoredetail.refee}"></td>
				</tr>
				<tr>
				<td>备注</td>
					<td><input name="remark" type="text" placeholder="请输入备注" class="easyui-validatebox" data-options="required:false" value="${scoredetail.remark}"></td>\
					<td><input name=opera type="hidden"  value="${sessionInfo.loginname}"></td>
				</tr>
			</table>
		</form>
	</div>
</div>