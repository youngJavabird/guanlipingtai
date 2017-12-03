package light.mvc.service.activity;

import java.util.List;

import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.PageFilter;

public interface ActivityServiceI {
	public List<Activity> dataGrid(Activity activity, PageFilter ph);

	public Long count(Activity activity, PageFilter ph);

	public void add(Activity activity);
	
	public void edit(Activity activity);
	
	public List<ActivityType> getCombox();
	
	public Activity get(Long id);

	public List<Activity> getActive();
}



