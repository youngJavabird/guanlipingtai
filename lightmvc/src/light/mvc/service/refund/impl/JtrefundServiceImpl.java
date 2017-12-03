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
import light.mvc.model.operate.Toperate;
import light.mvc.model.refund.Tjtrefund;
import light.mvc.model.refundrcord.Trefundrecord;
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
import light.mvc.pageModel.jtrefund.Jtrefund;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.refundrecord.Refundrecord;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.service.card.CardtypeServiceI;
import light.mvc.service.exchange.ExchangeServiceI;
import light.mvc.service.operate.impl.OperateServiceImpl;
import light.mvc.service.refund.JtrefundServiceI;
import light.mvc.service.refund.RefundServiceI;
import light.mvc.service.scoredetail.ScoredetailServiceI;
import light.mvc.service.sys.UserServiceI;
import light.mvc.utils.MD5Util;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.LogUtils;
import com.sun.accessibility.internal.resources.accessibility;

@Service
public class JtrefundServiceImpl implements JtrefundServiceI {
	
	Logger logger = Logger.getLogger(JtrefundServiceImpl.class.getName());

	@Autowired
	private BaseDaoI<Tjtrefund> jtrefunddao;
	@Autowired
	private BaseDaoI<Trefundrecord> refundrecorddao;

	@Override
	public List<Jtrefund> dataGrid(Jtrefund jtrefund, PageFilter ph) {

		List<Jtrefund> ul = new ArrayList<Jtrefund>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT id,openid,moneyaccount/100 as moneyaccount,money/100 as money,refundexplain,phone,name,reason,state,createdate,remark from wnf_jt_refund t ";
		List<Object[]> l = jtrefunddao.findBySql(sql + whereHql(jtrefund, params)  , params,ph.getPage(), ph.getRows());
		for (Object[] o : l) {
			Jtrefund c = new Jtrefund();
			c.setId(o[0]==null?"":o[0].toString());			
			c.setOpenid(o[1]==null?"":o[1].toString());
			c.setMoneyaccount(o[2]==null?"":o[2].toString());		
			c.setMoney(o[3]==null?"":o[3].toString());		
			c.setRefundexplain(o[4]==null?"":o[4].toString());		
			c.setPhone(o[5]==null?"":o[5].toString());
			c.setName(o[6]==null?"":o[6].toString());
			c.setReason(o[7]==null?"":o[7].toString());		
			c.setState(o[8]==null?"":o[8].toString());
			c.setCreatedate(o[9]==null?"":o[9].toString());
			c.setRemark(o[10]==null?"":o[10].toString());
			ul.add(c);
		}
		return ul;
	}




	@Override
	public Long count(Jtrefund jtrefund, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tjtrefund t where 1=1 ";
		if (StringUtil.isNotEmpty(jtrefund.getPhone()+"")){
			hql += " and  t.phone like "+"'%" + jtrefund.getPhone() + "%'";
		}
		if (StringUtil.isNotEmpty(jtrefund.getOpenid()+"")){
			hql += " and  t.openid = "+"'"+ jtrefund.getOpenid()+"'";
		}
		if (StringUtil.isNotEmpty(jtrefund.getCreatedatetimeStart()+"")){
			String date = sdf.format(jtrefund.getCreatedatetimeStart());
			hql += " and unix_timestamp(t.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(jtrefund.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(jtrefund.getCreatedatetimeEnd());
			hql += " and unix_timestamp(t.createdate) <= unix_timestamp('"+date2+"')";

		}
//
//		sql += " and b.type = 5 and b.state = 0 and  "
//				+ "b.orderid = c.orderid and c.state = '06 ' and a.phone is not null";
//
//
//
//		BigInteger count = scoredetaildao.countBySql(sql);

		return jtrefunddao.count("select count(*) " + hql , params);
	}

	private String whereHql(Jtrefund jtrefund, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String hql = " where 1=1";
		if (StringUtil.isNotEmpty(jtrefund.getPhone()+"")){
			hql += " and  t.phone like "+"'%" + jtrefund.getPhone() + "%'";
		}
		if (StringUtil.isNotEmpty(jtrefund.getOpenid()+"")){
			hql += " and  t.openid = "+"'"+ jtrefund.getOpenid()+"'";
		}
		if (StringUtil.isNotEmpty(jtrefund.getCreatedatetimeStart()+"")){
			String date = sdf.format(jtrefund.getCreatedatetimeStart());
			hql += " and unix_timestamp(t.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(jtrefund.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(jtrefund.getCreatedatetimeEnd());
			hql += " and unix_timestamp(t.createdate) <= unix_timestamp('"+date2+"')";

		}

		

		return hql;
	}




	@Override
	public Jtrefund jtrefund(Long id) {
		Tjtrefund t = jtrefunddao.get(Tjtrefund.class, id);
		Jtrefund r = new Jtrefund();
		BeanUtils.copyProperties(t, r);
		return r;
	}




	@Override
	public void check(String id) {
		String sql = "update wnf_jt_refund set state=2 where id="+id;
		jtrefunddao.executeSql(sql);	
	}




	@Override
	public void refusecheck(String id,String remark) {
		String sql = "update wnf_jt_refund set state=3,remark="+"'"+remark+"'"+" where id="+id;
		jtrefunddao.executeSql(sql);
	}



	@Override
	public void oncheck(String id) {
		String sql = "update wnf_jt_refund set state=4 where id="+id;
		jtrefunddao.executeSql(sql);
		
	}




	@Override
	public void add(Refundrecord refundrecord) {
		try {
			Trefundrecord t = new Trefundrecord();
			BeanUtils.copyProperties(refundrecord, t);
			refundrecorddao.save(t);
		} catch (Exception e) {
			logger.info("refundrecordAdd:" + e.getMessage());
		}
	}






}
