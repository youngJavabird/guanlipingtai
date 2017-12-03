package light.mvc.service.richtext;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.richtext.Richtext;

public interface RichtextServiceI {
	public List<Richtext> dataGrid(Richtext richtext, PageFilter ph);
	
	public Long count(Richtext richtext, PageFilter ph);
	
	public void add(Richtext richtext);

	public void edit(Richtext richtext);
	
	public Richtext get(Long id);
	
	public List<Richtext> combobox_data();
	
}
