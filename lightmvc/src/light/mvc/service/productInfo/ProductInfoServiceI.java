package light.mvc.service.productInfo;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.productInfo.ProductInfo;
import light.mvc.pageModel.productInfo.ProductInfoView;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.producttype.Producttype;

public interface ProductInfoServiceI {
public List<ProductInfoView> dataGrid(ProductInfoView productInfo, PageFilter ph);
	
	public Long count(ProductInfoView productInfo, PageFilter ph);
	
	public void add(ProductInfo productInfo);
	
	public void edit(ProductInfo productInfo);
	
	public ProductInfo get(Long id);
	
	public List<Productfield> fieldCombox();
	
	public List<Product> productCombox();
}
