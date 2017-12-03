package light.mvc.service.smstemp.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.activity.Tactivity;
import light.mvc.model.smstemp.Tsmstemp;
import light.mvc.model.transtype.Ttranstype;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.smstemp.Smstemp;
import light.mvc.pageModel.smstemp.SmstempView;
import light.mvc.pageModel.transtype.Transtype;
import light.mvc.service.smstemp.SmstempServiceI;
import light.mvc.utils.StringUtil;

@Service
public class SmstempServiceImpl implements SmstempServiceI {
	Logger logger = Logger.getLogger(SmstempServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tsmstemp> smstempDao;

	@Autowired
	private BaseDaoI<Tactivity> activityDao;
	
	@Autowired
	private BaseDaoI<Ttranstype> transtypeDao;
	
	@Override
	public List<SmstempView> dataGrid(Smstemp smstemp, PageFilter ph) {
		List<SmstempView> ul = new ArrayList<SmstempView>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.createtime,t.id,t.msgid,t.Remark,t.smstemplet,t.upddate,wa.`name` as activity_id, "
				+ "wt.transtypename as transtype "
				+ "from wnf_sms_templet t "
				+ "inner join wnf_activity wa on t.activity_id = wa.id "
				+ "inner join wnf_transtype wt on t.transtype = wt.transtype";
		List<Object[]> l = smstempDao.findBySql(sql + whereHql(smstemp, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				SmstempView u = new SmstempView();
				u.setCreatetime((Date)objects[0]);
				u.setId(Long.valueOf(objects[1]+""));
				u.setMsgid((Integer)objects[2]);
				u.setRemark(objects[3]+"");
				u.setSmstemplet(objects[4]+"");
				u.setUpddate((Date)objects[5]);
				u.setActivity_id(objects[6]+"");
				u.setTranstype(objects[7]+"");
				ul.add(u);
			}
		}
		
		return ul;
	}

	@Override
	public Long count(Smstemp smstemp, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tsmstemp t ";
		return smstempDao.count("select count(*) " + hql + whereHql(smstemp, params), params);
	}
	
	private String whereHql(Smstemp smstemp, Map<String, Object> params){
		String hql = "";
		if (smstemp != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(smstemp.getTranstype()) && !smstemp.getTranstype().equals("全部")){
					hql += " and t.transtype = :transtype";
					params.put("transtype", smstemp.getTranstype());
				}
				if (smstemp.getActivity_id() != -1 && smstemp.getActivity_id() != 0) {
					hql += " and t.activity_id like :activity_id";
					params.put("activity_id", smstemp.getActivity_id());
				}
			} catch (Exception e) {
				logger.info("smstempletWhereHql:" + e.getMessage());
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
	public void add(Smstemp r) {
		try {
			Tsmstemp t = new Tsmstemp();
			BeanUtils.copyProperties(r, t);
			smstempDao.save(t);
		} catch (Exception e) {
			logger.info("smstempAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Smstemp r){
		try {
			Tsmstemp t = new Tsmstemp();
			BeanUtils.copyProperties(r, t);
			smstempDao.update(t);
		} catch (Exception e) {
			logger.info("smstempEdit:" + e.getMessage());
		}
	}
	
	@Override
	public void delete(Long id){
		try {
			Tsmstemp t = smstempDao.get(Tsmstemp.class, id);
			smstempDao.delete(t);
		} catch (Exception e) {
			logger.info("smstempDrop:" + e.getMessage());
		}
	}
	
	@Override
	public Smstemp get(Long id){
		Tsmstemp t = smstempDao.get(Tsmstemp.class, id);
		Smstemp r = new Smstemp();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public List<Activity> getActivityCombox(){
		try{
			List<Activity> ul = new ArrayList<Activity>();
			String hql = " from Tactivity t where t.state = '3' order by t.name";
			List<Tactivity> l = activityDao.find(hql);
			for (Tactivity t : l) {
				Activity u = new Activity();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("activityGetCombox:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Transtype> getTransCombox(){
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
