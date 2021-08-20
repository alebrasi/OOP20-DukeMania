package it.dukemania.View.notesGraphics;

/***
 * 
 * @author Sofia
 * 
 */
public enum Columns {
    /***
     * 
     */
    COLUMN1(1), COLUMN2(2), COLUMN3(3), COLUMN4(4), COLUMN5(5), COLUMN6(6), COLUMN7(7), COLUMN8(8);

    private int numericValue;


    Columns(final int numericValue) {
        this.numericValue = numericValue;
    }


    public Integer getNumericvalue() {
        return this.numericValue;
    }
}
