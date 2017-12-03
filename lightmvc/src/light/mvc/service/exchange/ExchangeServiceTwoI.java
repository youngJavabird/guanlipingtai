package light.mvc.service.exchange;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.operate.Operate;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;

public interface ExchangeServiceTwoI {

	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph);

	public Long count(Scoredetail scoredetail, PageFilter ph);

	public Scoredetail scoredetail(Long id);
	
	public List<Operate> getopera(String userid,String guid);

	List<Operate> getopera2(String userid, String card_code,String sendid);
}
