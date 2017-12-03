package light.mvc.service.scoredetail.impl;

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
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.service.card.CardtypeServiceI;
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
public class ScoredetailServiceImpl implements ScoredetailServiceI {

	@Autowired
	private BaseDaoI<Tscoredetail> scoredetaildao;


	@Override
	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph) {

		List<Scoredetail> ul = new ArrayList<Scoredetail>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select * from view_cards x ";


		List<Object[]> l = scoredetaildao.findBySql(sql + whereHql(scoredetail, params)  , ph.getPage(), ph.getRows());
//		System.out.println(sql+ whereHql(scoredetail, params));
		for (Object[] o : l) {
			Scoredetail c = new Scoredetail();
			c.setUserid(o[0]==null?"":o[0].toString());			
			c.setOrderid(o[1]==null?"":o[1].toString());
			c.setCreatedate(o[2]==null?"":o[2].toString());		
			c.setPaytype(o[3]==null?"":o[3].toString());		
			c.setType(o[4]==null?"":o[4].toString());		
			c.setBalance(o[5]==null?"":o[5].toString());
			c.setPbalance(o[6]==null?"":o[6].toString());			
			c.setTypename(o[7]==null?"":o[7].toString());		
			c.setP_price(o[8]==null?"":o[8].toString());
			c.setPrice(o[9]==null?"":o[9].toString());
			c.setPurchase(o[10]==null?"":o[10].toString());
			c.setPro_num(o[11]==null?"":o[11].toString());
			c.setUpdate(o[12]==null?"":o[12].toString());
			c.setState(o[13]==null?"":o[13].toString());
			c.setRemark(o[14]==null?"":o[14].toString());
			c.setPhone(o[15]==null?"":o[15].toString());
			c.setTypeid(o[16]==null?"":o[16].toString());
			ul.add(c);
		}

		return ul;
	}




	@Override
	public Long count(Scoredetail scoredetail, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT COUNT(*) from view_cards x where 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
			sql += " and  x.phone like "+"'%" + scoredetail.getPhone() + "%'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
			sql += " and  x.userid = "+"'"+ scoredetail.getUserid()+"'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getType()+"")){
			sql += " and  x.type = "+ scoredetail.getType();
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(x.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(x.createdate) <= unix_timestamp('"+date2+"')";

		}

//		sql += "  and (c.state=1 or b.state<>1 or (b.state=1 and c.state <> 2) or a.type in (5,8))  ";


		BigInteger count = scoredetaildao.countBySql(sql);

		return count.longValue();
	}

	private String whereHql(Scoredetail scoredetail, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = " where 1=1";
		if (StringUtil.isNotEmpty(scoredetail.getPhone()+"")){
			sql += " and  x.phone like "+"'%" + scoredetail.getPhone() + "%'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getUserid()+"")){
			sql += " and  x.userid = "+"'"+ scoredetail.getUserid()+"'";
		}
		if (StringUtil.isNotEmpty(scoredetail.getType()+"")){
			sql += " and  x.type = "+ scoredetail.getType();
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(x.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(x.createdate) <= unix_timestamp('"+date2+"')";

		}
		sql += "  order by x.createdate desc,x.orderid,x.upddate  ";

		return sql;
	}




}
