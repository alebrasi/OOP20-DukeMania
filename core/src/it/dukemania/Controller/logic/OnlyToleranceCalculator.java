package it.dukemania.Controller.logic;

public class OnlyToleranceCalculator implements ScoreStrategy {

    private static final int NOTE_POINT = 100;
    private static final int NOTE_TOLERANCE = 50;

    /**
     * calculate the score using only tolerance in order to permit
     * the player to understand how to get better score.
     */
    @Override
    public final int scoreCalculation(
            final Columns column, final long start, final long end, final NoteRange currentRange, final int columnNumber) {
        int normalPoint = (int) ((double) (end - start - Math.abs(currentRange.getEnd() - end)
                - Math.abs(currentRange.getStart() - start)) / (end - start)  * NOTE_POINT);
        // NOTE_POINT multiplied by the percentage of match between the note and the range
        return (((normalPoint >= NOTE_POINT - NOTE_TOLERANCE 
                ? NOTE_POINT : (normalPoint + NOTE_TOLERANCE < 0 
                        ? 0 : normalPoint + NOTE_TOLERANCE))) * columnNumber);
    }

}
