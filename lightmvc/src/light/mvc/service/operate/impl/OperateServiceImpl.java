package light.mvc.service.operate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.activity.Tactivity;
import light.mvc.model.demo.Tdemo;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.operate.Toperate;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.demo.Demo;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.operate.Operate;
import light.mvc.service.activity.ActivityServiceI;
import light.mvc.service.activity.impl.ActivityServiceImpl;
import light.mvc.service.demo.DemoServiceI;
import light.mvc.service.operate.OperateServiceI;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateServiceImpl implements OperateServiceI {
	
	Logger logger = Logger.getLogger(OperateServiceImpl.class.getName());

	@Autowired
	private BaseDaoI<Toperate> operateDao;

	@Override
	public void add(Operate r) {
		try {
			Toperate t = new Toperate();
			BeanUtils.copyProperties(r, t);
			operateDao.save(t);
		} catch (Exception e) {
			logger.info("operateAdd:" + e.getMessage());
		}
	}

	@Override
	public List<Operate> dataGrid(Operate operate, PageFilter ph) {
		List<Operate> ul = new ArrayList<Operate>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Toperate t ";
		List<Toperate> l = operateDao.find(hql  + whereHql(operate, params), params, ph.getPage(), ph.getRows());
		for (Toperate t : l) {
			Operate u = new Operate();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}

	private String whereHql(Operate operate, Map<String, Object> params) {
		String hql = "";
		if (operate != null) {
			hql += " where 1=1 ";
			if (operate.getPhone() != null) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + operate.getPhone() + "%%");
			}
		}
		return hql;
	}
	
	@Override
	public Long count(Operate operate, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Toperate t ";
		return operateDao.count("select count(*) " + hql + whereHql(operate, params) , params);
	}

	

}
