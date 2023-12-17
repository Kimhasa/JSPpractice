package idusw.javaweb.openapia.model;

// Lombok을 사용하면 boiler plate (상용구, 관용구) code를 손쉽게 작성할 수 있음
public class MemberDTO {
    // DTO : Data Transfer Object (데이터 전송 객체), 데이터를 주고 받을 때 사용하는 객체
    // Database Table의 구조와 관련이 높음
    private Long mid;
    private String fullName;
    private String email;
    private String pw;
    private String zipcode;
    // getter / setter method -> lombok library 사용 시 애노테이션으로 처리 가능 : boiler-plate code를 생성

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
