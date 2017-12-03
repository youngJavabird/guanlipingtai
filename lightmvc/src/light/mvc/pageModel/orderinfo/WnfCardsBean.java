package light.mvc.pageModel.orderinfo;

public class WnfCardsBean implements java.io.Serializable {
	private static final long serialVersionUID = 6159615613952892233L;
	private int id;
	private String card_name;//卡券商家
	private String card_picture;//卡券图片
	private String card_password;//卡券密码
	private String card_type;//卡券类型
	private String card_colour;//卡券颜色
	private String card_bar_code;//卡券条码
	private String card_explain;//卡券说明
	private String card_price;//卡券金额
	private String card_code;//卡券编号
	private String card_endtime;//卡券截止时间
	private String card_service_condition_one;//使用说明一
	private String card_service_condition_two;//使用说明二
	private String card_offset_description;//抵消说明
	private String card_detile;//卡券详细说明
	private int state;//状态
	private String createdate;//创建时间
	private String remark;//备注
	private String card_userid;//操作员代码
	private String card_effecticeday;//有效天数
	private String card_seqno;//起始券号
	private String card_typeid;//状态编号
	private String type_id;
	
	/*------退货需要的字段-----*/
	private String totalamount;
	private String cardnum;
	private String cardtypeid;
	private String usercardstate;//用户卡券使用状态
	
	private String guid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCard_picture() {
		return card_picture;
	}

	public void setCard_picture(String card_picture) {
		this.card_picture = card_picture;
	}

	public String getCard_password() {
		return card_password;
	}

	public void setCard_password(String card_password) {
		this.card_password = card_password;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_colour() {
		return card_colour;
	}

	public void setCard_colour(String card_colour) {
		this.card_colour = card_colour;
	}

	public String getCard_bar_code() {
		return card_bar_code;
	}

	public void setCard_bar_code(String card_bar_code) {
		this.card_bar_code = card_bar_code;
	}

	public String getCard_explain() {
		return card_explain;
	}

	public void setCard_explain(String card_explain) {
		this.card_explain = card_explain;
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

	public String getCard_endtime() {
		return card_endtime;
	}

	public void setCard_endtime(String card_endtime) {
		this.card_endtime = card_endtime;
	}

	public String getCard_service_condition_one() {
		return card_service_condition_one;
	}

	public void setCard_service_condition_one(String card_service_condition_one) {
		this.card_service_condition_one = card_service_condition_one;
	}

	public String getCard_service_condition_two() {
		return card_service_condition_two;
	}

	public void setCard_service_condition_two(String card_service_condition_two) {
		this.card_service_condition_two = card_service_condition_two;
	}

	public String getCard_offset_description() {
		return card_offset_description;
	}

	public void setCard_offset_description(String card_offset_description) {
		this.card_offset_description = card_offset_description;
	}

	public String getCard_detile() {
		return card_detile;
	}

	public void setCard_detile(String card_detile) {
		this.card_detile = card_detile;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCard_userid() {
		return card_userid;
	}

	public void setCard_userid(String card_userid) {
		this.card_userid = card_userid;
	}

	public String getCard_effecticeday() {
		return card_effecticeday;
	}

	public void setCard_effecticeday(String card_effecticeday) {
		this.card_effecticeday = card_effecticeday;
	}

	public String getCard_seqno() {
		return card_seqno;
	}

	public void setCard_seqno(String card_seqno) {
		this.card_seqno = card_seqno;
	}

	public String getCard_typeid() {
		return card_typeid;
	}

	public void setCard_typeid(String card_typeid) {
		this.card_typeid = card_typeid;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getCardtypeid() {
		return cardtypeid;
	}

	public void setCardtypeid(String cardtypeid) {
		this.cardtypeid = cardtypeid;
	}

	public String getUsercardstate() {
		return usercardstate;
	}

	public void setUsercardstate(String usercardstate) {
		this.usercardstate = usercardstate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
