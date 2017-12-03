package light.mvc.service.productfield.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.goods.Tproductsupplier;
import light.mvc.model.productfield.Tproductfield;
import light.mvc.model.producttype.Tproducttypes;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.productfield.ProductfieldView;
import light.mvc.pageModel.producttype.Producttype;
import light.mvc.service.productfield.ProductfieldServiceI;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductfieldServiceImpl implements ProductfieldServiceI {
	Logger logger = Logger.getLogger(ProductfieldServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tproductfield> productfieldDao;
	
	@Autowired
	private BaseDaoI<Tproducttypes> producttypeDao;
	
	
	@Override
	public List<ProductfieldView> dataGrid(Productfield productfield, PageFilter ph){
		List<ProductfieldView> ul = new ArrayList<ProductfieldView>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id,t.name,wct.typename as type from wnf_product_field_name t "
				+ "left join wnf_product_field_contrast wct on t.type = wct.id";
		List<Object[]> l = productfieldDao.findBySql(sql + whereHql(productfield, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				ProductfieldView u = new ProductfieldView();
				u.setId(Long.valueOf(objects[0]+""));
				u.setName(objects[1]+"");
				u.setType(objects[2]+"");
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public Long count(Productfield productfield, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproductfield t ";
		return productfieldDao.count("select count(*) " + hql + whereHql(productfield, params), params);
	}
	
	private String whereHql(Productfield productfield, Map<String, Object> params){
		String hql = "";
		if (productfield != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(productfield.getName())){
					hql += " and t.name like :name";
					params.put("name", "%%" + productfield.getName() + "%%");
				}
				if(productfield.getType()!=-1 && productfield.getType()!=0){
					hql += " and t.type = :type";
					params.put("type", productfield.getType());
				}
			} catch (Exception e) {
				logger.info("productfieldWhereHql:" + e.getMessage());
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
	public void add(Productfield r) {
		try {
			Tproductfield t = new Tproductfield();
			BeanUtils.copyProperties(r, t);
			productfieldDao.save(t);
		} catch (Exception e) {
			logger.info("productfieldAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Productfield r){
		try {
			Tproductfield t = new Tproductfield();
			BeanUtils.copyProperties(r, t);
			productfieldDao.update(t);
		} catch (Exception e) {
			logger.info("productfieldEdit:" + e.getMessage());
		}
	}

	@Override
	public Productfield get(Long id){
		Tproductfield t = productfieldDao.get(Tproductfield.class, id);
		Productfield r = new Productfield();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public List<Producttype> typeCombox(){
		try {
			List<Producttype> ul = new ArrayList<Producttype>();
			String hql = " from Tproducttypes t order by t.id";
			List<Tproducttypes> l = producttypeDao.find(hql);
			for (Tproducttypes t : l) {
				Producttype u = new Producttype();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("typeGetCombox:" + e.getMessage());
			return null;
		}
	}


}
