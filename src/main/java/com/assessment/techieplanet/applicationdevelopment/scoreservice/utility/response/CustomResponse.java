package com.assessment.techieplanet.applicationdevelopment.scoreservice.utility.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomResponse {

    private String message;

    private Object data;

    private Integer code;

    private boolean status;

    public CustomResponse() {
        this.message = message;
        this.data = data;
        this.code = HttpStatus.OK.value();
        this.status = status;
    }

    public CustomResponse(Object data) {
        this.data = data;
    }
}
