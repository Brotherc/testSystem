package ytk.base.pojo.po;

public class Zsd {
    private String uuid;

    private String name;

    private Long kcuuid;

    private String shortcode;

    private String zsdcode;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getKcuuid() {
        return kcuuid;
    }

    public void setKcuuid(Long kcuuid) {
        this.kcuuid = kcuuid;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode == null ? null : shortcode.trim();
    }

    public String getZsdcode() {
        return zsdcode;
    }

    public void setZsdcode(String zsdcode) {
        this.zsdcode = zsdcode == null ? null : zsdcode.trim();
    }
}