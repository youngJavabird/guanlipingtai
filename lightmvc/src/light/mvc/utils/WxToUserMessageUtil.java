package light.mvc.utils;

import java.io.IOException;

import light.mvc.pageModel.orderinfo.PostBean;
import light.mvc.pageModel.orderinfo.TextMessage;



/**
 * 发送文字给指定用户
 * @author hanyi
 *
 */
public class WxToUserMessageUtil {
	//发送消息给用户
	public static String toMassagesForUser(String fromUserName,String massage,String token){

		
		PostBean p=new PostBean();
		p.setToken(token);
		p.setTouser(fromUserName);
		TextMessage tx=new TextMessage();
		tx.setContent(massage);
		try {
			String rString=PostUtil.Post(tx, p);
			System.out.println(rString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "error";
		}
		return "ok";
	}
	public static void main(String[] args) {
		String str = toMassagesForUser("oG-orw6SRqlEQ_hLflTXFbZLYFp4","你好","Ls8FJFEG1GBgyURbSQKQmjBriLeHd4BiWorB_H0Gsb6SJZCoYaI_p6A9H0OzyT-9aPeJL1l1eCvav8QU481ixWOnowOOVozUuz3NMY0B7Kr8hZjSg38NyMBlse85E2W7LAEaAFAPEI");
		System.out.println(str);
	}
}
