package light.mvc.service.headlelines.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.headlelines.Theadlelines;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.headlelines.Headlelines;
import light.mvc.pageModel.headlelines.HeadlelinesView;
import light.mvc.service.headlelines.HeadlelinesServiceI;
import light.mvc.utils.StringUtil;

@Service
public class HeadlelinesServiceImpl implements HeadlelinesServiceI {
	Logger logger = Logger.getLogger(HeadlelinesServiceImpl.class.getName());
	
	@Autowired
	private BaseDaoI<Theadlelines> headlelinesDaoI;
	
	@Override
	public List<HeadlelinesView> dataGrid(Headlelines headlelines, PageFilter ph){
		List<HeadlelinesView> ul = new ArrayList<HeadlelinesView>();
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id,t.guid,t.describes,t.href,t.sort,t.createtime,t.upddate,t.title,"
				+ "t.clicktimes + t.virclicktime clicktimes,t.picture from wnf_topline t";
		List<Object[]> l = headlelinesDaoI.findBySql(sql + whereHql(headlelines, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (int i = 0; i < l.size(); i++) {
			Object[] objects=l.get(i);
			if(objects.length>0){
				HeadlelinesView u = new HeadlelinesView();
				u.setId(Long.valueOf(objects[0]+""));
				u.setGuid(objects[1]+"");
				u.setDescribes(objects[2]+"");
				u.setHref(objects[3]+"");
				u.setSort((Integer)objects[4]);
				u.setCreatetime((Date)objects[5]);
				u.setUpddate((Date)objects[6]);
				u.setTitle(objects[7]+"");
				u.setClicktimes(Integer.parseInt(objects[8]+""));
				u.setPicture(objects[9]+"");
				ul.add(u);
			}
		}
		return ul;
	}
	
	@Override
	public Long count(Headlelines headlelines, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Theadlelines t ";
		return headlelinesDaoI.count("select count(*) " + hql + whereHql(headlelines, params), params);
	}
	
	private String whereHql(Headlelines headlelines, Map<String, Object> params){
		String hql = "";
		if (headlelines != null) {
			try {
				hql += " where 1=1 ";
				if(StringUtil.isNotEmpty(headlelines.getDescribes())){
					hql += " and t.describes like :describes";
					params.put("describes", "%%" + headlelines.getDescribes() + "%%");
				}
				if (headlelines.getCreatetime() != null) {
					hql += " and t.createtime >= :createtime";
					params.put("createtime", headlelines.getCreatetime());
				}
				if (headlelines.getUpddate() != null) {
					hql += " and t.createtime <= :upddate";
					params.put("upddate", headlelines.getUpddate());
				}
			} catch (Exception e) {
				logger.info("headlelinesWhereHql:" + e.getMessage());
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
	public void add(Headlelines r) {
		try {
			Theadlelines t = new Theadlelines();
			BeanUtils.copyProperties(r, t);
			headlelinesDaoI.save(t);
		} catch (Exception e) {
			logger.info("headlelinesAdd:" + e.getMessage());
		}
	}
	
	@Override
	public void edit(Headlelines r){
		try {
			Theadlelines t = new Theadlelines();
			BeanUtils.copyProperties(r, t);
			headlelinesDaoI.update(t);
		} catch (Exception e) {
			logger.info("headlelinesEdit:" + e.getMessage());
		}
	}
	
	@Override
	public Headlelines get(Long id){
		Theadlelines t = headlelinesDaoI.get(Theadlelines.class, id);
		Headlelines r = new Headlelines();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	
	@Override
	public BigInteger getMaxSort(){
		String sql = "select max(sort) from wnf_topline";
		BigInteger sort = headlelinesDaoI.countBySqlNew(sql);
		return sort;
	}
	
	@Override
	public void updateOtherInfo(int sort){
		try {
			String sql = "update wnf_topline set sort=sort+1";
			Map<String, Object> params = new HashMap<String, Object>();
			sql += " where 1=1 ";
			sql += " and sort >= :sort";
			params.put("sort", sort);
			headlelinesDaoI.executeSql(sql,params);
		} catch (Exception e) {
			logger.info("headlelinesUpdateOtherInfo:"+e.getMessage());
		}
	}
	
	@Override
	public void updateSort(int oldsort, int newsort){
		try {
			//如果新序号小于原序号
			if (oldsort > newsort) {
				String sql = "update wnf_topline set sort=sort+1";
				Map<String, Object> params = new HashMap<String, Object>();
				sql += " where 1=1 ";
				sql += " and sort < :oldsort";
				params.put("oldsort", oldsort);
				sql += " and sort >= :newsort";
				params.put("newsort", newsort);
				headlelinesDaoI.executeSql(sql,params);
			}
			//如果新序号大于原序号
			else if (oldsort < newsort) {
				String sql = "update wnf_topline set sort=sort-1";
				Map<String, Object> params = new HashMap<String, Object>();
				sql += " where 1=1 ";
				sql += " and sort > :oldsort";
				params.put("oldsort", oldsort);
				sql += " and sort <= :newsort";
				params.put("newsort", newsort);
				headlelinesDaoI.executeSql(sql,params);
			}
		} catch (Exception e) {
			logger.info("headlelinesUpdateSort:"+e.getMessage());
		}
	}
	
	@Override
	public void delete(Long id) {
		Theadlelines t = headlelinesDaoI.get(Theadlelines.class,id);
		headlelinesDaoI.delete(t);
	}
}
