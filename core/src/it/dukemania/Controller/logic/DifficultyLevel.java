package it.dukemania.Controller.logic;



public enum DifficultyLevel {
    /**
     * if it is impossible to assign a difficulty to a track this value is used.
     * The tracks with this difficulty cannot be played.
     */
    UNKNOWN("???", 0),
    /**
     * number of note less or equal than 1/5 of MAX_NOTE.
     */
    VERY_EASY("Very Easy", 1),
    /**
     * number of note less or equal than 2/5 of MAX_NOTE.
     */
    EASY("Easy", 2),
    /**
     * number of note less or equal than 3/5 of MAX_NOTE.
     */
    NORMAL("Normal", 3),
    /**
     * number of note less or equal than 4/5 of MAX_NOTE.
     */
    DIFFICULT("Difficult", 4),
    /**
     * number of note less or equal than MAX_NOTE.
     */
    VERY_DIFFICULT("Very Difficult", 5);

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
