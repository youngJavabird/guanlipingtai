package light.mvc.service.supplier;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.supplier.Supplier;

public interface SupplierServiceI {
	public List<Supplier> dataGrid(Supplier supplier, PageFilter ph);

	public Long count(Supplier supplier, PageFilter ph);
	
	public void add(Supplier supplier);

	public void edit(Supplier supplier);
	
	public Supplier get(Long id);

}
