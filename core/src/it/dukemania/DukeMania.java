package it.dukemania;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;




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

import it.dukemania.View.notesGraphics.Columns;
import it.dukemania.View.notesGraphics.ComputingShift;
import it.dukemania.View.notesGraphics.ComputingShiftImpl;
import it.dukemania.View.notesGraphics.EventsFromKeyboard;
import it.dukemania.View.notesGraphics.FromEventToInput;
import it.dukemania.View.notesGraphics.Key;
import it.dukemania.View.notesGraphics.KeyImpl;
import it.dukemania.View.notesGraphics.Note;
import it.dukemania.View.notesGraphics.NoteImpl;
import it.dukemania.View.notesGraphics.NoteLogic;
import it.dukemania.View.notesGraphics.NoteLogicImpl;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;


public class DukeMania extends ApplicationAdapter {
    //Engine ae = null;

    //sofi
    //private Logic logic = new LogicImpl();         //rapo
    private final Size dimensions;
    private Stage buttonsStage;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont font;
    private TextButtonStyle styleDown;
    private TextButtonStyle styleUp;
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
    private Key key;
    private ComputingShift shift = new ComputingShiftImpl();
    private OrthographicCamera camera = new OrthographicCamera();
    private Viewport buttonsViewport;
    private Viewport stageViewport;
    private final int numberOfColumns;
    private List<TextButton> buttons = new ArrayList<>();
    private static final int BUTTONDIM = 120;

        public DukeMania() { 
            this.xNote = 50;
            this.yNote = 80;
            this.dimensions = new SizeImpl();
            this.numberOfColumns = this.dimensions.getNumberOfColumns();
            this.buttonHeight = this.BUTTONDIM;
            this.posySparks = this.buttonHeight - 35; //15
            this.posyBlue = this.buttonHeight;   //20
            //notes = logic.getnotes();      //rapo
    }
	


	@Override
	public void create() {
	    this.background = new Texture(Gdx.files.internal("galaxy.png"));
	    this.backgroundImage = new Image(this.background);
	    this.batch = new SpriteBatch();
	    this.backgroundBatch = new SpriteBatch();
	    this.buttonsViewport = new ExtendViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);
	    this.stageViewport = new StretchViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera); //backgroundimage.width
	    //ae = new Engine();

	    //sofi
	    this.buttonsStage = new Stage(this.buttonsViewport, this.batch);
	    this.stage = new Stage(this.stageViewport, this.backgroundBatch);
        Gdx.input.setInputProcessor(buttonsStage);
        this.timeStart = Instant.now().toEpochMilli();
        this.note6uno = new NoteLogicImpl(3, 200, 1, Columns.COLUMN1, 200);
        this.note7uno = new NoteLogicImpl(2, 600, 2, Columns.COLUMN2, 200);
        this.note8uno = new NoteLogicImpl(1, 1000, 3, Columns.COLUMN3, 200);
        this.note9uno = new NoteLogicImpl(4, 1400, 4, Columns.COLUMN4, 200);
        this.note10uno = new NoteLogicImpl(2, 1600, 5, Columns.COLUMN2, 200);

        this.logicNotes.add(note6uno);
        this.logicNotes.add(note7uno);
        this.logicNotes.add(note8uno);
        this.logicNotes.add(note9uno); 
        this.logicNotes.add(note10uno);

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

        //placement of the buttons 
        for (int i = 0; i < this.numberOfColumns; i++) {
            this.buttons.add(new TextButton("", this.styleUp));
            this.buttons.get(i).setSize(this.BUTTONDIM, this.BUTTONDIM);  //set the size of the buttons
            this.buttons.get(i).setPosition(i * this.dimensions.getSize().getX() / this.numberOfColumns + this.shift.calculateShifting(this.numberOfColumns) * i, 0);  //set the position of each button
            //this.buttons.get(i).setTransform(true);
            //this.buttons.get(i).setScale(0.6f);
            this.buttonsStage.addActor(this.buttons.get(i));
        }

        for (final NoteLogic noteLogic : logicNotes) {
            this.notes.add(associationNote(noteLogic));
        }

        //adding elements on the stage
        this.stage.addActor(backgroundImage);

	}
	
	
	//this method associates the logical note to the corresponding graphic note
	private Note associationNote(final NoteLogic noteLogic) {
            return new NoteImpl(this.dimensions.getSize().getY(), this.dimensions.getSize().getX(), noteLogic.getColumn(), this.batch, posyBlue, posySparks, noteLogic.getHeight() * this.yNote, noteLogic.getTimeStart(), noteLogic.getDuration(), this.numberOfColumns);
        }
	
	//this method returns the notes that are playing right now
	private List<Note> getPlayingNotes(final long actualTime) {
	    final List<Note> playing = new ArrayList<>();
            for (final Note n : this.notes) {
                if (n.getStartTime() <= actualTime) {
                    playing.add(n);
                }
            }
	    return playing;

	}
	
	//this method returns the notes that have already finished playing
	private List<Note> notFinished(final List<Note> playing, final long actualTime) {
	    final List<Note> finished = new ArrayList<>();
	    for (final Note n : playing) {
	        if (n.getStartTime() + n.getDuration() * (long) Math.pow(10, 3) >= actualTime) {
	            finished.add(n);
	        }
	    }
            return finished;

	}

	@Override
	public void render() {
	    //Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ae.playBuffer();
		this.stage.draw();
		this.backgroundBatch.begin();
		this.buttonsStage.draw();
		this.batch.begin();

		//sofi
		//set the style of the buttons
		for (final TextButton b : this.buttons) {
		    b.setStyle(this.styleUp);
		}

        final long actualTime = Instant.now().toEpochMilli() - this.timeStart;

        this.notesPlaying = getPlayingNotes(actualTime);

        //drawing of each note
        if (!this.notesPlaying.isEmpty()) {
            for (final Note n : this.notesPlaying) {
                n.drawNote();
                this.keyboard = new FromEventToInput(n);
                this.key = new KeyImpl(n, actualTime);

                //set the sparks
                if (this.keyboard.isColumnSelected()) {
                    n.isSparked(n.getColumn(), this.batch);
                }

                /*//it returns the time when the user starts to press a key
                if (this.keyboard.isColumnSelected() && !n.isPressed()) { 
                    n.setIsPressed(true);
                    this.key.startPressing();
                }*/

                /*//it returns the time when the user finishes to press a key
                if (!this.keyboard.isColumnSelected() && n.isPressed()) {
                    n.setIsPressed(false);
                    this.key.finishPressing();
                }*/


                /*//tempo di caduta
                if (n.getTimeOfFall() > 0) {
                    System.out.println("nota " + n.getColumn().name() + "tempo di caduta " + n.getTimeOfFall());
                }*/

                //change the style of the buttons if they are clicked
                for (int i = 0; i < this.numberOfColumns; i++) {
                    if (!this.keyboard.isButtonPressed(i + 1)) {
                        this.buttons.get(i).setStyle(this.styleUp);
                    } else {
                        this.buttons.get(i).setStyle(this.styleDown);
                    }
                }

            }
            //removal of notes that are terminated
            this.notesPlaying.removeAll(notFinished(this.notesPlaying, actualTime));
            }

        this.batch.end();
        this.backgroundBatch.end();


	}
	
	@Override
	public void dispose() {
	    this.stage.dispose();
		this.batch.dispose();
		this.background.dispose();
		this.backgroundBatch.dispose();
	}
	
	
}
