<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">

</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
		<input id="id" name="id" type="text" style="display:none" value="${	product.id}">
			<table class="grid">
				
		   <tr>
<%-- 					<td>商品图说明一</td>
					<td><img alt="商品图说明一"  src="../../../upload/${product.pictureone}" width="120px" height="80px"/></td>
					<td>商品图说明二</td>
					<td><img id="pictureone" alt="商品图说明二"  src="../../../upload/${product.picturetwo}" width="120px" height="80px"/></td>
				</tr>
			 	<tr>
					<td>商品图说明三</td>
					<td><img alt="商品图说明三"  src="../../../upload/${product.picturethree}" width="120px" height="80px"/></td> --%>
					<td>商品详情图</td>
					<td><img alt="商品详情图"  src="../../../upload/${product.longpicture}" width="120px" height="80px"/></td>
				</tr>
				<tr>
				<%--  <td>商品列表图</td>
					<td><img alt="商品列表图"  src="../../../upload/${product.pictureshop}" width="120px" height="80px"/></td> --%>
					<td>商品名称</td>
					<td>${product.name}</td>
				</tr>
				<tr>
					<td>原价</td>
					<td>${product.price}</td>
					<td>明细说明</td>
					<td>${product.detile}</td>
				</tr>
				<tr>				 
					<td>供货商价格</td>
					<td>${product.supplierprice}</td>
					 <td>商品价格(分)</td>
					<td>${product.new_price}</td>
				</tr>                                                                                                                                                                                                                                                                                                                           
				<tr>
					<td>库存</td>
					<td>${product.num}</td>
					<td>卡券数量</td>
					<td>${product.cardnum}</td>
				</tr>  
				<tr>
					<td>活跃度</td>
					<td>${product.liveness}</td>
				</tr>
				<tr>
						<td style="text-align:center">供货商名称</td>
					<td colspan="3">
						<input name="supplierid" disabled="true"   class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/productsupplierCombox',
	                    method:'get',
	                    valueField:'guid',
	                    textField:'suppliername',
	                    panelHeight:'auto',
	                    editable:false" value="${product.supplierid}">
					</td>
				</tr> 
				<tr>
				<td style="text-align:center">商品类型</td>
					<td colspan="3">
						<input name="product_type_id"  disabled="true"  class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/goods/producttypeCombox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false" value="${product.product_type_id}">
					</td>
				</tr>
					<tr>
				<td style="text-align:center">卡券类型</td>
					<td colspan="3">
						<input name="cardtypeid"  disabled="true"  class="easyui-combobox" data-options="
						required:true,
						url:'${ctx}/cardtype/combobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'typename',
	                    panelHeight:'auto',
	                    editable:false" value="${product.cardtypeid}">
					</td>
				</tr>
				 <tr>
                                 <td style="text-align:center"><label>销售状态</label></td>
                                   	 <td><select name="state"  disabled="true" id="id">
                                     <c:choose>
							           <c:when test="${product.state==1}">
							                <option id="0" value="0" >正常</option>
							               <option id="1" value="1" selected="selected">禁用</option>
							           </c:when>
							           <c:otherwise>
							               <option id="0" value="0" >正常</option>
							               <option id="1" value="1" >禁用</option>
							           </c:otherwise>
							           </c:choose> 
                                 </select></td>
                             </tr>	
<%--                          <tr >
				 <td>一级属性</td>				 	
					<td><c:forEach items="${fieldname}" var="fllist">	
					&nbsp&nbsp&nbsp${fllist.name} 库存：${fllist.num}&nbsp&nbsp&nbsp
							 </c:forEach>
					</td> 
							
						</tr>
						<tr>
					<td>二级属性</td>
					<td><c:forEach items="${fieldnametwo}" var="fllist">
					&nbsp&nbsp&nbsp ${fllist.name} 库存：${fllist.num}&nbsp&nbsp&nbsp	               
							 </c:forEach></td>	
				</tr> --%>
			</table>
		
	</div>
</div>