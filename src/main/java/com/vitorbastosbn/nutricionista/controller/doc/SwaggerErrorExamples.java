package com.vitorbastosbn.nutricionista.controller.doc;

/**
 * Constantes com exemplos de erros padronizados para documentação Swagger.
 * Garante consistência nos exemplos de erro em toda a API.
 */
public final class SwaggerErrorExamples {

    private SwaggerErrorExamples() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada");
    }

    public static final String UNAUTHORIZED_401 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 401,
              "message": "Acesso negado",
              "error": {
                "code": "UNAUTHORIZED",
                "details": "Token JWT ausente ou inválido"
              }
            }
            """;

    public static final String FORBIDDEN_403 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 403,
              "message": "Acesso negado",
              "error": {
                "code": "ACCESS_DENIED",
                "details": "Você não tem permissão para acessar este recurso"
              }
            }
            """;

    public static final String NOT_FOUND_404 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 404,
              "message": "Recurso não encontrado",
              "error": {
                "code": "RESOURCE_NOT_FOUND",
                "details": "Recurso não encontrado"
              }
            }
            """;

    public static final String USER_NOT_FOUND_404 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 404,
              "message": "Recurso não encontrado",
              "error": {
                "code": "RESOURCE_NOT_FOUND",
                "details": "Usuário não encontrado com ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6"
              }
            }
            """;

    public static final String ROLE_NOT_FOUND_404 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 404,
              "message": "Recurso não encontrado",
              "error": {
                "code": "RESOURCE_NOT_FOUND",
                "details": "Role não encontrada com ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6"
              }
            }
            """;

    public static final String VALIDATION_ERROR_400 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 400,
              "message": "Dados inválidos foram enviados",
              "error": {
                "code": "VALIDATION_ERROR",
                "details": "Falha na validação dos dados. Verifique os campos com erro abaixo.",
                "fieldErrors": [
                  {
                    "field": "email",
                    "rejectedValue": "invalid-email",
                    "message": "Email deve ser válido"
                  },
                  {
                    "field": "fullName",
                    "rejectedValue": "",
                    "message": "Nome é obrigatório"
                  }
                ]
              }
            }
            """;

    public static final String EMAIL_CONFLICT_409 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 409,
              "message": "Violação de regra de negócio",
              "error": {
                "code": "BUSINESS_ERROR",
                "details": "Este email já está cadastrado no sistema"
              }
            }
            """;

    public static final String ROLE_CONFLICT_409 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 409,
              "message": "Violação de regra de negócio",
              "error": {
                "code": "BUSINESS_ERROR",
                "details": "Usuário já possui essa role"
              }
            }
            """;

    public static final String LAST_ROLE_CONFLICT_409 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 409,
              "message": "Violação de regra de negócio",
              "error": {
                "code": "BUSINESS_ERROR",
                "details": "Um usuário deve ter pelo menos uma role"
              }
            }
            """;

    public static final String INTERNAL_SERVER_ERROR_500 = """
            {
              "timestamp": "2026-02-09T22:40:00",
              "status": 500,
              "message": "Erro interno do servidor",
              "error": {
                "code": "INTERNAL_SERVER_ERROR",
                "details": "Um erro inesperado ocorreu. Por favor, tente novamente mais tarde."
              }
            }
            """;

    // ========== AUTH SPECIFIC ERRORS ==========

    public static final String INVALID_CREDENTIALS_409 = """
            {
              "timestamp": "2026-02-09T23:00:00",
              "status": 409,
              "message": "Violação de regra de negócio",
              "error": {
                "code": "BUSINESS_ERROR",
                "details": "Email ou senha inválidos"
              }
            }
            """;

    public static final String INVALID_REFRESH_TOKEN_409 = """
            {
              "timestamp": "2026-02-09T23:00:00",
              "status": 409,
              "message": "Violação de regra de negócio",
              "error": {
                "code": "BUSINESS_ERROR",
                "details": "Refresh token inválido: Token expirado"
              }
            }
            """;
}

