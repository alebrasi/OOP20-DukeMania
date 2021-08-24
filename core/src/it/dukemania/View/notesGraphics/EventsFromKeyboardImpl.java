package it.dukemania.View.notesGraphics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.dukemania.Controller.logic.Columns;



public class EventsFromKeyboardImpl implements EventsFromKeyboard {
    private final Columns column;
    private final List<Integer> keys = Arrays.asList(Input.Keys.A, Input.Keys.S, Input.Keys.D, 
            Input.Keys.F, Input.Keys.H, Input.Keys.J, Input.Keys.K, Input.Keys.L);



    public EventsFromKeyboardImpl(final Columns column) { 
        this.column = column;
    }



    @Override
    public boolean isColumnSelected(final int max) {
        return Gdx.input.isKeyPressed(this.associationKeyColumn(this.column.getNumericValue(), max));
    }





    @Override
    public int associationKeyColumn(final int column, final int max) {
        final List<Integer> usedKeys = this.keys.stream().limit(max).collect(Collectors.toList()); 
        return usedKeys.get(column - 1);
    }

    @Override
    public boolean isButtonPressed(final int column, final int max) {
        return Gdx.input.isKeyPressed(this.associationKeyColumn(column, max));
    }


    /*TASTI
     * 1 - A 
     * 2 - S
     * 3 - D
     * 4 - F
     * 5 - H
     * 6 - J
     * 7 - K
     * 8 - L
     * */

}
