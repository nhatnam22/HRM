package com.module.hrm.web.common.exception;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.enumeration.MessageParams;
import jakarta.annotation.Nullable;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.*;
import javax.naming.AuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;
import tech.jhipster.web.util.HeaderUtil;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final boolean CASUAL_CHAIN_ENABLED = false;

    // User input validation exception
    private static final URI CONSTRAINT_VIOLATION_TYPE = URI.create("/problem/constraint-violation");
    private static final URI RESOURCE_NOTFOUND_TYPE = URI.create("/problem/resource-notfound");
    private static final URI ACCESS_DENIED_TYPE = URI.create("/problem/access-denied");
    private static final URI UNAUTHORIZED_TYPE = URI.create("/problem/unauthorized");
    private static final URI DEFAULT_TYPE = URI.create("/problem/problem-with-message");

    private static final String MISSING_KEY = "MISSING_KEY ";
    private static final String MISSING_PARAM = "MISSING_PARAM ";

    private static final String PAYLOAD_KEY = "payload";
    private static final String FIELD_ERRORS_KEY = "field";
    private static final String MESSAGE_KEY = "message";

    private final MessageSource messageSource;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Environment env;

    public GlobalExceptionHandler(MessageSource messageSource, Environment env) {
        this.messageSource = messageSource;
        this.env = env;
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
        log.debug("Handling {} error", ex.getClass().getSimpleName());

        ProblemDetail problemDetail = buildProblemDetail(
            (HttpStatus) ex.getStatusCode(),
            CONSTRAINT_VIOLATION_TYPE,
            MessageKeys.ERROR_TITLE,
            ex.getMessageCode(),
            ex.getMessageArgs(),
            ex.getBindingResult()
        );

        return handleExceptionInternal((Exception) ex, problemDetail, null, ex.getStatusCode(), request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, NativeWebRequest request) {
        log.debug("Handling {} error", ex.getClass().getSimpleName());

        ProblemDetail problemDetail = buildProblemDetail(
            (HttpStatus) ex.getStatusCode(),
            RESOURCE_NOTFOUND_TYPE,
            MessageKeys.ERROR_TITLE,
            ex.getMessageCode(),
            ex.getMessageArgs(),
            null
        );

        return handleExceptionInternal((Exception) ex, problemDetail, null, ex.getStatusCode(), request);
    }

    @ExceptionHandler(ValidationGenericException.class)
    public ResponseEntity<Object> handleValidationGenericException(ValidationGenericException ex, NativeWebRequest request) {
        log.debug("Handling {} error", ex.getClass().getSimpleName());

        ProblemDetail problemDetail = buildProblemDetail(
            (HttpStatus) ex.getStatusCode(),
            DEFAULT_TYPE,
            MessageKeys.ERROR_TITLE,
            ex.getMessageCode(),
            ex.getMessageArgs(),
            null
        );

        return handleExceptionInternal((Exception) ex, problemDetail, null, ex.getStatusCode(), request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleAnyException(Throwable ex, NativeWebRequest request) {
        log.debug("Converting Exception to Problem Details:", ex);
        ProblemDetailWithCause pdCause = wrapAndCustomizeProblem(ex, request);
        return handleExceptionInternal((Exception) ex, pdCause, buildHeaders(ex), HttpStatusCode.valueOf(pdCause.getStatus()), request);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex,
        @Nullable Object body,
        org.springframework.http.HttpHeaders headers,
        HttpStatusCode statusCode,
        WebRequest request
    ) {
        body = body == null ? wrapAndCustomizeProblem((Throwable) ex, (NativeWebRequest) request) : body;
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    protected ProblemDetailWithCause wrapAndCustomizeProblem(Throwable ex, NativeWebRequest request) {
        return customizeProblem(getProblemDetailWithCause(ex), ex, request);
    }

    private ProblemDetailWithCause getProblemDetailWithCause(Throwable ex) {
        if (ex instanceof BadAuthenticationException) return ProblemDetailWithCause.ProblemDetailWithCauseBuilder.instance()
            .withStatus(toStatus(ex).value())
            .withDetail(
                localize(
                    String.valueOf(((BadAuthenticationException) ex).getMessageCode()),
                    ((BadAuthenticationException) ex).getMessageArgs()
                )
            )
            .build();

        /*
         * if ( ex instanceof com.unibenfoods.dms.service.EmailAlreadyUsedException )
         * return (ProblemDetailWithCause) new EmailAlreadyUsedException().getBody(); if
         * ( ex instanceof com.unibenfoods.dms.service.InvalidPasswordException ) return
         * (ProblemDetailWithCause) new InvalidPasswordException().getBody();
         */

        if (
            ex instanceof ErrorResponseException exp && exp.getBody() instanceof ProblemDetailWithCause problemDetailWithCause
        ) return problemDetailWithCause;

        return ProblemDetailWithCause.ProblemDetailWithCauseBuilder.instance().withStatus(toStatus(ex).value()).build();
    }

    protected ProblemDetailWithCause customizeProblem(ProblemDetailWithCause problem, Throwable err, NativeWebRequest request) {
        if (problem.getStatus() <= 0) problem.setStatus(toStatus(err));

        if (problem.getType() == null || problem.getType().equals(URI.create("about:blank"))) problem.setType(getMappedType(err));

        problem.setTitle(localize(MessageKeys.ERROR_TITLE));

        if (problem.getDetail() == null) {
            // higher precedence to cause
            problem.setDetail(getCustomizedErrorDetails(err));
        }

        Map<String, Object> problemProperties = problem.getProperties();
        if (problemProperties == null || !problemProperties.containsKey(PAYLOAD_KEY)) {
            if (
                err instanceof MethodArgumentNotValidException fieldException && fieldException.getBindingResult() != null
            ) problem.setProperty(PAYLOAD_KEY, getFieldErrors(fieldException.getBindingResult()));
            else if (err instanceof BadRequestException fieldException && fieldException.getBindingResult() != null) problem.setProperty(
                PAYLOAD_KEY,
                getFieldErrors(fieldException.getBindingResult())
            );
        }

        problem.setCause(buildCause(err.getCause(), request).orElse(null));

        return problem;
    }

    private List<Map<String, String>> getFieldErrors(BindingResult bindingResult) {
        return bindingResult
            .getFieldErrors()
            .stream()
            .map(f -> Map.of(FIELD_ERRORS_KEY, f.getField(), MESSAGE_KEY, localize(f.getCode(), f.getArguments())))
            .toList();
    }

    private HttpStatus toStatus(final Throwable throwable) {
        // Let the ErrorResponse take this responsibility
        if (throwable instanceof AuthenticationException) return HttpStatus.UNAUTHORIZED;

        if (throwable instanceof ErrorResponse err) return HttpStatus.valueOf(err.getBody().getStatus());

        return Optional.ofNullable(getMappedStatus(throwable)).orElse(
            Optional.ofNullable(resolveResponseStatus(throwable)).map(ResponseStatus::value).orElse(HttpStatus.INTERNAL_SERVER_ERROR)
        );
    }

    private ResponseStatus resolveResponseStatus(final Throwable type) {
        final ResponseStatus candidate = findMergedAnnotation(type.getClass(), ResponseStatus.class);
        return candidate == null && type.getCause() != null ? resolveResponseStatus(type.getCause()) : candidate;
    }

    private ProblemDetail buildProblemDetail(
        HttpStatus status,
        URI type,
        MessageKeys title,
        MessageKeys messageCode,
        @Nullable Object[] args,
        @Nullable BindingResult br
    ) {
        String message = localize(messageCode, args);

        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, message);

        pd.setType(type);
        pd.setTitle(localize(title));

        if (br != null) pd.setProperty(
            PAYLOAD_KEY,
            br
                .getFieldErrors()
                .stream()
                .map(field -> Map.of(FIELD_ERRORS_KEY, field.getField(), MESSAGE_KEY, localize(field.getCode(), field.getArguments())))
                .toList()
        );

        return pd;
    }

    private URI getMappedType(Throwable err) {
        if (err instanceof MethodArgumentNotValidException) return CONSTRAINT_VIOLATION_TYPE;
        if (err instanceof ResourceNotFoundException) return RESOURCE_NOTFOUND_TYPE;
        if (err instanceof AccessDeniedException) return ACCESS_DENIED_TYPE;
        if (err instanceof BadCredentialsException) return UNAUTHORIZED_TYPE;
        if (err instanceof BadAuthenticationException) return UNAUTHORIZED_TYPE;

        return DEFAULT_TYPE;
    }

    private String getCustomizedErrorDetails(Throwable err) {
        if (err instanceof MethodArgumentNotValidException) return localize(MessageKeys.ERROR_VALIDATION);
        if (err instanceof BadCredentialsException) return localize(MessageKeys.LOGIN_FAILURE);
        if (err instanceof AuthenticationException) return localize(MessageKeys.TOKEN_EXPIRED);

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (err instanceof HttpMessageConversionException) return "Unable to convert http message";
            if (err instanceof DataAccessException) return "Failure during data access";
            if (containsPackageName(err.getMessage())) return "Unexpected runtime exception";
        }

        return localize(MessageKeys.ERROR_SYSTEM);
    }

    private HttpStatus getMappedStatus(Throwable err) {
        // Where we disagree with Spring defaults
        if (err instanceof AccessDeniedException) return HttpStatus.FORBIDDEN;
        if (err instanceof ConcurrencyFailureException) return HttpStatus.CONFLICT;
        if (err instanceof BadCredentialsException) return HttpStatus.UNAUTHORIZED;
        return null;
    }

    private HttpHeaders buildHeaders(Throwable err) {
        return err instanceof ValidationGenericException ex
            ? HeaderUtil.createFailureAlert(
                applicationName,
                true,
                ex.getMessageCode().getValue(),
                ex.getMessageCode().getValue(),
                ex.getDefaultMsg()
            )
            : null;
    }

    private Optional<ProblemDetailWithCause> buildCause(final Throwable throwable, NativeWebRequest request) {
        if (throwable != null && isCasualChainEnabled()) {
            return Optional.of(customizeProblem(getProblemDetailWithCause(throwable), throwable, request));
        }
        return Optional.ofNullable(null);
    }

    private String localize(String code, Object[] args) {
        MessageKeys key = MessageKeys.valueOf(code);
        return localize(key, args);
    }

    private String localize(MessageKeys key) {
        return localize(key, null);
    }

    private String localize(MessageKeys key, @Nullable Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof MessageParams param) {
                    args[i] = getMessage(param.getValue(), null, MISSING_PARAM + param.getValue(), locale);
                } else if (args[i] instanceof DefaultMessageSourceResolvable) {
                    DefaultMessageSourceResolvable source = ((DefaultMessageSourceResolvable) args[i]);
                    String fieldName = org.springframework.util.StringUtils.hasText(source.getDefaultMessage())
                        ? source.getDefaultMessage()
                        : source.getCode();

                    // remove sub class
                    if (fieldName.indexOf('.') > 0) {
                        String[] names = fieldName.split("\\.");
                        if (names.length > 0) {
                            fieldName = names[names.length - 1];
                        }
                    }

                    args[i] = getMessage(fieldName, null, MISSING_PARAM + fieldName, locale);
                }
            }
        }

        return getMessage(key.getValue(), args, MISSING_KEY + key.getValue(), locale);
    }

    private String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale) {
        try {
            return this.messageSource.getMessage(code, args, defaultMessage, locale);
        } catch (Exception e) {
            log.info(defaultMessage);
        }

        return defaultMessage;
    }

    private boolean isCasualChainEnabled() {
        // Customize as per the needs
        return CASUAL_CHAIN_ENABLED;
    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "jakarta.", "javax.", "com.", "io.", "de.", "vn.pratek.dms");
    }
}
