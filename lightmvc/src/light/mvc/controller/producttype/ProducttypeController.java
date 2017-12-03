package light.mvc.controller.producttype;

import javax.servlet.http.HttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.service.producttype.ProducttypeServiceI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/producttype")
public class ProducttypeController extends BaseController {
	Logger logger = Logger.getLogger(ProducttypeController.class.getName());
	@Autowired
	private ProducttypeServiceI producttypeService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/producttype/producttype";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Producttype producttype, PageFilter ph){
		Grid grid = new Grid();
		grid.setRows(producttypeService.dataGrid(producttype, ph));
		grid.setTotal(producttypeService.count(producttype, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/producttype/producttypeAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Producttype producttype = producttypeService.get(id);
		request.setAttribute("producttype", producttype);
		return "/producttype/producttypeEdit";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Producttype producttype){
		Json j = new Json();
		try {
			producttypeService.add(producttype);
			j.setSuccess(true);
			j.setMsg("添加成功");
		} catch (Exception e) {
			logger.info("producttypeAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Producttype producttype){
		Json j = new Json();
		try {
			producttypeService.edit(producttype);
			j.setSuccess(true);
			j.setMsg("修改成功");
		} catch (Exception e) {
			logger.info("producttypeEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
}
