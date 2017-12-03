package light.mvc.service.card.impl;

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
import light.mvc.service.card.CardtypetwoServiceI;
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
public class CardtypetwoServiceImpl implements CardtypetwoServiceI {

	@Autowired
	private BaseDaoI<Tcardtype> carddao;
	@Autowired
	private BaseDaoI<Tcard> cardddao;

	@Override
	public List<Card> dataGrid(Card card, PageFilter ph) {
		
		List<Card> ul = new ArrayList<Card>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT t.*,us.cnt '已使用' from (select b.type_id, a.name '券类型', a.num '库存张/套数',0 as '已购买套券',sum(case b.state WHEN 9 then 1 end) '未激活',"
                           +" sum(case b.state WHEN 8 then 1 end) '预备卡券',sum(case b.state WHEN 1 then 1 end) '已激活',sum(case b.state WHEN 10 then 1 end) '已退券未审核',"
                           +" sum(case b.state WHEN 11 then 1 end) '已退券已审核',sum(case b.state WHEN 3 then 1 end) '发券失败' from wnf_product a LEFT JOIN wnf_card_send b"
                           +" ON a.cardtypeid = b.type_id where b.state in (11,10,9,8,3,1) and a.giftcard = 0 and b.type_id >= 12  "
                           + whereHql(card, params) 
                          +"  group by b.type_id ) as t LEFT JOIN (select cardtype,count(*) cnt from wnf_user_card a, wnf_scoredetail b where a.state=1 and a.orderid = b.orderid " 
                           + whereHql(card, params) 
                           +" group by a.cardtype) as us  on t.type_id = us.cardtype  union all select a.cardtypeid,CONCAT('(礼包)',a.name) , a.num '库存张/套数',sum(b.pro_num) as '已购买套券',null '未激活',null '预备卡券',null '已激活',null '已退券未审核',"
                           +" null '已退券已审核',null '发券失败',null '已使用' from wnf_product a, wnf_scoredetail b where a.cardtypeid = b.productid and a.giftcard = 1 " 
                           + whereHql(card, params) 
                           +" group by a.cardtypeid ";

		
		List<Object[]> l = carddao.findBySql(sql , ph.getPage(), ph.getRows());
		
		for (Object[] o : l) {
			Card c = new Card();
			c.setCard_typeid(o[0]==null?"0":o[0].toString());
			c.setCard_name(o[1]==null?"0":o[1].toString());
			c.setGuid(o[2]==null?"0":o[2].toString());
			c.setCard_detile(o[3]==null?"0":o[3].toString());
			c.setCard_picture(o[4]==null?"0":o[4].toString());
			c.setCard_colour(o[5]==null?"0":o[5].toString());
			c.setCard_code(o[6]==null?"0":o[6].toString());
			c.setCreatedate(o[7]==null?"0":o[7].toString());
			c.setCard_userid(o[8]==null?"0":o[8].toString());
			c.setCard_bar_code(o[9]==null?"0":o[9].toString());
//			c.setCard_seqno(o[10]==null?"0":o[10].toString());
			c.setAaaa(o[10]==null?0:Integer.parseInt(o[10].toString()));
			ul.add(c);
		}
		
		return ul;
	}

	@Override
	public Long count(Card card, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT count(*) from (SELECT t.*,us.cnt '已使用' from (select b.type_id, a.name '券类型', a.num '库存张/套数',0 as '已购买套券',sum(case b.state WHEN 9 then 1 end) '未激活',"
                +" sum(case b.state WHEN 8 then 1 end) '预备卡券',sum(case b.state WHEN 1 then 1 end) '已激活',sum(case b.state WHEN 10 then 1 end) '已退券未审核',"
                +" sum(case b.state WHEN 11 then 1 end) '已退券已审核',sum(case b.state WHEN 3 then 1 end) '发券失败' from wnf_product a LEFT JOIN wnf_card_send b"
                +" ON a.cardtypeid = b.type_id where b.state in (11,10,9,8,3,1) and a.giftcard = 0 and b.type_id >= 12  "
                + whereHql(card, params) 
               +"  group by b.type_id ) as t LEFT JOIN (select cardtype,count(*) cnt from wnf_user_card a, wnf_scoredetail b where a.state=1 and a.orderid = b.orderid " 
                + whereHql(card, params) 
                +" group by a.cardtype) as us  on t.type_id = us.cardtype  union all select a.cardtypeid,a.name , a.num '库存张/套数',sum(b.pro_num) '已购买套券',null '未激活',null '预备卡券',null '已激活',null '已退券未审核',"
                +" null '已退券已审核',null '发券失败',null '已使用' from wnf_product a, wnf_scoredetail b where a.cardtypeid = b.productid and a.giftcard = 1 " 
                + whereHql(card, params) 
                +" group by a.cardtypeid) aaa ";
		

		BigInteger count = carddao.countBySql(sql);

		return count.longValue();
	}

	private String whereHql(Card card, Map<String, Object> params) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		String sql = " and 1=1";
		if (StringUtil.isNotEmpty(card.getCreatedatetimeStart()+"")){
			String date = sdf.format(card.getCreatedatetimeStart());
			sql += " and unix_timestamp(b.createdate) >= unix_timestamp('"+date+"')";
		}
		if (StringUtil.isNotEmpty(card.getCreatedatetimeEnd()+"")) {
			String date2 = sdf.format(card.getCreatedatetimeEnd());
			sql += " and unix_timestamp(b.createdate) <= unix_timestamp('"+date2+"')";

		}
		return sql;
	}



}
