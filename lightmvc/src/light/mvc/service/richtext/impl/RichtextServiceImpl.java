package light.mvc.service.richtext.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.ricktext.Trichtext;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.richtext.Richtext;
import light.mvc.service.richtext.RichtextServiceI;

@Service
public class RichtextServiceImpl implements RichtextServiceI {
	Logger logger = Logger.getLogger(RichtextServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Trichtext> richtextDao;
	
	@Override
	public List<Richtext> dataGrid(Richtext richtext, PageFilter ph){
		List<Richtext> ul = new ArrayList<Richtext>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Trichtext t ";
		List<Trichtext> l = richtextDao.find(hql + whereHql(richtext, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Trichtext t : l) {
			Richtext u = new Richtext();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	@Override
	public Long count(Richtext richtext, PageFilter ph){
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Trichtext t ";
		return richtextDao.count("select count(*) " + hql + whereHql(richtext, params), params);
	}
	
	private String whereHql(Richtext richtext, Map<String, Object> params){
		String hql = "";
		if (richtext != null) {
			try {
				hql += " where 1=1 ";
			} catch (Exception e) {
				logger.info("richtextWhereHql:" + e.getMessage());
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
	public List<Richtext> combobox_data(){
		try {
			List<Richtext> ul = new ArrayList<Richtext>();
			String hql = " from Trichtext t order by t.name";
			List<Trichtext> l = richtextDao.find(hql);
			for (Trichtext t : l) {
				Richtext u = new Richtext();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("richtextCombox:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public void add(Richtext r){
		try {
			Trichtext t = new Trichtext();
			BeanUtils.copyProperties(r, t);
			richtextDao.save(t);
		} catch (Exception e) {
			logger.info("richtextAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Richtext r){
		try {
			Trichtext t = new Trichtext();
			BeanUtils.copyProperties(r, t);
			richtextDao.update(t);
		} catch (Exception e) {
			logger.info("richtextEdit:" + e.getMessage());
		}
	}

	@Override
	public Richtext get(Long id){
		Trichtext t = richtextDao.get(Trichtext.class, id);
		Richtext r = new Richtext();
		BeanUtils.copyProperties(t, r);
		return r;
	}
}
