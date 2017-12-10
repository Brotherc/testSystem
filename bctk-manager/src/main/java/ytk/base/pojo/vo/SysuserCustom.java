package ytk.base.pojo.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ytk.base.pojo.po.Sysuser;
import ytk.util.DateUtil;

public class SysuserCustom extends Sysuser{
	
	private static final String USERSTATE_NORMAL="1";
	private static final String USERSTATE_STOP="2";
	
	private static final String USERSTATE_NORMALVIEW="正常";
	private static final String USERSTATE_STOPVIEW="暂停";
	
	private static final Integer GENDER_M=1;
	private static final Integer GENDER_W=2;
	
	private static final String GENDER_MVIEW="男";
	private static final String GENDER_WVIEW="女";
	
	public static final Map< Integer, String>genderMap=new HashMap<Integer, String>();
	public static final Map< String, String>userstateMap=new HashMap<String, String>();
	static{
		genderMap.put(GENDER_M, GENDER_MVIEW);
		genderMap.put(GENDER_W, GENDER_WVIEW);
		userstateMap.put(USERSTATE_NORMAL, USERSTATE_NORMALVIEW);
		userstateMap.put(USERSTATE_STOP, USERSTATE_STOPVIEW);
	}
	
	private String xiName;
	private Long birthdayMax;
	private Long birthdayMin;
	
	private String birthdayView;
	private String genderView;
	private String userstateView;
	private String birthdayMinView;
	private String birthdayMaxView;
	
	private Menu menu;
	private List<Operation> operationList;
	private String roleId;
	private String roleName;

	private List<Role> roleList;

	public String getXiName() {
		return xiName;
	}
	public void setXiName(String xiName) {
		this.xiName = xiName;
	}
	public Long getBirthdayMax() {
		return birthdayMax;
	}
	public void setBirthdayMax(Long birthdayMax) {
		this.birthdayMax = birthdayMax;
		this.birthdayMaxView=DateUtil.formatDate(birthdayMax);
	}
	public Long getBirthdayMin() {
		return birthdayMin;
	}
	public void setBirthdayMin(Long birthdayMin) {
		this.birthdayMin = birthdayMin;
		this.birthdayMinView=DateUtil.formatDate(birthdayMin);
	}
	
	
	public String getGenderView() {
		return genderView;
	}
	public String getBirthdayView() {
		return birthdayView;
	}
	public String getUserstateView() {
		return userstateView;
	}
	public String getBirthdayMinView() {
		return birthdayMinView;
	}
	public String getBirthdayMaxView() {
		return birthdayMaxView;
	}
	
	
	@Override
	public void setGender(Integer gender) {
		super.setGender(gender);
		this.genderView=genderMap.get(gender);
	}
	@Override
	public void setBirthday(Long birthday) {
		super.setBirthday(birthday);
		this.birthdayView=DateUtil.formatDate(birthday);
	}
	@Override
	public void setUserstate(String userstate) {
		super.setUserstate(userstate);
		this.userstateView=userstateMap.get(userstate);
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
	@Override
	public String toString() {
		return super.toString();
	}
	public void setBirthdayMinView(String birthdayMinView) {
		if(birthdayMinView!=null&&!birthdayMinView.equals("")){
			this.birthdayMinView = birthdayMinView;
			this.birthdayMin = DateUtil.parseDate(birthdayMinView).getTime();
		}
	}
	public void setBirthdayMaxView(String birthdayMaxView) {

		if(birthdayMaxView!=null&&!birthdayMaxView.equals("")){
			this.birthdayMaxView = birthdayMaxView;
			this.birthdayMax = DateUtil.parseDate(birthdayMaxView).getTime();
		}
	}
	public void setBirthdayView(String birthdayView) {
		if(birthdayView!=null&&!birthdayView.equals("")){
			this.birthdayView = birthdayView;
			setBirthday(DateUtil.parseDate(birthdayView).getTime());
		}
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
