package light.mvc.pageModel.orderinfo;

public class OrderinfoView implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private Long id;
	private String orderid;//订单号
	private String from_user_name;//openid
	private String phone;//手机号
	private String name;//收货联系人
	private String address;//详细地址
	private String provinces;//省市
	private String area;//区域
	private String zipcode;//邮编
	private String pid;//商品id
	private String num;//数量
	private String state;//状态
	private String createdate;//创建时间
	private String totalamount;
	private String logistics;
	private String logisticsnum;
	private String proname;//商品名称
	private String detile;
	private int product_type_id;//商品分类ID
	private String longpicture;//长图
	private String translate;//分类
	private String new_price;//价格
	private String price;//原价
	private String pronum;//库存数量
	private Integer cardtype;
	
	private String paytime;//付款时间
	private String detail;//产品名称
	private String typename;//品牌
	private String fildename;//规格
	private String suppliername;//供应商名称
	private String supplierprice;//供货价
	private String countprice;//平台订单总额
	private String remark;//券码品牌  DQ
	private String card_name;//券码名称
	private String card_price;//券码价格
	private String card_code;//券号
	private String card_password;//券码密码
	private String card_endtime;//券码有效期
	private String username;//姓名
	private String upddate;
	private String orderstate;//订单状态
	private String cardstate;//卡券状态
	private String card_service_condition_one;
	private String isinvoice;
	private String invoicename;
	private String filed;
	private String usertype;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getDetile() {
		return detile;
	}
	public void setDetile(String detile) {
		this.detile = detile;
	}
	public int getProduct_type_id() {
		return product_type_id;
	}
	public void setProduct_type_id(int product_type_id) {
		this.product_type_id = product_type_id;
	}
	public String getLongpicture() {
		return longpicture;
	}
	public void setLongpicture(String longpicture) {
		this.longpicture = longpicture;
	}
	public String getTranslate() {
		return translate;
	}
	public void setTranslate(String translate) {
		this.translate = translate;
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
	public String getPronum() {
		return pronum;
	}
	public void setPronum(String pronum) {
		this.pronum = pronum;
	}
	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getFildename() {
		return fildename;
	}
	public void setFildename(String fildename) {
		this.fildename = fildename;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSupplierprice() {
		return supplierprice;
	}
	public void setSupplierprice(String supplierprice) {
		this.supplierprice = supplierprice;
	}
	public String getCountprice() {
		return countprice;
	}
	public void setCountprice(String countprice) {
		this.countprice = countprice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_price() {
		return card_price;
	}
	public void setCard_price(String card_price) {
		this.card_price = card_price;
	}
	public String getCard_code() {
		return card_code;
	}
	public void setCard_code(String card_code) {
		this.card_code = card_code;
	}
	public String getCard_password() {
		return card_password;
	}
	public void setCard_password(String card_password) {
		this.card_password = card_password;
	}
	public String getCard_endtime() {
		return card_endtime;
	}
	public void setCard_endtime(String card_endtime) {
		this.card_endtime = card_endtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUpddate() {
		return upddate;
	}
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
	public String getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}
	public String getCardstate() {
		return cardstate;
	}
	public void setCardstate(String cardstate) {
		this.cardstate = cardstate;
	}
	public String getCard_service_condition_one() {
		return card_service_condition_one;
	}
	public void setCard_service_condition_one(String card_service_condition_one) {
		this.card_service_condition_one = card_service_condition_one;
	}
	public String getIsinvoice() {
		return isinvoice;
	}
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}
	public String getInvoicename() {
		return invoicename;
	}
	public void setInvoicename(String invoicename) {
		this.invoicename = invoicename;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
