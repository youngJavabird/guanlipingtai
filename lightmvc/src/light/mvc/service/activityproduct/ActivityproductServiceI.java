package light.mvc.service.activityproduct;

import java.util.List;

import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityProduct.ActivityProduct;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;

public interface ActivityproductServiceI {
	public List<ActivityProduct> dataGrid(ActivityProduct activityProduct, PageFilter ph);

	public Long count(ActivityProduct activityProduct, PageFilter ph);

	public void add(ActivityProduct activityProduct);
	
	public void edit(ActivityProduct activityProduct);
	
	public List<Cardchannel> getCombox();
	
	public ActivityProduct get(Long id);
}



