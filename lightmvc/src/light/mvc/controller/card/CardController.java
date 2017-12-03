package light.mvc.controller.card;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.sys.User;
import light.mvc.service.base.ServiceException;
import light.mvc.service.card.CardtypeServiceI;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/card")
public class CardController extends BaseController {

	@Autowired
	private CardtypeServiceI cardservice;

	@RequestMapping("/manager")
	public String manager() {
		return "/card/card";
	}


	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Card card, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(cardservice.dataGrid(card, ph));
		grid.setTotal(cardservice.count(card, ph));
		return grid;
	}
	
	@RequestMapping("/card_type_combox")
	@ResponseBody
	public JSONArray card_type_combox() {
		List<Cardtype> cl = cardservice.card_type_combox();
		JSONArray jsonArray = JSONArray.fromObject(cl);
		//System.out.println(jsonArray.toString());
		return jsonArray;
	}
//	@RequestMapping("/dataGrid2")
//	@ResponseBody
//	public Grid dataGrid2(Card card, PageFilter ph) {
//		Grid grid = new Grid();
//		grid.setRows(cardservice.dataGrid2(card, ph));
//		grid.setTotal(cardservice.count2(card, ph));
//		return grid;
//	}

}
