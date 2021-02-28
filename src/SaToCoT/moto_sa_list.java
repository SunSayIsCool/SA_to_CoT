package SaToCoT;

public class moto_sa_list {

    private String radioid;
    private String team;
    private String role;

    public moto_sa_list (String radioid, String team, String role) {
        this.radioid = radioid;
        this.team = team;
        this.role = role;
    }

    public String getRadioId() {
        return radioid;
    }

    public String getTeam() {
        return team;
    }

    public String getRole() {
        return role;
    }

    public void setRadioId(String radioid) {
        this.radioid = radioid;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
