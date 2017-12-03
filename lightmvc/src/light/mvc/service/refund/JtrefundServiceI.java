package light.mvc.service.refund;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.jtrefund.Jtrefund;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.refundrecord.Refundrecord;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;

public interface JtrefundServiceI {

	public List<Jtrefund> dataGrid(Jtrefund jtrefund, PageFilter ph);

	public Long count(Jtrefund jtrefund, PageFilter ph);

	public Jtrefund jtrefund(Long id);
	
	public void check(String id);
	
	public void refusecheck(String id,String remark);
	
	public void oncheck(String id);
	
	public void add(Refundrecord refundrecord);
}
