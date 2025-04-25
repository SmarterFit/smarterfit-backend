package com.smarterfit.enums;

public enum ErrorCode {

    // Erros relacionados ao usuário
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found."),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists."),
    INVALID_USER_CREDENTIALS("INVALID_USER_CREDENTIALS", "Invalid user credentials."),

    // Erros relacionados à data
    INVALID_DATE("INVALID_DATE", "Invalid date range provided."),
    PAST_DATE("PAST_DATE", "The date cannot be in the past."),

    // Erros de grupo de classes
    CLASS_GROUP_NOT_FOUND("CLASS_GROUP_NOT_FOUND", "Class group not found."),
    CLASS_GROUP_NAME_ALREADY_EXISTS("CLASS_GROUP_NAME_ALREADY_EXISTS", "Class group name already exists."),
    INACTIVE_CLASS_GROUP("INACTIVE_CLASS_GROUP", "Class group is inactive."),

    // Erros relacionados ao evento de aula
    CLASS_EVENT_NOT_FOUND("CLASS_EVENT_NOT_FOUND", "Class event not found."),
    CLASS_EVENT_INVALID_DATES("CLASS_EVENT_INVALID_DATES", "Event dates are invalid."),

    // Erros relacionados ao pagamento
    PAYMENT_FAILED("PAYMENT_FAILED", "Payment failed."),
    PAYMENT_PENDING("PAYMENT_PENDING", "Payment is still pending."),

    // Erros gerais
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Requested resource not found."),
    INVALID_INPUT("INVALID_INPUT", "Invalid input provided."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error.");

    private final String code;
    private final String message;

    // Construtor
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Métodos para acessar o código e a mensagem
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static void main(String[] args) {
        // Exemplo de uso
        for (ErrorCode errorCode : ErrorCode.values()) {
            System.out.println("Code: " + errorCode.getCode() + ", Message: " + errorCode.getMessage());
        }
    }

}
