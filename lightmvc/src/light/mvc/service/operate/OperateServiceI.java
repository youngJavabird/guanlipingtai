package light.mvc.service.operate;

import java.util.List;

import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.demo.Demo;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.operate.Operate;

public interface OperateServiceI {


	public void add(Operate operate);
	
	public List<Operate> dataGrid(Operate operate, PageFilter ph);

	public Long count(Operate operate, PageFilter ph);

}
