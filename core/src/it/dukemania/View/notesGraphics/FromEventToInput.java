package it.dukemania.View.notesGraphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

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
        return (Gdx.input.isKeyPressed((this.associationKeyColumn(this.note.getColumn()))));

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



    @Override
    public int associationKeyColumn(final Columns column) {
        if (column.equals(Columns.COLUMN1)) {
            return Input.Keys.D;
        }
        if (column.equals(Columns.COLUMN2)) {
            return Input.Keys.F;
        }
        if (column.equals(Columns.COLUMN3)) {
            return Input.Keys.J;
        }
        if (column.equals(Columns.COLUMN4)) {
            return Input.Keys.K;
        }
        return 0;
        //TODO if setnumberofcolumn>4 
    }

}
