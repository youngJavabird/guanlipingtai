package light.mvc.controller.smstemp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.smstemp.Smstemp;
import light.mvc.pageModel.transtype.Transtype;
import light.mvc.service.smstemp.SmstempServiceI;
import light.mvc.utils.StringUtil;

@Controller
@RequestMapping("/smstemp")
public class SmstempController extends BaseController {
	Logger logger = Logger.getLogger(SmstempController.class.getName());
	@Autowired
	private SmstempServiceI smstempService;
	@RequestMapping("/manager")
	public String manager() {
		return "/smstemp/smstemp";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Smstemp smstemp, PageFilter ph){
		Grid grid = new Grid();
		grid.setRows(smstempService.dataGrid(smstemp, ph));
		grid.setTotal(smstempService.count(smstemp, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/smstemp/smstempAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Smstemp smstemp = smstempService.get(id);
		request.setAttribute("smstemp", smstemp);
		return "/smstemp/smstempEdit";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Smstemp smstemp){
		Json j = new Json();
		try {
			if (StringUtil.isNotEmpty(smstemp.getSmstemplet())) {
				j.setMsg("短信模板不能为空！");
				return j;
			}
			smstemp.setCreatetime(new Date());
			smstempService.add(smstemp);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			logger.info("SmstempAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Smstemp smstemp){
		Json j = new Json();
		try {
			if (StringUtil.isNotEmpty(smstemp.getSmstemplet())) {
				j.setMsg("短信模板不能为空！");
				return j;
			}
			Smstemp s = smstempService.get(smstemp.getId());
			smstemp.setCreatetime(s.getCreatetime());
			smstemp.setUpddate(new Date());
			smstempService.edit(smstemp);
			j.setSuccess(true);
			j.setMsg("修改成功！");
		} catch (Exception e) {
			logger.info("SmstempEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/drop")
	@ResponseBody
	public Json drop(Long id) {
		Json j = new Json();
		try {
			smstempService.delete(id);
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			logger.info("SmstempDrop:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/activityCombox")
	@ResponseBody
	public JSONArray activitycombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Activity> list = smstempService.getActivityCombox();
			if (list==null) {
				list = new ArrayList<Activity>();
			}
			Activity activity = new Activity();
			activity.setId(Long.parseLong("-1"));
			activity.setName("全部");
			list.add(0,activity);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("ActivityCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/transCombox")
	@ResponseBody
	public JSONArray transcombox(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Transtype> list = smstempService.getTransCombox();
			Transtype transtype = new Transtype();
			transtype.setTranstype("全部");
			transtype.setTranstypename("全部");
			list.add(0, transtype);
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("TransCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/activityComboxs")
	@ResponseBody
	public JSONArray activitycomboxs(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Activity> list = smstempService.getActivityCombox();
			if (list==null) {
				list = new ArrayList<Activity>();
			}
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("ActivityCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	@RequestMapping("/transComboxs")
	@ResponseBody
	public JSONArray transcomboxs(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<Transtype> list = smstempService.getTransCombox();
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("TransCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
}
