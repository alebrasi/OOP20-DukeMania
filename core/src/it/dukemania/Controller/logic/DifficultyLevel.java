package it.dukemania.Controller.logic;



public enum DifficultyLevel {
    UNKNOWN("???",0),
    VERY_EASY("Very Easy",1),
    EASY("Easy",2),
    NORMAL("Normal",3),
    DIFFICULT("Difficult",4),
    VERY_DIFFICULT("Very Difficult",5);

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
