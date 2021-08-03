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
        return (Gdx.input.isKeyPressed(Input.Keys.D) && this.note.getColumn().equals(Columns.column1)) ? true : false;

    }

    @Override
    public boolean isColumn2Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.F) && this.note.getColumn().equals(Columns.column2)) ? true : false;
    }

    @Override
    public boolean isColumn3Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.J) && this.note.getColumn().equals(Columns.column3)) ? true : false;
    }

    @Override
    public boolean isColumn4Selected() {

        return (Gdx.input.isKeyPressed(Input.Keys.K) && this.note.getColumn().equals(Columns.column4)) ? true : false;
    }

    @Override
    public boolean isButton1Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.D) ? true : false;
    }

    @Override
    public boolean isButton2Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.F) ? true : false;
    }

    @Override
    public boolean isButton3Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.J) ? true : false;
    }

    @Override
    public boolean isButton4Pressed() {
        return Gdx.input.isKeyPressed(Input.Keys.K) ? true : false;
    }

}
