package light.mvc.service.message;

import java.math.BigInteger;
import java.util.List;

import light.mvc.pageModel.message.Message;

public interface MessageServiceI {
	public BigInteger getUserCount(String userid);
	
	public BigInteger getfromuserCount(String userid);

	
	public void insertUserCard(String userid,String cardid,String activeid);
	
//	public List<WnfCardsBean> queryCard(String cardnum,String cardtyepid);
	
	
	public void insertNewUserCard(Message m);
	
	
	public void updateCardStatus(String id);
	
	
	/**
	 * 判断用户是否参加过此活动
	 */
	public BigInteger queryUserActiveStatus(String user,String active);
	
	
	
	public List<Message> getUserList(String phone);
	
	
	/**
	 * 查询库里有没有重复的随机数
	 * 
	 */
	public Integer getRandom(String r);
	
	
	
	public Integer insertUserSend(String fid, String oid, String aid,
			String type_id,BigInteger count);
	
	
	public BigInteger activenum(String code);
	
	
	public Integer getactivecardnum(String code);
}
