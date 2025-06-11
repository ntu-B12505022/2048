import java.time.LocalDate;
import java.time.Period;

public class Member {
	private String uid;
    private String email;
    private String password;
    private LocalDate birthDate;

    public Member(String uid, String email, String password, LocalDate birthDate) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }
}
