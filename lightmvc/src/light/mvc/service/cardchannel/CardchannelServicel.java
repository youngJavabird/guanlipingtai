package light.mvc.service.cardchannel;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;

public interface CardchannelServicel {
	public List<Cardchannel> dataGrid(Cardchannel cardchannel, PageFilter ph);

	public Long count(Cardchannel cardchannel, PageFilter ph);

	public void add(Cardchannel cardchannel);
	
	public void edit(Cardchannel cardchannel);
	
	public Cardchannel get(Long id);

}
