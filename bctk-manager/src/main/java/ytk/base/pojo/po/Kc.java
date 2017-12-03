package ytk.base.pojo.po;

public class Kc {
    private Long uuid;

    private String name;

    private Long createtime;

    private String shortcode;

    private String kccode;

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
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode == null ? null : shortcode.trim();
    }

    public String getKccode() {
        return kccode;
    }

    public void setKccode(String kccode) {
        this.kccode = kccode == null ? null : kccode.trim();
    }
}