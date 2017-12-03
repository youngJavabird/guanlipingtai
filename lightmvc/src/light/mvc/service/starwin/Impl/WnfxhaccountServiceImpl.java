package light.mvc.service.starwin.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import light.mvc.dao.BaseDaoI;

import light.mvc.model.starwin.TStarWin;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.utils.StringUtil;


@Service
public class WnfxhaccountServiceImpl implements WnfxhaccountServiceI {

	@Autowired
	private BaseDaoI<TStarWin> starwinDao;

	@Override
	public Starwin get(long id){
		TStarWin t=starwinDao.get(TStarWin.class, id);
		Starwin r=new Starwin();
		r.setId(t.getId());
		r.setPhone(t.getPhone());
		r.setName(t.getName());
		r.setPasswd(t.getPasswd());
		r.setState(t.getState());
		r.setCreatedate(t.getCreatedate());
		return r;
	}
	@Override
	public List<Starwin> dataGrid(Starwin starwin,PageFilter ph) {
		List<Starwin> ul = new ArrayList<Starwin>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TStarWin t";
		List<TStarWin> l = starwinDao.find(hql + whereHql(starwin, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		for (TStarWin t : l) {
			Starwin u = new Starwin();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	@Override
	public List<Starwin> dataGrid(Starwin starwin) {
		List<Starwin> ul = new ArrayList<Starwin>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TStarWin t";
		List<TStarWin> l = starwinDao.find(hql + whereHql(starwin, params),params);
		for (TStarWin t : l) {
			Starwin u = new Starwin();
			BeanUtils.copyProperties(t, u);
			ul.add(u);
		}
		return ul;
	}
	@Override
	public Long count(Starwin starwin) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TStarWin t ";
		return starwinDao.count("select count(*) " + hql + whereHql(starwin, params), params);
	}

	@Override
	public Long count(Starwin starwin, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from TStarWin t ";
		return starwinDao.count("select count(*) " + hql + whereHql(starwin, params), params);
	}

	private String whereHql(Starwin starwin, Map<String, Object> params) {
		String hql = "";
		if (starwin != null) {
			hql += " where 1=1 ";
			if (starwin.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + starwin.getName() + "%%");
			}
			if (starwin.getPhone() != null) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + starwin.getPhone() + "%%");
			}
			if (starwin.getCreatedatetimeStart() != null) {
				hql += " and t.createdate >= :createdatetimeStart";
				params.put("createdatetimeStart", starwin.getCreatedatetimeStart());

			}
			if (starwin.getCreatedatetimeEnd() != null) {
				hql += " and t.createdate <= :createdatetimeEnd";
				params.put("createdatetimeEnd", starwin.getCreatedatetimeEnd());

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
	



}
