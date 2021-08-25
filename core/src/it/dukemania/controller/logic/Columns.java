package it.dukemania.controller.logic;

public enum Columns {
    /**
     * first column.
     */
    COLUMN_1(1),
    /**
     * second column.
     */
    COLUMN_2(2),
    /**
     * third column.
     */
    COLUMN_3(3),
    /**
     * fourth column.
     */
    COLUMN_4(4),
    /**
     * fifth column.
     */
    COLUMN_5(5),
    /**
     * sixth column.
     */
    COLUMN_6(6),
    /**
     * seventh column.
     */
    COLUMN_7(7),
    /**
     * eighth column.
     */
    COLUMN_8(8);

    private int numericValue;

    Columns(final int numericValue) {
        this.numericValue = numericValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }
}
