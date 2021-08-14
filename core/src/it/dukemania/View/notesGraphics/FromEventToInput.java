package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;


public class FromEventToInput implements EventsFromKeyboard {
    private Note note;



    public FromEventToInput(final Note note) { 
        this.note = note;
    }



    @Override
    public boolean isColumnSelected() {
        return Gdx.input.isKeyPressed(this.associationKeyNumber(this.note.getColumn().getNumericvalue()));

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
