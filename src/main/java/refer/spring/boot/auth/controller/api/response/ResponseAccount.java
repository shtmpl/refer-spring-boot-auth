package refer.spring.boot.auth.controller.api.response;

import java.time.OffsetDateTime;

public class ResponseAccount {

    private OffsetDateTime createdAt;

    private String username;

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
