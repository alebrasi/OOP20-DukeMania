package it.dukemania;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
import it.dukemania.Model.GameModel;
import it.dukemania.View.notesGraphics.AssetsManager;
import it.dukemania.View.notesGraphics.ComputingShift;
import it.dukemania.View.notesGraphics.ComputingShiftImpl;
import it.dukemania.View.notesGraphics.EventsFromKeyboard;
import it.dukemania.View.notesGraphics.Key;
import it.dukemania.View.notesGraphics.Note;
import it.dukemania.View.notesGraphics.NoteImpl;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;
import it.dukemania.midi.MidiTrack;
import it.dukemania.midi.Song;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;


public class PlayScreen extends ApplicationAdapter implements Window {

    // functional fields
    private final Size dimensions;
    private Stage buttonsStage;
    private Stage stage;
    private GlyphLayout layout;
    private BitmapFont fontScoreboard;
    private FreeTypeFontGenerator generator;


    // window fields
    private TextButtonStyle styleDown;
    private TextButtonStyle styleUp;
    private Texture background;
    private Texture scoreboard;
    private Texture textureNote;
    private Texture textureSparks;
    private SpriteBatch batch;
    private SpriteBatch backgroundBatch;


    private String text;
    private final int posySpark;
    private final int finishLine;
    private int score = 0;
    private long startTime = 0;
    private EventsFromKeyboard keyboard;
    private Key key;
    private final ComputingShift shift;
    private OrthographicCamera camera;
    private final int numberOfColumns;
    private float deltaTime = 0;
    private List<TextButton> buttons;
    private Song song;
    private MidiTrack selectedTrack;
    private List<Note> notes;
    private final ColumnLogic logic;
    //private PlayerAudio player;
    private String songHash;
    private SwitchWindowNotifier switchWindowNotifier;
    //constant
    private static final int BUTTON_DIM = 120;
    private static final int YNOTE = 80;
    private GameModel data;
    private AssetsManager ass = AssetsManager.getInstance();



    @Override
    public void dispose() {
        buttonsStage.dispose();
        stage.dispose();
        //fontScoreboard.dispose();
        //generator.dispose();
        //background.dispose();
        //scoreboard.dispose();
        //textureNote.dispose();
        //textureSparks.dispose();
        batch.dispose();
        backgroundBatch.dispose();
        this.startTime = 0;
        super.dispose();
    }

    public PlayScreen() {
        dimensions = new SizeImpl();
        notes = new ArrayList<>();
        buttons = new ArrayList<>();
        shift = new ComputingShiftImpl();
        numberOfColumns = this.dimensions.getNumberOfColumns();
        posySpark = PlayScreen.BUTTON_DIM - this.shift.getNoteShift();
        finishLine = PlayScreen.BUTTON_DIM;
        logic = new ColumnLogicImpl(this.dimensions.getNumberOfColumns());

    }

    private BitmapFont font;
    private Skin skin;
    private TextureAtlas atlas;

    @Override
    public void create() {


        notes = new ArrayList<>();
        buttons = new ArrayList<>();
        layout = new GlyphLayout();
        camera = new OrthographicCamera();


        this.background = ass.getTexture("blueBackground.png");
        final Image backgroundImage = new Image(this.background);
        this.textureNote = ass.getTexture("note.png");
        this.textureSparks = ass.getTexture("blueSpark.png");
        this.scoreboard = ass.getTexture("scoreboard.png");
        this.scoreboard.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.batch = new SpriteBatch();
        this.backgroundBatch = new SpriteBatch();
        final Viewport buttonsViewport = new ExtendViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);
        final Viewport stageViewport = new StretchViewport(this.dimensions.getSize().getX(), this.dimensions.getSize().getY(), camera);


        this.buttonsStage = new Stage(buttonsViewport, this.batch);
        this.stage = new Stage(stageViewport, this.backgroundBatch);
        Gdx.input.setInputProcessor(buttonsStage);


        final BitmapFont font = new BitmapFont();
        final Skin skin = new Skin();
        final TextureAtlas atlas = ass.getTextureAtlas("pinkAndBlueButtons.atlas");

        logic.initAudio(song);
        skin.addRegions(atlas);

        this.fontScoreboard = ass.generateFontScoreboard();

        /*this.generator = new FreeTypeFontGenerator(Gdx.files.internal("scoreboard_font.TTF"));
        final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = PlayScreen.FONT_SIZE;
        parameter.color = Color.WHITE;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        fontScoreboard = generator.generateFont(parameter);
        fontScoreboard.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
         */

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

        final List<LogicNote> logicNotes = logic.noteQueuing(selectedTrack);
        for (final LogicNote noteLogic : logicNotes) {
            notes.add(associationNote(noteLogic));
        }

        //adding elements on the stage
        this.stage.addActor(backgroundImage);

        Gdx.graphics.setResizable(false);
        Gdx.graphics.setWindowedMode(dimensions.getSize().getX(), dimensions.getSize().getY());
    }

    //this method associates the logical note to the corresponding graphic note
    private Note associationNote(final LogicNote noteLogic) {
        return new NoteImpl(this.dimensions.getSize().getY(),
            noteLogic.getColumn(),
                (int) (noteLogic.getNoteDuration()) / 5500,
            noteLogic.getNoteStarts() / 1000 - 2500,
            noteLogic.getNoteDuration() / 1000,
            this.numberOfColumns
        );
    }

    //this method returns the notes that are playing right now
    /*private List<Note> getPlayingNotes(final long actualTime) {
        final List<Note> playing = new ArrayList<>();
            for (final Note n : this.notes) {
                if (n.getStartTime() <= actualTime) {
                    playing.add(n);
                }
            }
        return playing;

    }*/


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
        if (n.getPosyNote() <= this.finishLine && n.getPosyNote() >= this.finishLine - this.shift.getSparksHeight()) {
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
        this.stage.act();
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
        //returns the notes that are playing right now
        final List<Note> notesPlaying = this.notes.stream().filter(n -> (n.getStartTime()) <= actualTime).collect(Collectors.toList()); //getPlayingNotes(actualTime);


        //drawing of each note
        for (final Note n : notesPlaying) {
            drawNote(n.getPosxNote(), n.getPosyNote(), n.getxNote(), n.getyNote());
            this.deltaTime = Gdx.graphics.getDeltaTime();
            if (n.getKeyboard().isEmpty()) {
                n.setKeyboard();
                n.setStartNote(Instant.now().toEpochMilli());
            }
            n.updateNote(this.deltaTime, this.startTime);

            //it returns the time when the user starts to press a key
            if (n.getKeyboard().get().isColumnSelected(this.numberOfColumns) && !n.isPressed()) {
                n.setIsPressed(true);
                if (n.getKey().isEmpty()) {
                    n.setKey();
                }
                n.getKey().get().startPressing(this.startTime);
            } else {
                //it returns the time when the user finishes to press a key
                if (!n.getKeyboard().get().isColumnSelected(this.numberOfColumns) && n.isPressed()) {
                    n.setIsPressed(false);
                    n.getKey().get().finishPressing(this.startTime);
                    this.score += this.logic.verifyNote(n.getColumn(), ((n.getKey().get().getInitialTime()) * 1000 - 250000), ((n.getKey().get().getFinalTime()) * 1000)-250000);
                }

            }


            //set the sparks
            if (n.getKeyboard().get().isColumnSelected(this.numberOfColumns)) {
                isSparked(n);
            }





            /*//it returns the time when the user starts to press a key
            if (this.keyboard.isColumnSelected(this.numberOfColumns) && !n.isPressed()) { 
                n.setIsPressed(true);
                this.key.startPressing();
            }*/ //delete this




            //change the style of the buttons if they are clicked
            for (int i = 0; i < this.numberOfColumns; i++) {
                if (!n.getKeyboard().get().isButtonPressed(i + 1, this.numberOfColumns)) {
                    this.buttons.get(i).setStyle(this.styleUp);
                } else {
                    this.buttons.get(i).setStyle(this.styleDown);
                }
            }

        }
        //removal of notes that are terminated
        notesPlaying.removeIf(x -> (x.getStartTime()  + x.getDuration()) / (long) Math.pow(10, 3) >= actualTime);

        //player.playNotes();
        if ((song.getDuration() / 1000) + 1000 < (Instant.now().toEpochMilli() - startTime)) {
            data.setScore(score);
            switchWindowNotifier.switchWindow(DukeManiaWindowState.LEADERBOARD, data);
            this.startTime = 0;
            this.score = 0;
        } else {
            logic.play();
        }



        this.batch.end();
        this.backgroundBatch.end();


    }



    @Override
    public void receiveData(final GameModel data) {
        /*
        final Object[] receivedData = (Object[]) data;
        this.song = (Song) receivedData[0];
        this.selectedTrack = (MidiTrack) receivedData[1];
        this.songHash = (String) receivedData[2];
         */
        this.data = data;
        this.song = data.getSelectedSong();
        this.selectedTrack = data.getSelectedTrack();
        this.songHash = data.getSongHash();
    }

    @Override
    public void setWindowListener(final SwitchWindowNotifier listener) {
        this.switchWindowNotifier = listener;
    }

    @Override
    public void resize(final int width, final int height) {
        if (width != dimensions.getSize().getX() && height != dimensions.getSize().getY()) {
            Gdx.graphics.setWindowedMode(dimensions.getSize().getX(), dimensions.getSize().getY());
        }
    }

}