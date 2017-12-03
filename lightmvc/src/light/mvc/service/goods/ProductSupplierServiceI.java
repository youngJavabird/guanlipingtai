package light.mvc.service.goods;

import java.util.List;

import light.mvc.pageModel.base.Tree;
import light.mvc.pageModel.goods.ProductSupplier;

public interface ProductSupplierServiceI {
	public List<ProductSupplier> treeGrid();
	public List<Tree> tree();
}
