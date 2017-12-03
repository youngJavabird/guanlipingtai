package light.mvc.service.headlelines;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.headlelines.Headlelines;
import light.mvc.pageModel.headlelines.HeadlelinesView;

public interface HeadlelinesServiceI {
	public List<HeadlelinesView> dataGrid(Headlelines headlelines, PageFilter ph);
	
	public Long count(Headlelines headlelines, PageFilter ph);
	
	public void add(Headlelines headlelines);
	
	public void edit(Headlelines headlelines);
	
	public Headlelines get(Long id);
	
	public BigInteger getMaxSort();
	
	public void updateOtherInfo(int sort);
	
	public void updateSort(int oldsort, int newsort);
	
	public void delete(Long id);
}
