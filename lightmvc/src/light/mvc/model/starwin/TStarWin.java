package light.mvc.model.starwin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import light.mvc.model.base.IdEntity;

@Entity
@Table(name = "wnf_xhaccount")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TStarWin  extends IdEntity implements java.io.Serializable{

	private String phone;
	private String name;
	private String passwd;
	private String state;
	private Date createdate;
	private String click_download;
	
	public String getClick_download() {
		return click_download;
	}
	public void setClick_download(String click_download) {
		this.click_download = click_download;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
}
