package light.mvc.service.productInfo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import light.mvc.dao.BaseDaoI;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.productInfo.TproductInfo;
import light.mvc.model.productfield.Tproductfield;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.productInfo.ProductInfo;
import light.mvc.pageModel.productInfo.ProductInfoView;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.service.productInfo.ProductInfoServiceI;
import light.mvc.service.productfield.impl.ProductfieldServiceImpl;
import light.mvc.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl implements ProductInfoServiceI {
Logger logger = Logger.getLogger(ProductfieldServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<TproductInfo> productInfoDao;
	
	@Autowired
	private BaseDaoI<Tproductfield> productfieldDao;

	@Autowired
	private BaseDaoI<Tproduct> productDao;
	
	@Override
	public List<ProductInfoView> dataGrid(ProductInfoView productInfo, PageFilter ph){
		List<ProductInfoView> ul = new ArrayList<ProductInfoView>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id,wnt.name as fieldname,wct.typename,wp.name as productname,t.num,"
				+ "CASE WHEN t.state='0' THEN '正常' WHEN t.state='1' THEN '禁用' ELSE '' END as state "
				+ "from wnf_product_field t "
				+ "left join wnf_product_field_name wnt on t.field_id = wnt.id "
				+ "left join wnf_product_field_contrast wct on wnt.type = wct.id "
				+ "left join wnf_product wp on t.id = wp.product_id";
		List<Object[]> l = productInfoDao.findBySql(sql + whereHql(productInfo, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				ProductInfoView u = new ProductInfoView();
				u.setId(Long.valueOf(objects[0]+""));
				u.setFieldname(objects[1]+"");
				u.setTypename(objects[2]+"");
				u.setProductname(objects[3]+"");
				u.setNum((Integer)objects[4]);
				u.setState(objects[5]+"");
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public Long count(ProductInfoView productInfo, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from wnf_product_field t "
				+ "left join wnf_product_field_name wnt on t.field_id = wnt.id "
				+ "left join wnf_product_field_contrast wct on wnt.type = wct.id "
				+ "left join wnf_product wp on t.id = wp.product_id";
		return productInfoDao.count("select count(*) " + hql + whereHql(productInfo, params), params);
	}
	
	private String whereHql(ProductInfoView productInfo, Map<String, Object> params){
		String hql = "";
		if (productInfo != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(productInfo.getProductname())){
					hql += " and wp.name like :productname";
					params.put("productname", "%%" + productInfo.getProductname() + "%%");
				}
				if(StringUtil.isNotEmpty(productInfo.getState())){
					String state = "";
					if (productInfo.getState().equals("正常")) {
						state = "0";
					}
					else {
						state = "1";
					}
					hql += " and t.state = :state";
					params.put("state", state);
				}
			} catch (Exception e) {
				logger.info("productInfoWhereHql:" + e.getMessage());
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
	public void add(ProductInfo r) {
		try {
			TproductInfo t = new TproductInfo();
			BeanUtils.copyProperties(r, t);
			productInfoDao.save(t);
		} catch (Exception e) {
			logger.info("productInfoAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(ProductInfo r){
		try {
			TproductInfo t = new TproductInfo();
			BeanUtils.copyProperties(r, t);
			productInfoDao.update(t);
		} catch (Exception e) {
			logger.info("productfieldEdit:" + e.getMessage());
		}
	}

	@Override
	public ProductInfo get(Long id){
		TproductInfo t = productInfoDao.get(TproductInfo.class, id);
		ProductInfo r = new ProductInfo();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public List<Productfield> fieldCombox(){
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
			logger.info("fieldGetCombox:" + e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Product> productCombox(){
		try {
			List<Product> ul = new ArrayList<Product>();
			String hql = " from Tproduct t order by t.id";
			List<Tproduct> l = productDao.find(hql);
			for (Tproduct t : l) {
				Product u = new Product();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("productGetCombox:" + e.getMessage());
			return null;
		}
	}
}
