package it.dukemania.View.notesGraphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class FromEventToInput implements EventsFromKeyboard {
    private Note note;
    private List<Integer> availableKeys = new ArrayList<>();
    private int number;


    public FromEventToInput(final Note note, final int number) {
        this.note = note;
        this.number = number;
    }

    private List<Integer> setNumberOfKeys() {
        this.availableKeys.add(Input.Keys.D);
        this.availableKeys.add(Input.Keys.F);
        this.availableKeys.add(Input.Keys.J);
        this.availableKeys.add(Input.Keys.K);
        if (this.number == 4) {
            return this.availableKeys;
        }
        return this.availableKeys; //edit this method
    }

    @Override
    public boolean isColumnSelected() {
        return Gdx.input.isKeyPressed(this.associationKeyNumber(this.note.getColumn().getNumericvalue()));

    }


    @Override
    public boolean isButton1Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.D);
    }

    @Override
    public boolean isButton2Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.F);
    }

    @Override
    public boolean isButton3Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.J);
    }

    @Override
    public boolean isButton4Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.K);
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
    public int associationKeyNumber(final int column) {
        if (column == 1) {
            return Input.Keys.D;
        }
        if (column == 2) {
            return Input.Keys.F;
        }
        if (column == 3) {
            return Input.Keys.J;
        }
        if (column == 4) {
            return Input.Keys.K;
        }
        if (column == 5) {
            return Input.Keys.S;
        }
        if (column == 6) {
            return Input.Keys.L;
        }
        if (column == 7) {
            return Input.Keys.A;
        }
        if (column == 8) {
            return Input.Keys.P;
        }
        return 0;
    }

    @Override
    public boolean isButtonPressed(final int numberOfColumn) {
        return Gdx.input.isKeyPressed(this.associationKeyNumber(numberOfColumn));
    }

}
