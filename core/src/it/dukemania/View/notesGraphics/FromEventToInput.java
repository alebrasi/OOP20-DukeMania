package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public class FromEventToInput implements EventsFromKeyboard{
    private Note note;
    
    

    public FromEventToInput(Note note) {
        this.note = note;
    }

    @Override
    public boolean isColumn1Selected() {
        return (Gdx.input.isKeyPressed(Input.Keys.D) && this.note.getColumn().equals(Columns.COLUMN1));

    }

    @Override
    public boolean isColumn2Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.F) && this.note.getColumn().equals(Columns.COLUMN2));
    }

    @Override
    public boolean isColumn3Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.J) && this.note.getColumn().equals(Columns.COLUMN3));
    }

    @Override
    public boolean isColumn4Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.K) && this.note.getColumn().equals(Columns.COLUMN4));
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

}
