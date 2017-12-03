package light.mvc.service.cardchannel.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.activity.Tactivity;
import light.mvc.model.cardchannel.Tcardchannel;
import light.mvc.model.carousel.Tcarousel;
import light.mvc.model.demo.Tdemo;
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.cardchannel.Cardchannel;
import light.mvc.pageModel.carousel.Carousel;
import light.mvc.pageModel.demo.Demo;
import light.mvc.service.activity.impl.ActivityServiceImpl;
import light.mvc.service.cardchannel.CardchannelServicel;
import light.mvc.service.carousel.CarouselServicel;
import light.mvc.utils.StringUtil;



@Service
public class CardchannelServiceImpl implements CardchannelServicel {
	
	Logger logger = Logger.getLogger(CardchannelServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tcardchannel> cardchannelDao;

	@Override
	public List<Cardchannel> dataGrid(Cardchannel cardchannel, PageFilter ph) {
		List<Cardchannel> ul = new ArrayList<Cardchannel>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcardchannel t ";
		List<Tcardchannel> l = cardchannelDao.find(hql + whereHql(cardchannel, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tcardchannel t : l) {
			Cardchannel u = new Cardchannel();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	private String whereHql(Cardchannel cardchannel, Map<String, Object> params){
		String hql = "";
		if (cardchannel != null) {
			try {
				hql += " where 1=1 ";
			} catch (Exception e) {
				e.getStackTrace();
				logger.info("activityWhereHql:" + e.getMessage());
			}
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
	public Long count(Cardchannel cardchannel, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcardchannel t ";
		return cardchannelDao.count("select count(*) " + hql + whereHql(cardchannel, params), params);
	}

	@Override
	public void add(Cardchannel r) {
		try {
			Tcardchannel t = new Tcardchannel();
			BeanUtils.copyProperties(r, t);
			cardchannelDao.save(t);
		} catch (Exception e) {
			logger.info("cardchannelAdd:" + e.getMessage());
		}
	}

	@Override
	public void edit(Cardchannel r) {
		try {
			Tcardchannel t = new Tcardchannel();
			BeanUtils.copyProperties(r, t);
			cardchannelDao.update(t);
		} catch (Exception e) {
			logger.info("cardchannelEdit:" + e.getMessage());
		}
	}

	@Override
	public Cardchannel get(Long id) {
		Tcardchannel t = cardchannelDao.get(Tcardchannel.class, id);
		Cardchannel r = new Cardchannel();
		BeanUtils.copyProperties(t, r);
		return r;
	}


}
