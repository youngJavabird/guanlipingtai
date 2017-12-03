package light.mvc.pageModel.refundrecord;

import java.util.Date;

public class Refundrecord  implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private long id;
	private String refundid;
	private String orderid;
	private String rebate;
	private String fee;
	private String state;
	private String remark;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRefundid() {
		return refundid;
	}
	public void setRefundid(String refundid) {
		this.refundid = refundid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
