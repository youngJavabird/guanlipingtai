package light.mvc.service.wnfcard;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;

import light.mvc.pageModel.wnfcard.Cardd;

public interface CarddServiceI {

	public List<Cardd> dataGrid(Cardd cardd, PageFilter ph);

	public Long count(Cardd cardd, PageFilter ph);

	public Cardd get(Long id);
	
	public void add(Cardd r);

}
