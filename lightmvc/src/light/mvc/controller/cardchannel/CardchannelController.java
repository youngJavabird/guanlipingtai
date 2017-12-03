package light.mvc.controller.cardchannel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import light.mvc.controller.base.BaseController;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.Grid;
import light.mvc.pageModel.base.Json;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.carousel.Carousel;
import light.mvc.service.cardchannel.CardchannelServicel;
import light.mvc.utils.StringUtil;
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
@RequestMapping("/cardchannel")
public class CardchannelController extends BaseController{
	
	Logger logger = Logger.getLogger(CardchannelController.class.getName());
	@Autowired
	private CardchannelServicel cardchannelServicel;
	@RequestMapping("/manager")
	public String manager() {
		return "/cardchannel/cardchannel";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Cardchannel cardchannel, PageFilter ph) {

		Grid grid = new Grid();
		grid.setRows(cardchannelServicel.dataGrid(cardchannel, ph));
		grid.setTotal(cardchannelServicel.count(cardchannel, ph));
		return grid;
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/cardchannel/cardchannelAdd";
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Cardchannel cardchannel = cardchannelServicel.get(id);
		request.setAttribute("cardchannel", cardchannel);
		return "/cardchannel/cardchannelEdit";
	}
	@RequestMapping("/showImgPage")
	public String showImgPage(HttpServletRequest request, Long id) {
		Cardchannel cardchannel = cardchannelServicel.get(id);
		request.setAttribute("cardchannel", cardchannel);
		return "/cardchannel/cardchannelShowImg";
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
		    MultipartFile multipartFile2 = multipartRequest.getFile("bannerpicture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		   	//上传图片至商城，并复制图片到管理平台
		   	String pictureName=up.uploadManage(multipartFile1, multipartRequest,"picture");
			String bannerpictureName=up.uploadManage(multipartFile2, multipartRequest,"picture");
		   	if (pictureName==null||bannerpictureName==null) {
		   		j.setMsg("操作失败，图片上传异常！");
		   		return j;
			}
		   	Cardchannel cardchannel = new Cardchannel();
		   	//给model赋值
		   	cardchannel.setChannel(request.getParameter("channel"));	  
		   	cardchannel.setPicture(pictureName);
		   	cardchannel.setBannerpicture(bannerpictureName);
		   	cardchannel.setCreatedate(new Date());
		   	cardchannel.setState(0);
		   	cardchannel.setGuid(uuid());
		   	cardchannelServicel.add(cardchannel);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		   	
		} catch (Exception e) {
			logger.info("CardchannelAdd:"+e.getMessage());
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
	  		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		    MultipartFile multipartFile1 = multipartRequest.getFile("picture");
		    MultipartFile multipartFile2 = multipartRequest.getFile("bannerpicture");
		   	UploadUtil up = new UploadUtil();
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   	
		   	String picturefileName="";
			String bannnerpicturefileName="";
		   	Cardchannel cardchannel = cardchannelServicel.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		picturefileName = cardchannel.getPicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		picturefileName = up.uploadManage(multipartFile1, multipartRequest,"picture");
			   	if (picturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}	   
			}	
		   	if (multipartFile2.getSize() == 0) {
		   		bannnerpicturefileName = cardchannel.getBannerpicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		bannnerpicturefileName = up.uploadManage(multipartFile2, multipartRequest,"bannerpicture");
			   	if (picturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
			   	Cardchannel cardchannels= new Cardchannel();
		  	
		   	//给model赋值
			BeanUtils.copyProperties(cardchannel, cardchannels);
			cardchannels.setId(Long.parseLong(request.getParameter("id")));
			cardchannels.setChannel(request.getParameter("channel"));
			cardchannels.setPicture(picturefileName);
			cardchannels.setBannerpicture(bannnerpicturefileName);
			cardchannels.setState(Integer.parseInt(request.getParameter("state")));
			cardchannelServicel.edit(cardchannels);
			j.setMsg("修改成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("CardchannelEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;

	}
}
