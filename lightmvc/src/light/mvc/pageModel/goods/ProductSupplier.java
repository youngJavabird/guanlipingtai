package light.mvc.pageModel.goods;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import light.mvc.model.sys.Torganization;

public class ProductSupplier implements java.io.Serializable{
	private Long id;
	private String suppliername;//供货商名称
	private String sendaddress;
	private String recvaddress;
	private String linkman; 
	private String phone;
	private Date createdate;
	private Date upddate;
	private String guid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSendaddress() {
		return sendaddress;
	}
	public void setSendaddress(String sendaddress) {
		this.sendaddress = sendaddress;
	}
	public String getRecvaddress() {
		return recvaddress;
	}
	public void setRecvaddress(String recvaddress) {
		this.recvaddress = recvaddress;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}