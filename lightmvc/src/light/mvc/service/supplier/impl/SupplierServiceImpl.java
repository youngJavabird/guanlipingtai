package light.mvc.service.supplier.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.supplier.Tsupplier;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.supplier.Supplier;
import light.mvc.service.supplier.SupplierServiceI;
import light.mvc.utils.StringUtil;

@Service
public class SupplierServiceImpl implements SupplierServiceI {

	Logger logger = Logger.getLogger(SupplierServiceImpl.class.getName());

	@Autowired
	private BaseDaoI<Tsupplier> supplierDao;

	@Override
	public List<Supplier> dataGrid(Supplier supplier, PageFilter ph) {
		List<Supplier> ul = new ArrayList<Supplier>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tsupplier t ";
		List<Tsupplier> l = supplierDao.find(hql + whereHql(supplier, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tsupplier t : l) {
			Supplier u = new Supplier();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(Supplier supplier, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tsupplier t ";
		return supplierDao.count("select count(*) " + hql + whereHql(supplier, params), params);
	}
	
	private String whereHql(Supplier supplier, Map<String, Object> params){
		String hql = "";
		if (supplier != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(supplier.getSuppliername())){
					hql += " and t.suppliername like :name";
					params.put("name", "%%" + supplier.getSuppliername() + "%%");
				}
				if (StringUtil.isNotEmpty(supplier.getState()) && !supplier.getState().equals("-1")) {
					hql += " and t.state = :state";
					params.put("state", supplier.getState());
				}
				if (StringUtil.isNotEmpty(supplier.getLinkman())) {
					hql += " and t.linkman like :linkman";
					params.put("linkman", "%%" + supplier.getLinkman() + "%%");
				}
			} catch (Exception e) {
				logger.info("supplierWhereHql:" + e.getMessage());
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
	public void add(Supplier r) {
		try {
			Tsupplier t = new Tsupplier();
			BeanUtils.copyProperties(r, t);
			supplierDao.save(t);
		} catch (Exception e) {
			logger.info("supplierAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Supplier r){
		try {
			Tsupplier t = new Tsupplier();
			BeanUtils.copyProperties(r, t);
			supplierDao.update(t);
		} catch (Exception e) {
			logger.info("supplierEdit:" + e.getMessage());
		}
	}
	
	@Override
	public Supplier get(Long id){
		Tsupplier t = supplierDao.get(Tsupplier.class, id);
		Supplier r = new Supplier();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	
}
