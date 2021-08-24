package it.dukemania.View.notesGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeImpl implements Size {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int widthGUI =  (int) screenSize.getWidth();
    private final int heightGUI = (int) screenSize.getHeight();
    private final int numberOfColumns;
    private final Pair<Integer, Integer> windowsize;


    public SizeImpl(final int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        final int width = this.widthGUI / (this.numberOfColumns == 4 ? 4 : 2);
        final int height = heightGUI * 2 / 3;
        this.windowsize = new Pair<>(width, height);
    }


    @Override
    public Pair<Integer, Integer> getSize() {
        return this.windowsize;
    }


    @Override
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }
}
