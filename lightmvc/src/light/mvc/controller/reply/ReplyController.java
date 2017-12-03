package light.mvc.controller.reply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.carousel.Carousel;
import light.mvc.pageModel.reply.Reply;
import light.mvc.service.carousel.CarouselServicel;
import light.mvc.service.reply.ReplyServicel;
import light.mvc.utils.UploadUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController{
	
	Logger logger = Logger.getLogger(ReplyController.class.getName());
	@Autowired
	private ReplyServicel replyServicel;
	@RequestMapping("/manager")
	public String manager() {
		return "/reply/reply";
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/reply/replyAdd";
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Reply reply = replyServicel.get(id);
		request.setAttribute("reply", reply);
		return "/reply/replyEdit";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Reply reply, PageFilter ph) {
		Grid grid = new Grid();
		grid.setRows(replyServicel.dataGrid(reply, ph));
		grid.setTotal(replyServicel.count(reply, ph));
		return grid;
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
	  	
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		   	Reply reply = new Reply();
		   	reply.setName(request.getParameter("name"));
		   	reply.setDetail(request.getParameter("detail"));
		   	reply.setCreatedate(new Date());
		   	reply.setState(0);
		   	replyServicel.add(reply);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		   	
		} catch (Exception e) {
			logger.info("replyAdd:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;
	}
	private String uuid() {
		String uuid=UUID.randomUUID().toString();
		return uuid;
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
	  
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   
		   	Reply reply = new Reply();
		  	
		   	//给model赋值
		   	reply.setId(Long.parseLong(request.getParameter("id")));
		   	reply.setName(request.getParameter("name"));
		   	reply.setDetail(request.getParameter("detail"));
		   	reply.setState(Integer.parseInt(request.getParameter("state")));
		   	reply.setCreatedate(new Date());
		   	replyServicel.edit(reply);
			j.setMsg("修改成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("replyEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;

	}
}
