package light.mvc.service.card;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.sys.User;

public interface CardtypetwoServiceI {

	public List<Card> dataGrid(Card card, PageFilter ph);

	public Long count(Card card, PageFilter ph);
	

}
