package it.dukemania.Controller.logic;

public class ScoreContext {

    private ScoreStrategy strategy;

    public ScoreContext(final ScoreStrategy strategy) {
        this.strategy = strategy;
    }

    public final int executeStrategy(
            final Columns column, final long start, final long end, final NoteRange currentRange, final int columnNumber) {
        return strategy.scoreCalculation(column, start, end, currentRange, columnNumber);
    }
}
