package com.vitorbastosbn.nutricionista.controller.doc;

/**
 * Constantes com exemplos de respostas de sucesso padronizadas para documentação Swagger.
 * Garante consistência nos exemplos de sucesso em toda a API.
 */
public final class SwaggerSuccessExamples {

    private SwaggerSuccessExamples() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada");
    }

    // ========== USER EXAMPLES ==========

    public static final String USER_RESPONSE = """
            {
              "id": "550e8400-e29b-41d4-a716-446655440000",
              "fullName": "João Silva",
              "email": "joao@example.com",
              "birthDate": "1990-05-15",
              "address": {
                "id": "650e8400-e29b-41d4-a716-446655440001",
                "street": "Rua das Flores",
                "number": "123",
                "complement": "Apto 101",
                "neighborhood": "Centro",
                "city": "São Paulo",
                "state": "SP",
                "zipCode": "01234-567",
                "country": "Brasil"
              },
              "contact": {
                "id": "750e8400-e29b-41d4-a716-446655440002",
                "phone": "(11) 98765-4321",
                "alternativePhone": "(11) 3456-7890",
                "whatsapp": "(11) 98765-4321"
              },
              "roles": [
                {
                  "id": "850e8400-e29b-41d4-a716-446655440003",
                  "name": "USER",
                  "description": "Usuário padrão"
                }
              ]
            }
            """;

    public static final String USER_PAGINATED_RESPONSE = """
            {
              "content": [
                {
                  "id": "550e8400-e29b-41d4-a716-446655440000",
                  "fullName": "João Silva",
                  "email": "joao@example.com",
                  "birthDate": "1990-05-15",
                  "address": {
                    "id": "650e8400-e29b-41d4-a716-446655440001",
                    "street": "Rua das Flores",
                    "number": "123",
                    "city": "São Paulo",
                    "state": "SP",
                    "zipCode": "01234-567",
                    "country": "Brasil"
                  },
                  "roles": [
                    {
                      "id": "750e8400-e29b-41d4-a716-446655440002",
                      "name": "USER",
                      "description": "Usuário padrão"
                    }
                  ]
                },
                {
                  "id": "550e8400-e29b-41d4-a716-446655440010",
                  "fullName": "Maria Santos",
                  "email": "maria@example.com",
                  "birthDate": "1985-03-20",
                  "address": {
                    "id": "650e8400-e29b-41d4-a716-446655440011",
                    "street": "Av. Paulista",
                    "number": "1000",
                    "city": "São Paulo",
                    "state": "SP",
                    "zipCode": "01310-100",
                    "country": "Brasil"
                  },
                  "roles": [
                    {
                      "id": "750e8400-e29b-41d4-a716-446655440012",
                      "name": "ADMIN",
                      "description": "Administrador do sistema"
                    }
                  ]
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 10,
                "sort": {
                  "sorted": true,
                  "unsorted": false,
                  "empty": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "totalPages": 5,
              "totalElements": 42,
              "last": false,
              "size": 10,
              "number": 0,
              "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
              },
              "numberOfElements": 2,
              "first": true,
              "empty": false
            }
            """;

    // ========== ROLE EXAMPLES ==========

    public static final String ROLE_RESPONSE = """
            {
              "id": "750e8400-e29b-41d4-a716-446655440002",
              "name": "ADMIN",
              "description": "Administrador do sistema"
            }
            """;

    public static final String ROLE_PAGINATED_RESPONSE = """
            {
              "content": [
                {
                  "id": "750e8400-e29b-41d4-a716-446655440002",
                  "name": "ADMIN",
                  "description": "Administrador do sistema"
                },
                {
                  "id": "750e8400-e29b-41d4-a716-446655440003",
                  "name": "USER",
                  "description": "Usuário padrão"
                },
                {
                  "id": "750e8400-e29b-41d4-a716-446655440004",
                  "name": "MANAGER",
                  "description": "Gerente com permissões especiais"
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 10,
                "sort": {
                  "sorted": true,
                  "unsorted": false,
                  "empty": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "totalPages": 1,
              "totalElements": 3,
              "last": true,
              "size": 10,
              "number": 0,
              "sort": {
                "sorted": true,
                "unsorted": false,
                "empty": false
              },
              "numberOfElements": 3,
              "first": true,
              "empty": false
            }
            """;

    // ========== AUTH EXAMPLES ==========

    public static final String AUTH_RESPONSE = """
            {
              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2FvQGV4YW1wbGUuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTcwNTE3NjAwMCwiZXhwIjoxNzA1MTc5NjAwfQ.signature",
              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImpvYW9AZXhhbXBsZS5jb20iLCJpYXQiOjE3MDUxNzYwMDAsImV4cCI6MTcwNTc4MDgwMH0.signature",
              "token_type": "Bearer",
              "expires_in": 3600,
              "refresh_expires_in": 604800,
              "user_id": "550e8400-e29b-41d4-a716-446655440000",
              "username": "joao@example.com",
              "email": "joao@example.com",
              "issued_at": "2026-02-10T00:00:00"
            }
            """;

    public static final String AUTH_LOGIN_RESPONSE = """
            {
              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzA1MTc2MDAwLCJleHAiOjE3MDUxNzk2MDB9.signature",
              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6ImFkbWluQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA1MTc2MDAwLCJleHAiOjE3MDU3ODA4MDB9.signature",
              "token_type": "Bearer",
              "expires_in": 3600,
              "refresh_expires_in": 604800,
              "user_id": "550e8400-e29b-41d4-a716-446655440000",
              "username": "admin@example.com",
              "email": "admin@example.com",
              "issued_at": "2026-02-10T00:00:00"
            }
            """;

    public static final String AUTH_REGISTER_RESPONSE = """
            {
              "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJub3ZvdXNlckBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE3MDUxNzYwMDAsImV4cCI6MTcwNTE3OTYwMH0.signature",
              "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXBlIjoicmVmcmVzaCIsInN1YiI6Im5vdm91c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA1MTc2MDAwLCJleHAiOjE3MDU3ODA4MDB9.signature",
              "token_type": "Bearer",
              "expires_in": 3600,
              "refresh_expires_in": 604800,
              "user_id": "550e8400-e29b-41d4-a716-446655440999",
              "username": "novouser@example.com",
              "email": "novouser@example.com",
              "issued_at": "2026-02-10T00:00:00"
            }
            """;
}

