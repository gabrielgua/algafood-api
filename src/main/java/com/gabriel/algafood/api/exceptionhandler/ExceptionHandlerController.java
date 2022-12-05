package com.gabriel.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.gabriel.algafood.core.validation.ValidacaoException;
import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    public static final String ERRO_GENERICO_USUARIO_FINAL =
            "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o " +
            "problema persistir, entre em contato com o administrador do sistema.";

    private MessageSource messageSource;

    public static final String ERRO_DADOS_INVALIDOS =
            "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = ERRO_GENERICO_USUARIO_FINAL;

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail).build();
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }



    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        String detail = ex.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType type = ProblemType.RECURSO_NAO_ENCONTRADO;

        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        String detail = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType type = ProblemType.NEGOCIO_ERRO;

        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        String detail = ex.getMessage();
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType type = ProblemType.ENTIDADE_EM_USO;

        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String detail = ex.getMessage();
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemType type = ProblemType.ACESSO_NEGADO;

        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage("Você não possui acesso para realizar essa operação!").build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }



    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O recurso '%s', que você tentou acessar, não existe.", ex.getRequestURL());
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. " +
                "Corrija e informe um valor compatível com o tipo '%s'.", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }


        String detail = "Corpo da requisição está inválido. Verifique o erro da sintaxe.";
        ProblemType type = ProblemType.CORPO_NAO_LEGIVEL;
        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();
        
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }



    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = "Tipo de conteúdo '"+ex.getContentType()+"' não é suportado. Verifique se o conteúdo em formato JSON.";
        ProblemType type = ProblemType.TIPO_DE_CONTEUDO_NAO_SUPORTADO;
        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType type = ProblemType.CORPO_NAO_LEGIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é inválido. Informe um valor compatível com o tipo '%s'.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder(status, type, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();


        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.CORPO_NAO_LEGIVEL;
        String className = ex.getReferringClass().getSimpleName();
        String detail = String.format("A propriedade '%s' não existe para '%s'. Corrija ou remova essa propriedade e tente novamente.", path, className);
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(ERRO_GENERICO_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    //Retorna um builder() completo de Problem.
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    //Retorna um builder() de Problem quando não se tem Type, usado para as Exceções tradadas pelo Spring.
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, String title, String detail) {
        return Problem.builder()
                .status(status.value())
                .title(title)
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private List<Problem.Object> fieldErrorsToList(BindingResult bindingResult) {

        return bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String name = objectError.getObjectName();
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = createProblemBuilder(status, status.getReasonPhrase(), ex.getMessage()).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .detail(ex.getMessage())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String detail = ERRO_DADOS_INVALIDOS;
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        List<Problem.Object> problemObjects = fieldErrorsToList(bindingResult);

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

}
