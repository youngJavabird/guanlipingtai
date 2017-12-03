package light.mvc.pageModel.jtrefund;

import java.util.Date;

public class Jtrefund  implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private String id;
	private String openid;
	private String moneyaccount;
	private String money;
	private String refundexplain;
	private String phone;
	private String name;
	private String reason;
	private String state;
	private String createdate;
	private String remark;
	private  Date createdatetimeStart;
	private Date createdatetimeEnd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getMoneyaccount() {
		return moneyaccount;
	}
	public void setMoneyaccount(String moneyaccount) {
		this.moneyaccount = moneyaccount;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getRefundexplain() {
		return refundexplain;
	}
	public void setRefundexplain(String refundexplain) {
		this.refundexplain = refundexplain;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}
	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}
	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}
	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}
	
	
}
