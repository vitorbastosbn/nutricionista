package com.vitorbastosbn.nutricionista.entity.enums;

/**
 * Enum que representa os estados (UF) do Brasil.
 */
public enum State {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String fullName;

    State(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAcronym() {
        return this.name();
    }

    /**
     * Retorna o enum State a partir da sigla.
     *
     * @param acronym Sigla do estado (ex: "SP", "RJ")
     * @return State correspondente
     * @throws IllegalArgumentException se a sigla não for válida
     */
    public static State fromAcronym(String acronym) {
        if (acronym == null || acronym.isBlank()) {
            return null;
        }
        try {
            return State.valueOf(acronym.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid state acronym: " + acronym);
        }
    }

    /**
     * Retorna o enum State a partir do nome completo.
     *
     * @param fullName Nome completo do estado (ex: "São Paulo", "Rio de Janeiro")
     * @return State correspondente ou null se não encontrado
     */
    public static State fromFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return null;
        }
        for (State state : State.values()) {
            if (state.fullName.equalsIgnoreCase(fullName.trim())) {
                return state;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getAcronym();
    }
}

