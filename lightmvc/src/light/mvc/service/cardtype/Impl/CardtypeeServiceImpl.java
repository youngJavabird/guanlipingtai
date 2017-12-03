package light.mvc.service.cardtype.Impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.cardtype.Tcardtypee;
import light.mvc.pageModel.cardtype.Cardtypee;
import light.mvc.service.cardtype.CardtypeeServicel;




@Service
public class CardtypeeServiceImpl implements CardtypeeServicel {
	
	Logger logger = Logger.getLogger(CardtypeeServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tcardtypee> cardtypeDao;

	@Override
	public List<Cardtypee> getCombox() {
		try{
			List<Cardtypee> ul = new ArrayList<Cardtypee>();
			String hql = " from Tcardtypee t  order by t.id";
			List<Tcardtypee> l = cardtypeDao.find(hql);
			for (Tcardtypee t : l) {
				Cardtypee u = new Cardtypee();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
			return ul;
		} catch (Exception e) {
			logger.info("cardtypeeGetCombox:" + e.getMessage());
			return null;
		}
	}



}
