package light.mvc.controller.operate;

import javax.servlet.http.HttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.demo.Demo;
import light.mvc.pageModel.operate.Operate;
import light.mvc.service.demo.DemoServiceI;
import light.mvc.service.operate.OperateServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/operate")
public class OperateController extends BaseController {

	@Autowired
	private OperateServiceI operateService;

	@RequestMapping("/manager")
	public String manager() {
		return "/operate/operate";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Operate operate, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(operateService.dataGrid(operate, ph));
		grid.setTotal(operateService.count(operate, ph));
		return grid;
	}


}
