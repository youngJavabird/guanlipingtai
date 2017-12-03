package light.mvc.model.smstemp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_sms_templet")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tsmstemp extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private int activity_id;
	private String smstemplet;
	private Date createtime;
	private Date upddate;
	private String transtype;
	private String Remark;
	private int msgid;
	public int getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public String getSmstemplet() {
		return smstemplet;
	}
	public void setSmstemplet(String smstemplet) {
		this.smstemplet = smstemplet;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	
}
