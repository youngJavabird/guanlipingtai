<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="demoEditForm" method="post">
			<table class="grid">
				<tr>
					<td>渠道图片<img id="picture" alt="渠道图片"  src="../../../upload/${cardchannel.picture}" width="200px" height="300px"  /></td>
				    <td>渠道banner图片<img id="bannerpicture" alt="渠道banner图片"  src="../../../upload/${cardchannel.bannerpicture}" width="200px" height="300px" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>