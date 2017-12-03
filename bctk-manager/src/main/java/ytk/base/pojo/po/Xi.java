package ytk.base.pojo.po;

import ytk.util.DateUtil;
import ytk.util.MyUtil;

public class Xi {
    private Long uuid;

    private String name;

    private Long createtime;

	private String createtimeView;
    
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
        this.createtimeView=DateUtil.formatDate(createtime);
    }
    
	public String getCreatetimeView() {
		return createtimeView;
	}

	public void setCreatetimeView(String createtimeView) {
		if(MyUtil.isNotNullAndEmptyByTrim(createtimeView)){
			this.createtimeView = createtimeView;
			this.createtime = DateUtil.parseDate(createtimeView).getTime();
		}
	}
	
}