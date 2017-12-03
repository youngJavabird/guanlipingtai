package light.mvc.controller.goods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.pageModel.sys.Organization;
import light.mvc.service.goods.ProductServiceI;
import light.mvc.service.goods.ProductTypeServiceI;
import light.mvc.utils.UploadUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/goodstype")
public class ProductTypeController extends BaseController{
	
	Logger logger = Logger.getLogger(ProductTypeController.class.getName());
	
	@Autowired
	private ProductTypeServiceI producttypeService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/goods/producttype";
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<ProductType> treeGrid() {
		return producttypeService.treeGrid();
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/goods/producttypeAdd";
	}
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {
		return producttypeService.tree();
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(ProductType producttype, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(producttypeService.dataGrid(producttype, ph));
		grid.setTotal(producttypeService.count(producttype, ph));
		return grid;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		try {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	     MultipartFile multipartFile1 = multipartRequest.getFile("picture");
	     UploadUtil up=new UploadUtil();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	 //得到处理后的上传图片名称
	   	 //图片一路径
	   	 String pictureonefileName=up.uploadManage(multipartFile1, multipartRequest,"picture");
  	 	if (pictureonefileName==null) {
	   		j.setMsg("操作失败，图片不能为空！");
	   		return j;
		}
  		ProductType producttype=new ProductType();
  		producttype.setPicture(pictureonefileName);
  		producttype.setName(request.getParameter("name"));
  		producttype.setCreatedate(new Date());
  		producttype.setState(0);
  		producttypeService.add(producttype);
	     j.setMsg("添加成功！");
	     j.setSuccess(true);
	} catch (Exception e) {
		logger.info("producttypeAdd:"+e.getMessage());
		j.setMsg(e.getMessage());
	}
	return j;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		ProductType producttype = producttypeService.get(id);
		request.setAttribute("producttype", producttype);
		return "/goods/producttypeEdit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
	try {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
	   	UploadUtil up = new UploadUtil();
	   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	   String pictureonefileName="";
	   	ProductType producttypes = producttypeService.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		pictureonefileName = producttypes.getPicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		pictureonefileName = up.uploadManage(multipartFile1, multipartRequest,"picture");
			   	if (pictureonefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}	   
			}	

		ProductType producttype=new ProductType();
		producttype.setId(Long.parseLong(request.getParameter("id")));
		producttype.setName(request.getParameter("name"));
		producttype.setPicture(pictureonefileName);
		producttype.setCreatedate(new Date());
		producttype .setState(Integer.parseInt(request.getParameter("state")));
		producttypeService.edit(producttype);
		j.setMsg("修改成功！");
		j.setSuccess(true);
	} catch (Exception e) {
		logger.info("goodstypeEdit:"+e.getMessage());
		j.setMsg("系统异常,请联系管理员！");
	}
	return j;
			
	}
	
	
}
