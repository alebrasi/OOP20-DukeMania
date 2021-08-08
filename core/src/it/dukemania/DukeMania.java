package it.dukemania;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import java.lang.Math;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

//import AudioEngine.Engine;
import it.dukemania.View.notesGraphics.Note;
import it.dukemania.View.notesGraphics.NoteImpl;
import it.dukemania.View.notesGraphics.NoteLogic;
import it.dukemania.View.notesGraphics.NoteLogicImpl;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;
import it.dukemania.View.notesGraphics.ColumnsEnum.Columns;
import it.dukemania.View.notesGraphics.EventsFromKeyboard;
import it.dukemania.View.notesGraphics.FromEventToInput;

public class DukeMania extends ApplicationAdapter {
	//Engine ae = null;
	
	//sofi
	//private Logic logic = new LogicImpl();         //rapo
	private Size dimensions = new SizeImpl();
        private Stage buttonsStage;
        private Stage stage;
        private TextureAtlas atlas;
        private Skin skin;
        private BitmapFont font;
        private TextButtonStyle styleDown;
        private TextButtonStyle styleUp;
        private TextButton button1;
        private TextButton button2;
        private TextButton button3;
        private TextButton button4;
        private Texture background;
        private Image backgroundImage;
        private int buttonHeight;
        private SpriteBatch batch;
        private SpriteBatch backgroundBatch;
        private int xNote;
        private int yNote;
        private int posySparks;
        private int posyBlue;
        private NoteLogic note6uno; //nell'attesa dell'array di rapo
        private NoteLogic note7uno;
        private NoteLogic note8uno;
        private NoteLogic note9uno;
        private NoteLogic note10uno;
        private List<Note> notes = new ArrayList<>(); 
        private List<NoteLogic> logicNotes = new ArrayList<>(); //per ora vuoto, poi array di rapo
        private List<Note> notesPlaying = new ArrayList<>(); 
        private long timeStart = 0;
        private EventsFromKeyboard keyboard;
        private OrthographicCamera camera = new OrthographicCamera();
        private Viewport buttonsViewport;
        private Viewport stageViewport;
        
        public DukeMania() { 
            this.xNote = 50;
            this.yNote = 80;
            this.buttonHeight = 2 * this.dimensions.getSize().getY() / 9;
            this.posySparks = this.buttonHeight - 35; //15
            this.posyBlue = this.buttonHeight;   //20
            //notes = logic.getnotes();      //rapo
    }
	
	@Override
	public void create () {
	    this.background = new Texture(Gdx.files.internal("galaxy.png"));
	    this.backgroundImage = new Image(this.background);
	    this.batch = new SpriteBatch();
	    this.backgroundBatch = new SpriteBatch();
	    this.buttonsViewport = new ExtendViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);
	    this.stageViewport = new StretchViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera); //backgroundimage.width
	    Container<Table> backgroundContainer = new Container<>();
	    //ae = new Engine();

	    //sofi
	    this.buttonsStage = new Stage(this.buttonsViewport, this.batch);
	    this.stage = new Stage(this.stageViewport, this.backgroundBatch);
            Gdx.input.setInputProcessor(buttonsStage);
            this.timeStart = Instant.now().toEpochMilli();
            this.note6uno = new NoteLogicImpl(3,200,1, Columns.COLUMN1, 200);
            this.note7uno = new NoteLogicImpl(2,600,2, Columns.COLUMN2, 200);
            this.note8uno = new NoteLogicImpl(1,1000,3, Columns.COLUMN3, 200);
            this.note9uno = new NoteLogicImpl(4,1400,4, Columns.COLUMN4, 200);
            this.note10uno = new NoteLogicImpl(2,1600,5, Columns.COLUMN2, 200);

            this.logicNotes.add(note6uno);
            this.logicNotes.add(note7uno);
            this.logicNotes.add(note8uno);
            this.logicNotes.add(note9uno); 
            this.logicNotes.add(note10uno);
            for (NoteLogic noteLogic : logicNotes) {
                    this.notes.add(associationNote(noteLogic));
            }
            
            
            this.font = new BitmapFont();
            this.skin = new Skin();
            this.atlas = new TextureAtlas(Gdx.files.internal("pink and blue button.atlas"));
            this.skin.addRegions(atlas);

            
            this.styleDown = new TextButtonStyle();
            this.styleDown.font = font;
            this.styleDown.up = this.skin.getDrawable("button up");
            this.styleDown.down = this.skin.getDrawable("botton down");
            this.styleUp = new TextButtonStyle();
            this.styleUp.font = font;
            this.styleUp.up = this.skin.getDrawable("botton down");
            this.styleUp.down = this.skin.getDrawable("button up");
            
            this.button1 = new TextButton("", this.styleUp);
            this.button2 = new TextButton("", this.styleUp);
            this.button3 = new TextButton("", this.styleUp);
            this.button4 = new TextButton("", this.styleUp);
            
            //set the position of each button
            this.button1.setPosition(0,0);
            this.button2.setPosition(this.dimensions.getSize().getX() / 4,0);
            this.button3.setPosition(2 * this.dimensions.getSize().getX() / 4,0);
            this.button4.setPosition(3 * this.dimensions.getSize().getX() / 4,0);
            //set the size of the buttons
            this.button1.setSize(this.dimensions.getSize().getX() / 4, buttonHeight);
            this.button2.setSize(this.dimensions.getSize().getX() / 4, buttonHeight);
            this.button3.setSize(this.dimensions.getSize().getX() / 4, buttonHeight);
            this.button4.setSize(this.dimensions.getSize().getX() / 4, buttonHeight);
            


            this.buttonsStage.addActor(button1);
            this.buttonsStage.addActor(button2);
            this.buttonsStage.addActor(button3);
            this.buttonsStage.addActor(button4);
            this.stage.addActor(backgroundImage);
            this.stage.addActor(backgroundContainer);
            //button.setTransform(true);
            //button.setScale(0.2f);
                
                
	}
	
	private Note associationNote(NoteLogic noteLogic) {
            return new NoteImpl(this.dimensions.getSize().getY(), this.dimensions.getSize().getX(), noteLogic.getColumn(), this.batch, posyBlue, posySparks, noteLogic.getHeight() * this.yNote, noteLogic.getTimeStart(), noteLogic.getDuration());
        }
	
	//this method returns the notes that are playing right now
	private List<Note> isPlaying(long actualTime) {
	    List<Note> playing = new ArrayList<>();
            for (Note n : this.notes) {
                if (n.getStartTime() <= actualTime) {
                    playing.add(n);
                }
            }
	    return playing;
	    
	}
	
	//this method returns the notes that have already finished playing
	private List<Note> notFinished (List<Note> playing, long actualTime){
	    List<Note> finished = new ArrayList<>();
	    for (Note n : playing) {
	        if (n.getStartTime() + n.getDuration() * (long) Math.pow(10, 3) >= actualTime) {
	            finished.add(n);
	        }
	    }
            return finished;
	    
	}

	@Override
	public void render () {
	        //Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ae.playBuffer();
		this.stage.draw();
		this.backgroundBatch.begin();
		this.buttonsStage.draw();
		this.batch.begin();
		
		
		//sofi
		button1.setStyle(this.styleUp);
                button2.setStyle(this.styleUp);
                button3.setStyle(this.styleUp);
                button4.setStyle(this.styleUp);
                
                long actualTime = Instant.now().toEpochMilli() - this.timeStart;
                
                this.notesPlaying = isPlaying(actualTime);
                
                
                
                if (!this.notesPlaying.isEmpty()) {
                    for (Note n : this.notesPlaying) {
                        n.drawNote();
                        this.keyboard = new FromEventToInput(n);
                        
                        if (this.keyboard.isColumn1Selected()) {
                            button1.setStyle(this.styleDown);
                            n.isSparked(Columns.COLUMN1, this.batch);
                        }
             
                        if (this.keyboard.isColumn2Selected()) {
                                button2.setStyle(this.styleDown);
                                n.isSparked(Columns.COLUMN2, this.batch);
                        }
                        if (this.keyboard.isColumn3Selected()) {
                                button3.setStyle(this.styleDown);
                                n.isSparked(Columns.COLUMN3, this.batch);
                        }
                        if (this.keyboard.isColumn4Selected()) {
                                button4.setStyle(this.styleDown);
                                n.isSparked(Columns.COLUMN4, this.batch);
                        }
                        
                        if (!this.keyboard.isButton1Pressed()) {
                                button1.setStyle(this.styleUp);
                        }
                        if (!this.keyboard.isButton2Pressed()) {
                                button2.setStyle(this.styleUp);
                        }
                        if (!this.keyboard.isButton3Pressed()) {
                                button3.setStyle(this.styleUp);
                        }
                        if (!this.keyboard.isButton4Pressed()) {
                                button4.setStyle(this.styleUp);
                        }
                    }
                    this.notesPlaying.removeAll(notFinished(this.notesPlaying, actualTime));
                    
                }
        
        this.batch.end();
        this.backgroundBatch.end();
        

	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
		this.background.dispose();
		this.backgroundBatch.dispose();
	}
	
	
}
