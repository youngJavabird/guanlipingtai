package light.mvc.pageModel.jjacount;

import java.util.Date;

public class Jjacount implements java.io.Serializable{

	private static final long serialVersionUID = 6159615613952892233L;
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
