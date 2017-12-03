package light.mvc.controller.richtext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.richtext.Richtext;
import light.mvc.service.richtext.RichtextServiceI;
import light.mvc.utils.StringUtil;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/richtext")
public class RichtextController extends BaseController {
	Logger logger = Logger.getLogger(RichtextController.class.getName());
	@Autowired
	private RichtextServiceI richtextService;
	@RequestMapping("/manager")
	public String manager() {
		return "/richtext/richtext";
	}
	@RequestMapping("/managers")
	public String managers() {
		return "/richtext/richtextAdd";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Richtext richtext, PageFilter ph){
		Grid grid = new Grid();
		grid.setRows(richtextService.dataGrid(richtext, ph));
		grid.setTotal(richtextService.count(richtext, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	@ResponseBody
	public JSONArray addPage(HttpServletRequest request, Long id) {
		JSONArray jsonarray = new JSONArray();
		try {
			Richtext richtext = richtextService.get(id);
			//格式转换
			String str = StringEscapeUtils.unescapeHtml3(richtext.getRichcontext());
			richtext.setRichcontext(str);
			logger.info(richtext.getRichcontext());
			request.setAttribute("richtext", richtext);
			jsonarray = JSONArray.fromObject(richtext);
		} catch (Exception e) {
			logger.info("richtextAddPage:" + e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/combobox_data")
	@ResponseBody
	public JSONArray combobox_data(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Richtext> list = richtextService.combobox_data();
			if (list == null) {
				list = new ArrayList<Richtext>();
			}
			Richtext r = new Richtext();
			r.setId(Long.parseLong("-1"));
			r.setName("添加");
			list.add(0, r);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("richtextCombox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Richtext richtext){
		Json j = new Json();
		try {
			richtext.setCreatedate(new Date());
			if (richtext.getId().equals(Long.parseLong("-1"))) {
				richtextService.add(richtext);
			}
			else {
				Richtext richtexts = richtextService.get(richtext.getId());
				richtext.setCreatedate(richtexts.getCreatedate());
				richtext.setUpddate(new Date());
				richtextService.edit(richtext);
			}
			j.setSuccess(true);
			j.setMsg("保存成功！");
		} catch (Exception e) {
			logger.info("richtextAdd:"+e.getMessage());
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
