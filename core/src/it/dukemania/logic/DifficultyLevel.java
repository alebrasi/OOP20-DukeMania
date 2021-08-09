package it.dukemania.logic;

public enum DifficultyLevel {
    SCONOSCIUTO("???"),
    MOLTO_FACILE("Molto Facile"),
    FACILE("Facile"),
    NORMALE("Normale"),
    DIFFICILE("Difficile"),
    MOLTO_DIFFICILE("Molto Difficile");
    
    private String effectiveName;
    DifficultyLevel(final String effectiveName) {
            this.effectiveName = effectiveName;
    }
    
    public String getEffectiveName() {
            return effectiveName;
    }
}
