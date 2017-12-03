package light.mvc.service.goods.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import light.mvc.dao.BaseDaoI;


import light.mvc.model.carousel.Tcarousel;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.goods.Tproducttype;
import light.mvc.model.sys.Torganization;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.service.goods.ProductTypeServiceI;


import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeServiceI{
	
	Logger logger = Logger.getLogger(ProductTypeServiceI.class.getName());
	@Autowired
	private BaseDaoI<Tproduct> productDao;
	@Autowired
	private BaseDaoI<Tproducttype> producttypeDao;
	@Override
	public List<ProductType> treeGrid() {
		List<ProductType> lr = new ArrayList<ProductType>();
		List<Tproducttype> l = producttypeDao
				.find("from Tproducttype t left join fetch t.producttype  order by t.id");
		if ((l != null) && (l.size() > 0)) {
			for (Tproducttype t : l) {
				ProductType r = new ProductType();
				BeanUtils.copyProperties(t, r);
//				if (t.getProducttype() != null) {
//					r.setId(t.getProducttype().getId());
//					r.setName(t.getProducttype().getName());
//				}
				lr.add(r);
			}
		}
		return lr;
	}
	
	@Override
	public List<Tree> tree() {
		List<Tproducttype> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = producttypeDao.find("select distinct t from Tproducttype t order by t.id");

		if ((l != null) && (l.size() > 0)) {
			for (Tproducttype r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				tree.setText(r.getName());
				
				lt.add(tree);
			}
		}
		return lt;
	}

	@Override
	public List<ProductType> dataGrid(ProductType productType, PageFilter ph) {
		List<ProductType> ul = new ArrayList<ProductType>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproducttype t ";
		List<Tproducttype> l = producttypeDao.find(hql + whereHql(productType, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tproducttype t : l) {
			ProductType u = new ProductType();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	private String whereHql(ProductType productType, Map<String, Object> params) {
		String hql = "";
		if (productType != null) {
			hql += " where 1=1 ";
			if (productType.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + productType.getName() + "%%");
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
	public Long count(ProductType productType, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproducttype t ";
		return productDao.count("select count(*) " + hql + whereHql(productType, params), params);
	}

	@Override
	public void edit(ProductType r) {
		Tproducttype t = new Tproducttype();
     	BeanUtils.copyProperties(r, t);
     	producttypeDao.update(t);
	}

	@Override
	public ProductType get(Long id) {
		Tproducttype t = producttypeDao.get(Tproducttype.class, id);
		ProductType r = new ProductType();
		BeanUtils.copyProperties(t, r);
		return r;
	}

	@Override
	public void add(ProductType r) {
		try {
			Tproducttype t = new Tproducttype();
			BeanUtils.copyProperties(r, t);
			producttypeDao.save(t);
		} catch (Exception e) {
			logger.info("ProductTypeAdd:" + e.getMessage());
		}
	}
}
