package light.mvc.pageModel.orderinfo;

import java.util.Date;

public class Orderinfo implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private String orderid;//订单号
	private String from_user_name;//openid
	private String phone;//手机号
	private String name;//收货联系人
	private String address;//详细地址
	private String provinces;//省市
	private String area;//区域
	private int zipcode;//邮编
	private String product_id;//商品id
	private int num;//数量
	private String price;//总价
	private String state;//状态
	private Date createdate;//创建时间
	private String remark;//备注
	private String totalamount;
	private String logistics;
	private String logisticsnum;
	private Date upddate;
	private String usertype;
	private String refundamount;
	private int cardnum;
	private String remoney;
	private String refee;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getFrom_user_name() {
		return from_user_name;
	}
	public void setFrom_user_name(String from_user_name) {
		this.from_user_name = from_user_name;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvinces() {
		return provinces;
	}
	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getLogisticsnum() {
		return logisticsnum;
	}
	public void setLogisticsnum(String logisticsnum) {
		this.logisticsnum = logisticsnum;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getRefundamount() {
		return refundamount;
	}
	public void setRefundamount(String refundamount) {
		this.refundamount = refundamount;
	}
	public int getCardnum() {
		return cardnum;
	}
	public void setCardnum(int cardnum) {
		this.cardnum = cardnum;
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
	
}
