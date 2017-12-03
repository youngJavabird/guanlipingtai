package light.mvc.pageModel.smstemp;

import java.util.Date;

public class Smstemp implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private int activity_id;
	private String smstemplet;
	private Date createtime;
	private Date upddate;
	private String transtype;
	private String Remark;
	private int msgid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
