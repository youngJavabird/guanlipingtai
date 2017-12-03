package light.mvc.model.activity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import light.mvc.model.base.IdEntity;

@Entity
@Table(name = "wnf_activity")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tactivity extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private int type_id;
	private String name;
	private String detile;
	private String picture;
	private String stime;
	//
	private String etime;
	private Date createtime;
	private String state;
	private int score;
	private String remark;
	private String herf;
	private String activitycode;
	private int cardtypeid;
	private int cardnum;
	private int inspect;
	private String reason;
	private int actype;
	private int seq_id;
	private int ishot;
	private int liveness;
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetile() {
		return detile;
	}
	public void setDetile(String detile) {
		this.detile = detile;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getState() {

		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHerf() {
		return herf;
	}
	public void setHerf(String herf) {
		this.herf = herf;
	}
	public String getActivitycode() {
		return activitycode;
	}
	public void setActivitycode(String activitycode) {
		this.activitycode = activitycode;
	}
	public int getCardtypeid() {
		return cardtypeid;
	}
	public void setCardtypeid(int cardtypeid) {
		this.cardtypeid = cardtypeid;
	}
	public int getCardnum() {
		return cardnum;
	}
	public void setCardnum(int cardnum) {
		this.cardnum = cardnum;
	}
	public int getInspect() {
		return inspect;
	}
	public void setInspect(int inspect) {
		this.inspect = inspect;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getActype() {
		return actype;
	}
	public void setActype(int actype) {
		this.actype = actype;
	}
	public int getSeq_id() {
		return seq_id;
	}
	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}
	public int getIshot() {
		return ishot;
	}
	public void setIshot(int ishot) {
		this.ishot = ishot;
	}
	public int getLiveness() {
		return liveness;
	}
	public void setLiveness(int liveness) {
		this.liveness = liveness;
	}

	
}
