package light.mvc.pageModel.base;

import java.io.Serializable;

public class VinfoBean implements Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private String activeDate;
	private String activeMer;
	private String activeMerName;
	private String amount;
	private String expiredDate;
	private String seqNo;
	private String status;
	private String typeId;
	private String useMer;
	private String useMerName;
	private String useTime;
	private String voucherNo;
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getActiveMer() {
		return activeMer;
	}
	public void setActiveMer(String activeMer) {
		this.activeMer = activeMer;
	}
	public String getActiveMerName() {
		return activeMerName;
	}
	public void setActiveMerName(String activeMerName) {
		this.activeMerName = activeMerName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getUseMer() {
		return useMer;
	}
	public void setUseMer(String useMer) {
		this.useMer = useMer;
	}
	public String getUseMerName() {
		return useMerName;
	}
	public void setUseMerName(String useMerName) {
		this.useMerName = useMerName;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
}
