package light.mvc.model.balance;

import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_jt_account")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tbalance extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = -570098299191874913L;
	private String userid;
	private String phone;
	private String balance;
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
