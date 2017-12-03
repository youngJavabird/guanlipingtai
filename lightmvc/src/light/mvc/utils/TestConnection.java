package light.mvc.utils;

import java.sql.ResultSet;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  

public class TestConnection {
	public static String SelectUser(String merno){  
		  //mysql
        //设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
        String driverName="com.mysql.jdbc.Driver";  
        String url="jdbc:mysql://192.168.10.8:3306:";  //test为数据库名称，1521为连接数据库的默认端口  
        String user="count";   //用户名  
        String password="G$D)t2Hs3#4&";  //密码  


      String key="";
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
          
        //数据库连接对象  
        Connection conn = null;  
          
        try {  
            //反射Oracle数据库驱动程序类  
            Class.forName(driverName);  
              
            //获取数据库连接  
            conn = DriverManager.getConnection(url, user, password);  
              
            //输出数据库连接  
            System.out.println(conn);  
              
            //定制sql命令  
            String sql = "select * from ROUTE_FORWARD_CONFIG where MERCH_NO = ?";  
              
            //创建该连接下的PreparedStatement对象  
            pstmt = conn.prepareStatement(sql);  
              
            //传递第一个参数值 root，代替第一个问号  
            pstmt.setString(1, merno);  
              
            //执行查询语句，将数据保存到ResultSet对象中  
            rs = pstmt.executeQuery();  
            //LogUtil.info("查询结果为:"+rs.toString()+".");
            //将指针移到下一行，判断rs中是否有数据  
            if(rs.next()){  
                //输出查询结果  
            	 key=rs.getString("SHA512KEY");
            	 System.out.println("密钥为:"+key);
             //   System.out.println("查询到名为【" + rs.getString("MERCH_NO") + "】的信息，其密码为：" + rs.getString("SHA512KEY"));  
            }else{  
            	key="";
                //输出查询结果  
            //    System.out.println("未查询到用户名为【" + rs.getString("MERCH_NO") + "】的信息");  
            }  
              
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }finally{  
            try{  
                if(rs != null){  
                    rs.close();  
                }  
                if(pstmt != null){  
                    pstmt.close();  
                }  
                if(conn != null){  
                    conn.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }     
        }  
      return key;
    }  
      
    public static void main(String[] args){  
    }  
}
