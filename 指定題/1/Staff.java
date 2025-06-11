
public class Staff {
	private String uid;
    private String email;
    private String password;

    public Staff(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
