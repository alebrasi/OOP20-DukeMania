package it.dukemania.logic;

public enum DifficultyLevel {
    SCONOSCIUTO("???",0),
    MOLTO_FACILE("Molto Facile",1),
    FACILE("Facile",2),
    NORMALE("Normale",3),
    DIFFICILE("Difficile",4),
    MOLTO_DIFFICILE("Molto Difficile",5);

    private String effectiveName;
    private int value;
    DifficultyLevel(final String effectiveName, int value) {
            this.effectiveName = effectiveName;
            this.value = value;
    }

    public String getEffectiveName() {
            return effectiveName;
    }

    public int getValue() {
        return value;
    }
    
}
