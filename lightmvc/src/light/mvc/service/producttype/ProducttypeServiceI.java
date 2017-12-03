package light.mvc.service.producttype;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.producttype.Producttype;

public interface ProducttypeServiceI {
	public List<Producttype> dataGrid(Producttype producttext, PageFilter ph);
	
	public Long count(Producttype producttext, PageFilter ph);
	
	public void add(Producttype producttext);
	
	public void edit(Producttype producttext);
	
	public Producttype get(Long id);
}
