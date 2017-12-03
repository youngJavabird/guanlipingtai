package light.mvc.service.reply.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.reply.Treply;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.reply.Reply;
import light.mvc.service.reply.ReplyServicel;
import light.mvc.utils.StringUtil;



@Service
public class ReplyServiceImpl implements ReplyServicel {
	
	Logger logger = Logger.getLogger(ReplyServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Treply> replyDao;

	@Override
	public List<Reply> dataGrid(Reply reply, PageFilter ph) {
		List<Reply> ul = new ArrayList<Reply>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Treply t ";
		List<Treply> l = replyDao.find(hql + whereHql(reply, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (Treply t : l) {
			Reply u = new Reply();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	
	private String whereHql(Reply reply, Map<String, Object> params){
		String hql = "";
		if (reply != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(reply.getName())){
					hql += " and t.name like :name";
					params.put("name", "%%" + reply.getName() + "%%");
				}
			} catch (Exception e) {
				logger.info("replyWhereHql:" + e.getMessage());
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
	public Long count(Reply reply, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Treply t ";
		return replyDao.count("select count(*) " + hql + whereHql(reply, params), params);
	}

	@Override
	public void add(Reply r) {
		try {
			Treply t = new Treply();
			BeanUtils.copyProperties(r, t);
			replyDao.save(t);
		} catch (Exception e) {
			logger.info("replyAdd:" + e.getMessage());
		}
	}

	@Override
	public void edit(Reply r) {
		try {
			Treply t = new Treply();
			BeanUtils.copyProperties(r, t);
			replyDao.update(t);
		} catch (Exception e) {
			logger.info("replyEdit:" + e.getMessage());
		}
	}

	@Override
	public Reply get(Long id) {
		Treply t = replyDao.get(Treply.class, id);
		Reply r = new Reply();
		BeanUtils.copyProperties(t, r);
		return r;
	}

}
