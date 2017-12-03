package light.mvc.service.jjacount.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.jjacount.TJjacount;
import light.mvc.pageModel.jjacount.Jjacount;
import light.mvc.service.jjacount.WnfJjacountService;

@Service
public class WnfJjacountServiceImpl implements WnfJjacountService {

	@Autowired
	private BaseDaoI<TJjacount> jjacountDao;
	@Override
	public List<Jjacount> getjjaccount() {
		 List<Jjacount> ul=new ArrayList<Jjacount>();
		 Map<String, Object> params = new HashMap<String, Object>();
		String hql="from  TJjacount t";
		List<TJjacount> l=jjacountDao.find(hql, params);
		for (TJjacount t : l) {
			Jjacount u=new Jjacount();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	@Override
	public int updstate(Jjacount jjacount) {
		String sql="update wnf_jjacount set state = '02' where jjaccount = '" + jjacount.getJjaccount() + "'";

       int res=jjacountDao.executeSql(sql);
      
	   return res;
	}
	@Override
	public List<TJjacount> check(String jjaccount) {
		String sql="from TJjacount t where t.jjaccount ='"+jjaccount+"'";
		//
		List<TJjacount> list = jjacountDao.find(sql);
		
	
		return list;
	}

}
