package net.thumbtack.school.hospital.dto.response;

import java.util.List;

public class ExceptionDtoResponse {
    private List<ErrorDtoResponse> errors;

    public ExceptionDtoResponse(List<ErrorDtoResponse> errors) {
        this.errors = errors;
    }

    public List<ErrorDtoResponse> getErrors() {
        return errors;
    }

}
