package light.mvc.service.activityproduct.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.activity.Tactivity;
import light.mvc.model.activityProduct.TactivityProduct;
import light.mvc.model.activityType.TactivityType;
import light.mvc.model.cardchannel.Tcardchannel;
import light.mvc.model.demo.Tdemo;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityProduct.ActivityProduct;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.demo.Demo;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.service.activityproduct.ActivityproductServiceI;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityproductServiceImpl implements ActivityproductServiceI {
	
	Logger logger = Logger.getLogger(ActivityproductServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<TactivityProduct> activityproductDao;
	
	@Autowired
	private BaseDaoI<Tcardchannel> cardchannelDao;
	
	@Override
	public List<ActivityProduct> dataGrid(ActivityProduct activityProduct, PageFilter ph) {
		List<ActivityProduct> ul = new ArrayList<ActivityProduct>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TactivityProduct t ";
		List<TactivityProduct> l = activityproductDao.find(hql + whereHql(activityProduct, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (TactivityProduct t : l) {
			ActivityProduct u = new ActivityProduct();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(ActivityProduct activityProduct, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TactivityProduct t ";
		return activityproductDao.count("select count(*) " + hql + whereHql(activityProduct, params), params);
	}
	
	private String whereHql(ActivityProduct activityProduct, Map<String, Object> params){
		String hql = "";
		if (activityProduct != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(activityProduct.getName())){
					hql += " and t.name like :name";
					params.put("name", "%%" + activityProduct.getName() + "%%");
				}
			} catch (Exception e) {
				logger.info("activityWhereHql:" + e.getMessage());
			}
		}
		return hql;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t.seq_id" ;
		}
		return orderString;
	}

	@Override
	public void add(ActivityProduct r) {
		try {
			TactivityProduct t = new TactivityProduct();
			BeanUtils.copyProperties(r, t);
			activityproductDao.save(t);
		} catch (Exception e) {
			logger.info("activityproductAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(ActivityProduct r){
		try {
			TactivityProduct t = new TactivityProduct();
			BeanUtils.copyProperties(r, t);
			activityproductDao.update(t);
		} catch (Exception e) {
			logger.info("activityproductEdit:" + e.getMessage());
		}
	}
	
	@Override
	public ActivityProduct get(Long id){
		TactivityProduct t = activityproductDao.get(TactivityProduct.class, id);
		ActivityProduct r = new ActivityProduct();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public List<Cardchannel> getCombox(){
		try{
			List<Cardchannel> ul = new ArrayList<Cardchannel>();
			String hql = " from Tcardchannel t where t.state = '0'";
			List<Tcardchannel> l = cardchannelDao.find(hql);
			for (Tcardchannel t : l) {
				Cardchannel u = new Cardchannel();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("activityGetCombox:" + e.getMessage());
			return null;
		}
	}
}


