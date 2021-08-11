package it.dukemania.logic;



public enum DifficultyLevel {
    SCONOSCIUTO("???",0),
    MOLTO_FACILE("Molto Facile",1),
    FACILE("Facile",2),
    NORMALE("Normale",3),
    DIFFICILE("Difficile",4),
    MOLTO_DIFFICILE("Molto Difficile",5);

    private String effectiveName;
    private Integer numericValue;
    DifficultyLevel(final String effectiveName, final int numericValue) {
            this.effectiveName = effectiveName;
            this.numericValue = numericValue;
    }

    public String getEffectiveName() {
            return effectiveName;
    }

    public Integer getNumericValue() {
        return numericValue;
    }

}
