package it.dukemania.Controller.logic;

public class FullCalculator implements ScoreStrategy {

    private static final int NOTE_POINT = 100;
    private static final int NOTE_TOLERANCE = 50;
    private static final int MAX_COMBO = 20;
    private static final int COMBO_POINT = 5;
    private static final int GIFT_POINT = 20;
    private int combo;

    public FullCalculator() {
        this.combo = 0;
    }

    /**
     * calculate the score using tolerance, gift point and combo point.
     */
    @Override
    public int scoreCalculation(
            final Columns column, final long start, final long end, final NoteRange currentRange, final int columnNumber) {
        int normalPoint = (int) ((double) (end - start - Math.abs(currentRange.getEnd() - end)
                - Math.abs(currentRange.getStart() - start)) / (end - start)  * NOTE_POINT);
        // NOTE_POINT multiplied by the percentage of match between the note and the range
        this.combo = normalPoint >= NOTE_POINT - NOTE_TOLERANCE ? (this.combo < MAX_COMBO ? this.combo + 1 : this.combo) : 0;
        //combo increase if you played a perfect note (100 - NOTE_TOLERANCE)%
        return (((normalPoint >= NOTE_POINT - NOTE_TOLERANCE 
                ? NOTE_POINT : (normalPoint + NOTE_TOLERANCE < 0 
                        ? (currentRange.getStart() <= start && currentRange.getEnd() >= end ? GIFT_POINT : 0) 
                        : normalPoint + NOTE_TOLERANCE)) + COMBO_POINT * combo) * columnNumber);
    }

}
