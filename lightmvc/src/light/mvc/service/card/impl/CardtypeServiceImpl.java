package light.mvc.service.card.impl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigInteger;
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
import light.mvc.model.sys.Torganization;
import light.mvc.model.sys.Tresource;
import light.mvc.model.sys.Trole;
import light.mvc.model.sys.Tuser;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.sys.User;
import light.mvc.service.card.CardtypeServiceI;
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
public class CardtypeServiceImpl implements CardtypeServiceI {

	@Autowired
	private BaseDaoI<Tcardtype> carddao;
	@Autowired
	private BaseDaoI<Tcard> cardddao;


	@Override
	public List<Cardtype> dataGrid(Card card, PageFilter ph) {
		
		List<Cardtype> ul = new ArrayList<Cardtype>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(*) as num,type_id,typename,b.remark/100 as price,convert(count(*)*(b.remark/100),decimal(10,2)) as total,b.id,case a.state when 0 then '未激活' WHEN 1 then '已激活' when 2 then '作废' when 3 then '已使用' when 4 then '激活失败' when 5 then '壹钱包已激活' else 'no' END as remark from wnf_card a ,wnf_card_type b where a.type_id=b.id  ";
		
		if(StringUtil.isNotEmpty(card.getState()+"")){
			sql += " and a.state = "+card.getState();
		}
		
		if(StringUtil.isNotEmpty(card.getType_id())){
			sql += " and a.type_id = "+card.getType_id();
		}
		
		sql += "     group by type_id,a.state,typename,b.remark   ";
	
		if(StringUtil.isEmpty(card.getType_id())&&StringUtil.isEmpty(card.getState()+"")){
			
			sql += " UNION  select sum(num) as num,type_id,typename,price,sum(total) as total,id,'全部' as remark from  (select count(*) as num,type_id,typename,b.remark/100 as price,convert(count(*)*(b.remark/100),decimal(10,2)) as total,b.id,a.state,b.remark from wnf_card a ,wnf_card_type b where a.type_id=b.id   ";
		
			sql+="    group by type_id,a.state,typename,b.remark) as f group by f.typename,f.remark, f.type_id ";
			
		}
		
		List<Object[]> l = carddao.findBySql(sql , ph.getPage(), ph.getRows());
		
		for (Object[] o : l) {
			Cardtype c = new Cardtype();
			c.setNum(o[0]==null?"":o[0].toString());
			c.setTypename(o[2]==null?"":o[2].toString());
			c.setPrice(o[3]==null?"":o[3].toString());
			c.setTotal(o[4]==null?"":o[4].toString());
			c.setId(Long.valueOf(o[5]==null?"":o[5].toString()));
			c.setRemark(o[6]==null?"":o[6].toString());
			ul.add(c);
		}
		
		return ul;
	}
	
	
	@Override
	public List<Cardtype> card_type_combox() {
		//select count(*) as num,type_id,typename,b.remark/100 as price,convert(count(*)*(b.remark/100),decimal(10,2)) as total from wnf_card a ,wnf_card_type b where a.type_id=b.id and a.state= group by type_id,typename,b.remark  limit
		List<Cardtype> ul = new ArrayList<Cardtype>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select id,typename  from wnf_card_type ";
		
		
		
		List<Object[]> l = carddao.findBySql(sql);
		
		for (Object[] o : l) {
			Cardtype c = new Cardtype();
			c.setId(Long.valueOf(o[0].toString()));
			c.setTypename(o[1].toString());
			
			ul.add(c);
		}
		
		return ul;
	}

	
	
	
/*	public Cardtype getBean(){
		   BeanInfo beanInfo = Introspector.getBeanInfo(Card.class); // 获取类属性
	        Object obj = Cardtype.class.newInstance(); // 创建 JavaBean 对象

	        // 给 JavaBean 对象的属性赋值
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for (int i = 0; i< propertyDescriptors.length; i++) {
	            PropertyDescriptor descriptor = propertyDescriptors[i];
	            String propertyName = descriptor.getName();

	            if (map.containsKey(propertyName)) {
	                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
	                Object value = map.get(propertyName);

	                Object[] args = new Object[1];
	                args[0] = value;

	                descriptor.getWriteMethod().invoke(obj, args);
	            }
	        }
	        return obj;
	}
	*/
	
	
	@Override
	public Long count(Card card, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = " from wnf_card_type t,wnf_card tc ";
//		String sql = "select count(*) from  (select count(*) from wnf_card_type t,wnf_card tc  where t.id = tc.type_id  ";
//		if(StringUtil.isNotEmpty(card.getState()+"")){
//			sql += " and tc.state = "+card.getState();
//		}
//		
//
//		if(StringUtil.isNotEmpty(card.getType_id())){
//			sql += " and tc.type_id = "+card.getType_id();
//		}
//		
//		sql += " group by type_id,tc.state ) as g ";
		
String sql = "select count(*) from  (select count(*) as num,type_id,typename,b.remark/100 as price,convert(count(*)*(b.remark/100),decimal(10,2)) as total,b.id,case a.state when 0 then '未激活' WHEN 1 then '已激活' when 2 then '作废' when 3 then '已使用' when 4 then '激活失败' when 5 then '壹钱包已激活' else 'no' END as remark from wnf_card a ,wnf_card_type b where a.type_id=b.id  ";
		
		if(StringUtil.isNotEmpty(card.getState()+"")){
			sql += " and a.state = "+card.getState();
		}
		
		if(StringUtil.isNotEmpty(card.getType_id())){
			sql += " and a.type_id = "+card.getType_id();
		}
		
		sql += "     group by type_id,a.state,typename,b.remark   ";
	
		if(StringUtil.isEmpty(card.getType_id())&&StringUtil.isEmpty(card.getState()+"")){
			
			sql += " UNION  select sum(num) as num,type_id,typename,price,sum(total) as total,id,'全部' as remark from  (select count(*) as num,type_id,typename,b.remark/100 as price,convert(count(*)*(b.remark/100),decimal(10,2)) as total,b.id,a.state,b.remark from wnf_card a ,wnf_card_type b where a.type_id=b.id   ";
		
			sql+="    group by type_id,a.state,typename,b.remark) as f group by f.typename,f.remark, f.type_id ";
			
		}
		sql+=" ) as u";

		BigInteger count = carddao.countBySql(sql);
		
		return count.longValue();
	}

	private String whereHql(Card card, Map<String, Object> params) {
		String hql = "";
		if (card != null) {
			hql += " where t.id = tc.type_id  and 1=1  ";
			if (card.getState() != null) {
				hql += " and tc.state = :state";
				params.put("state",card.getState());
			}
			
		}
		hql+=" group by type_id,typename,t.remark ";
		return hql;
	}
	
	
	private String whereSql(Card card){
		return null;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}


//	@Override
//	public List<Card> dataGrid2(Card card, PageFilter ph) {
//		List<Card> ul = new ArrayList<Card>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		String hql = " from Tcard t ";
//		List<Tcard> l = cardddao.find(hql, params, ph.getPage(), ph.getRows());
//		for (Tcard t : l) {
//			Card u = new Card();
//			BeanUtils.copyProperties(t, u);
//			ul.add(u);
//		}
//		return ul;
//	}
//
//
//	@Override
//	public Long count2(Card card, PageFilter ph) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		String hql = " from Tcard t ";
//		return cardddao.count("select count(*) " + hql + whereHql(card, params), params);
//	}

}
