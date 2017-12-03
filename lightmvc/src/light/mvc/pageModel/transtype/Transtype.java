package light.mvc.pageModel.transtype;

public class Transtype implements java.io.Serializable {
	private static final long serialVersionUID = -570098299191874913L;
	private Long id;
	private String transtype;
	private String transtypename;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getTranstypename() {
		return transtypename;
	}
	public void setTranstypename(String transtypename) {
		this.transtypename = transtypename;
	}
	
}
