package it.dukemania.View.notesGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeImpl implements Size {
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int widthGUI =  (int) screenSize.getWidth();
    private final int heightGUI = (int) screenSize.getHeight();
    private final int numberOfColumns = 4;
    private final int width = this.widthGUI / (this.numberOfColumns == 4 ? 4 : 2);
    private final int height = heightGUI * 2 / 3;
    private final Pair<Integer, Integer> windowsize = new Pair<>(width, height);




    @Override
    public Pair<Integer, Integer> getSize() {
        return this.windowsize;
    }


    @Override
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }
}
