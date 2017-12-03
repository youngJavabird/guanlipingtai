package light.mvc.pageModel.balance;

import java.util.Date;

/**
 * 卡券
 * @author lianss
 *
 */
public class Balance implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private String userid;
	private String phone;
	private String balance;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}

	
	
}