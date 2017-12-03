package light.mvc.service.transdetail;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.transdetail.Transdetail;
import light.mvc.pageModel.transtype.Transtype;

public interface TransdetailServiceI {
	public List<Transdetail> dataGrid(Transdetail transdetail, PageFilter ph);

	public Long count(Transdetail transdetail, PageFilter ph);
	
	public List<Transtype> getTranstypeCbo();
}
