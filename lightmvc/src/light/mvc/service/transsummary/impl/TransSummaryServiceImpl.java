package light.mvc.service.transsummary.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.transtype.Ttranstype;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.transdetail.Transdetail;
import light.mvc.pageModel.transsummary.Transsummary;
import light.mvc.pageModel.transtype.Transtype;
import light.mvc.service.transdetail.TransdetailServiceI;
import light.mvc.service.transsummary.TransSummaryServiceI;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransSummaryServiceImpl implements TransSummaryServiceI {
	Logger logger = Logger.getLogger(TransSummaryServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Transsummary> transsummaryDao;
	
	@Autowired
	private BaseDaoI<Ttranstype> transtypeDao;
	
	@Override
	public List<Transsummary> dataGrid(Transsummary transsummary, PageFilter ph) {
		List<Transsummary> ul = new ArrayList<Transsummary>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = " select t.channel_id,wtt.transtypename as transtype,t.code,t.barcode,t.merch_no,t.termno,"
				+ "t.merch_channel,t.termno_channel,t.acqbin,t.pmerchorder_no,t.merchorder_no,t.org_code,"
				+ "t.trans_no,t.trsno_center,t.trsno_center_channel,t.trsdate_send,t.trsdate_center,"
				+ "t.card_no,t.score,t.status,t.responscode,t.respinfo,t.settledate,t.memo,t.amount/100 as amount "
				+ "from wnf_transdetail t "
				+ "left join wnf_transtype wtt on t.transtype = wtt.transtype ";
		List<Object[]> l = transsummaryDao.findBySql(sql + whereHql(transsummary, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				Transsummary u = new Transsummary();
				u.setChannel_id(objects[0]==null?"":objects[0].toString());
				u.setTranstype(objects[1]==null?"":objects[1].toString());
				u.setCode(objects[2]==null?"":objects[2].toString());
				u.setBarcode(objects[3]==null?"":objects[3].toString());
				u.setMerch_no(objects[4]==null?"":objects[4].toString());
				u.setTermno(objects[5]==null?"":objects[5].toString());
				u.setMerch_channel(objects[6]==null?"":objects[6].toString());
				u.setTermno_channel(objects[7]==null?"":objects[7].toString());
				u.setAcqbin(objects[8]==null?"":objects[8].toString());
				u.setPmerchorder_no(objects[9]==null?"":objects[9].toString());
				u.setMerchorder_no(objects[10]==null?"":objects[10].toString());
				u.setOrg_code(objects[11]==null?"":objects[11].toString());
				u.setTrans_no(objects[12]==null?"":objects[12].toString());
				u.setTrsno_center(objects[13]==null?"":objects[13].toString());
				u.setTrsno_center_channel(objects[14]==null?"":objects[14].toString());
				u.setTrsdate_send((Date)objects[15]);
				u.setTrsdate_center((Date)objects[16]);
				u.setCard_no(objects[17]==null?"":objects[17].toString());
				u.setScore(objects[18]==null?"":objects[18].toString());
				u.setStatus(objects[19]==null?"":objects[19].toString());
				u.setResponscode(objects[20]==null?"":objects[20].toString());
				u.setRespinfo(objects[21]==null?"":objects[21].toString());
				u.setSettledate(objects[22]==null?"":objects[22].toString());
				u.setMemo(objects[23]==null?"":objects[23].toString());
				u.setAmount(objects[24]==null?"":objects[24].toString());
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public Long count(Transsummary transsummary, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "from Ttransdetail t ";
		return transsummaryDao.count("select count(*) " + sql + whereHql(transsummary, params), params);
	}
	
	private String whereHql(Transsummary transsummary, Map<String, Object> params){
		String hql = "";
		if (transsummary != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(transsummary.getTranstype())&&!transsummary.getTranstype().equals("全部")){
					hql += " and t.transtype = :transtype";
					params.put("transtype", "%%" + transsummary.getTranstype() + "%%");
				}
				if (transsummary.getTrsdate_send() != null) {
					hql += " and t.trsdate_send >= :stime";
					params.put("stime", transsummary.getTrsdate_send());
				}
				if (transsummary.getTrsdate_center() != null) {
					hql += " and t.trsdate_send <= :etime";
					params.put("etime", transsummary.getTrsdate_center());
				}
			} catch (Exception e) {
				logger.info("transsummaryWhereHql:" + e.getMessage());
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
	
	public List<Transtype> getTranstypeCbo(){
		try{
			List<Transtype> ul = new ArrayList<Transtype>();
			String hql = " from Ttranstype t order by t.id";
			List<Ttranstype> l = transtypeDao.find(hql);
			for (Ttranstype t : l) {
				Transtype u = new Transtype();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("transtypeGetCombox:" + e.getMessage());
			return null;
		}
	}
}
