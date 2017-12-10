package ytk.base.pojo.vo;

import java.util.List;

import ytk.base.pojo.po.Student;

public class StudentCustom extends Student{
	private String classUuid;
	private String className;
	private Long xiUuid;
	private String xiName;
	private Long zyUuid;
	private String zyName;
	private Long njUuid;
	private String njName;
	
	private String ksglUuid;

	private Menu menu;
	private List<Operation> operationList;

	public Long getXiUuid() {
		return xiUuid;
	}

	public void setXiUuid(Long xiUuid) {
		this.xiUuid = xiUuid;
	}

	public String getKsglUuid() {
		return ksglUuid;
	}

	public void setKsglUuid(String ksglUuid) {
		this.ksglUuid = ksglUuid;
	}

	public String getClassUuid() {
		return classUuid;
	}

	public void setClassUuid(String classUuid) {
		this.classUuid = classUuid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getXiName() {
		return xiName;
	}

	public void setXiName(String xiName) {
		this.xiName = xiName;
	}

	public Long getZyUuid() {
		return zyUuid;
	}

	public void setZyUuid(Long zyUuid) {
		this.zyUuid = zyUuid;
	}

	public String getZyName() {
		return zyName;
	}

	public void setZyName(String zyName) {
		this.zyName = zyName;
	}

	public Long getNjUuid() {
		return njUuid;
	}

	public void setNjUuid(Long njUuid) {
		this.njUuid = njUuid;
	}

	public String getNjName() {
		return njName;
	}

	public void setNjName(String njName) {
		this.njName = njName;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}
	
}
