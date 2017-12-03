package light.mvc.service.liquidation.impl;

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
import light.mvc.service.liquidation.LiquidationServiceI;
import light.mvc.service.reconciliation.ReconciliationServiceI;
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
public class LiquidationServiceImpl implements LiquidationServiceI {

	@Autowired
	private BaseDaoI<Tscoredetail> scoredetaildao;


	@Override
	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph) {

		List<Scoredetail> ul = new ArrayList<Scoredetail>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select '抵用' as type, count(*) as '张数', b.new_price/100 as '购买价格', "
				+"  b.price/100 '券面价格',"
				+" DATE_FORMAT(a.usedate,GET_FORMAT(DATE,'INTERNAL')) as ddate "
				+" from wnf_user_card a, wnf_product b ";

		List<Object[]> l = scoredetaildao.findBySql(sql + whereHql(scoredetail, params)  , params,ph.getPage(), ph.getRows());
		for (Object[] o : l) {
			Scoredetail c = new Scoredetail();
			c.setUserid(o[0]==null?"":o[0].toString());			
			c.setOrderid(o[1]==null?"":o[1].toString());
			c.setPhone(o[2]==null?"":o[2].toString());		
			c.setBalance(o[3]==null?"":o[3].toString());		
			c.setPbalance(o[4]==null?"":o[4].toString());		
			ul.add(c);
		}

		return ul;
	}




	@Override
	public Long count(Scoredetail scoredetail, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = "select count(*) from (select '抵用' as type, count(*) as '张数', b.new_price/100 as '购买价格', "
				+"  b.price/100 '券面价格',"
				+" DATE_FORMAT(a.usedate,GET_FORMAT(DATE,'INTERNAL')) as ddate "
				+" from wnf_user_card a, wnf_product b where 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")&&StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.usedate)>=unix_timestamp('"+date+"') ";
			sql += " and unix_timestamp(a.usedate)<=unix_timestamp('"+date2+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")&&StringUtil.isEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += "and unix_timestamp(a.usedate)>=unix_timestamp('"+date+"') ";

		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")&&StringUtil.isEmpty(scoredetail.getCreatedatetimeStart()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += "and unix_timestamp(a.usedate)<=unix_timestamp('"+date2+"') ";

		}
		sql += " and a.state in(1) and a.cardtype in(50) group by ddate) d";

		BigInteger count = scoredetaildao.countBySql(sql);
		if(count==null){
			BigInteger bigInt=new BigInteger("0");
			count=bigInt;
		}
		return count.longValue();
	}

	private String whereHql(Scoredetail scoredetail, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = " where 1=1 and b.cardtypeid = a.cardtype  ";

		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")&&StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += "and unix_timestamp(a.usedate)>=unix_timestamp('"+date+"') ";
			sql += " and unix_timestamp(a.usedate)<=unix_timestamp('"+date2+"')";

		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")&&StringUtil.isEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += "and unix_timestamp(a.usedate)>=unix_timestamp('"+date+"') ";


		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")&&StringUtil.isEmpty(scoredetail.getCreatedatetimeStart()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += "and unix_timestamp(a.usedate)<=unix_timestamp('"+date2+"') ";


		}
		sql += "and a.state in(1) and a.cardtype in(50) group by ddate";
		

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
