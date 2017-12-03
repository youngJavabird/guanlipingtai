package light.mvc.service.carousel.impl;

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
import light.mvc.pageModel.activity.Activity;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.carousel.Carousel;
import light.mvc.service.activity.impl.ActivityServiceImpl;
import light.mvc.service.carousel.CarouselServicel;
import light.mvc.utils.StringUtil;



@Service
public class CarouselServiceImpl implements CarouselServicel {
	
	Logger logger = Logger.getLogger(CarouselServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Tcarousel> carouselDao;

	@Override
	public List<Carousel> dataGrid(Carousel carousel, PageFilter ph) {
		List<Carousel> ul = new ArrayList<Carousel>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcarousel t ";
		List<Tcarousel> l = carouselDao.find(hql + whereHql(carousel, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Tcarousel t : l) {
			Carousel u = new Carousel();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	private String whereHql(Carousel carousel, Map<String, Object> params){
		String hql = "";
		if (carousel != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(carousel.getName())){
					hql += " and t.name like :name";
					params.put("name", "%%" + carousel.getName() + "%%");
				}
			} catch (Exception e) {
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
	public Long count(Carousel carousel, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tcarousel t ";
		return carouselDao.count("select count(*) " + hql + whereHql(carousel, params), params);
	}

	@Override
	public void add(Carousel r) {
		try {
			Tcarousel t = new Tcarousel();
			BeanUtils.copyProperties(r, t);
			carouselDao.save(t);
		} catch (Exception e) {
			logger.info("carouselAdd:" + e.getMessage());
		}
	}

	@Override
	public void edit(Carousel r) {
		try {
			Tcarousel t = new Tcarousel();
			BeanUtils.copyProperties(r, t);
			carouselDao.update(t);
		} catch (Exception e) {
			logger.info("carouselEdit:" + e.getMessage());
		}
	}

	@Override
	public Carousel get(Long id) {
		Tcarousel t = carouselDao.get(Tcarousel.class, id);
		Carousel r = new Carousel();
		BeanUtils.copyProperties(t, r);
		return r;
	}

}
