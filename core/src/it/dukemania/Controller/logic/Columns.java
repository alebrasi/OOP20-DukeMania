package it.dukemania.Controller.logic;

public enum Columns {
    COLUMN_1(1), COLUMN_2(2), COLUMN_3(3), COLUMN_4(4),
    COLUMN_5(5), COLUMN_6(6), COLUMN_7(7), COLUMN_8(8);

    private int numericValue;
    Columns(final int numericValue) {
        this.numericValue = numericValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }
}
