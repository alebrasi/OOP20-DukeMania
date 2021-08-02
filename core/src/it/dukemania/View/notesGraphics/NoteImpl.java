package it.dukemania.View.notesGraphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;

public class NoteImpl implements Note {


        private SpriteBatch batchNote;
	private Texture textureNote;
	private Texture textureSparks;
	private int posxNote; //note's position
	private int posyNote;
	private int xNote;
	private int yNote; //texture's dimension
	private Double speedNote;
	private Columns column;
	private int posyBlue;
	private int posxSparks;
	private int posySparks;
	private int xSparks;
	private int ySparks;
	private long startNote;
	private long duration;
	
	
	public NoteImpl(int heightpos, int width, Columns letter, SpriteBatch batch, int posyBlue, int posySparks, int height, long startNote, long duration) {
		this.batchNote = batch;
		this.textureNote = new Texture(Gdx.files.internal("nota azzurra.png"));
		this.textureSparks = new Texture(Gdx.files.internal("blue spark.png"));
		this.xNote = 50;
		this.yNote = height;
		this.column = letter;
		if(letter.equals(Columns.column1)) {
			this.posxNote = width / 8 - this.xNote / 2;
		} if(letter.equals(Columns.column2)) {
			this.posxNote = 3 * width / 8 - this.xNote / 2;
		} if(letter.equals(Columns.column3)) {
			this.posxNote = 5 * width / 8 - this.xNote / 2;
		} if(letter.equals(Columns.column4)) {
			this.posxNote = 7 * width / 8 - this.xNote / 2;
		} 
		this.posyNote = heightpos;
		this.speedNote = 200.0;
		this.posyBlue = posyBlue;
		this.posySparks = posySparks;
		this.posxSparks = this.posxNote - 25;
		this.xSparks = 100;
		this.ySparks = 70;
		this.startNote = startNote;
		this.duration = duration;
	}

	@Override
	public void drawNote() {
		if (this.posyNote  > 0) {
			this.posyNote -= (this.speedNote * Gdx.graphics.getDeltaTime());
			this.batchNote.draw(this.textureNote, this.posxNote, this.posyNote, this.xNote, this.yNote, 0, 1, 1, 0);
			
		}

	}

	@Override
	public void isSparked(Columns type, SpriteBatch sparks) {
		if (this.posyNote <= this.posyBlue && this.posyNote >= this.posyBlue - 40){
			sparks.draw(this.textureSparks, this.posxSparks, this.posySparks, this.xSparks, this.ySparks, 0, 1, 1, 0);
		}
		
	}

	@Override
	public long getStartTime() {
		return this.startNote;
		
	}
	
	@Override
	public long getDuration() {
	    return this.duration;
	}


	@Override
	public Columns getColumn() {
		return this.column;
	}

	@Override
	public int getPosyNote() {
		return this.posyNote;
	}

    
	


}
