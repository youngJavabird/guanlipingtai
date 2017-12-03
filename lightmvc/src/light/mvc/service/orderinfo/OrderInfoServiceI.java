package light.mvc.service.orderinfo;

import java.util.List;

import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.orderinfo.CardUtilBean;
import light.mvc.pageModel.orderinfo.Orderinfo;
import light.mvc.pageModel.orderinfo.OrderinfoView;
import light.mvc.pageModel.usercard.Usercard;

public interface OrderInfoServiceI {
	public List<OrderinfoView> dataGrid(Orderinfo orderinfo, PageFilter ph);
	
	public Long count(Orderinfo orderinfo, PageFilter ph);
	
	public void edit(Orderinfo orderinfo);
	
	public Orderinfo get(Long id);
	
	public List<CardUtilBean> getCardType(String orderid);
	
	public List<Card> queryCard(String cardNum,String cardTypeId);
	
	public void InsertUserCard(Usercard uc);
	
	public void EditCardState(Card c);
	
	public String getToken(String type);
	
	public Card getCard(Long id);
	
	public void EditOrderInfo(Orderinfo orderinfo);
	
	public void EditRemark(String orderid,String refundamount,String refee,String remark);
}
