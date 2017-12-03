package light.mvc.controller.headlelines;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.headlelines.Headlelines;
import light.mvc.service.headlelines.HeadlelinesServiceI;
import light.mvc.utils.StringUtil;
import light.mvc.utils.UploadUtil;

@Controller
@RequestMapping("/headlelines")
public class HeadlelinesController extends BaseController {
	Logger logger = Logger.getLogger(HeadlelinesController.class.getName());
	
	@Autowired
	private HeadlelinesServiceI headlelinesService;
	
	@RequestMapping("/manager")
	public String manager() {
		return "/headlelines/headlelines";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Headlelines headlelines, PageFilter ph){
		Grid grid = new Grid();
		grid.setRows(headlelinesService.dataGrid(headlelines, ph));
		grid.setTotal(headlelinesService.count(headlelines, ph));
		return grid;
	}
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "/headlelines/headlelinesAdd";
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Headlelines headlelines = headlelinesService.get(id);
		request.setAttribute("headlelines", headlelines);
		return "/headlelines/headlelinesEdit";
	}
	
	/**
	 * 添加头条
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
			//获取数据库最大序号，并校验序号是否可用
			int sort = Integer.parseInt(request.getParameter("sort"));
			BigInteger retnum = headlelinesService.getMaxSort();
			int maxsort = 0;
			if (retnum!=null) {
				maxsort = retnum.intValue();
			}
			if (sort-1>maxsort) {
				j.setMsg("该序号无效,有效序号为0-"+(maxsort+1)+"之间！");
				return j;
			}
			//上传图片
		   	String picturefileName=up.uploadManage(multipartFile1, multipartRequest,"picturetopline");
		   	if (picturefileName.equals("")) {
		   		j.setMsg("操作失败，图片上传异常！");
		   		return j;
			}
			
			//sort大于等于当前sort的全部加1
			headlelinesService.updateOtherInfo(Integer.parseInt(request.getParameter("sort")));
			//新增该几录
			String uuid = UUID.randomUUID().toString();
			Headlelines headlelines = new Headlelines();
			headlelines.setGuid(uuid);
			headlelines.setDescribes(request.getParameter("describes"));
			headlelines.setHref(request.getParameter("href"));
			headlelines.setSort(Integer.parseInt(request.getParameter("sort")));
			headlelines.setTitle(request.getParameter("title"));
			headlelines.setClicktimes(Integer.parseInt(request.getParameter("virclicktime")));
			headlelines.setPicture(picturefileName);
			headlelines.setCreatetime(new Date());
			headlelines.setClicktimes(0);
			headlelinesService.add(headlelines);
	   		j.setMsg("添加成功！");
	   		j.setSuccess(true);
		} catch (Exception e) {
			logger.info("headlelinesAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
	/**
	 * 修改头条
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
			//获取数据库最大序号，并校验序号是否可用
			int sort = Integer.parseInt(request.getParameter("sort"));
			BigInteger retnum = headlelinesService.getMaxSort();
			int maxsort = 0;
			if (retnum!=null) {
				maxsort = retnum.intValue();
			}
			if (sort-1>maxsort) {
				j.setMsg("该序号无效,有效序号为0-"+(maxsort+1)+"之间！");
				return j;
			}
			String picturefileName = "";
			Headlelines headle = headlelinesService.get(Long.parseLong(request.getParameter("id")));
			
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		picturefileName = headle.getPicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturefileName=up.uploadManage(multipartFile1, multipartRequest,"picturetopline");
		   	}
		   	if (picturefileName.equals("")) {
		   		j.setMsg("操作失败，图片上传异常！");
		   		return j;
			}
			
			headlelinesService.updateSort(headle.getSort(),Integer.parseInt(request.getParameter("sort")));
			//新增该几录
			Headlelines headlelines = new Headlelines();
			headlelines.setId(Long.parseLong(request.getParameter("id")));
			headlelines.setGuid(headle.getGuid());
			headlelines.setDescribes(request.getParameter("describes"));
			headlelines.setHref(request.getParameter("href"));
			headlelines.setSort(Integer.parseInt(request.getParameter("sort")));
			headlelines.setTitle(request.getParameter("title"));
			headlelines.setVirclicktime(Integer.parseInt(request.getParameter("virclicktime")));
			headlelines.setClicktimes(headle.getClicktimes());
			headlelines.setPicture(picturefileName);
			headlelines.setCreatetime(headle.getCreatetime());
			headlelines.setUpddate(new Date());
			headlelinesService.edit(headlelines);
			
	   		j.setMsg("修改成功！");
	   		j.setSuccess(true);
		} catch (Exception e) {
			logger.info("headlelinesAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	@RequestMapping("/drop")
	@ResponseBody
	public Json drop(Long id){
		Json j = new Json();
		try {
			headlelinesService.delete(id);
	   		j.setMsg("删除成功！");
	   		j.setSuccess(true);
		} catch (Exception e) {
			logger.info("headlelinesDrop:" + e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	
}
