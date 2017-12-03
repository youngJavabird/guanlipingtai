package light.mvc.model.goods;

import javax.persistence.Entity;

import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import light.mvc.model.base.IdEntity;
/**
 * 商品实体映射商品表类
 * @author hanyi
 *
 */
@Entity
@Table(name = "wnf_product")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tproduct extends IdEntity implements java.io.Serializable{
	private String name;//商品名称
	private String detile;//商品描述
	private String product_type_id;
	private String pictureone;//图片名称1
	private String picturetwo;//图片名称2
	private String picturethree;//图片名称3
	private String longpicture;//长图
	private String new_price;//价格
	private String price;//原价
	private String num;//库存数量
	private String start_time;//开始时间
	private String end_time;//结束时间
	private String createdate;//创建时间
	private int state;//状态
	private String remark;//备注
	private int cardnum;//卡券数量
	private String pictureshop;//商品小图
	private int cardtypeid;//卡券类型id
	private String suppliername;//供应商名称
	private int supplierprice;//供货商价格
	private String guid;
	private String supplierid;//供货商ID
	private int liveness;//活跃度
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



	public String getProduct_type_id() {
		return product_type_id;
	}
	public void setProduct_type_id(String product_type_id) {
		this.product_type_id = product_type_id;
	}
	public String getPictureone() {
		return pictureone;
	}
	public void setPictureone(String pictureone) {
		this.pictureone = pictureone;
	}
	public String getPicturetwo() {
		return picturetwo;
	}
	public void setPicturetwo(String picturetwo) {
		this.picturetwo = picturetwo;
	}
	public String getPicturethree() {
		return picturethree;
	}
	public void setPicturethree(String picturethree) {
		this.picturethree = picturethree;
	}
	public String getLongpicture() {
		return longpicture;
	}
	public void setLongpicture(String longpicture) {
		this.longpicture = longpicture;
	}
	public String getNew_price() {
		return new_price;
	}
	public void setNew_price(String new_price) {
		this.new_price = new_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCardnum() {
		return cardnum;
	}
	public void setCardnum(int cardnum) {
		this.cardnum = cardnum;
	}
	public String getPictureshop() {
		return pictureshop;
	}
	public void setPictureshop(String pictureshop) {
		this.pictureshop = pictureshop;
	}
	public int getCardtypeid() {
		return cardtypeid;
	}
	public void setCardtypeid(int cardtypeid) {
		this.cardtypeid = cardtypeid;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public int getSupplierprice() {
		return supplierprice;
	}
	public void setSupplierprice(int supplierprice) {
		this.supplierprice = supplierprice;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public int getLiveness() {
		return liveness;
	}
	public void setLiveness(int liveness) {
		this.liveness = liveness;
	}


}
