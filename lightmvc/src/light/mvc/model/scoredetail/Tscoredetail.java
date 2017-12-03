package light.mvc.model.scoredetail;

import javax.persistence.Entity;

import java.util.Date;

import javax.persistence.Table;

import light.mvc.model.base.IdEntity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "wnf_scoredetail")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tscoredetail extends IdEntity implements java.io.Serializable{
	private static final long serialVersionUID = -570098299191874913L;
	private String userid;
	private String type;	
	private String balance;
	private String pbalance;
	private Date createdate;
	private String orderid;
	private String p_price;
	private String price;
	private String pro_num;
	private Date update;
	private String state;
	private String purchase;
	private String phone;
	private String guid;
	private String cardtype;
	private String oldstate;
	private String remoney;
	private String refee;
	private String remark2;
	private String typeid;
	private String opera;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPbalance() {
		return pbalance;
	}
	public void setPbalance(String pbalance) {
		this.pbalance = pbalance;
	}

	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getP_price() {
		return p_price;
	}
	public void setP_price(String p_price) {
		this.p_price = p_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPro_num() {
		return pro_num;
	}
	public void setPro_num(String pro_num) {
		this.pro_num = pro_num;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public Date getUpdate() {
		return update;
	}
	public void setUpdate(Date update) {
		this.update = update;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPurchase() {
		return purchase;
	}
	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getOldstate() {
		return oldstate;
	}
	public void setOldstate(String oldstate) {
		this.oldstate = oldstate;
	}
	public String getRemoney() {
		return remoney;
	}
	public void setRemoney(String remoney) {
		this.remoney = remoney;
	}
	public String getRefee() {
		return refee;
	}
	public void setRefee(String refee) {
		this.refee = refee;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getOpera() {
		return opera;
	}
	public void setOpera(String opera) {
		this.opera = opera;
	}
	
}
