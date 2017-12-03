package light.mvc.service.balance;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.balance.Balance;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;

public interface BalanceServiceI {

	public List<Balance> dataGrid(Balance balance, PageFilter ph);

	public Long count(Balance balance, PageFilter ph);


}
