package sample;

public class harris_sa_list {

    private String sign;
    private String team;
    private String role;

    public harris_sa_list (String sign, String team, String role) {
        this.sign = sign;
        this.team = team;
        this.role = role;
    }

    public String getCallsign() {
        return sign;
    }

    public String getTeam() {
        return team;
    }

    public String getRole() {
        return role;
    }

    public void setCallsign(String sign) {
        this.sign = sign;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setRole(String role) {
        this.role = role;
    }
}