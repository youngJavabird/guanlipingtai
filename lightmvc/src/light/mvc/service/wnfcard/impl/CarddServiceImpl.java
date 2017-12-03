package light.mvc.service.wnfcard.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import light.mvc.dao.BaseDaoI;
import light.mvc.model.carousel.Tcarousel;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.wnfcard.Tcardd;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.base.SessionInfo;
import light.mvc.pageModel.card.Card;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.sys.User;
import light.mvc.pageModel.wnfcard.Cardd;
import light.mvc.service.card.CardtypeServiceI;
import light.mvc.service.goods.impl.ProductServiceImpl;
import light.mvc.service.sys.UserServiceI;
import light.mvc.service.wnfcard.CarddServiceI;
import light.mvc.utils.MD5Util;
import light.mvc.utils.StringUtil;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.log.LogUtils;
import com.sun.accessibility.internal.resources.accessibility;

@Service
public class CarddServiceImpl implements CarddServiceI {
	Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());
	@Autowired
	private BaseDaoI<Tcardd> cardddao;


	@Override
	public List<Cardd> dataGrid(Cardd cardd, PageFilter ph) {
		List<Cardd> ul = new ArrayList<Cardd>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcardd t ";
		List<Tcardd> l = cardddao.find(hql + whereHql(cardd, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tcardd t : l) {
			Cardd u = new Cardd();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;

	}
	

	@Override
	public Long count(Cardd cardd, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcardd t ";
		return cardddao.count("select count(*) " + hql + whereHql(cardd, params), params);
	}

	private String whereHql(Cardd cardd, Map<String, Object> params) {
		String hql = "";
		if (cardd != null) {
			hql += " where 1=1 ";
			
		}
		return hql;
	}
	
	
	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	public Cardd get(Long id) {
		Tcardd t = cardddao.get(Tcardd.class, id);
		Cardd r = new Cardd();
		BeanUtils.copyProperties(t, r);
		return r;
	}

	@Override
	public void add(Cardd r) {
		try {
			Tcardd t = new Tcardd();
			BeanUtils.copyProperties(r, t);
			cardddao.save(t);
		} catch (Exception e) {
			logger.info("CarddAdd:" + e.getMessage());
		}
		
	}


}
