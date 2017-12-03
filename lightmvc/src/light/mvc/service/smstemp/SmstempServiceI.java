package light.mvc.service.smstemp;

import java.util.List;

import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.activityType.ActivityType;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.smstemp.Smstemp;
import light.mvc.pageModel.smstemp.SmstempView;
import light.mvc.pageModel.transtype.Transtype;

public interface SmstempServiceI {
	public List<SmstempView> dataGrid(Smstemp smstemp, PageFilter ph);
	
	public Long count(Smstemp smstemp, PageFilter ph);
	
	public void add(Smstemp smstemp);
	
	public void edit(Smstemp smstemp);
	
	public void delete(Long id);
	
	public List<Activity> getActivityCombox();
	
	public List<Transtype> getTransCombox();
	
	public Smstemp get(Long id);
}
