package it.dukemania;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.dukemania.Controller.logic.ColumnLogic;
import it.dukemania.Controller.logic.ColumnLogicImpl;
import it.dukemania.Controller.logic.LogicNote;
import it.dukemania.Controller.logic.LogicNoteImpl;
import it.dukemania.View.notesGraphics.ComputingShift;
import it.dukemania.View.notesGraphics.ComputingShiftImpl;
import it.dukemania.View.notesGraphics.EventsFromKeyboard;
import it.dukemania.View.notesGraphics.EventsFromKeyboardImpl;
import it.dukemania.View.notesGraphics.Key;
import it.dukemania.View.notesGraphics.KeyImpl;
import it.dukemania.View.notesGraphics.Note;
import it.dukemania.View.notesGraphics.NoteImpl;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;
import it.dukemania.audioengine.PlayerAudio;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;


public class PlayScreen extends ApplicationAdapter implements Window {
    //Engine ae = null;

    private final Size dimensions = new SizeImpl();
    private Stage buttonsStage;
    private Stage stage;
    private final GlyphLayout layout = new GlyphLayout(); 
    private BitmapFont fontScoreboard;
    private FreeTypeFontGenerator generator;
    private TextButtonStyle styleDown;
    private TextButtonStyle styleUp;
    private Texture background;
    private Texture scoreboard;
    private Texture textureNote;
    private Texture textureSparks;
    private SpriteBatch batch;
    private SpriteBatch backgroundBatch;
    private final int posySpark;
    private int score = 0;
    private long startTime = 0;
    private EventsFromKeyboard keyboard;
    private Key key;
    private final ComputingShift shift = new ComputingShiftImpl();
    private final OrthographicCamera camera = new OrthographicCamera();
    private final int numberOfColumns;
    private float deltaTime = 0;
    private final List<TextButton> buttons = new ArrayList<>();
    private Song song;
    private MidiTrack selectedTrack;
    private final List<Note> notes = new ArrayList<>(); 
    private final ColumnLogic logic = new ColumnLogicImpl(this.dimensions.getNumberOfColumns());
    private PlayerAudio player;
    private String songHash;
    private SwitchWindowNotifier switchWindowNotifier;
    //constant
    private static final int BUTTON_DIM = 120;
    private static final int YNOTE = 80;
    private static final int FONT_SIZE = 40;

        public PlayScreen() { 
            this.numberOfColumns = this.dimensions.getNumberOfColumns();
            this.posySpark = PlayScreen.BUTTON_DIM - this.shift.getNoteShift(); 

    }
	


	@Override
	public void create() {
	    //this.dimensions = new SizeImpl();

	    final List<LogicNote> logicNotes = logic.noteQueuing(selectedTrack);
        player = new PlayerAudio(song);
	    this.background = new Texture(Gdx.files.internal("Textures/blueBackground.png"));
	    final Image backgroundImage = new Image(this.background);
	    this.textureNote = new Texture(Gdx.files.internal("Textures/blueNote.png"));
	    this.textureSparks = new Texture(Gdx.files.internal("Textures/blueSpark.png"));
	    this.scoreboard = new Texture(Gdx.files.internal("Textures/scoreboard.png"), true);
	    this.scoreboard.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	    this.batch = new SpriteBatch();
	    this.backgroundBatch = new SpriteBatch();
	    final Viewport buttonsViewport = new ExtendViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);
	    final Viewport stageViewport = new StretchViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);
	    //ae = new Engine();

	    this.buttonsStage = new Stage(buttonsViewport, this.batch);
	    this.stage = new Stage(stageViewport, this.backgroundBatch);
        Gdx.input.setInputProcessor(buttonsStage);
/*
        this.note6uno = new NoteLogicImpl(1, 200, 1, Columns.COLUMN1, 200);
        this.note7uno = new NoteLogicImpl(2, 600, 2, Columns.COLUMN2, 200);
        this.note8uno = new NoteLogicImpl(3, 1000, 3, Columns.COLUMN3, 200);
        this.note9uno = new NoteLogicImpl(4, 1400, 4, Columns.COLUMN4, 200);
        this.note10uno = new NoteLogicImpl(2, 1600, 5, Columns.COLUMN2, 200);

        this.logicNotes.add(note6uno);
        this.logicNotes.add(note7uno);
        this.logicNotes.add(note8uno);
        this.logicNotes.add(note9uno); 
        this.logicNotes.add(note10uno);

 */

        final BitmapFont font = new BitmapFont();
        final Skin skin = new Skin();
        final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("pinkAndBlueButtons.atlas"));
        skin.addRegions(atlas);

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("scoreboard_font.TTF"));
        final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = PlayScreen.FONT_SIZE;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        fontScoreboard = generator.generateFont(parameter);
        fontScoreboard.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        this.styleDown = new TextButtonStyle();
        this.styleDown.font = font;
        this.styleDown.up = skin.getDrawable("buttonDown");
        this.styleDown.down = skin.getDrawable("buttonUp");
        this.styleUp = new TextButtonStyle();
        this.styleUp.font = font;
        this.styleUp.up = skin.getDrawable("buttonUp");
        this.styleUp.down = skin.getDrawable("buttonDown");

        //placement of the buttons 
        for (int i = 0; i < this.numberOfColumns; i++) {
            this.buttons.add(new TextButton("", this.styleUp));
            this.buttons.get(i).setSize(PlayScreen.BUTTON_DIM, PlayScreen.BUTTON_DIM);  //set the size of the buttons
            this.buttons.get(i).setPosition(i * this.dimensions.getSize().getX() / this.numberOfColumns 
                    + this.shift.calculateShifting(this.numberOfColumns) * i, 0);  //set the position of each button
            this.buttonsStage.addActor(this.buttons.get(i));
        }

        for (final LogicNote noteLogic : logicNotes) {
            notes.add(associationNote(noteLogic));
        }

        //adding elements on the stage
        this.stage.addActor(backgroundImage);
        Gdx.graphics.setWindowedMode(dimensions.getSize().getX(), dimensions.getSize().getY());
        Gdx.graphics.setResizable(false);
	}
	
	
	//this method associates the logical note to the corresponding graphic note
	private Note associationNote(final LogicNote noteLogic) {
            return new NoteImpl(this.dimensions.getSize().getY(), noteLogic.getColumn(), 
                    noteLogic.getHeight() * PlayScreen.YNOTE, noteLogic.getNoteStarts(), noteLogic.getNoteDuration(), this.numberOfColumns);
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
	

	private void drawNote(final int posxNote, final int posyNote, final int xNote, final int yNote) {
	    final Rectangle clipBounds = new Rectangle(0, dimensions.getSize().getY() - 610, dimensions.getSize().getX(), 
	            dimensions.getSize().getY() - 190); //magic numbers

        this.batch.flush();
        ScissorStack.pushScissors(clipBounds);
        this.batch.draw(this.textureNote, posxNote, posyNote, xNote, yNote, 0, 1, 1, 0);
        this.batch.flush();
        ScissorStack.popScissors();

        /*renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.rect(clipBoundsprova.x, clipBoundsprova.y, clipBoundsprova.width, clipBoundsprova.height);
        renderer.circle(30, clipBounds.y, 3);
        renderer.end();*/

	}
	
	private void isSparked(final Note n) {
	    if (n.getPosyNote() <= PlayScreen.BUTTON_DIM && n.getPosyNote() >= PlayScreen.BUTTON_DIM - this.shift.getSparksHeight()) {
            this.batch.draw(this.textureSparks, n.getPosxSpark(), this.posySpark, n.getxSpark(), n.getySpark(), 0, 1, 1, 0);
        }
	}

	@Override
	public void render() {
		//ae.playBuffer();
		//set the start time
		if (this.startTime == 0) {
		    this.startTime = Instant.now().toEpochMilli();
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.stage.draw();
		this.backgroundBatch.begin();
		this.buttonsStage.draw();
		this.batch.begin();

		//draw the score and the scoreboard
		this.layout.setText(fontScoreboard, Integer.toString(this.score));
	    final float fontWidth = this.layout.width;
	    final float fontHeight = this.layout.height; 
		this.fontScoreboard.draw(batch, Integer.toString(this.score), this.dimensions.getSize().getX() / 2 - fontWidth / 2, this.dimensions.getSize().getY() - fontHeight * this.shift.getFontAccuracy());
		this.batch.draw(this.scoreboard, 0, this.dimensions.getSize().getY() - this.shift.getScoreboardHeight(),
		        this.dimensions.getSize().getX(), this.shift.getScoreboardHeight());

		//set the style of the buttons
		for (final TextButton b : this.buttons) {
		    b.setStyle(this.styleUp);
		}

        final long actualTime = Instant.now().toEpochMilli() - this.startTime;
        final List<Note> notesPlaying = getPlayingNotes(actualTime);

        //drawing of each note
        if (!notesPlaying.isEmpty()) {
            for (final Note n : notesPlaying) {
                drawNote(n.getPosxNote(), n.getPosyNote(), n.getxNote(), n.getyNote());
                this.deltaTime = Gdx.graphics.getDeltaTime();
                n.updateNote(this.deltaTime);
                this.keyboard = new EventsFromKeyboardImpl(n);
                this.key = new KeyImpl(n);


                //set the sparks
                if (this.keyboard.isColumnSelected(this.numberOfColumns)) {
                    isSparked(n);
                }


                //it returns the time when the user starts to press a key
                if (this.keyboard.isColumnSelected(this.numberOfColumns) && !n.isPressed()) { 
                    n.setIsPressed(true);
                    this.key.startPressing();
                }

                //it returns the time when the user finishes to press a key
                if (!this.keyboard.isColumnSelected(this.numberOfColumns) && n.isPressed()) {
                    n.setIsPressed(false);
                    this.key.finishPressing();
                    this.score += this.logic.verifyNote(this.key.getColumn(), this.key.getInitialTime(), this.key.getFinalTime());
                }


                //tempo di caduta
                if (n.getTimeOfFall() > 0) {
                    System.out.println("nota " + n.getColumn().name() + "tempo di caduta " + n.getTimeOfFall());
                }


                //change the style of the buttons if they are clicked
                for (int i = 0; i < this.numberOfColumns; i++) {
                    if (!this.keyboard.isButtonPressed(i + 1, this.numberOfColumns)) {
                        this.buttons.get(i).setStyle(this.styleUp);
                    } else {
                        this.buttons.get(i).setStyle(this.styleDown);
                    }
                }

            }
            //removal of notes that are terminated
            notesPlaying.removeAll(notFinished(notesPlaying, actualTime));
            }
        player.playNotes();
        if (song.getDuration() / 1000 < (Instant.now().toEpochMilli() - startTime)) {
            switchWindowNotifier.switchWindow(DukeManiaWindowState.LEADERBOARD, new Object[] {songHash, "Pluto", 69});
        }

        this.batch.end();
        this.backgroundBatch.end();


	}
	
	@Override
	public void dispose() {
	    //release of all resources
	    this.stage.dispose();
		this.batch.dispose();
		this.background.dispose();
		this.backgroundBatch.dispose();
		this.generator.dispose();
	}

    @Override
    public void receiveData(final Object data) {
        final Object[] receivedData = (Object[]) data;
        this.song = (Song) receivedData[0];
        this.selectedTrack = (MidiTrack) receivedData[1];
        this.songHash = (String) receivedData[2];
    }

    @Override
    public void setWindowListener(final SwitchWindowNotifier listener) {
        this.switchWindowNotifier = listener;
    }


}
