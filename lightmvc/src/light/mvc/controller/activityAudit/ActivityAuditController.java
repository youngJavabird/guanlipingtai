package light.mvc.controller.activityAudit;

import javax.servlet.http.HttpServletRequest;

import light.mvc.controller.activity.ActivityController;
import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.service.activity.ActivityServiceI;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/activityaudit")
public class ActivityAuditController extends BaseController {
	
	Logger logger = Logger.getLogger(ActivityAuditController.class.getName());
	@Autowired
	private ActivityServiceI activityservice;
	@RequestMapping("/manager")
	public String manager() {
		return "/activityaudit/activityAudit";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Activity activity, PageFilter ph) {
		Grid grid = new Grid();
		activity.setState("4");
		grid.setRows(activityservice.dataGrid(activity, ph));
		grid.setTotal(activityservice.count(activity, ph));
		return grid;
	}
	
	@RequestMapping("/showImgPage")
	public String showImgPage(HttpServletRequest request, Long id) {
		Activity activity = activityservice.get(id);
		request.setAttribute("activity", activity);
		return "/activity/activityShowImg";
	}
	
	@RequestMapping("/throughaudit")
	@ResponseBody
	public Json throughAudit(Long id){
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(id);
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(id);
			activitys.setState("0");
			activityservice.edit(activitys);
			j.setMsg("该活动已成功申请审核并上架！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityAudit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	@RequestMapping("/refuseaudit")
	@ResponseBody
	public Json refuseAudit(Long id, String reason){
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(id);
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(id);
			activitys.setState("3");
			activitys.setReason(reason);
			activityservice.edit(activitys);
			j.setMsg("该活动已成功拒绝审核！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityAudit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
}
