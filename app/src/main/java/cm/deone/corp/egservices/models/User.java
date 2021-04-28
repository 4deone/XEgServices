package cm.deone.corp.egservices.models;

public class User {
    private String uId;
    private String uName;
    private String uEmail;
    private String uAvatar;
    private String uPhone;
    private String uDate;

    public User() {
    }

    public User(String uId,
                String uName,
                String uEmail,
                String uAvatar,
                String uPhone,
                String uDate) {
        this.uId = uId;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uAvatar = uAvatar;
        this.uPhone = uPhone;
        this.uDate = uDate;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuAvatar() {
        return uAvatar;
    }

    public void setuAvatar(String uAvatar) {
        this.uAvatar = uAvatar;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuDate() {
        return uDate;
    }

    public void setuDate(String uDate) {
        this.uDate = uDate;
    }
}
