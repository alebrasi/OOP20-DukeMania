package it.dukemania.View.notesGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeImpl implements Size {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int widthGUI =  (int) screenSize.getWidth();
    private int heightGUI = (int) screenSize.getHeight();
    private int numberOfColumns = 4;
    private int width = this.widthGUI / (this.numberOfColumns == 4 ? 4 : 2);
    private int height = heightGUI * 2 / 3;
    private Pair<Integer, Integer> windowsize = new Pair<>(width, height);




    @Override
    public Pair<Integer, Integer> getSize() {
        return this.windowsize;
    }


    @Override
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }
}
