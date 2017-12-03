package light.mvc.service.statistics.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




























import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import light.mvc.dao.BaseDaoI;
import light.mvc.model.goods.Tproduct;
import light.mvc.model.starwin.TStarWin;
import light.mvc.model.statistics.Tidentification;
import light.mvc.model.statistics.Tstatistics;
import light.mvc.pageModel.base.PageFilter;
import light.mvc.pageModel.card.Cardtype;
import light.mvc.pageModel.goods.Product;
import light.mvc.pageModel.starwin.Starwin;
import light.mvc.pageModel.statistics.Identification;
import light.mvc.pageModel.statistics.Statistics;
import light.mvc.service.starwin.WnfxhaccountServiceI;
import light.mvc.service.statistics.StatisticsServiceI;
import light.mvc.service.supplier.impl.SupplierServiceImpl;
import light.mvc.utils.StringUtil;


@Service
public class StatisticsServiceImpl implements StatisticsServiceI {
	
	Logger logger = Logger.getLogger(StatisticsServiceImpl.class.getName());

	@Autowired
	private BaseDaoI<Tidentification> identificationDao;
	@Autowired
	private BaseDaoI<Tstatistics> statisticsDao;



	@Override
	public List<Identification> dataGrid(Identification identification,
			PageFilter ph) {
		return null;
	}
	//PV,UV流量统计
	@Override
	public List<Identification> dataGrid(Identification identification) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		List<Identification> ul = new ArrayList<Identification>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
			 Statement stmt = (Statement) conn.createStatement();

		String sql = " select DATE_FORMAT(CAST(a.accesstime AS DATETIME),'%Y-%m-%d') dete,b.name,b.type,count(*) as PV,count(DISTINCT ip) as UV from statistics a left join identification b on a.identification=b.identification where 1=1";
		if(StringUtil.isNotEmpty(identification.getIdentification()+"")){
			sql += " and  a.identification='"+identification.getIdentification()+"'";
		}
		if(StringUtil.isNotEmpty(identification.getCreatedatetimeStart()+"")){
			String date = sdf.format(identification.getCreatedatetimeStart());
			sql += " and unix_timestamp(a.accesstime) >= unix_timestamp('"+date+"')";
		}
		if(StringUtil.isNotEmpty(identification.getCreatedatetimeEnd()+"")){
			String date2 = sdf.format(identification.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.accesstime) <= unix_timestamp('"+date2+"')";
		}
		sql += " group by dete,b.name,a.identification,b.type";
		ResultSet rs = stmt.executeQuery(sql);
		 while (rs.next()) {
             Identification  i=new Identification();
             i.setDete(rs.getString(1)==null?"":rs.getString(1).toString());
             i.setName(rs.getString(2)==null?"":rs.getString(2).toString());
             i.setType(rs.getString(3)==null?"":rs.getString(3).toString());
             i.setPv(rs.getString(4)==null?"":rs.getString(4).toString());
             i.setUv(rs.getString(5)==null?"":rs.getString(5).toString());
             ul.add(i);
         }
		} catch (SQLException e) {
			logger.info("MySQL操作错误");
	            e.printStackTrace();
		}
		return ul;
	}

	@Override
	public List<Identification> activity(Identification identification) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		List<Identification> ul = new ArrayList<Identification>();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
			 Statement stmt = (Statement) conn.createStatement();

		String sql = " select DATE_FORMAT(CAST(a.accesstime AS DATETIME),'%Y-%m-%d') dete,a.userid,b.name,count(*) as PV,count(DISTINCT ip) as UV from statistics a left join identification b on a.identification=b.identification where b.typestate=1";
		if(StringUtil.isNotEmpty(identification.getIdentification()+"")){
			sql += " and  a.identification='"+identification.getIdentification()+"'";
		}
		if(StringUtil.isNotEmpty(identification.getCreatedatetimeStart()+"")){
			String date = sdf.format(identification.getCreatedatetimeStart());
			sql += " and unix_timestamp(a.accesstime) >= unix_timestamp('"+date+"')";
		}
		if(StringUtil.isNotEmpty(identification.getCreatedatetimeEnd()+"")){
			String date2 = sdf.format(identification.getCreatedatetimeEnd());
			sql += " and unix_timestamp(a.accesstime) <= unix_timestamp('"+date2+"')";
		}
		sql += " group by dete,a.userid,b.name";
		ResultSet rs = stmt.executeQuery(sql);
		 while (rs.next()) {
             Identification  i=new Identification();
             i.setDete(rs.getString(1).toString());
             i.setUserid(rs.getString(2).toString());
             i.setName(rs.getString(3).toString());
             i.setPv(rs.getString(4).toString());
             i.setUv(rs.getString(5).toString());
             ul.add(i);
         }
		} catch (SQLException e) {
			logger.info("MySQL操作错误");
	            e.printStackTrace();
		}
		return ul;
	}

	private String orderHql(PageFilter ph) {
		String orderString = "";
		if ((ph.getSort() != null) && (ph.getOrder() != null)) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	public Identification get(long id) {
		Tidentification t = identificationDao.get(Tidentification.class, id);
		Identification r = new Identification();
		BeanUtils.copyProperties(t, r);
		return r;
	}
	@Override
	public Long count(Identification identification) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tstatistics t ";
		return identificationDao.count("select count(*) " + hql , params);
	}

	@Override
	public Long count(Identification identification, PageFilter ph) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tstatistics t ";
		return identificationDao.count("select count(*) " + hql , params);
	}

	  public static Connection getConnection(String db_url,String db_userName,String db_passWord) {
	        Connection connection = null;
	        //1.加载驱动
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (ClassNotFoundException e) { 
	            e.printStackTrace();
	            return null;
	        }
	        //2.获得数据库连接
	        try {
	            connection = (Connection) DriverManager.getConnection(db_url, db_userName, db_passWord);
	        } catch (SQLException e) {
	            e.printStackTrace();;
	            return null;
	        }
	        return connection;
	    }
		@Override
		public List<Identification> name_combox() {
			Connection conn;
			List<Identification> ul = new ArrayList<Identification>();
			try {
				conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
		
			 Statement stmt = (Statement) conn.createStatement();
				Map<String, Object> params = new HashMap<String, Object>();
				String sql = "select  concat(name,type) neme,identification  from identification ";
				
					
				ResultSet rs = stmt.executeQuery(sql);
				 while (rs.next()) {
		             Identification  i=new Identification();
		             i.setNeme(rs.getString(1).toString());
		             i.setIdentification(rs.getString(2).toString());
		             ul.add(i);
		         }
			} catch (SQLException e) {
				e.printStackTrace();
			}
				return ul;
			}
		//用户详细数据
		@Override
		public List<Statistics> dataGrid(Statistics statistics) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			List<Statistics> ul = new ArrayList<Statistics>();
			Map<String, Object> params = new HashMap<String, Object>();
			try {
				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
				 Statement stmt = (Statement) conn.createStatement();

			String sql = " SELECT DISTINCT(ip),accesstime,userid,phone FROM statistics where 1=1";
			if(StringUtil.isNotEmpty(statistics.getIdentification()+"")){
				sql += " and  identification='"+statistics.getIdentification()+"'";
			}
//			if(StringUtil.isNotEmpty(statistics.getChannel()+"")){
//				sql += " and  channel like '%"+statistics.getChannel()+"%'";
//			}
			if(StringUtil.isNotEmpty(statistics.getCreatedatetimeStart()+"")){
				String date = sdf.format(statistics.getCreatedatetimeStart());
				sql += " and unix_timestamp(accesstime) >= unix_timestamp('"+date+"')";
			}
			if(StringUtil.isNotEmpty(statistics.getCreatedatetimeEnd()+"")){
				String date2 = sdf.format(statistics.getCreatedatetimeEnd());
				sql += " and unix_timestamp(accesstime) <= unix_timestamp('"+date2+"')";
			}
			sql += " group by ip desc order by accesstime";

			ResultSet rs = stmt.executeQuery(sql);
			 while (rs.next()) {
				 Statistics  i=new Statistics();
	             i.setIp(rs.getString(1).toString());
	             i.setAccesstime(rs.getString(2).toString());
	             if(rs.getString(3)==null||rs.getString(3)==""){
	            	 continue;
	             }else{
	             String userid=rs.getString(3).toString();
	             String[] userinfo=new String[20];
	             userinfo=userid.split(",");
	             for(int t=0;t<userinfo.length;t++){
	            	 i.setName(userinfo[0]);
//	            	 i.setEname(userinfo[1]);
//	            	 i.setEmail(userinfo[2]);
//	            	 i.setSchool(userinfo[3]);
//	            	 i.setPhone(userinfo[4]);
//	            	 i.setMajor(userinfo[5]);
	             }
	             }
	             ul.add(i);
	         }
			} catch (SQLException e) {
				logger.info("MySQL操作错误");
		            e.printStackTrace();
			}
			return ul;
		}
		//渠道001
		@Override
		public List<Statistics> listone(Statistics statistics) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			List<Statistics> ul = new ArrayList<Statistics>();
			Map<String, Object> params = new HashMap<String, Object>();
			try {
				Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
				 Statement stmt = (Statement) conn.createStatement();

			String sql = " SELECT DISTINCT(ip),accesstime,userid,phone FROM statistics where 1=1";
			if(StringUtil.isNotEmpty(statistics.getIdentification()+"")){
				sql += " and  identification='"+statistics.getIdentification()+"'";
			}
//			if(StringUtil.isNotEmpty(statistics.getChannel()+"")){
//				sql += " and  channel like '%"+statistics.getChannel()+"%'";
//			}
			if(StringUtil.isNotEmpty(statistics.getCreatedatetimeStart()+"")){
				String date = sdf.format(statistics.getCreatedatetimeStart());
				sql += " and unix_timestamp(accesstime) >= unix_timestamp('"+date+"')";
			}
			if(StringUtil.isNotEmpty(statistics.getCreatedatetimeEnd()+"")){
				String date2 = sdf.format(statistics.getCreatedatetimeEnd());
				sql += " and unix_timestamp(accesstime) <= unix_timestamp('"+date2+"')";
			}
			sql += " and  userid like '%,001%' group by ip desc order by accesstime";

			ResultSet rs = stmt.executeQuery(sql);
			 while (rs.next()) {
				 Statistics  i=new Statistics();
	             i.setIp(rs.getString(1).toString());
	             i.setAccesstime(rs.getString(2).toString());
	             if(rs.getString(3)==null||rs.getString(3)==""){
	            	 continue;
	             }else{
	             String userid=rs.getString(3).toString();
	             String[] userinfo=new String[20];
	             userinfo=userid.split(",");
	             for(int t=0;t<userinfo.length;t++){
	            	 i.setName(userinfo[0]==null?"":userinfo[0].toString());
	            	 i.setEname(userinfo[1]==null?"":userinfo[1].toString());
	            	 i.setEmail(userinfo[2]==null?"":userinfo[2].toString());
	            	 i.setSchool(userinfo[3]==null?"":userinfo[3].toString());
	            	 i.setPhone(userinfo[4]==null?"":userinfo[4].toString());
	            	 i.setMajor(userinfo[5]==null?"":userinfo[5].toString());
	             }
	             }
	             ul.add(i);
	         }
			} catch (SQLException e) {
				logger.info("MySQL操作错误");
		            e.printStackTrace();
			}
			return ul;
		}
		
		//渠道002
				@Override
				public List<Statistics> listtwo(Statistics statistics) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
					List<Statistics> ul = new ArrayList<Statistics>();
					Map<String, Object> params = new HashMap<String, Object>();
					try {
						Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.10.8:3306/count?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", "count", "G$D)t2Hs3#4&");
						 Statement stmt = (Statement) conn.createStatement();

					String sql = " SELECT DISTINCT(ip),accesstime,userid,phone FROM statistics where 1=1";
					if(StringUtil.isNotEmpty(statistics.getIdentification()+"")){
						sql += " and  identification='"+statistics.getIdentification()+"'";
					}
//					if(StringUtil.isNotEmpty(statistics.getChannel()+"")){
//						sql += " and  channel like '%"+statistics.getChannel()+"%'";
//					}
					if(StringUtil.isNotEmpty(statistics.getCreatedatetimeStart()+"")){
						String date = sdf.format(statistics.getCreatedatetimeStart());
						sql += " and unix_timestamp(accesstime) >= unix_timestamp('"+date+"')";
					}
					if(StringUtil.isNotEmpty(statistics.getCreatedatetimeEnd()+"")){
						String date2 = sdf.format(statistics.getCreatedatetimeEnd());
						sql += " and unix_timestamp(accesstime) <= unix_timestamp('"+date2+"')";
					}
					sql += " and  userid like '%,002%' group by ip desc order by accesstime";

					ResultSet rs = stmt.executeQuery(sql);
					 while (rs.next()) {
						 Statistics  i=new Statistics();
			             i.setIp(rs.getString(1).toString());
			             i.setAccesstime(rs.getString(2).toString());
			             if(rs.getString(3)==null||rs.getString(3)==""){
			            	 continue;
			             }else{
			             String userid=rs.getString(3).toString();
			             String[] userinfo=new String[20];
			             userinfo=userid.split(",");
			             for(int t=0;t<userinfo.length;t++){
			            	 i.setName(userinfo[0]==null?"":userinfo[0].toString());
			            	 i.setEname(userinfo[1]==null?"":userinfo[1].toString());
			            	 i.setEmail(userinfo[2]==null?"":userinfo[2].toString());
			            	 i.setSchool(userinfo[3]==null?"":userinfo[3].toString());
			            	 i.setPhone(userinfo[4]==null?"":userinfo[4].toString());
			            	 i.setMajor(userinfo[5]==null?"":userinfo[5].toString());
			             }
			             }
			             ul.add(i);
			         }
					} catch (SQLException e) {
						logger.info("MySQL操作错误");
				            e.printStackTrace();
					}
					return ul;
				}
		@Override
		public Long count(Statistics statistics) {
			Map<String, Object> params = new HashMap<String, Object>();
			String hql = " from Tstatistics t ";
			return statisticsDao.count("select count(*) " + hql , params);
		}

}



