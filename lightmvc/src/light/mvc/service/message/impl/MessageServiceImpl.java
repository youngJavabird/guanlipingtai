package light.mvc.service.message.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.headlelines.Theadlelines;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.headlelines.Headlelines;
import light.mvc.pageModel.headlelines.HeadlelinesView;
import light.mvc.pageModel.message.Cardsend;
import light.mvc.pageModel.message.MemberApp;
import light.mvc.pageModel.message.Message;
import light.mvc.service.headlelines.HeadlelinesServiceI;
import light.mvc.service.message.MessageServiceI;
import light.mvc.utils.StringUtil;

@Service
public class MessageServiceImpl implements MessageServiceI {
	Logger logger = Logger.getLogger(MessageServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Message> messageDao;
	@Autowired
	private BaseDaoI<MemberApp> memberAppDao;
	@Autowired
	private BaseDaoI<Cardsend> cardsendDao;


	

	@Override
	public BigInteger getUserCount(String userid) {
		String sql = "select count(*) from wnf_member_app where userid = '"+userid+"'";
		BigInteger a=messageDao.countBySql(sql);
		return a;
	}

	@Override
	public BigInteger getfromuserCount(String userid) {
		String sql = "select count(*) from wnf_member where fromusername = '"+userid+"'";
		BigInteger a=messageDao.countBySql(sql);
		return a;
	}

	@Override
	public void insertUserCard(String userid, String cardid, String activeid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertNewUserCard(Message m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCardStatus(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigInteger queryUserActiveStatus(String user, String active) {
		String sql = "SELECT count(*) FROM `wnf_card_send` where user_phone = '"+user+"' and activeid = '"+active+"' and state in (0,1);";
		BigInteger a=messageDao.countBySql(sql);
		return a;
	}

	@Override
	public List<Message> getUserList(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRandom(String r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertUserSend(String fid, String oid, String aid,
			String type_id, BigInteger count) {

		String sql = "";
		Integer state =0;
		if(StringUtil.isMobile(fid)){
			//查询user_guid
			List<Object[]> malist =memberAppDao.findBySql("select * from wnf_member_app where userid = '"+fid+"'");
			String user_guid = "";
//			if(malist.size()>0){
//				MemberApp m=new MemberApp();
//			    user_guid=malist.get(0).getId();
//			}
			for (Object[] o : malist) {
				MemberApp m=new MemberApp();
				m.setId(o[0].toString());
				user_guid=m.getId();
			}
			//app用户
			sql = "insert into wnf_card_send (user_guid,state,createdate,orderid,activeid,user_phone,type_id)values('"+user_guid+"',0,now(),'"+oid+"','"+aid+"','"+fid+"','"+type_id+"')";
			state =  cardsendDao.executeSql(sql);
		}else{
			//微信用户
			sql = "insert into wnf_card_send (from_user_name,state,createdate,orderid,activeid,user_phone,type_id)values('"+fid+"',0,now(),'"+oid+"','"+aid+"','"+""+"','"+type_id+"')";
			 state = cardsendDao.executeSql(sql);
		}
				
		return state;
	}

	@Override
	public BigInteger activenum(String code) {
		String sql = "select count(*)  from wnf_card_send where state in (0,1) and activeid = '"+code+"'";
		BigInteger a=messageDao.countBySql(sql);
		return a;
	}

	@Override
	public Integer getactivecardnum(String code) {
		String sql = "select cardnum from wnf_activity where activitycode= "+code;
		BigInteger b=messageDao.countBySqlNew(sql);
		Integer a=b.intValue();
		return a;
	}
	
	
}
