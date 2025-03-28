package br.com.floresdev.contador_comite_back.domain.financeiro;

public enum ReportPeriod {
    DIARIO,
    SEMANAL,
    MENSAL;

    public static ReportPeriod fromString(String period) {
        switch (period.toUpperCase()) {
            case "DIARIO":
                return DIARIO;
            case "SEMANAL":
                return SEMANAL;
            case "MENSAL":
                return MENSAL;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
