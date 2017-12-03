package light.mvc.controller.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.utils.StringUtil;
import light.mvc.utils.UploadUtil;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	Logger logger = Logger.getLogger(ActivityController.class.getName());
	@Autowired
	private ActivityServiceI activityservice;
	@RequestMapping("/manager")
	public String manager() {
		return "/activity/activity";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Activity activity, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(activityservice.dataGrid(activity, ph));
		grid.setTotal(activityservice.count(activity, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/activity/activityAdd";
	}
	
	@RequestMapping("/showImgPage")
	public String showImgPage(HttpServletRequest request, Long id) {
		Activity activity = activityservice.get(id);
		request.setAttribute("activity", activity);
		return "/activity/activityShowImg";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Activity activity = activityservice.get(id);
		request.setAttribute("activity", activity);
		return "/activity/activityEdit";
	}

	@RequestMapping("/showReason")
	public String showReason(HttpServletRequest request, Long id) {
		Activity activity = activityservice.get(id);
		request.setAttribute("activity", activity);
		return "/activity/activityShowReason";
	}
	
	@RequestMapping("/showHerfPage")
	public String showHerfPage(HttpServletRequest request, Long id) {
		Activity activity = activityservice.get(id);
		request.setAttribute("activity", activity);
		return "/activity/activityEditHerf";
	}
	
	/**
	 * 添加方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
		try {
	  		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	String currTime = getCurrTime();
		    String strTime = currTime.substring(8, currTime.length());
             String strRandom = buildRandom(2) + "";
             String strReq = strTime + strRandom;
		   	//判断活动开始和结束时间是否为空
             String stime = null;
             String etime = null;
		   	if (StringUtil.isNotEmpty(request.getParameter("stime"))) {
				stime = request.getParameter("stime");
			}
		   	if (StringUtil.isNotEmpty(request.getParameter("etime"))) {
				etime = request.getParameter("etime");
			}
		   	//上传图片至商城，并复制图片到管理平台
		   	String picturefileName=up.uploadManage(multipartFile1, multipartRequest,"pictureactivity");
		   	if (picturefileName.equals("")) {
		   		j.setMsg("操作失败，图片上传异常！");
		   		return j;
			}
		   	Activity activitys = new Activity();
		   	//给model赋值
		   	activitys.setType_id(Integer.parseInt(request.getParameter("type_id")));
		   	activitys.setName(request.getParameter("name"));
		   	activitys.setDetile(request.getParameter("detile"));
		   	activitys.setPicture(picturefileName);
		   	activitys.setStime(stime);
		   	activitys.setEtime(etime);
		   	activitys.setCardtypeid(Integer.parseInt(request.getParameter("cardtypeid")));
		   	activitys.setCreatetime(new Date());
		   	activitys.setActivitycode(strReq);
		   	activitys.setState("2");
		   	activitys.setRemark("0");
		   	activitys.setSeq_id(Integer.parseInt(request.getParameter("seq_id")));
		   	activitys.setActype(Integer.parseInt(request.getParameter("actype")));
		   	activitys.setScore(Integer.parseInt(request.getParameter("score")));
			activitys.setLiveness(Integer.parseInt(request.getParameter("liveness")));
		   	activitys.setHerf(request.getParameter("herf"));
		   	activityservice.add(activitys);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		   	
		} catch (Exception e) {
			logger.info("ActivityAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 修改方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json j = new Json();
		try {
	  		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	
		   	//判断活动开始和结束时间是否为空
		   	String stime = null;
		   	String etime = null;
		   	if (StringUtil.isNotEmpty(request.getParameter("stime"))) {
				stime = request.getParameter("stime");
			}
		   	if (StringUtil.isNotEmpty(request.getParameter("etime"))) {
				etime = request.getParameter("etime");
			}
		   	String picturefileName="";
	   		Activity activity = activityservice.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		picturefileName = activity.getPicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturefileName = up.uploadManage(multipartFile1, multipartRequest,"pictureactivity");
			   	if (picturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
			}
		   	
		   	Activity activitys = new Activity();
		  	
		   	//给model赋值
			BeanUtils.copyProperties(activity, activitys);
		   	activitys.setId(Long.parseLong(request.getParameter("id")));
		   	activitys.setType_id(Integer.parseInt(request.getParameter("type_id")));
		   	activitys.setCardtypeid(Integer.parseInt(request.getParameter("cardtypeid")));
		   	activitys.setName(request.getParameter("name"));
		   	activitys.setDetile(request.getParameter("detile"));
		   	activitys.setPicture(picturefileName);
		   	activitys.setStime(stime);
		   	activitys.setEtime(etime);
		   	activitys.setState("0");
		   	activitys.setRemark("0");
		   	activitys.setSeq_id(Integer.parseInt(request.getParameter("seq_id")));
		   	activitys.setActype(Integer.parseInt(request.getParameter("actype")));
		   	activitys.setScore(Integer.parseInt(request.getParameter("score")));
		   	activitys.setLiveness(Integer.parseInt(request.getParameter("liveness")));
		   	activitys.setHerf(request.getParameter("herf"));
		   	activitys.setIshot(Integer.parseInt(request.getParameter("ishot")));
		   	activityservice.edit(activitys);
			j.setMsg("修改成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 下架
	 * @param id
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public Json audit(Long id) {
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(id);
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(id);
			activitys.setState("1");
			activityservice.edit(activitys);
			j.setMsg("该活动已成功下架！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityAudit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	/**
	 * 上架
	 * @param id
	 * @return
	 */
	@RequestMapping("/up")
	@ResponseBody
	public Json up(Long id) {
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(id);
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(id);
			activitys.setState("0");
			activityservice.edit(activitys);
			j.setMsg("该活动已成功上架！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityOut:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 提交审核
	 * @param id
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public Json check(Long id) {
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(id);
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(id);
			activitys.setState("4");
			activityservice.edit(activitys);
			j.setMsg("该活动已成功提交审核！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("Activitycheck:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	/**
	 * 修改活动链接
	 * @param Operate
	 * @return
	 */
	@RequestMapping("/editHerf")
	@ResponseBody
	public Json editHerf(Activity a) {
		Json j = new Json();
		try {
	   		Activity activity = activityservice.get(a.getId());
			Activity activitys = new Activity();
			BeanUtils.copyProperties(activity, activitys);
			activitys.setId(a.getId());
			activitys.setHerf(a.getHerf());
			activityservice.edit(activitys);
			j.setMsg("操作成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("ActivityEditHerf:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 加载下拉框数据
	 * @return
	 */
	@RequestMapping("/combobox_data")
	@ResponseBody
	public JSONArray combobox_data(){
		JSONArray jsonarray = new JSONArray();
		try {
			List<ActivityType> list = activityservice.getCombox();
			jsonarray = JSONArray.fromObject(list);  
		} catch (Exception e) {
			logger.info("ActivityCombobox:"+e.getMessage());
		}
		return jsonarray;
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
}
