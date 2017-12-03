package light.mvc.service.goods.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.field.Tfield;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.goods.Tproductsupplier;
import light.mvc.model.goods.Tproducttype;
import light.mvc.model.productfield.Tproductfield;
import light.mvc.model.productpicture.Tproductpicture;
import light.mvc.model.purchase.Tpurchase;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.field.Field;
import light.mvc.pageModel.field.Fieldname;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.productpicture.Productpicture;
import light.mvc.pageModel.purchase.Purchase;
import light.mvc.service.goods.ProductServiceI;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductServiceI {
	
	Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tproduct> productDao;
	@Autowired
	private BaseDaoI<Tproducttype> producttypeDao;	
	@Autowired
	private BaseDaoI<Tproductsupplier> productsupplierDao;	
	@Autowired
	private BaseDaoI<Tproductfield> productfieldDao;	
	@Autowired
	private BaseDaoI<Tfield> fieldDao;
	@Autowired
	private BaseDaoI<Tproductpicture> productpictureDao;
	@Autowired
	private BaseDaoI<Tpurchase> purchaseDao;

	private String uuid() {
		String uuid=UUID.randomUUID().toString();
		return uuid;
	}
	/**
	 * 添加商品的同时添加数据到轮播图
	 */
	@Override
	public void add(Product r) {
    try {
		Tproduct t = new Tproduct();
     	BeanUtils.copyProperties(r, t);
     	productDao.save(t);
         String guid=t.getGuid();
         String pictureone=t.getPictureone();
         String picturetwo=t.getPicturetwo();
         String picturethree=t.getPicturethree();
         Tproductpicture p= new Tproductpicture();
         p.setGuid(uuid());
         p.setProduct_guid(guid);
         p.setPicture(pictureone);
         productpictureDao.save(p);
         Tproductpicture p1= new Tproductpicture();
         p1.setGuid(uuid());
         p1.setProduct_guid(guid);
         p1.setPicture(picturetwo);
         productpictureDao.save(p1);
         Tproductpicture p2= new Tproductpicture();
         p2.setGuid(uuid());
         p2.setProduct_guid(guid);
         p2.setPicture(picturethree);
         productpictureDao.save(p2);
    } catch (Exception e) {
		logger.info("productAdd:" + e.getMessage());
	}
	}

	@Override
	public void add(Productpicture productpicture) {
		// TODO Auto-generated method stub
		
	}
  /**
   * 删除属性
   */
	@Override
	public void delete(Long id) {
		Tfield t = fieldDao.get(Tfield.class, id);
		fieldDao.delete(t);
	}

	@Override
	public void edit(Product r) {
		Tproduct t = new Tproduct();
     	BeanUtils.copyProperties(r, t);
		productDao.update(t);
	}

	@Override
	public Product get(Long id) {
		Tproduct t = productDao.get(Tproduct.class, id);
		Product r = new Product();
		BeanUtils.copyProperties(t, r);
		return r;
	}

	@Override
	public List<Product> dataGrid(Product product, PageFilter ph) {
		List<Product> ul = new ArrayList<Product>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproduct t ";
		List<Tproduct> l = productDao.find(hql + whereHql(product, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tproduct t : l) {
			Product u = new Product();
			BeanUtils.copyProperties(t, u);
//			if (t.getProducttype() != null) {
//				u.setProduct_type_id(t.getProducttype().getId());
//				u.setProducttypename(t.getProducttype().getName());
//			}
			ul.add(u);
		}
		return ul;
	}

	@Override
	public Long count(Product product, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tproduct t ";
		return productDao.count("select count(*) " + hql + whereHql(product, params), params);
	}

	private String whereHql(Product product, Map<String, Object> params) {
		String hql = "";
		if (product != null) {
			hql += " where 1=1 ";
			if (StringUtil.isNotEmpty(product.getName())) {
				hql += " and t.name like :name";
				params.put("name", "%%" + product.getName() + "%%");
			}
			if (StringUtil.isNotEmpty(product.getProduct_type_id())){
					hql += " and t.product_type_id = :product_type_id";
					params.put("product_type_id", product.getProduct_type_id());
				
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
	public List<ProductType> typeCombox() {
		try {
			List<ProductType> ul = new ArrayList<ProductType>();
			String hql = " from Tproducttype t order by t.id";
			List<Tproducttype> l = producttypeDao.find(hql);
			for (Tproducttype t : l) {
				ProductType u = new ProductType();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("ProducttypeGetCombox:" + e.getMessage());
			return null;
		}
		
	}
	@Override
	public List<ProductSupplier> supplierCombox() {
		try {
			List<ProductSupplier> ul = new ArrayList<ProductSupplier>();
			String hql = " from Tproductsupplier t order by t.id";
			List<Tproductsupplier> l = productsupplierDao.find(hql);
			for (Tproductsupplier t : l) {
				ProductSupplier u = new ProductSupplier();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("ProductSupplierGetCombox:" + e.getMessage());
			return null;
		}
}

	@Override
	public List<Productfield> fieldCombox() {
		try {
			List<Productfield> ul = new ArrayList<Productfield>();
			String hql = " from Tproductfield t order by t.id";
			List<Tproductfield> l = productfieldDao.find(hql);
			for (Tproductfield t : l) {
				Productfield u = new Productfield();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("ProductfieldGetCombox:" + e.getMessage());
			return null;
		}
}

	@Override
	public void add(Field r) {
		 try {
			 Tfield t = new Tfield();
		     	BeanUtils.copyProperties(r, t);
		     	fieldDao.save(t);
		  } catch (Exception e) {
				logger.info("fieldAdd:" + e.getMessage());
			}
			}
/**
 * 查询商品一级属性
 */
	@Override
	public List<Fieldname> fieldname(long product_id) {
		List<Fieldname> ul = new ArrayList<Fieldname>();
		String sql = " select t.id,t.field_id,t.product_id,t.picture,t.num,f.name from wnf_product_field t left join wnf_product_field_name f on t.field_id=f.id where t.state=0 and t.product_id="+product_id;
		List<Object[]> l =fieldDao.findBySql(sql);
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				Fieldname u = new Fieldname();
				u.setId(Long.valueOf(objects[0]+""));
				u.setValue(Integer.parseInt(objects[1]+""));
				u.setField_id(Integer.parseInt(objects[1]+""));
				u.setProduct_id(Integer.parseInt(objects[2]+""));
				u.setPicture(objects[3]+"");
				u.setNum(Integer.parseInt(objects[4]+""));
				u.setName(objects[5]+"");			
				ul.add(u);
			}
		}
		return ul;
	}

	/**
	 * 查询商品二级属性
	 */
		@Override
		public List<Fieldname> fieldnametwo(long product_id) {
			List<Fieldname> ul = new ArrayList<Fieldname>();
			String sql = " select t.id,t.field_id,t.product_id,t.picture,t.num,f.name from wnf_product_field t left join wnf_product_field_name f on t.field_id=f.id where t.state=1 and t.product_id="+product_id;
			List<Object[]> l =fieldDao.findBySql(sql);
			for (int i = 0; i < l.size(); i++) {
				Object[] objects=l.get(i);
				if(objects.length>0){
					Fieldname u = new Fieldname();
					u.setId(Long.valueOf(objects[0]+""));
					u.setValue(Integer.parseInt(objects[1]+""));
					u.setField_id(Integer.parseInt(objects[1]+""));
					u.setProduct_id(Integer.parseInt(objects[2]+""));
					u.setPicture(objects[3]+"");
					u.setNum(Integer.parseInt(objects[4]+""));
					u.setName(objects[5]+"");			
					ul.add(u);
				}
			}
			return ul;
		}
	@Override
	public void addp(Purchase r) {
		 try {
			 Tpurchase t = new Tpurchase();
		     	BeanUtils.copyProperties(r, t);
		     	purchaseDao.save(t);
		  } catch (Exception e) {
				logger.info("purchaseAdd:" + e.getMessage());
			}
		
	}
	@Override
	public List<Purchase> purchase(long product_id) {
		List<Purchase> ul = new ArrayList<Purchase>();
		String sql = " SELECT id,shelfnum,shelfbegintime,shelfendtime,scale from wnf_product_purchase where product_id="+product_id;
		List<Object[]> l =purchaseDao.findBySql(sql);
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				Purchase u = new Purchase();
				u.setId(Long.valueOf(objects[0]+""));
				u.setShelfnum(objects[1]+"");
				u.setShelfbegintime(objects[2]+"");
				u.setShelfendtime(objects[3]+"");
				u.setScale(objects[4]+"");
				ul.add(u);
			}
		}
		return ul;
	}
	@Override
	public void deletepurchase(Long id) {
		Tpurchase t = purchaseDao.get(Tpurchase.class, id);
		purchaseDao.delete(t);	
	}

	}

