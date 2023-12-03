package ro.uaic.info.l7.dtos;

public class PreferenceDto {
    private Long id;
    private String username;
    private String content;
    private String registrationNumber;

    public PreferenceDto(Long id, String username, String content, String registrationNumber) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.registrationNumber = registrationNumber;
    }

    public PreferenceDto() {
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
