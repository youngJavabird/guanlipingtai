package light.mvc.service.balance.impl;

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
import light.mvc.model.balance.Tbalance;
import light.mvc.model.card.Tcard;
import light.mvc.model.card.Tcardtype;
import light.mvc.model.cardchannel.Tcardchannel;
import light.mvc.model.scoredetail.Tscoredetail;
import light.mvc.model.sys.Torganization;
import light.mvc.model.sys.Tresource;
import light.mvc.model.sys.Trole;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.balance.Balance;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;
import light.mvc.service.balance.BalanceServiceI;
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
public class BalanceServiceImpl implements BalanceServiceI {

	@Autowired
	private BaseDaoI<Tbalance> balancedao;


	@Override
	public List<Balance> dataGrid(Balance balance, PageFilter ph) {
		
		List<Balance> ul = new ArrayList<Balance>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select userid, phone, balance/100 as balance from wnf_jt_account_rp where phone is not null ";
		
		List<Object[]> l = balancedao.findBySql(sql, ph.getPage(), ph.getRows());
		
		for (Object[] o : l) {
			Balance c = new Balance();
			c.setUserid(o[0]==null?"":o[0].toString());
			c.setPhone(o[1]==null?"":o[1].toString());
			c.setBalance(o[2]==null?"":o[2].toString());
			ul.add(c);
		}
		
		return ul;
	}
	
	
	
	
	@Override
	public Long count(Balance balance, PageFilter ph) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select COUNT(1) from wnf_jt_account_rp where phone is not null ";

		BigInteger count = balancedao.countBySql(sql);
		
		return count.longValue();
	}

	private String whereHql(Scoredetail scoredetail, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = "";
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeStart()+"")){
			String date = sdf.format(scoredetail.getCreatedatetimeStart());
			sql += " and unix_timestamp(s.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(scoredetail.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(scoredetail.getCreatedatetimeEnd());
			sql += " and unix_timestamp(s.createdate) <= unix_timestamp('"+date2+"')";

		}
		sql += "  order by s.userid,s.createdate  ";
		return sql;
	}
	
	


}
