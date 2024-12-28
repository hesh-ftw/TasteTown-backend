package com.chamath.TasteTown.Response;

import lombok.Data;

@Data
public class MessageResponse {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
