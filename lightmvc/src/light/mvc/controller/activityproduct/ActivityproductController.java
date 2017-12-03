package light.mvc.controller.activityproduct;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityProduct.ActivityProduct;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.service.activityproduct.ActivityproductServiceI;
import light.mvc.utils.StringUtil;
import light.mvc.utils.UploadUtil;

@Controller
@RequestMapping("/activityproduct")
public class ActivityproductController extends BaseController {

	Logger logger = Logger.getLogger(ActivityproductController.class.getName());
	@Autowired
	private ActivityproductServiceI activityproductServiceI;
	@RequestMapping("/manager")
	public String manager() {
		return "/activityproduct/activityproduct";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(ActivityProduct activityProduct, PageFilter ph) {

		Grid grid = new Grid();
		grid.setRows(activityproductServiceI.dataGrid(activityProduct, ph));
		grid.setTotal(activityproductServiceI.count(activityProduct, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/activityproduct/activityproductAdd";
	}
	
	@RequestMapping("/showImgPage")
	public String showImgPage(HttpServletRequest request, Long id) {
		ActivityProduct activityproduct = activityproductServiceI.get(id);
		request.setAttribute("activityproduct", activityproduct);
		return "/activityproduct/activityproductShowImg";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		ActivityProduct activityProduct= activityproductServiceI.get(id);
		request.setAttribute("activityProduct", activityProduct);
		return "/activityproduct/activityproductEdit";
	}

	
	/**
	 * 添加方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
		try {
	  		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	//上传图片至商城，并复制图片到管理平台
		   	String picturefileName=up.uploadManage(multipartFile1, multipartRequest,"pictureactivity");
		   	if (picturefileName.equals("")) {
		   		j.setMsg("操作失败，图片上传异常！");
		   		return j;
			}
		   	ActivityProduct activitypeoducts = new ActivityProduct();
		   	//给model赋值
		   	activitypeoducts.setType_id(Integer.parseInt(request.getParameter("type_id")));
		   	activitypeoducts.setName(request.getParameter("name"));
		   	activitypeoducts.setDetile(request.getParameter("detile"));
		   	activitypeoducts.setPicture(picturefileName);
		   	activitypeoducts.setCreatetime(new Date());
		   	activitypeoducts.setState(0);
		   	activitypeoducts.setSeq_id(Integer.parseInt(request.getParameter("seq_id")));
	     	activityproductServiceI.add(activitypeoducts);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		   	
		} catch (Exception e) {
			logger.info("ActivityAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 修改方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
		try {
	  		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	
		   	String picturefileName="";
		   	ActivityProduct activitypeoduct = activityproductServiceI.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		picturefileName = activitypeoduct.getPicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturefileName = up.uploadManage(multipartFile1, multipartRequest,"pictureactivity");
			   	if (picturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
			}
		   	
		   	ActivityProduct activitypeoducts = new ActivityProduct();
		  	
		   	//给model赋值
			BeanUtils.copyProperties(activitypeoduct, activitypeoducts);
			activitypeoducts.setId(Long.parseLong(request.getParameter("id")));
			activitypeoducts.setType_id(Integer.parseInt(request.getParameter("type_id")));
			activitypeoducts.setSeq_id(Integer.parseInt(request.getParameter("seq_id")));
			activitypeoducts.setName(request.getParameter("name"));
			activitypeoducts.setDetile(request.getParameter("detile"));
			activitypeoducts.setPicture(picturefileName);
			activitypeoducts.setState(Integer.parseInt(request.getParameter("state")));
			activityproductServiceI.edit(activitypeoducts);
			j.setMsg("修改成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityproductEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	

	/**
	 * 加载渠道下拉框数据
	 * @return
	 */
	@RequestMapping("/combobox_data")
	@ResponseBody
	public JSONArray combobox_data(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Cardchannel> list = activityproductServiceI.getCombox();
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("ActivityproductCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	
	
}
