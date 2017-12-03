package light.mvc.service.goods;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.goods.ProductType;

public interface ProductTypeServiceI {
	public List<ProductType> treeGrid();
	public List<Tree> tree();
	public List<ProductType> dataGrid(ProductType productType, PageFilter ph);
	public Long count(ProductType productType, PageFilter ph);
	public void edit(ProductType productType);
	public ProductType get(Long id);
	public void add(ProductType productType);
}
