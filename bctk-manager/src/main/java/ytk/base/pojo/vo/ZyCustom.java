package ytk.base.pojo.vo;

import ytk.base.pojo.po.Zy;
import ytk.util.DateUtil;

public class ZyCustom extends Zy{
	private Long createimeMin;
	private Long createimeMax;
	private String createimeMinView;
	private String createimeMaxView;
	private String xiname;
	private String q;
	
	private String createimeView;
	
	public Long getCreateimeMin() {
		return createimeMin;
	}
	public void setCreateimeMin(Long createimeMin) {
		this.createimeMin = createimeMin;
	}
	public Long getCreateimeMax() {
		return createimeMax;
	}
	public void setCreateimeMax(Long createimeMax) {
		this.createimeMax = createimeMax;
	}
	public String getXiname() {
		return xiname;
	}
	public void setXiname(String xiname) {
		this.xiname = xiname;
	}
	public String getCreateimeView() {
		return createimeView;
	}
	
	@Override
	public void setCreateime(Long createime) {
		super.setCreateime(createime);
		this.createimeView=DateUtil.formatDate(createime);
	}
	
	public String getCreateimeMinView() {
		return createimeMinView;
	}
	public void setCreateimeMinView(String createimeMinView) {
		if(createimeMinView!=null&&!createimeMinView.equals("")){
			this.createimeMinView = createimeMinView;
			this.createimeMin = DateUtil.parseDate(createimeMinView).getTime();
		}

	}
	public String getCreateimeMaxView() {
		return createimeMaxView;
	}
	public void setCreateimeMaxView(String createimeMaxView) {
		if(createimeMaxView!=null&&!createimeMaxView.equals("")){
			this.createimeMaxView = createimeMaxView;
			this.createimeMax = DateUtil.parseDate(createimeMaxView).getTime();
		}

	}
	
	
	
	public void setCreateimeView(String createimeView) {
		if(createimeView!=null&&!createimeView.equals("")){
			this.createimeView = createimeView;
			setCreateime(DateUtil.parseDate(createimeView).getTime());
		}
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	
	
	
}
