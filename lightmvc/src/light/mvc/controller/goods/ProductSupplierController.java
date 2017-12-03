package light.mvc.controller.goods;

import java.util.List;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.ProductSupplier;

import light.mvc.service.goods.ProductSupplierServiceI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goodssupplier")
public class ProductSupplierController extends BaseController{
	@Autowired
	private ProductSupplierServiceI productsupplierService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/goodssupplier/productsupplier";
	}

	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<ProductSupplier> treeGrid() {
		return productsupplierService.treeGrid();
	}
	
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<Tree> tree() {
		return productsupplierService.tree();
	}
}
