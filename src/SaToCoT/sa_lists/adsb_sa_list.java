package SaToCoT.sa_lists;

public class adsb_sa_list {
    private String sign;
    private String domain;
    private String item;
    private String alias;

    public adsb_sa_list(String sign, String domain, String item, String alias) {
        this.sign = sign;
        this.domain = domain;
        this.item = item;
        this.alias = alias;
    }

    public String getCallsign() {
        return sign;
    }

    public String getDomain() {
        return domain;
    }

    public String getItem() {
        return item;
    }

    public String getAlias() {
        return alias;
    }

    public void setCallsign(String sign) {
        this.sign = sign;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setAlias(String alias){
        this.alias = alias;
    }
}
