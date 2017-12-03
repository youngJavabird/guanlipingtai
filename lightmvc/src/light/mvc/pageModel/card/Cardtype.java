package light.mvc.pageModel.card;

import java.util.Date;

public class Cardtype implements java.io.Serializable {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	
	private Long id;
	
	
	
	private String typename;//类型名称
	
	

	private String type ;//类型编号
	
	private String remark;//
	
	private String num;
	
	private String price;//金额
	
	private String total; //总金额
	
	public Cardtype() {
		super();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	/*private String typename;//类型名称
	
	private String type ;//类型编号
	
	private String remark;//
	
	

	private String total; //总金额

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}*/

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	
	
	
}