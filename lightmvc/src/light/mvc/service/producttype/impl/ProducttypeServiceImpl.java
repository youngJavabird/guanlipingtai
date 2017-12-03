package light.mvc.service.producttype.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.producttype.Tproducttypes;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.service.producttype.ProducttypeServiceI;
import light.mvc.utils.StringUtil;

@Service
public class ProducttypeServiceImpl implements ProducttypeServiceI {
	Logger logger = Logger.getLogger(ProducttypeServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tproducttypes> producttypeDao;
	
	@Override
	public List<Producttype> dataGrid(Producttype producttype, PageFilter ph){
		List<Producttype> ul = new ArrayList<Producttype>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproducttypes t ";
		List<Tproducttypes> l = producttypeDao.find(hql + whereHql(producttype,params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tproducttypes t : l) {
			Producttype u = new Producttype();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	@Override
	public Long count(Producttype producttext, PageFilter ph){
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproducttypes t ";
		return producttypeDao.count("select count(*) " + hql + whereHql(producttext, params), params);
	}
	
	private String whereHql(Producttype producttype, Map<String, Object> params){
		String hql = "";
		if (producttype != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(producttype.getTypename())){
					hql += " and t.typename like :typename";
					params.put("typename", "%%" + producttype.getTypename() + "%%");
				}
			} catch (Exception e) {
				logger.info("productTypeWhereHql:"+e.getMessage());
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
	public void add(Producttype r){
		try {
			Tproducttypes t = new Tproducttypes();
			BeanUtils.copyProperties(r, t);
			producttypeDao.save(t);
		} catch (Exception e) {
			logger.info("producttypeAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Producttype r){
		try {
			Tproducttypes t = new Tproducttypes();
			BeanUtils.copyProperties(r, t);
			producttypeDao.update(t);
		} catch (Exception e) {
			logger.info("producttypeEdit:" + e.getMessage());
		}
	}
	
	@Override
	public Producttype get(Long id){
		Tproducttypes t = producttypeDao.get(Tproducttypes.class, id);
		Producttype r = new Producttype();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
}
