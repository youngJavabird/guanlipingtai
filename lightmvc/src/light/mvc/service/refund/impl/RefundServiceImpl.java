package light.mvc.service.refund.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
import light.mvc.service.refund.RefundServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MD5Util;
import light.mvc.utils.StringUtil;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.LogUtils;
import com.sun.accessibility.internal.resources.accessibility;

@Service
public class RefundServiceImpl implements RefundServiceI {

	@Autowired
	private BaseDaoI<Tscoredetail> scoredetaildao;


	@Override
	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph) {

		List<Scoredetail> ul = new ArrayList<Scoredetail>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT  * from view_refund a ";
		List<Object[]> l = scoredetaildao.findBySql(sql + whereHql(scoredetail, params)  , params,ph.getPage(), ph.getRows());
//		System.out.println(sql+ whereHql(scoredetail, params));
		for (Object[] o : l) {
			Scoredetail c = new Scoredetail();
			c.setUserid(o[0]==null?"":o[0].toString());			
			c.setOrderid(o[1]==null?"":o[1].toString());
			c.setPhone(o[2]==null?"":o[2].toString());		
			c.setBalance(o[3]==null?"":o[3].toString());		
			c.setPbalance(o[4]==null?"":o[4].toString());		
			c.setCreatedate(o[5]==null?"":o[5].toString());
			c.setState(o[6]==null?"":o[6].toString());
			c.setRemoney(o[7]==null?"":o[7].toString());		
			c.setRefee(o[8]==null?"":o[8].toString());
			c.setRemark(o[9]==null?"":o[9].toString());
			ul.add(c);
		}

		return ul;
	}




	@Override
	public Long count(Scoredetail scoredetail, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT count(*) FROM view_refund a where 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
			sql += " and  a.phone = "+"'" + scoredetail.getPhone() + "'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
			sql += " and  a.userid = "+"'"+ scoredetail.getUserid()+"'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(a.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.createdate) <= unix_timestamp('"+date2+"')";

		}



		BigInteger count = scoredetaildao.countBySql(sql);

		return count.longValue();
	}

	private String whereHql(Scoredetail scoredetail, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = " where 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
			sql += " and  a.phone = "+"'" + scoredetail.getPhone() + "'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
			sql += " and  a.userid = "+"'"+ scoredetail.getUserid()+"'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(a.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.createdate) <= unix_timestamp('"+date2+"')";

		}
		
		return sql;
	}




	@Override
	public Scoredetail scoredetail(String orderid) {
//		Tscoredetail t = scoredetaildao.get(Tscoredetail.class, orderid);
//		Scoredetail r = new Scoredetail();
//		BeanUtils.copyProperties(t, r);
		String sql = "SELECT a.userid, b.orderid , a.phone, b.balance/100 as balance, b.pbalance/100 as pbalance, b.createdate,case when b.type = 5 then '充值' when b.type = 7 then '退款' end ,c.refundamount,c.refee,c.remark from wnf_jt_account a "
				+" LEFT JOIN wnf_scoredetail  b on a.userid = b.userid LEFT JOIN wnf_orderinfo c on b.orderid = c.orderid where b.type = 5 and b.state = 0 and  "
				+ "c.state = '06 ' and a.phone is not null and b.orderid="+"'"+orderid+"'";
		List<Object[]> l = scoredetaildao.findBySql(sql);
		Scoredetail c = new Scoredetail();
		for (Object[] o : l) {
			c.setUserid(o[0]==null?"":o[0].toString());			
			c.setOrderid(o[1]==null?"":o[1].toString());
			c.setPhone(o[2]==null?"":o[2].toString());		
			c.setBalance(o[3]==null?"":o[3].toString());		
			c.setPbalance(o[4]==null?"":o[4].toString());		
			c.setCreatedate(o[5]==null?"":o[5].toString());
			c.setState(o[6]==null?"":o[6].toString());
			c.setRemoney(o[7]==null?"":o[7].toString());		
			c.setRefee(o[8]==null?"":o[8].toString());
			c.setRemark(o[9]==null?"":o[9].toString());
		}
		return c;
	}




}
