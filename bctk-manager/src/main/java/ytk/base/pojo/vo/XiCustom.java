package ytk.base.pojo.vo;

import ytk.base.pojo.po.Xi;
import ytk.util.DateUtil;

public class XiCustom extends Xi{
	private String createtimeMinView;
	private String createtimeMaxView;
	private Long createtimeMin;
	private Long createtimeMax;
	public String getCreatetimeMinView() {
		return createtimeMinView;
	}
	public void setCreatetimeMinView(String createtimeMinView) {
		if(createtimeMinView!=null&&!createtimeMinView.equals("")){
			this.createtimeMinView = createtimeMinView;
			this.createtimeMin = DateUtil.parseDate(createtimeMinView).getTime();
		}

	}
	public String getCreatetimeMaxView() {
		return createtimeMaxView;
	}
	public void setCreatetimeMaxView(String createtimeMaxView) {
		if(createtimeMaxView!=null&&!createtimeMaxView.equals("")){
			this.createtimeMaxView = createtimeMaxView;
			this.createtimeMax = DateUtil.parseDate(createtimeMaxView).getTime();
		}

	}

	public Long getCreatetimeMin() {
		return createtimeMin;
	}
	public void setCreatetimeMin(Long createtimeMin) {
		this.createtimeMin = createtimeMin;
	}
	public Long getCreatetimeMax() {
		return createtimeMax;
	}
	public void setCreatetimeMax(Long createtimeMax) {
		this.createtimeMax = createtimeMax;
	}
	
}
