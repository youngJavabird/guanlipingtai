package light.mvc.service.goods;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.field.Field;
import light.mvc.pageModel.field.Fieldname;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.goods.ProductType;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.productpicture.Productpicture;
import light.mvc.pageModel.purchase.Purchase;
/**
 * 商品service层
 * @author hanyi
 * @time 2016/8/1
 */
public interface ProductServiceI {

	public List<Product> dataGrid(Product product, PageFilter ph);

	public Long count(Product product, PageFilter ph);

	public void add(Product product);

	public void edit(Product product);

	public Product get(Long id);
	
	public List<ProductType> typeCombox();
	
	public List<ProductSupplier> supplierCombox();
	
	public List<Productfield> fieldCombox();
	
	public void add(Field field);
	//查询商品一级属性
	public List<Fieldname> fieldname(long id);
	//查询商品二级属性
	public List<Fieldname> fieldnametwo(long id);
	//删除属性
	public void delete(Long id);
	//添加商品的同时添加轮播图表
	public void add(Productpicture productpicture);
	
	public void  addp(Purchase purchase);
	//查询限购信息
	public List<Purchase> purchase(long id);
	//删除限购
	public void deletepurchase(Long id);
}
