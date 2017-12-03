package light.mvc.controller.productfield;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.service.productfield.ProductfieldServiceI;

@Controller
@RequestMapping("/productfield")
public class ProductFieldController extends BaseController {
	Logger logger = Logger.getLogger(ProductFieldController.class.getName());
	@Autowired
	private ProductfieldServiceI productfieldService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/productfield/productfield";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Productfield productfield, PageFilter ph){
		Grid grid = new Grid();
		grid.setRows(productfieldService.dataGrid(productfield, ph));
		grid.setTotal(productfieldService.count(productfield, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/productfield/productfieldAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Productfield productfield = productfieldService.get(id);
		request.setAttribute("productfield", productfield);
		return "/productfield/productfieldEdit";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Productfield productfield){
		Json j = new Json();
		try {
			productfieldService.add(productfield);
			j.setSuccess(true);
			j.setMsg("添加成功");
		} catch (Exception e) {
			logger.info("productfieldAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Productfield productfield){
		Json j = new Json();
		try {
			productfieldService.edit(productfield);
			j.setSuccess(true);
			j.setMsg("修改成功");
		} catch (Exception e) {
			logger.info("productfieldEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/typeCombox")
	@ResponseBody
	public JSONArray typeCombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Producttype> list = productfieldService.typeCombox();
			Producttype producttype = new Producttype();
			producttype.setId(Long.parseLong("-1"));
			producttype.setTypename("全部");
			if (list == null) {
				list = new ArrayList<Producttype>();
			}
			list.add(0, producttype);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("typeCombox:" + e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/typeComboxs")
	@ResponseBody
	public JSONArray typeComboxs(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Producttype> list = productfieldService.typeCombox();
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("typeCombox:" + e.getMessage());
		}
		return jsonarray;
	}
}
