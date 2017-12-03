package light.mvc.service.exchange.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import light.mvc.dao.BaseDaoI;
import light.mvc.framework.constant.GlobalConstant;
import light.mvc.model.card.Tcard;
import light.mvc.model.card.Tcardtype;
import light.mvc.model.cardchannel.Tcardchannel;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.scoredetail.Tscoredetail;
import light.mvc.model.sys.Torganization;
import light.mvc.model.sys.Tresource;
import light.mvc.model.sys.Trole;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.service.card.CardtypeServiceI;
import light.mvc.service.exchange.ExchangeServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MD5Util;
import light.mvc.utils.ResponseCodeDefault;
import light.mvc.utils.SSocketClientImpl;
import light.mvc.utils.StringUtil;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.LogUtils;
import com.sun.accessibility.internal.resources.accessibility;

@Service
public class ExchangeServiceImpl implements ExchangeServiceI {

	@Autowired
	private BaseDaoI<Tscoredetail> scoredetaildao;


	@Override
	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph) {

		List<Scoredetail> ul = new ArrayList<Scoredetail>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select b.userid, b.phone, a.price/100, a.p_price/100,g.card_code,"
                   +" e.typename,"
                   +" IFNULL(case when d.state = 0 then '未使用' when d.state = 1 then '已使用' "
                   +" when d.state = 2 then '作废' when d.state = 7 then '已使用并隐藏' "
                   +" when d.state = 10 then '已退卡券未加钱'  when d.state = 11 then '已退卡券已加钱' when d.state = 20 then '退券' end "
                   +" ,case when  c.state=0 then '待发送' when  c.state=3 then '发券失败' "
                   +" when  c.state=8 then '预备卡券'  when  c.state=9 then '未激活'"
                   +" when c.state = 10 then '已退卡券未加钱'  when c.state = 11 then '已退卡券已加钱' end),"
                   +" IFNULL(d.usedate,c.upddate),g.guid,e.id as cardtype,a.orderid,c.id from wnf_scoredetail a"
                   +" left JOIN wnf_jt_account b on a.userid = b.userid "
                   +" LEFT JOIN wnf_card_send c on a.orderid = c.orderid"
                   +" LEFT JOIN wnf_user_card d on c.usercard_id = d.id"
                   +" LEFT JOIN wnf_card_type e on d.cardtype = e.id or c.type_id = e.id"
                   +" 	LEFT JOIN wnf_product h on a.productid = h.cardtypeid"
                   +" LEFT JOIN wnf_card g on d.card_id = g.guid where h.giftcard = 0 "
		             + whereHql(scoredetail, params) +
                 "union ALL select b.userid, b.phone, c.payprice/100 ,i.price/100,g.card_code,"
                   +" e.typename,"
                   +" IFNULL(case when d.state = 0 then '未使用' when d.state = 1 then '已使用' "
                   +" when d.state = 2 then '作废' when d.state = 7 then '已使用并隐藏' "
                   +" when d.state = 10 then '已退卡券未加钱'  when d.state = 11 then '已退卡券已加钱' when d.state = 20 then '退券' end "
                   +" ,case when  c.state=0 then '待发送' when  c.state=3 then '发券失败' "
                   +" when  c.state=8 then '预备卡券'  when  c.state=9 then '未激活'"
                   +" when c.state = 10 then '已退卡券未加钱'  when c.state = 11 then '已退卡券已加钱' end),"
                   +" IFNULL(d.usedate,c.upddate),g.guid,e.id as cardtype,a.orderid,c.id from wnf_scoredetail a"
                   +" left JOIN wnf_jt_account b on a.userid = b.userid "
                   +" LEFT JOIN wnf_card_send c on a.orderid = c.orderid"
                   +" LEFT JOIN wnf_user_card d on c.usercard_id = d.id"
                   +" LEFT JOIN wnf_card_type e on d.cardtype = e.id or c.type_id = e.id"
                   +" 	LEFT JOIN wnf_product h on a.productid = h.cardtypeid"
                   +" LEFT JOIN wnf_product i on c.type_id = i.cardtypeid"
                   +" LEFT JOIN wnf_card g on d.card_id = g.guid where h.giftcard = 1 ";
		List<Object[]> l = scoredetaildao.findBySql(sql + whereHql(scoredetail, params)  , params,ph.getPage(), ph.getRows());
		for (Object[] o : l) {
			Scoredetail c = new Scoredetail();
			c.setUserid(o[0]==null?"":o[0].toString());			
			c.setPhone(o[1]==null?"":o[1].toString());
			c.setPrice(o[2]==null?"":o[2].toString());		
			c.setP_price(o[3]==null?"":o[3].toString());	
			String code =o[4]==null?"":o[4].toString();
				String a =sendToCore(code+",DEC,RSA_NO_PADDING,PRI");
		    	String[] rcode=new String[1];
		    	rcode=a.split(",");
		     	if(rcode[0].equals("00")&&!rcode[0].equals("")){
		     		c.setCard_code(""+rcode[1].trim()+"");	
		     	}else{
		     		c.setCard_code("");
		     	}
			c.setTypename(o[5]==null?"":o[5].toString());
			c.setState(o[6]==null?"":o[6].toString());			
			c.setUpdate(o[7]==null?"":o[7].toString());		
			c.setGuid(o[8]==null?"":o[8].toString());		
			c.setCardtype(o[9]==null?"":o[9].toString());	
			c.setOrderid(o[10]==null?"":o[10].toString());
			c.setSendid(o[11]==null?"":o[11].toString());
			ul.add(c);
		}

		return ul;
	}

	public static String sendToCore(String message){
		
        String resultStr = null;
        SSocketClientImpl ssocket = new SSocketClientImpl("192.168.2.5", 9999, 60000);
//       SSocketClientImpl ssocket = new SSocketClientImpl("172.16.3.2", 9999, 60000);
        String r = null;
        try
        {
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSSS");
//        	System.out.println(sdf.format(System.currentTimeMillis()));
            r = ssocket.send(message.toString().getBytes("UTF-8"));
//            System.out.println(sdf.format(System.currentTimeMillis()));
            resultStr = r;
        }
        catch(Exception e)
        {
        	//log.info((new StringBuilder("sendToCore c error:")).append(e.getMessage()).toString(), e);
        	return "{\"retcode\":"+ResponseCodeDefault.SYS_ERROR_TIMEOUT+",\"result\":"+ResponseCodeDefault.ERROR_INFO.get(ResponseCodeDefault.SYS_ERROR_TIMEOUT)+"}";
        }
        //log.info((new StringBuilder("sendTtoCore responset data:")).append(resultStr).toString());
	
        return resultStr;
}


	@Override
	public Long count(Scoredetail scoredetail, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = " select sum(aa) from( select COUNT(*) as aa from wnf_scoredetail a  left JOIN wnf_jt_account b on a.userid = b.userid LEFT JOIN wnf_card_send c on a.orderid = c.orderid LEFT JOIN wnf_user_card d on c.usercard_id = d.id LEFT JOIN wnf_card_type e on d.cardtype = e.id and c.type_id = e.id 	LEFT JOIN wnf_product h on a.productid = h.cardtypeid LEFT JOIN wnf_card g on d.card_id = g.guid  where h.giftcard = 0 "    + whereHql(scoredetail, params) +
				           " union all SELECT COUNT(*) as aa FROM wnf_scoredetail a LEFT JOIN wnf_jt_account b ON a.userid = b.userid LEFT JOIN wnf_card_send c ON a.orderid = c.orderid LEFT JOIN wnf_user_card d ON c.usercard_id = d.id LEFT JOIN wnf_card_type e ON d.cardtype = e.id or c.type_id = e.id LEFT JOIN wnf_product h ON a.productid = h.cardtypeid LEFT JOIN wnf_product i on c.type_id = i.cardtypeid LEFT JOIN wnf_card g ON d.card_id = g.guid WHERE h.giftcard = 1 "+ whereHql(scoredetail, params)
				            +" ) cc";
//		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
//			sql += " and  b.phone like "+"'%" + scoredetail.getPhone() + "%'";
//		}
//		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
//			sql += " and  b.userid = "+"'"+ scoredetail.getUserid()+"'";
//		}
//		if (StringUtil.isNotEmpty(scoredetail.getCard_code()+"")){
//			String a =sendToCore(scoredetail.getCard_code()+",ENC,RSA_NO_PADDING,PUB");
//			String[] rcode=new String[1];
//	    	rcode=a.split(",");
//	     	if(rcode[0].equals("00")&&!rcode[0].equals("")){
//	     		sql += " and  g.card_code = "+"'" + rcode[1]+ "'" ;
//	     	}
//		}
//		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
//			String date = sdf.format(scoredetail.getCreatedatetimeStart());
//			sql += " and unix_timestamp(a.createdate) >= unix_timestamp('"+date+"')";
//		}
//		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
//			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
//			sql += " and unix_timestamp(a.createdate) <= unix_timestamp('"+date2+"')";
//
//		}
//
//		sql += " and  a.type = 6 and (d.state in(0,1,2,7,10,11,20) or c.state <> 1)  ";


		BigDecimal count = scoredetaildao.countBySql2(sql);
		return count.longValue();
	}

	private String whereHql(Scoredetail scoredetail, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = " and 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
			sql += " and  b.phone = "+"'" + scoredetail.getPhone() + "'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
			sql += " and  b.userid = "+"'"+ scoredetail.getUserid()+"'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCard_code()+"")){
			String a =sendToCore(scoredetail.getCard_code()+",ENC,RSA_NO_PADDING,PUB");
			String[] rcode=new String[1];
	    	rcode=a.split(",");
	     	if(rcode[0].equals("00")&&!rcode[0].equals("")){
	     		sql += " and  g.card_code = "+"'" + rcode[1]+ "'" ;
	     	}
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(a.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.createdate) <= unix_timestamp('"+date2+"')";

		}
		sql += "  and a.type in(6,10) and (d.state in(0,1,2,7,10,11,20) or c.state <> 1)  ";
		
		return sql;
	}




	@Override
	public Scoredetail scoredetail(Long id) {
		Tscoredetail t = scoredetaildao.get(Tscoredetail.class, id);
		Scoredetail r = new Scoredetail();
		BeanUtils.copyProperties(t, r);
		return r;
	}




}
