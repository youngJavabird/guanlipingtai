package light.mvc.controller.jjacount;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import light.mvc.controller.base.BaseController;
import light.mvc.model.jjacount.TJjacount;
import light.mvc.pageModel.jjacount.Jjacount;
import light.mvc.service.jjacount.WnfJjacountService;

@Controller
@RequestMapping("/jjacount")
public class WnfJjacountController extends BaseController{

	@Autowired
	private WnfJjacountService wnfJjacountService;

	private Jjacount jjacount;


	public Jjacount getJjacount() {
		return jjacount;
	}
	public void setJjacount(Jjacount jjacount) {
		this.jjacount = jjacount;
	}
	@RequestMapping("/manager")
	public String manager() {
		return "/jjacount/wnfjjacount";
	}
	@RequestMapping("/check2")
	public String getjjaccount(HttpServletRequest request,HttpServletResponse response){
		String url="";
		String sendMessage="";
		String jjaccount=request.getParameter("jjaccount");
		Jjacount jjacount=new Jjacount();
		jjacount.setJjaccount(jjaccount);
		int res=wnfJjacountService.updstate(jjacount);
		if(res>0){
			sendMessage="核销成功";
		}else{
			sendMessage="核销出错，请重试。。";
		}
		request.setAttribute("sendmessage", sendMessage);
		return "/jjacount/wnfjjacount";
		
//		
//		for (Jjacount jjacount : list) {
//			if(jjaccount.length()!=16){
//				sendMessage="券号长度必须为十六位！";
//				request.setAttribute("sendmessage", sendMessage);
//				url="/jjacount/wnfjjacount";
//			}
//			else if(jjacount.getJjaccount().equals(jjaccount)){
//				
////				sendMessage="有效券号，是否核销";
////				request.setAttribute("sendmessage", sendMessage);		
//				int res=wnfJjacountService.updstate(jjacount);
//				if(res>0){
//					
//					sendMessage="核销成功";
//					request.setAttribute("sendmessage", sendMessage);
//					url="/jjacount/wnfjjacount";
//					break;
//				
//				}else{
//					sendMessage="该券号已核销过";
//					request.setAttribute("sendmessage", sendMessage);
//					url="/jjacount/wnfjjacount";
//				}
//			}
//			else {
//				
//				sendMessage="券号不正确";
//				request.setAttribute("sendmessage", sendMessage);
//				url="/jjacount/wnfjjacount";
//        }
//			
//		}
//		return url;
}
	
	@RequestMapping("/check")
	public String getjjaccount1(HttpServletRequest request,HttpServletResponse response){
		String url="";
		String sendMessage="";
		String jjaccount=request.getParameter("jjaccount");
		if(!jjaccount.equals("") && jjaccount!=null){
			List<TJjacount> list=wnfJjacountService.check(jjaccount);	
			if(jjaccount.length()!=16){
				sendMessage="券号长度必须为十六位！";
				request.setAttribute("sendmessage", sendMessage);
				url="/jjacount/wnfjjacount";
			}else{
				if(list.size()!=0){
					//表示存在此号码
					if(list.get(0).getState().equals("02")){
						sendMessage="该券号已核销过";
						request.setAttribute("sendmessage", sendMessage);
						url="/jjacount/wnfjjacount";
					}
					else{
						//调方法该状态成功后
						
						
						//再显示已核销
						sendMessage="确定核销此券号？"+jjaccount+"";
						request.setAttribute("sendmessage", sendMessage);
						url="/jjacount/wnfjjacount";
					}
				}else{
					sendMessage="券号不正确!";
					request.setAttribute("sendmessage", sendMessage);
					url="/jjacount/wnfjjacount";
				}
			}
		}else{
			sendMessage="请输入券号!";
			request.setAttribute("sendmessage", sendMessage);
			url="/jjacount/wnfjjacount";
		}
		return url;
		
	}
}
