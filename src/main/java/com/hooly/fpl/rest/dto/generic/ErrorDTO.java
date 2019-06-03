package com.hooly.fpl.rest.dto.generic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Data
public class ErrorDTO {

    private String code;
    private List<String> fields;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDTO errorDTO = (ErrorDTO) o;
        return Objects.equals(code, errorDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
