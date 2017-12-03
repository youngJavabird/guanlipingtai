<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../inc.jsp"></jsp:include>
	<link href="${ctx}/ueditor/themes/default/css/ueditor.css" rel="stylesheet" type="text/css">
    <script type="text/javascript"  src="${ctx}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript"  src="${ctx}/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript"  src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
		
	    //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    var ue = UE.getEditor('editor');
	    function isFocus(e){
	        alert(UE.getEditor('editor').isFocus());
	        UE.dom.domUtils.preventDefault(e)
	    }
	    function setblur(e){
	        UE.getEditor('editor').blur();
	        UE.dom.domUtils.preventDefault(e)
	    }
	</script>
	
	<script type="text/javascript">
	 $(function() {
		 $('#richtextAddForm').form({
				url : '${ctx}/richtext/add',
				onSubmit : function() {
					progressLoad();
					var isValid = $(this).form('validate');
					if (!isValid) {
						progressClose();
						return isValid;
					}
					var editor = UE.getEditor('editor').getContent();
					if(editor==""){
						progressClose();
						parent.$.messager.alert('提示', "输入内容不能为空", 'info');
						return false;
					}
					$('#richcontext').val(editor);
					UE.getEditor('editor').hasContents();
					return isValid;
				},
				success : function(result) {
					progressClose();
					result = $.parseJSON(result);
					if (result.success) {
						var state = $('#id').combobox('getValue');
						if(state == -1){
							$('#richtextAddForm input').val('');
							$('#btn_save').val('保存');
							UE.getEditor('editor').setContent('');
						}
						parent.$.messager.alert('提示', result.msg, 'info');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
				}
			});
	});
	 
	 $(document).ready(function () {
		 $("#id").combobox({
			 onChange: function (n,o) {
				 loadInfo();
			 }
		 });
	  });
	 
	 function loadInfo(){
		 var id = $('#id').combobox('getValue');
		 if(id != null && id != "" && id != -1){
				$.post('${ctx}/richtext/addPage', {
					id : id
				}, function(result) {
					UE.getEditor('editor').setContent(result[0]["richcontext"]);
					$('#name').val(result[0]["name"]);
				}, 'JSON');
		 }
		 else{
			 UE.getEditor('editor').setContent('');
			 $('#name').val('');
		 }
	 }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false,striped:true" >
	<div data-options="region:'center',border:false" title="" style="padding:3px;" >
		<form id="richtextAddForm" method="post">
		<input id="richcontext" name="richcontext" type="text" style="display:none" value="${richtext.richcontext}">
			<table class="grid">
				<tr>
					<td style="text-align:center">富文本名称</td>
					<td><input id="name" name="name" type="text" style="width:311px;" maxlength="50" placeholder="请输入富文本名称" class="easyui-validatebox span2" data-options="required:true" value="${richtext.name}"></td>
					<td style="text-align:center">选择修改富文本</td>
					<td><input id="id" name="id" style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/richtext/combobox_data',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false,
	                    panelHeight:200" value="-1"></td>
				</tr>
			</table>
		
		<div style="text-align:center">富文本内容</div>
		<div>   
		    <script id="editor" type="text/plain" style="width:98%;height:350px;"></script>  
		</div>  
		<div style="text-align:center"><input id="btn_save" type="submit" value="保存"></div>
		</form>
	</div>
</div>
</body>
</html>

 