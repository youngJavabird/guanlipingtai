package light.mvc.pageModel.starwin;

import java.util.Date;

public class Starwin implements java.io.Serializable{

	private static final long serialVersionUID = 6159615613952892233L;

	private Long id;
	private String phone;
	private String name;
	private String passwd;
	private String state;
	private Date createdate;	
	private  Date createdatetimeStart;
	private Date createdatetimeEnd;
    private String click_download;
	
	public String getClick_download() {
		return click_download;
	}
	public void setClick_download(String click_download) {
		this.click_download = click_download;
	}
	
	public Date getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(Date createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public Date getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(Date createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	
}
