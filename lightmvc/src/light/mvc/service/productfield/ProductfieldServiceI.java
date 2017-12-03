package light.mvc.service.productfield;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.goods.ProductSupplier;
import light.mvc.pageModel.productfield.Productfield;
import light.mvc.pageModel.productfield.ProductfieldView;
import light.mvc.pageModel.producttype.Producttype;

public interface ProductfieldServiceI {
	public List<ProductfieldView> dataGrid(Productfield productfield, PageFilter ph);
	
	public Long count(Productfield productfield, PageFilter ph);
	
	public void add(Productfield productfield);
	
	public void edit(Productfield productfield);
	
	public Productfield get(Long id);
	
	public List<Producttype> typeCombox();

}
