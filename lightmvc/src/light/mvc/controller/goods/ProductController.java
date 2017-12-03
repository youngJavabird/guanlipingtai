package light.mvc.controller.goods;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.demo.Demo;
import light.mvc.pageModel.field.Field;
import light.mvc.pageModel.field.Fieldname;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.pageModel.purchase.Purchase;
import light.mvc.service.goods.ProductServiceI;
import light.mvc.utils.UploadUtil;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Controller
@RequestMapping("/goods")
public class ProductController extends BaseController{
	Logger logger = Logger.getLogger(ProductController.class.getName());
	@Autowired
	private ProductServiceI productService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/goods/product";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Product product, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(productService.dataGrid(product, ph));
		grid.setTotal(productService.count(product, ph));
		return grid;
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		return "/goods/productEdit";
	}
	@RequestMapping("/viewPage")
	public String viewPage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		List<Fieldname> fieldname=productService.fieldname(id);
		request.setAttribute("fieldname", fieldname);
		List<Fieldname> fieldnametwo=productService.fieldnametwo(id);
		request.setAttribute("fieldnametwo", fieldnametwo);
		return "/goods/productView";
	}
	@RequestMapping("/fieldviewPage")
	public String fieldviewPage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		List<Fieldname> fieldname=productService.fieldname(id);
		request.setAttribute("fieldname", fieldname);
		List<Fieldname> fieldnametwo=productService.fieldnametwo(id);
		request.setAttribute("fieldnametwo", fieldnametwo);
		return "/goods/productfieldView";
	}
	@RequestMapping("/addfieldpage")
	public String addfieldpage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		return "/goods/productAddfield";
	}
	@RequestMapping("/addfieldtwopage")
	public String addfieldtwopage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		return "/goods/productAddfieldtwo";
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/goods/productAdd";
	}
	@RequestMapping("/addpurchasepage")
	public String addpurchasepage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		return "/goods/productAddpurchase";
	}
	@RequestMapping("/purchaseviewPage")
	public String purchaseviewPage(HttpServletRequest request, Long id) {
		Product product = productService.get(id);
		request.setAttribute("product", product);
		List<Purchase> purchase=productService.purchase(id);
		request.setAttribute("purchase", purchase);
		return "/goods/productpurchaseView";
	}
	@RequestMapping("/deletepurchase")
	@ResponseBody
	public Json deletepurchase(HttpServletRequest request,HttpServletResponse response,Long id) {
		Json j = new Json();
		try {
			productService.deletepurchase(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("deletepurchase:"+e.getMessage());
			j.setMsg(e.getMessage());
		}

		return j;
	}
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(HttpServletRequest request,HttpServletResponse response,Long id) {
		Json j = new Json();
		try {
			productService.delete(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("productdelete:"+e.getMessage());
			j.setMsg(e.getMessage());
		}

		return j;
	}
	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		try {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	     MultipartFile multipartFile1 = multipartRequest.getFile("pictureone");
	     MultipartFile multipartFile2 = multipartRequest.getFile("picturetwo");
	     MultipartFile multipartFile3 = multipartRequest.getFile("picturethree");
	     MultipartFile multipartFilelong = multipartRequest.getFile("longpicture");
	     MultipartFile multipartFilepictureshop = multipartRequest.getFile("pictureshop");
	     UploadUtil up=new UploadUtil();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	 //得到处理后的上传图片名称
	   	 //图片一路径
	   	 String pictureonefileName=up.uploadManage(multipartFile1, multipartRequest,"pictureone");
	     //图片二路径
	   	 String picturetwofileName=up.uploadManage(multipartFile2, multipartRequest,"picturetwo");
	     //图片三路径
	   	 String picturethreefileName=up.uploadManage(multipartFile3, multipartRequest,"picturethree");
	   	 //详情图路径
	   	 String picturelongfileName=up.uploadManage(multipartFilelong, multipartRequest,"longpicture");
	   	 //商品小图路径
  	    String pictureshopfileName=up.uploadManage(multipartFilepictureshop, multipartRequest,"pictureshop");
  	 	if (pictureonefileName==null||picturetwofileName==null||picturethreefileName==null||picturelongfileName==null||pictureshopfileName==null) {
	   		j.setMsg("操作失败，图片不能为空！");
	   		return j;
		}
        Product	product=new Product();
	   	 product.setPictureone(pictureonefileName);
	   	 product.setPicturetwo(picturetwofileName);
	   	 product.setPicturethree(picturethreefileName);
	     product.setLongpicture(picturelongfileName);
	     product.setPictureshop(pictureshopfileName);	 
	     product.setName(request.getParameter("name"));
	     product.setDetile(request.getParameter("detile"));
	     product.setProduct_type_id(request.getParameter("product_type_id"));
	     product.setCardtypeid(Integer.parseInt(request.getParameter("cardtypeid")));
	     product.setNew_price(request.getParameter("new_price"));
	     product.setPrice(request.getParameter("price"));
	     product.setNum(request.getParameter("num"));
	     product.setCardnum(Integer.parseInt(request.getParameter("cardnum")));
         product.setSupplierid(request.getParameter("supplierid"));
         product.setSupplierprice(Integer.parseInt(request.getParameter("supplierprice")));
         product.setLiveness(Integer.parseInt(request.getParameter("liveness")));
	     product.setState(0);
		product.setGuid(uuid());
	     productService.add(product);
	     j.setMsg("添加成功！");
	     j.setSuccess(true);
	} catch (Exception e) {
		logger.info("productAdd:"+e.getMessage());
		j.setMsg(e.getMessage());
	}
	return j;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
	try {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    MultipartFile multipartFile1 = multipartRequest.getFile("pictureone");
	    MultipartFile multipartFile2 = multipartRequest.getFile("picturetwo");
	    MultipartFile multipartFile3= multipartRequest.getFile("picturethree");
	    MultipartFile multipartFile4 = multipartRequest.getFile("longpicture");
	    MultipartFile multipartFile5= multipartRequest.getFile("pictureshop");
	   	UploadUtil up = new UploadUtil();
	   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	   String pictureonefileName="";
	   	   String picturetwofileName="";
	   	   String picturethreefileName="";
	   	   String longpicturefileName="";
	   	   String pictureshopfileName="";
	     	Product products = productService.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		pictureonefileName = products.getPictureone();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		pictureonefileName = up.uploadManage(multipartFile1, multipartRequest,"pictureone");
			   	if (pictureonefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}	   
			}	
		   	if (multipartFile2.getSize() == 0) {
		   		picturetwofileName = products.getPicturetwo();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturetwofileName = up.uploadManage(multipartFile2, multipartRequest,"picturetwo");
			   	if (picturetwofileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
		 	if (multipartFile3.getSize() == 0) {
		 		picturethreefileName = products.getPicturethree();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturethreefileName = up.uploadManage(multipartFile3, multipartRequest,"picturethree");
			   	if (picturethreefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
		 	if (multipartFile4.getSize() == 0) {
		 		longpicturefileName = products.getLongpicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		longpicturefileName = up.uploadManage(multipartFile4, multipartRequest,"longpicture");
			   	if (longpicturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
		 	if (multipartFile5.getSize() == 0) {
		 		pictureshopfileName = products.getPictureshop();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		pictureshopfileName = up.uploadManage(multipartFile5, multipartRequest,"pictureshop");
			   	if (pictureshopfileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
		
		Product product=new Product();
		product.setId(Long.parseLong(request.getParameter("id")));
		product.setName(request.getParameter("name"));
		product.setDetile(request.getParameter("detile"));
		product.setNew_price(request.getParameter("new_price"));
		product.setProduct_type_id(request.getParameter("product_type_id"));
		product.setCardtypeid(Integer.parseInt(request.getParameter("cardtypeid")));
		product.setPrice(request.getParameter("price"));
		product.setNum(request.getParameter("num"));
		product.setCardnum(Integer.parseInt(request.getParameter("cardnum")));
     	product.setSupplierid(request.getParameter("supplierid"));
		product.setSupplierprice(Integer.parseInt(request.getParameter("supplierprice")));
		product.setLiveness(Integer.parseInt(request.getParameter("liveness")));
		product .setState(Integer.parseInt(request.getParameter("state")));
		product.setGuid(request.getParameter("guid"));
		product.setPictureone(pictureonefileName);
		product.setPicturetwo(picturetwofileName);
		product.setPicturethree(picturethreefileName);
		product.setLongpicture(longpicturefileName);
		product.setPictureshop(pictureshopfileName);
		productService.edit(product);
		j.setMsg("修改成功！");
		j.setSuccess(true);
	} catch (Exception e) {
		logger.info("goodsEdit:"+e.getMessage());
		j.setMsg("系统异常,请联系管理员！");
	}
	return j;
			
	}
	private String uuid() {
		String uuid=UUID.randomUUID().toString();
		return uuid;
	}
	@RequestMapping("/producttypeCombox")
	@ResponseBody
	public JSONArray typeCombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<ProductType> list = productService.typeCombox();
//			ProductType producttype = new ProductType();
//			if (list == null) {
//				list = new ArrayList<ProductType>();
//			}
//			list.add(0, producttype);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("producttypeCombox:" + e.getMessage());
		}
		return jsonarray;
	}
	@RequestMapping("/productsupplierCombox")
	@ResponseBody
	public JSONArray supplierCombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<ProductSupplier> list = productService.supplierCombox();
//			ProductSupplier productSupplier = new ProductSupplier();
//			if (list == null) {
//				list = new ArrayList<ProductSupplier>();
//			}
//			list.add(0, productSupplier);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("productsupplierCombox:" + e.getMessage());
		}
		return jsonarray;
	}
	@RequestMapping("/productfieldCombox")
	@ResponseBody
	public JSONArray fieldCombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Productfield> list = productService.fieldCombox();
					Productfield productfield = new Productfield();
			if (list == null) {
				list = new ArrayList<Productfield>();
			}
			list.add(0, productfield);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("productfieldCombox:" + e.getMessage());
		}
		return jsonarray;
	}
	@RequestMapping("/addfield")
	@ResponseBody
	public Json addfield(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		try {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	     MultipartFile multipartFile = multipartRequest.getFile("picture");
	     UploadUtil up=new UploadUtil();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	 //得到处理后的上传图片名称
	   	 //图片路径
	   	 String picturefileName=up.uploadManage(multipartFile, multipartRequest,"picture");
//  	 	if (picturefileName==null) {
//	   		j.setMsg("操作失败，图片不能为空！");
//	   		return j;
//		}
        Field	field=new Field();
        field.setField_id(Integer.parseInt(request.getParameter("field_id")));
        field.setValue(Integer.parseInt(request.getParameter("field_id")));
        field.setProduct_id(Integer.parseInt(request.getParameter("product_id")));
        field.setPicture(picturefileName);
        field.setNum(Integer.parseInt(request.getParameter("num")));
        field.setState(0);
	     productService.add(field);
	     j.setMsg("添加成功！");
	     j.setSuccess(true);
	} catch (Exception e) {
		logger.info("fieldAdd:"+e.getMessage());
		j.setMsg(e.getMessage());
	}
	return j;
	}
	@RequestMapping("/addfieldtwo")
	@ResponseBody
	public Json addfieldtwo(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		try {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	     MultipartFile multipartFile = multipartRequest.getFile("picture");
	     UploadUtil up=new UploadUtil();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   	 //得到处理后的上传图片名称
	   	 //图片路径
	   	 String picturefileName=up.uploadManage(multipartFile, multipartRequest,"picture");
//  	 	if (picturefileName==null) {
//	   		j.setMsg("操作失败，图片不能为空！");
//	   		return j;
//		}
        Field	field=new Field();
        field.setField_id(Integer.parseInt(request.getParameter("field_id")));
        field.setValue(Integer.parseInt(request.getParameter("field_id")));
        field.setProduct_id(Integer.parseInt(request.getParameter("product_id")));
        field.setPicture(picturefileName);
        field.setNum(Integer.parseInt(request.getParameter("num")));
        field.setState(1);
	     productService.add(field);
	     j.setMsg("添加成功！");
	     j.setSuccess(true);
	} catch (Exception e) {
		logger.info("fieldAdd:"+e.getMessage());
		j.setMsg(e.getMessage());
	}
	return j;
	}
	
	@RequestMapping("/addpurchase")
	@ResponseBody
	public Json addpurchase(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		try {
		Purchase	purchase=new Purchase();
	   String product_id=request.getParameter("product_id");
		purchase.setProduct_id(Integer.parseInt(product_id));
		  String shelfnum=request.getParameter("shelfnum");
		purchase.setShelfnum(shelfnum);
		purchase.setShelfbegintime(request.getParameter("shelfbegintime"));
		purchase.setShelfendtime(request.getParameter("shelfendtime"));
		purchase.setScale(request.getParameter("scale"));
	     productService.addp(purchase);
	     logger.info("商品号为"+product_id+"的商品设置卡券数量为："+shelfnum);
	     j.setMsg("添加成功！");
	     j.setSuccess(true);
	} catch (Exception e) {
		logger.info("purchaseAdd:"+e.getMessage());
		j.setMsg(e.getMessage());
	}
	return j;
	}

}
