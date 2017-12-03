package light.mvc.service.statistics;


import java.util.List;

import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.pageModel.statistics.Identification;
import light.mvc.pageModel.statistics.Statistics;


public interface StatisticsServiceI {
	/**
	 * 获取流量信息
	 * @param page
	 * @param rows
	 * @return
	 */

	public List<Identification> dataGrid(Identification identification,PageFilter ph);
	
	//所有PV,UV
	public List<Identification> dataGrid(Identification identification);
	//公众号，活动商城PV UV
	public List<Identification> activity(Identification identification);

	public	Long count(Identification identification, PageFilter ph);

    public Identification get(long id);

    public Long count(Identification identification);
    
	public List<Identification> name_combox();
	
	public List<Statistics> dataGrid(Statistics statistics);
	
	public Long count(Statistics statistics);
//渠道001
	public List<Statistics> listone(Statistics statistics);
	//渠道002	
	public List<Statistics> listtwo(Statistics statistics);

}
