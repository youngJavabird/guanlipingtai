package light.mvc.model.jjacount;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;
import light.mvc.model.base.IdEntity;

@Entity
@Table(name = "wnf_jjacount")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TJjacount extends IdEntity implements java.io.Serializable{
	private String guid;
	private String jjaccount;
	private String state;
	private Date createdate;
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getJjaccount() {
		return jjaccount;
	}
	public void setJjaccount(String jjaccount) {
		this.jjaccount = jjaccount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	

}
