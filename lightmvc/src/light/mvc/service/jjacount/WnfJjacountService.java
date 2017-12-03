package light.mvc.service.jjacount;

import java.util.List;

import light.mvc.model.jjacount.TJjacount;
import light.mvc.pageModel.jjacount.Jjacount;

public interface WnfJjacountService {
	
	public List<Jjacount> getjjaccount();
	
	public int updstate(Jjacount jjacount);
	
	public List<TJjacount> check(String jjaccount);

}
