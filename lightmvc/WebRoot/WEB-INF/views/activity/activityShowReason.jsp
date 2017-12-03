<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="demoEditForm" method="post">
			<table class="grid">
				<tr>
					<td>拒绝审核原因:</td>
					<td><input id="reason" name="reason" type="text" value="${activity.reason}"></td>
				</tr>
			</table>
		</form>
	</div>
</div>