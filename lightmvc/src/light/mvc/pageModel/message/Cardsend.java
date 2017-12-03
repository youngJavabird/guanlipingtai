package light.mvc.pageModel.message;

import java.util.Date;

public class Cardsend implements java.io.Serializable{

	private static final long serialVersionUID = 6159615613952892233L;

	
	private String id;
	
	private String usercard_id;
	
	private String from_user_name;
	
	private String state;
	
	private String type_id;
	
	private String createdate;
	
	private String upddate;
	
	private String remark;
	
	private String orderid;
	
	private String activeid;
	
	private String user_guid;
	
	private String user_phone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsercard_id() {
		return usercard_id;
	}

	public void setUsercard_id(String usercard_id) {
		this.usercard_id = usercard_id;
	}

	public String getFrom_user_name() {
		return from_user_name;
	}

	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getUpddate() {
		return upddate;
	}

	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getActiveid() {
		return activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

	public String getUser_guid() {
		return user_guid;
	}

	public void setUser_guid(String user_guid) {
		this.user_guid = user_guid;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

}
