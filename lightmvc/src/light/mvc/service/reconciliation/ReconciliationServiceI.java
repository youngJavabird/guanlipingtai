package light.mvc.service.reconciliation;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.scoredetail.Scoredetail;
import light.mvc.pageModel.sys.User;

public interface ReconciliationServiceI {

	public List<Scoredetail> dataGrid(Scoredetail scoredetail, PageFilter ph);

	public Long count(Scoredetail scoredetail, PageFilter ph);

	public Scoredetail scoredetail(Long id);
}
