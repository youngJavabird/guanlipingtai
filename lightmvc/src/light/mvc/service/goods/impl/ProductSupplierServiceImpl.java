package light.mvc.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import light.mvc.dao.BaseDaoI;


import light.mvc.model.goods.Tproduct;
import light.mvc.model.goods.Tproductsupplier;
import light.mvc.model.goods.Tproducttype;
import light.mvc.model.sys.Torganization;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.service.goods.ProductSupplierServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSupplierServiceImpl implements ProductSupplierServiceI{
	@Autowired
	private BaseDaoI<Tproduct> productDao;
	@Autowired
	private BaseDaoI<Tproductsupplier> productsupplierDao;
	@Override
	public List<ProductSupplier> treeGrid() {
		List<ProductSupplier> lr = new ArrayList<ProductSupplier>();
		List<Tproductsupplier> l = productsupplierDao
				.find("from Tproductsupplier t left join fetch t.productsupplier  order by t.id");
		if ((l != null) && (l.size() > 0)) {
			for (Tproductsupplier t : l) {
				ProductSupplier r = new ProductSupplier();
				BeanUtils.copyProperties(t, r);
//				if (t.getProductsupplier() != null) {
//					r.setId(t.getProductsupplier().getId());
//					r.setSuppliername((t.getProductsupplier().getSuppliername()));
//				}
				lr.add(r);
			}
		}
		return lr;
	}
	
	@Override
	public List<Tree> tree() {
		List<Tproductsupplier> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		l = productsupplierDao.find("select distinct t from Tproductsupplier t order by t.id");

		if ((l != null) && (l.size() > 0)) {
			for (Tproductsupplier r : l) {
				Tree tree = new Tree();
				tree.setId(r.getId().toString());
				tree.setText(r.getSuppliername());
				
				lt.add(tree);
			}
		}
		return lt;
	}
}
