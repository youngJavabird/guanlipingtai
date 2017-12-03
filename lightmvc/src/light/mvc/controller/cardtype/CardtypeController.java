package light.mvc.controller.cardtype;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.cardtype.Cardtypee;
import light.mvc.service.cardtype.CardtypeeServicel;


@Controller
@RequestMapping("/cardtype")
public class CardtypeController extends BaseController {

	Logger logger = Logger.getLogger(CardtypeController.class.getName());
	@Autowired
	private CardtypeeServicel cardtypeService;

	
	/**
	 * 加载下拉框数据
	 * @return
	 */
	@RequestMapping("/combobox")
	@ResponseBody
	public JSONArray combobox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Cardtypee> list = cardtypeService.getCombox();
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("CradtypeCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	
	
}
