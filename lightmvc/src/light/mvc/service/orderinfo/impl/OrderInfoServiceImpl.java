package light.mvc.service.orderinfo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.card.Tcard;
import light.mvc.model.orderinfo.Torderinfo;
import light.mvc.model.token.Ttoken;
import light.mvc.model.usercard.Tusercard;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.orderinfo.Orderinfo;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.service.orderinfo.OrderInfoServiceI;
import light.mvc.utils.StringUtil;
import light.mvc.utils.getAccess_token;
import light.mvc.utils.getjsapi_ticket;
import light.mvc.pageModel.orderinfo.CardUtilBean;
import light.mvc.pageModel.token.WnfToken;
import light.mvc.pageModel.usercard.Usercard;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderInfoServiceImpl implements OrderInfoServiceI {
	Logger logger = Logger.getLogger(OrderInfoServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Torderinfo> orderInfoDao;
	
	@Autowired
	private BaseDaoI<Tusercard> userCardDao;
	
	@Autowired
	private BaseDaoI<Tcard> cardDao;
	
	@Autowired
	private BaseDaoI<Ttoken> tokenDao;
	
	@Override
	public List<OrderinfoView> dataGrid(Orderinfo orderinfo, PageFilter ph){
		List<OrderinfoView> ul = new ArrayList<OrderinfoView>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql ="select t.usertype, t.id,t.orderid,t.from_user_name,t.phone,t.`name`,t.address,'DQ' remark,"
				+ "t.size as fildename,wps.suppliername,b.supplierprice,b.new_price,t.isinvoice,t.invoicename,"
				+ "t.provinces,t.area,t.zipcode,t.product_id as pid,t.num,t.state,t.filed,"
				+ "t.createdate,t.totalamount,t.logistics,t.logisticsnum,t.`name` username,t.upddate,"
				+ "b.name as proname,t.price ,t.paytime,b.detile detail,"
				+ "c.name as typename,b.num as pronum,t.price * t.num as countprice,"
				+ "GROUP_CONCAT(f.card_name) card_name,f.card_price,GROUP_CONCAT(f.card_code) card_code,"
				+ "GROUP_CONCAT(f.card_password) card_password,f.card_endtime, "
				+ "CASE WHEN t.state='00' THEN '待支付' WHEN t.state='01' THEN '待发货' WHEN t.state='02' THEN '待收货' "
				+ "WHEN t.state='03' THEN '换货中' WHEN t.state='04' THEN '退货中' WHEN t.state='05' THEN '已退货' "
				+ "WHEN t.state='06' THEN '支付完成' ELSE '' END as orderstate, "
				+ "CASE WHEN wuc.state='0' THEN '已激活未使用' WHEN wuc.state='1' THEN '已使用' WHEN wuc.state='2' THEN '作废' "
				+ "ELSE '' END as cardstate,GROUP_CONCAT(f.card_service_condition_one ) as card_service_condition_one "
				+ "from wnf_orderinfo t left join wnf_product b on t.product_id=b.id "
				+ "left join wnf_product_supplier wps on b.supplierid = wps.guid "
				+ "left join wnf_product_type c on b.product_type_id = c.id "
				+ "left join wnf_user_card wuc on wuc.orderid = t.orderid and wuc.state <> '2' "
				+ "left join wnf_card f on f.id = wuc.card_id ";
			String gsql = " GROUP BY t.id,t.orderid,t.from_user_name,t.phone,t.`name`,t.address,"
				+"t.size,wps.suppliername,b.supplierprice,b.new_price,t.provinces,t.area,t.zipcode,t.product_id,t.num,t.state,"
				+"t.createdate,t.totalamount,t.logistics,t.logisticsnum,t.`name`,t.upddate,b.name,b.price ,b.new_price,t.paytime,b.detile,"
				+"c.name,b.num,b.new_price,t.num,f.card_price,f.card_endtime, t.state, wuc.state,t.isinvoice,t.invoicename";
			
		String nsql = sql+whereHql(orderinfo, params)+gsql+orderHql(ph);
		List<Object[]> l = orderInfoDao.findBySql(nsql, params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				OrderinfoView u = new OrderinfoView();
				u.setUsertype(objects[0]==null?"":objects[0].toString());
				u.setId(Long.valueOf(objects[1]==null?"":objects[1].toString()));
				u.setOrderid(objects[2]==null?"":objects[2].toString());
				u.setFrom_user_name(objects[3]==null?"":objects[3].toString());
				u.setPhone(objects[4]==null?"":objects[4].toString());
				u.setName(objects[5]==null?"":objects[5].toString());
				u.setAddress(objects[6]==null?"":objects[6].toString());
				u.setRemark(objects[7]==null?"":objects[7].toString());
				u.setFildename(objects[8]==null?"":objects[8].toString());
				u.setSuppliername(objects[9]==null?"":objects[9].toString());
				u.setSupplierprice(objects[10]==null?"":objects[10].toString());
				u.setNew_price(objects[11]==null?"":objects[11].toString());
				u.setIsinvoice(objects[12]==null?"":objects[12].toString());
				u.setInvoicename(objects[13]==null?"":objects[13].toString());
				u.setProvinces(objects[14]==null?"":objects[14].toString());
				u.setArea(objects[15]==null?"":objects[15].toString());
				u.setZipcode(objects[16]==null?"":objects[16].toString());
				u.setPid(objects[17]==null?"":objects[17].toString());
				u.setNum(objects[18]==null?"":objects[18].toString());
				u.setState(objects[19]==null?"":objects[19].toString());
				u.setFiled(objects[20]==null?"":objects[20].toString());
				u.setCreatedate(objects[21]==null?"":objects[21].toString());
				u.setTotalamount(objects[22]==null?"":objects[22].toString());
				u.setLogistics(objects[23]==null?"":objects[23].toString());
				u.setLogisticsnum(objects[24]==null?"":objects[24].toString());
				u.setUsername(objects[25]==null?"":objects[25].toString());
				u.setUpddate(objects[26]==null?"":objects[26].toString());
				u.setProname(objects[27]==null?"":objects[27].toString());
				u.setPrice(objects[28]==null?"":objects[28].toString());
				u.setPaytime(objects[29]==null?"":objects[29].toString());
				u.setDetail(objects[30]==null?"":objects[30].toString());
				u.setTypename(objects[31]==null?"":objects[31].toString());
				u.setPronum(objects[32]==null?"":objects[32].toString());
				u.setCountprice(objects[33]==null?"":objects[33].toString());
				u.setCard_name(objects[34]==null?"":objects[34].toString());
				u.setCard_price(objects[35]==null?"":objects[35].toString());
				u.setCard_code(objects[36]==null?"":objects[36].toString());
				u.setCard_password(objects[37]==null?"":objects[37].toString());
				u.setCard_endtime(objects[38]==null?"":objects[38].toString());
				u.setOrderstate(objects[39]==null?"":objects[39].toString());
				u.setCard_service_condition_one(objects[40]==null?"":objects[40].toString());
				ul.add(u);
			}
		}
		return ul;
	}
	
	@Override
	public Long count(Orderinfo orderinfo, PageFilter ph){
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Torderinfo t";
		return orderInfoDao.count("select count(*) " + hql+whereHql(orderinfo, params), params);
	}
	
	private String whereHql(Orderinfo orderinfo, Map<String, Object> params){
		//无论有没有条件都执行这个条件
		String hql = " where  (TIMESTAMPDIFF(HOUR,t.createdate,NOW())<=24 and t.state='00' or t.state <> '00') ";
		if (orderinfo!=null) {
			if (StringUtil.isNotEmpty(orderinfo.getOrderid())) {
				hql += " and t.orderid like :orderid";
				params.put("orderid", "%%" + orderinfo.getOrderid() + "%%");
			}
			if (StringUtil.isNotEmpty(orderinfo.getState()) && !orderinfo.getState().equals("-1")) {
				hql += " and t.state = :state";
				params.put("state", orderinfo.getState());
			}
			if (StringUtil.isNotEmpty(orderinfo.getUsertype()) && !orderinfo.getUsertype().equals("-1")) {
				hql += " and t.usertype = :usertype";
				params.put("usertype", orderinfo.getUsertype());
			}
			if (orderinfo.getUpddate()!=null) {
				hql += " and t.upddate > :upddate";
				params.put("upddate", orderinfo.getUpddate());
			}
			if (orderinfo.getCreatedate()!=null) {
				hql += " and t.upddate < :createdate";
				params.put("createdate", orderinfo.getCreatedate());
			}
		}
		return hql;
	}
	
	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	public List<CardUtilBean> getCardType(String orderid) {
		try {
			String sql = "select wc.card_typeid as cardtypeid,wo.cardnum,wo.product_id,"
					+ " wo.from_user_name,wo.orderid,wp.name productName,wps.recvaddress,"
					+ " wps.linkman,wps.phone,wo.createdate,wo.upddate,wo.usertype"
					+ " from wnf_orderinfo wo "
					+ " left join wnf_user_card wuc on wo.orderid = wuc.orderid"
					+ " left join wnf_card wc on wc.guid = wuc.card_id"
					+ " left join wnf_product wp on wp.id = wo.product_id"
					+ " left join wnf_product_supplier wps on wp.supplierid = wps.guid"
					+ " where wo.orderid = " + orderid;
				List<Object[]> l = orderInfoDao.findBySql(sql);
				List<CardUtilBean> ul = new ArrayList<CardUtilBean>();
				SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < l.size(); i++) {
					Object[] objects=l.get(i);
					if(objects.length>0){
						CardUtilBean u = new CardUtilBean();
						u.setCardtypeid(objects[0]==null?"":objects[0].toString());
						u.setCardnum(objects[1]==null?"":objects[1].toString());
						u.setProduct_id(objects[2]==null?"":objects[2].toString());
						u.setFrom_user_name(objects[3]==null?"":objects[3].toString());
						u.setProductName(objects[5]==null?"":objects[5].toString());
						u.setRecvaddress(objects[6]==null?"":objects[6].toString());
						u.setLinkman(objects[7]==null?"":objects[7].toString());
						u.setPhone(objects[8]==null?"":objects[8].toString());
						u.setCreatedate(formatter.parse(objects[9]==null?"":objects[9].toString()));
						u.setUpddate(formatter.parse(objects[10]==null?"":objects[10].toString()));
						u.setUsertype(Integer.parseInt(objects[11]==null?"":objects[11].toString()));
						ul.add(u);
					}
				}
				return ul;
		} catch (Exception e) {
			logger.info("OrderinfoGetCardType:"+e);
			return null;
		}
	}

	@Override
	public List<Card> queryCard(String cardNum,String cardTypeId){
		try {
			String sql = " from Tcard a ";
			Map<String, Object> params = new HashMap<String, Object>();
			List<Tcard> l = cardDao.find(sql + queryCardWhereHql(cardNum, cardTypeId, params),params,0,Integer.parseInt(cardNum));
			List<Card> ul = new ArrayList<Card>();
			for (Tcard t : l) {
				Card u = new Card();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("OrderinfoQueryCard:"+e);
			return null;
		}
	}
	
	private String queryCardWhereHql(String cardNum, String cardTypeId, Map<String, Object> params){
		String hql = " where state = 0 ";
		if (StringUtil.isNotEmpty(cardTypeId)) {
			hql += " and a.card_typeid = :cardtypeid";
			params.put("cardtypeid", cardTypeId);
		}
		hql += " order by a.id";
		return hql;
	}

	@Override
	public Orderinfo get(Long id){
		try {
			Torderinfo t = orderInfoDao.get(Torderinfo.class, id);
			Orderinfo r = new Orderinfo();
			BeanUtils.copyProperties(t, r);
			return r;
		} catch (Exception e) {
			logger.info("OrderInfoGet:" + e);
			return null;
		}
	}
	
	@Override
	public Card getCard(Long id) {
		try {
			Tcard t = cardDao.get(Tcard.class,id);
			Card r = new Card();
			BeanUtils.copyProperties(t, r);
			return r;
		} catch (Exception e) {
			logger.info("OrderInfoGetCard:" + e);
			return null;
		}
	}
	
	@Override
	public void edit(Orderinfo r){
		try {
			Torderinfo t = new Torderinfo();
			BeanUtils.copyProperties(r, t);
			orderInfoDao.update(t);
		} catch (Exception e) {
			logger.info("OrderInfoEdit:" + e);
		}
	}

	@Override
	public void InsertUserCard(Usercard r){
		Tusercard t = new Tusercard();
		BeanUtils.copyProperties(r, t);
		userCardDao.save(t);
	}

	@Override
	public void EditCardState(Card r){
		try {
			Tcard t = new Tcard();
			BeanUtils.copyProperties(r, t);
			cardDao.update(t);
		} catch (Exception e) {
			logger.info("OrderInfoEditCardState:" + e);
		}
	}
	
	@Override
	public void EditOrderInfo(Orderinfo r){
		try {
			Torderinfo t = new Torderinfo();
			BeanUtils.copyProperties(r, t);
			orderInfoDao.update(t);
		} catch (Exception e) {
			logger.info("OrderInfoEditOrderInfo:" + e);
		}
	}
	
	@Override
	public String getToken(String code){
		long time=new Date().getTime()/1000;
		String hql = " from Ttoken t where id = 1 ";
	    List<Ttoken> list = tokenDao.find(hql);
	    int etime=list.get(0).getTime();
		String token=list.get(0).getToken();
		String jsapi_ticket=list.get(0).getJsapi_ticket();
		if((time-etime)>6000){
			token=getAccess_token.getToken();
			jsapi_ticket=getjsapi_ticket.getjsapi_ticket(token);
			WnfToken token2 = new WnfToken();
			token2.setToken(token);
			token2.setTime(Integer.parseInt(String.valueOf(time)));
			token2.setJsapi_ticket(jsapi_ticket);
			token2.setId(Long.parseLong("1"));
			Ttoken t = new Ttoken();

			BeanUtils.copyProperties(token2, t);
			try {
				tokenDao.update(t);
			} catch (Exception e) {
				return token;
			}
			
		}
		String msg="";
		if(code.equals("token")){
			msg=token;
		}else if(code.equals("jsapi_ticket")){
			msg=jsapi_ticket;
		}
		return msg;
	}

	@Override
	public void EditRemark(String orderid, String refundamount, String refee,
			String remark) {
		String sql = "update wnf_orderinfo set refundopr=(SELECT opera from wnf_card_operate where guid="+orderid+" and state=3),refundtime=(SELECT date from wnf_card_operate where guid="+orderid+" and state=3 LIMIT 0,1),refundamount="+refundamount+",refee="+refee+",remark="+"'"+remark+"'"+" where orderid="+orderid;
		orderInfoDao.executeSql(sql);
		
	}
	
}
