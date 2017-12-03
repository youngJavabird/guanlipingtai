package light.mvc.controller.supplier;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.supplier.Supplier;
import light.mvc.service.supplier.SupplierServiceI;

@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseController {

	Logger logger = Logger.getLogger(SupplierController.class.getName());

	@Autowired
	private SupplierServiceI supplierservice;
	@RequestMapping("/manager")
	public String manager() {
		return "/supplier/supplier";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Supplier supplier, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(supplierservice.dataGrid(supplier, ph));
		grid.setTotal(supplierservice.count(supplier, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage(){
		return "/supplier/supplierAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Supplier r = supplierservice.get(id);
		request.setAttribute("supplier", r);
		return "/supplier/supplierEdit";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Supplier supplier){
		Json j = new Json();
		try {
			String uuid = UUID.randomUUID().toString();
			supplier.setCreatedate(new Date());
			supplier.setState("00");
			supplier.setGuid(uuid);
			supplierservice.add(supplier);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			logger.info("supplierAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Supplier supplier){
		Json j = new Json();
		try {
			Supplier suppliers = supplierservice.get(supplier.getId());
			supplier.setCreatedate(suppliers.getCreatedate());
			supplier.setGuid(suppliers.getGuid());
			supplier.setUpddate(new Date());
			supplierservice.edit(supplier);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			logger.info("supplierAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
}
