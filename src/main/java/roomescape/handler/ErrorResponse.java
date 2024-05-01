package roomescape.handler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.validation.FieldError;

record ErrorResponse(String message, List<ErrorDetail> details) {
    public ErrorResponse(String message) {
        this(message, Collections.emptyList());
    }

    public ErrorResponse(FieldError[] errors) {
        this("", Arrays.stream(errors).map(ErrorDetail::new).toList());
    }
}
