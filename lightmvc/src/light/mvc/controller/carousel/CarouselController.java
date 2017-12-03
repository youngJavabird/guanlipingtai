package light.mvc.controller.carousel;

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
import light.mvc.service.carousel.CarouselServicel;
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
@RequestMapping("/carousel")
public class CarouselController extends BaseController{
	
	Logger logger = Logger.getLogger(CarouselController.class.getName());
	@Autowired
	private CarouselServicel carouselServicel;
	@RequestMapping("/manager")
	public String manager() {
		return "/carousel/carousel";
	}
	@RequestMapping("/addPage")
	public String addPage() {
		return "/carousel/carouselAdd";
	}
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Carousel carousel = carouselServicel.get(id);
		request.setAttribute("carousel", carousel);
		return "/carousel/carouselEdit";
	}
	@RequestMapping("/showImgPage")
	public String showImgPage(HttpServletRequest request, Long id) {
		Carousel carousel = carouselServicel.get(id);
		request.setAttribute("carousel", carousel);
		return "/carousel/carouselShowImg";
	}
	@RequestMapping("/dataGrid")
	@ResponseBody
	public Grid dataGrid(Carousel carousel, PageFilter ph) {

		Grid grid = new Grid();
		grid.setRows(carouselServicel.dataGrid(carousel, ph));
		grid.setTotal(carouselServicel.count(carousel, ph));
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
		   	Carousel carousel = new Carousel();
		   	carousel.setType(Integer.parseInt(request.getParameter("type")));
		   	carousel.setName(request.getParameter("name"));
		   	carousel.setDetail(request.getParameter("detail"));
		   	carousel.setPicture(pictureName);
		   	carousel.setBannerpicture(bannerpictureName);
		   	carousel.setCreatetime(new Date());
		   	carousel.setState(0);
		   	carousel.setGuid(uuid());
		   	carousel.setHref(request.getParameter("href"));
		   	carouselServicel.add(carousel);
			j.setMsg("添加成功！");
			j.setSuccess(true);
		   	
		} catch (Exception e) {
			logger.info("CarouselAdd:"+e.getMessage());
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
			Carousel carousel= carouselServicel.get(Long.parseLong(request.getParameter("id")));
		   	//如果没有选择图片，则使用原图片
		   	if (multipartFile1.getSize() == 0) {
		   		picturefileName = carousel.getPicture();
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
		   		bannnerpicturefileName = carousel.getBannerpicture();
			}
		   	else {
		   		//上传图片至商城，并复制图片到管理平台
		   		bannnerpicturefileName = up.uploadManage(multipartFile2, multipartRequest,"bannerpicture");
			   	if (picturefileName.equals("")) {
			   		j.setMsg("操作失败，图片上传异常！");
			   		return j;
				}
		   	}
		   	Carousel carousels= new Carousel();
		  	
		   	//给model赋值
			BeanUtils.copyProperties(carousel, carousels);
			carousels.setId(Long.parseLong(request.getParameter("id")));
			carousels.setType(Integer.parseInt(request.getParameter("type")));
			carousels.setName(request.getParameter("name"));
			carousels.setDetail(request.getParameter("detail"));
			carousels.setPicture(picturefileName);
			carousels.setBannerpicture(bannnerpicturefileName);
			carousels.setState(Integer.parseInt(request.getParameter("state")));
			carousels.setHref(request.getParameter("href"));
			carouselServicel.edit(carousels);
			j.setMsg("修改成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			logger.info("CarouselEdit:"+e.getMessage());
			j.setMsg("系统异常,请联系管理员！");
		}
		return j;

	}
}
