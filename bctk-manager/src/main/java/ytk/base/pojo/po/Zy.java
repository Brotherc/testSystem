package ytk.base.pojo.po;

public class Zy {
    private Long uuid;

    private String name;

    private Long createime;

    private Long xuuid;

    private String shortcode;

    private String zycode;

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

    public Long getCreateime() {
        return createime;
    }

    public void setCreateime(Long createime) {
        this.createime = createime;
    }

    public Long getXuuid() {
        return xuuid;
    }

    public void setXuuid(Long xuuid) {
        this.xuuid = xuuid;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode == null ? null : shortcode.trim();
    }

    public String getZycode() {
        return zycode;
    }

    public void setZycode(String zycode) {
        this.zycode = zycode == null ? null : zycode.trim();
    }
}