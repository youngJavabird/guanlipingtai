package light.mvc.service.starwin;


import java.util.List;

import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.starwin.Starwin;


public interface WnfxhaccountServiceI {
	/**
	 * 获取星河账户信息
	 * @param page
	 * @param rows
	 * @return
	 */

	public List<Starwin> dataGrid(Starwin starwin,PageFilter ph);
	
	public List<Starwin> dataGrid(Starwin starwin);

	public	Long count(Starwin starwin, PageFilter ph);

    public Starwin get(long id);

    public Long count(Starwin starwin);
	

}
