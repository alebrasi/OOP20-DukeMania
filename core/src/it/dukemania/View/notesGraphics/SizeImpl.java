package it.dukemania.View.notesGraphics;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeImpl implements Size{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int widthGUI =  (int) screenSize.getWidth();
    int heightGUI = (int) screenSize.getHeight();
    int width = widthGUI / 4;
    int height = heightGUI / 2;
    Pair<Integer, Integer> windowsize = new Pair<>(width,height);
    
    @Override
    public Pair<Integer, Integer> getSize() {
        return this.windowsize;
    }
}
