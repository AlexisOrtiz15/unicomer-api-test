package com.unicomer.configuration.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@ActiveProfiles("test")
public class RestResponseEntityExceptionHandlerTest {

    private RestResponseEntityExceptionHandler responseEntityExceptionHandler;

    private WebRequest webRequest;

    @BeforeEach
    public void setup() {
        webRequest = Mockito.mock(WebRequest.class);
        responseEntityExceptionHandler = new RestResponseEntityExceptionHandler();
    }

    @Test
    public void shouldReturnErrorWithBadRequestWhenMethodArgumentNotValidExceptionIsHandled()
            throws NoSuchMethodException, SecurityException {

        FieldError fieldError = new FieldError("testRequestDto", "field", "DefaultMessage");

        BindingResult bindingResult =
                new BeanPropertyBindingResult(new TestRequestDto(), "testRequestDto");
        bindingResult.addError(fieldError);

        Method method = TestRequestDto.class.getMethod("getField");

        MethodParameter methodParam = new MethodParameter(method, -1);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(methodParam, bindingResult);

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handleMethodArgumentNotValid(exception,
                        new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("field");

        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(FieldError.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription()).isEqualTo("DefaultMessage");
    }
    
    @Test
    public void shouldReturnErrorWithBadRequestWhenHttpClientErrorExceptionIsHandled() {

        HttpClientErrorException exception =
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Any Error");

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handleHttpClientError(exception, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");

        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(HttpClientErrorException.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription()).isEqualTo("400 Any Error");

    }
    
    
    @Test
    public void shouldReturnErrorWithForbiddenWhenAccessDeniedExceptionIsHandled() {

        AccessDeniedException exception = new AccessDeniedException("index.html");

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handleAccessDenied(exception, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.FORBIDDEN.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");
        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");
        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(AccessDeniedException.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription()).isEqualTo("index.html");

    }
    
    @Test
    public void shouldReturnErrorWithNotFoundWhenEntityNotFoundExceptionIsHandled() {

        EntityNotFoundException exception = new EntityNotFoundException("username not found");

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handleEntityNotFound(exception, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");

        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(EntityNotFoundException.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription()).isEqualTo("username not found");

    }
    
    
    @Test
    public void shouldReturnErrorWithUnsupportMediaTypeWhenIllegalArgumentExceptionIsHandled() {

        IllegalArgumentException exception = new IllegalArgumentException("unsupported media type");

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handleInvalidMimeType(exception, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");

        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(IllegalArgumentException.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription())
                .isEqualTo("unsupported media type");

    }

    @Test
    public void shouldReturnErrorWithInternalServerErrorWhenExceptionIsHandled() {

        Exception exception = new Exception("Any Exception");

        ResponseEntity<Object> response =
                responseEntityExceptionHandler.handle500Exception(exception, webRequest);

        ApiErrorWrapper apiErrors = (ApiErrorWrapper) response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        assertThat(apiErrors.getErrors().get(0).getCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());

        assertThat(apiErrors.getErrors().get(0).getSource()).isEqualTo("base");

        assertThat(apiErrors.getErrors().get(0).getType())
                .isEqualTo(Exception.class.getSimpleName());

        assertThat(apiErrors.getErrors().get(0).getDescription()).isEqualTo("Any Exception");

    }

    class TestRequestDto {

        public TestRequestDto() {}

        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

    }

}
