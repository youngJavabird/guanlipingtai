package light.mvc.service.card;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.sys.User;

public interface CardtypeServiceI {

	public List<Cardtype> dataGrid(Card card, PageFilter ph);

	public Long count(Card card, PageFilter ph);

	public List<Cardtype> card_type_combox();
	
//	public List<Card> dataGrid2(Card card, PageFilter ph);
//	
//	public Long count2(Card card, PageFilter ph);
//	
	//public void add(Card user);

	//public void delete(Long id);

	//public void edit(User user);

	//public User get(Long id);

	//public User login(User user);

	//public List<String> listResource(Long id);

	//public boolean editUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	//public User getByLoginName(User user);

	//public List<Card> getUserListByUserType();

	//public String[] getUserListNameByUserType();

}
