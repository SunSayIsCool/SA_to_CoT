package SaToCoT;

public class moto_sa_list {

    private String radioid;
    private String domain;
    private String item;
    private String alias;

    public moto_sa_list (String radioid, String domain, String item, String alias) {
        this.radioid = radioid;
        this.domain = domain;
        this.item = item;
        this.alias = alias;
    }

    public String getRadioId() {
        return radioid;
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

    public void setRadioId(String radioid) {
        this.radioid = radioid;
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
