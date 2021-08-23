package it.dukemania.View.notesGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeImpl implements Size {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int widthGUI =  (int) screenSize.getWidth();
    private final int heightGUI = (int) screenSize.getHeight();
    private final int numberOfColumns;
    private final int width;
    private final int height;
    private final Pair<Integer, Integer> windowsize;


    public SizeImpl(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        this.width = this.widthGUI / (this.numberOfColumns == 4 ? 4 : 2);
        this.height = heightGUI * 2 / 3;
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
