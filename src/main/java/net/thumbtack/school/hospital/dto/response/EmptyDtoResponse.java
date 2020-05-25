package net.thumbtack.school.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public class EmptyDtoResponse {
    private final String emptyResponse = "{}";

    @JsonValue
    public String getEmptyResponse() {
        return emptyResponse;
    }
}
