package it.dukemania.View.notesGraphics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;



public class EventsFromKeyboardImpl implements EventsFromKeyboard {
    private final Note note;
    private final List<Integer> keys = Arrays.asList(Input.Keys.D, Input.Keys.F, Input.Keys.J, 
            Input.Keys.K, Input.Keys.S, Input.Keys.L, Input.Keys.A, Input.Keys.P);



    public EventsFromKeyboardImpl(final Note note) { 
        this.note = note;
    }



    @Override
    public boolean isColumnSelected(final int max) {
        return Gdx.input.isKeyPressed(this.associationKeyColumn(this.note.getColumn().getNumericValue(), max));
    }



    /*TASTI
     * 1 - D 
     * 2 - F
     * 3 - J
     * 4 - K
     * 5 - S
     * 6 - L
     * 7 - A
     * 8 - P
     * */

    @Override
    public int associationKeyColumn(final int column, final int max) {
        final List<Integer> usedKeys = this.keys.stream().limit(max).collect(Collectors.toList()); 
        return usedKeys.get(column - 1);
    }

    @Override
    public boolean isButtonPressed(final int column, final int max) {
        return Gdx.input.isKeyPressed(this.associationKeyColumn(column, max));
    }



}
