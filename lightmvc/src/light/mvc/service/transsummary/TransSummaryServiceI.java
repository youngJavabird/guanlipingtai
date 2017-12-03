package light.mvc.service.transsummary;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.transsummary.Transsummary;
import light.mvc.pageModel.transtype.Transtype;

public interface TransSummaryServiceI {
	public List<Transsummary> dataGrid(Transsummary transsummary, PageFilter ph);

	public Long count(Transsummary transsummary, PageFilter ph);
	
	public List<Transtype> getTranstypeCbo();
}
