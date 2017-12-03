package light.mvc.service.activity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.activity.Tactivity;
import light.mvc.model.activityType.TactivityType;
import light.mvc.model.demo.Tdemo;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.demo.Demo;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityServiceI {
	
	Logger logger = Logger.getLogger(ActivityServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tactivity> activityDao;
	
	@Autowired
	private BaseDaoI<TactivityType> activityTypeDao;
	
	@Override
	public List<Activity> dataGrid(Activity activity, PageFilter ph) {
		List<Activity> ul = new ArrayList<Activity>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tactivity t ";
		List<Tactivity> l = activityDao.find(hql + whereHql(activity, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tactivity t : l) {
			Activity u = new Activity();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(Activity activity, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tactivity t ";
		return activityDao.count("select count(*) " + hql + whereHql(activity, params), params);
	}
	
	private String whereHql(Activity activity, Map<String, Object> params){
		String hql = "";
		if (activity != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(activity.getName())){
					hql += " and t.name like :name";
					params.put("name", "%%" + activity.getName() + "%%");
				}
				if (StringUtil.isNotEmpty(activity.getState())) {
					hql += " and t.state = :state";
					params.put("state", activity.getState());
				}
				if (activity.getStime() != null) {
					hql += " and t.etime >= :stime";
					params.put("stime", activity.getStime());
					System.out.println(activity.getStime());
				}
				if (activity.getEtime() != null) {
					hql += " and t.etime <= :etime";
					params.put("etime", activity.getEtime());
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
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	public void add(Activity r) {
		try {
			Tactivity t = new Tactivity();
			BeanUtils.copyProperties(r, t);
			activityDao.save(t);
		} catch (Exception e) {
			logger.info("activityAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Activity r){
		try {
			Tactivity t = new Tactivity();
			BeanUtils.copyProperties(r, t);
			activityDao.update(t);
		} catch (Exception e) {
			logger.info("activityEdit:" + e.getMessage());
		}
	}
	
	@Override
	public Activity get(Long id){
		Tactivity t = activityDao.get(Tactivity.class, id);
		Activity r = new Activity();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public List<ActivityType> getCombox(){
		try{
			List<ActivityType> ul = new ArrayList<ActivityType>();
			String hql = " from TactivityType t where t.state = '0' order by t.name";
			List<TactivityType> l = activityTypeDao.find(hql);
			for (TactivityType t : l) {
				ActivityType u = new ActivityType();
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
	public List<Activity> getActive() {
		List<Activity> ul = new ArrayList<Activity>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select id,name,activitycode,cardtypeid  from wnf_activity";
		List<Object[]> l = activityDao.findBySql(sql);
		for (Object[] o : l) {
			Activity c = new Activity();
			c.setId(Long.valueOf(o[0].toString()));
			c.setName(o[1].toString());
			c.setActivitycode(o[2].toString());
			c.setCardtypeid(Integer.parseInt(o[3].toString()));
			ul.add(c);
		}
		return ul;
	}
}


