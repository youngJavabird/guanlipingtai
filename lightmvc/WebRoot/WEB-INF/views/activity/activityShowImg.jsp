<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="demoEditForm" method="post">
			<table class="grid">
				<tr>
					<td><img id="picture" alt="活动图片"  src="../../../upload/${activity.picture}" width="500px" height="300px" style="margin:0px 80px;" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>