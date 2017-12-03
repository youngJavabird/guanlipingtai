<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<script type="text/javascript">
	
	function checkURL(str){
		var re=/^(http[s]?:\/\/)(([A-Za-z0-9-~\/_?=&%#]+)\.)+([A-Za-z0-9-~\/])+$/;
	    if(re.test(str)){
	        return true;
	    }else{
	        return false;
	    }
	}
	
$(function() {
		
		$('#activityAddForm').form({
			url : '${ctx}/activity/add',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
/* 				//赠送的类型最少选择一个
			var cardtypeid = $('#cardtypeid').val();
				var score = $('#score').val();
				if(cardtypeid =null&&score ==null){
					progressClose();
					parent.$.messager.alert('提示', '活动最少要选择一种赠送类型', 'info');
					return false;
				}  */
				//判断活动开始日期是否小于结束日期
				var stime = $('#stime').val();
				var etime = $('#etime').val();
				if(stime != null && etime != null && stime != "" && etime != ""){
					if(stime > etime){
						progressClose();
						parent.$.messager.alert('提示', '活动开始时间不能大于结束时间', 'info');
						return false;
					}
				}
				else if(stime == "" && etime == ""){
					
				}
				else{
					progressClose();
					parent.$.messager.alert('提示', '活动开始时间和结束时间不能只填一个', 'info');
					return false;
				}
				
				/* var url = $('#herf').val();
				//判断活动链接格式是否正确
				if(!checkURL(url) && url!=''){
					progressClose();
					parent.$.messager.alert('提示', '输入的活动地址格式不正确', 'info');
					return false;
				} */
				var file = document.getElementById('picture').files[0];
				if(file == undefined){
					progressClose();
					parent.$.messager.alert('提示', '请选择图片', 'info');
					return false;
				}
				var fileName = file.name;
				var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length).toUpperCase();  
				//"JPEG","PNG","JPG"  判断图片的格式
				if (file_typename != ".JPG" && file_typename != ".JPEG" && file_typename != ".PNG") {
					progressClose();
					parent.$.messager.alert('提示', '图片格式错误或不支持该格式', 'info');
					return false;
				}
				else{
					if (parseInt(file.size) > 1024 * 300){
						progressClose();
						parent.$.messager.alert('提示', '图片大小不能超过300KB', 'info');
						return false;
					}
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					$('#activityAddForm input').val('');
					$('#activityAddForm textarea').val('');
					parent.$.messager.alert('提示', result.msg, 'info');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
	});
/* $('#youComId').combobox('select', '请选择');
$.ajax({  
    url: '/activity/combobox_data',  
    dataType: 'json',  
    success: function (jsonstr) {  
        jsonstr.unshift({  
            'id': '-1',  
            'typename': '-请选择-'  
        });//向json数组开头添加自定义数据  
        $('#cardtypeid').combobox({  
            data: jsonstr,  
            valueField: 'id',  
            textField: 'typename',  
            onLoadSuccess: function () { //加载完成后,设置选中第一项  
                var val = $(this).combobox('getData');  
  
            }  
        });  
    }  
});   */
</script>
<style>
	table td{
		width:120px;
		hight:30px;
	};
</style>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<form enctype="multipart/form-data" id="activityAddForm" method="post">
			<table class="grid">
				<tr>
					<td style="text-align:center">活动名称</td>
					<td colspan="3"><input name="name" type="text" style="width:311px;" maxlength="25" placeholder="请输入活动名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">活动描述</td>
					<td colspan="3"><textarea name="detile" rows="" cols="" maxlength="250" style="width:309px;hight:60px;" ></textarea></td>
				</tr>
				<tr>
					<td style="text-align:center">活动时间</td>
					<td colspan="3">
						<input id="stime" name="stime" placeholder="点击选择活动开始时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
						<input id="etime" name="etime" placeholder="点击选择活动结束时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="text-align:center">排序号</td>
					<td colspan="3"><input id="seq_id" name="seq_id" type="text" style="width:311px;" placeholder="请输入活动排序号" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">活动链接</td>
					<td colspan="3"><input id="herf" name="herf" type="text" style="width:311px;" placeholder="请输入活动链接" class="easyui-validatebox span2" data-options="required:false" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">活跃度</td>
					<td colspan="3"><input id="liveness" name="liveness" type="text" style="width:100px;" placeholder="请输入活跃度" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
                                 <td style="text-align:center"><label>活动归属</label></td>
                                   	 <td><select name="actype" id="actype">
							               <option id="0" value="0" >公众号</option>
							               <option id="1" value="1" >APP</option>  
							               <option id="2" value="2" >公众号+APP</option>	
							               </select>						           
                                     </td>
                 </tr>	
				<tr>
					<td style="text-align:center">活动类型</td>
					<td colspan="3">
						<input name="type_id"  style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/activity/combobox_data',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false,
	                    panelHeight:200" value="">
					</td>
				</tr>
				<tr>
				<td style="text-align:center" >卡券类型</td>
					<td colspan="3">
						<input name="cardtypeid"  id="cardtypeid" style="width:315px;" class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/cardtype/combobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    editable:false,
	                    panelHeight:200" value="">
					</td>
				</tr>
				<tr>
					<td style="text-align:center">活动积分</td>
					<td colspan="3"><input name="score" type="text" class="easyui-numberbox" data-options="required:true,min:0,max:999999999" value=""></td>
				</tr>
				<tr>
					<td style="text-align:center">活动图片</td>
					<td colspan="3">
					<input id="picture" name="picture" type="file" data-options="required:true" style="width:315px"></td>
				</tr>
			</table>
		</form>
	</div>
	<div></div>
</div>