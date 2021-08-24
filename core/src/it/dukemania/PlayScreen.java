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
import it.dukemania.View.notesGraphics.GraphicNote;
import it.dukemania.View.notesGraphics.GraphicNoteImpl;
import it.dukemania.View.notesGraphics.Size;
import it.dukemania.View.notesGraphics.SizeImpl;
import it.dukemania.midi.ParsedTrack;
import it.dukemania.midi.Song;
import it.dukemania.windowmanager.DukeManiaWindowState;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;


public class PlayScreen extends ApplicationAdapter implements Window {

    // functional fields
    private Size dimensions;
    private Stage buttonsStage;
    private Stage stage;
    private GlyphLayout layout;
    private BitmapFont fontScoreboard;



    // window fields
    private TextButtonStyle styleDown;
    private TextButtonStyle styleUp;
    private Texture scoreboard;
    private Texture textureNote;
    private Texture textureSparks;
    private SpriteBatch batch;
    private SpriteBatch backgroundBatch;


    private final int posySpark;
    private final int finishLine;
    private int score;
    private long startTime;
    private final ComputingShift shift;
    private int numberOfColumns;
    private List<TextButton> buttons;
    private Song song;
    private ParsedTrack selectedTrack;
    private List<GraphicNote> notes;
    private final ColumnLogic logic;
    private String songHash;
    private SwitchWindowNotifier switchWindowNotifier;
    //constant
    private static final int BUTTON_DIM = 120;
    private GameModel data;
    private final AssetsManager assetManager = AssetsManager.getInstance();



    @Override
    public void dispose() {
        buttonsStage.dispose();
        stage.dispose();
        batch.dispose();
        backgroundBatch.dispose();
        this.startTime = 0;
        super.dispose();
    }

    public PlayScreen() {
        notes = new ArrayList<>();
        buttons = new ArrayList<>();
        shift = new ComputingShiftImpl();
        posySpark = PlayScreen.BUTTON_DIM - this.shift.getNoteShift();
        finishLine = PlayScreen.BUTTON_DIM;
        logic = new ColumnLogicImpl(this.numberOfColumns);

    }


    @Override
    public void create() {


        notes = new ArrayList<>();
        buttons = new ArrayList<>();
        layout = new GlyphLayout();
        final OrthographicCamera camera = new OrthographicCamera();

        dimensions = new SizeImpl(this.numberOfColumns);
        final Texture background = assetManager.getTexture("blueBackground.png");
        final Image backgroundImage = new Image(background);
        this.textureNote = assetManager.getTexture("note.png");
        this.textureSparks = assetManager.getTexture("blueSpark.png");
        this.scoreboard = assetManager.getTexture("scoreboard.png");
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
        final TextureAtlas atlas = assetManager.getTextureAtlas("pinkAndBlueButtons.atlas");

        logic.initAudio(song);
        skin.addRegions(atlas);

        this.fontScoreboard = assetManager.generateFontScoreboard();


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

        this.logic.setColumnNumber(this.numberOfColumns);
        this.logic.contextInit();
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
    private GraphicNote associationNote(final LogicNote noteLogic) {
        return new GraphicNoteImpl(this.dimensions.getSize().getY(),
            noteLogic.getColumn(),
                (int) (noteLogic.getNoteDuration()) / this.shift.getDurationOffset(),
            noteLogic.getNoteStarts() / 1000 - this.shift.getNoteStartOffset(),
            noteLogic.getNoteDuration() / 1000,
            this.numberOfColumns
        );
    }


    private void drawNote(final int posxNote, final int posyNote, final int xNote, final int yNote) {
        final Rectangle clipBounds = new Rectangle(0, dimensions.getSize().getY() - 600, dimensions.getSize().getX(), 
                dimensions.getSize().getY() - 200); //magic numbers




        this.batch.flush();
        ScissorStack.pushScissors(clipBounds);
        this.batch.draw(this.textureNote, posxNote, posyNote, xNote, yNote, 0, 1, 1, 0);
        this.batch.flush();
        ScissorStack.popScissors();


    }

    private void isSparked(final GraphicNote n) {
        if (n.getPosyNote() <= this.finishLine && n.getPosyNote() >= this.finishLine - this.shift.getSparksHeight()) {
            this.batch.draw(this.textureSparks, n.getPosxSpark(), this.posySpark, n.getxSpark(), n.getySpark(), 0, 1, 1, 0);
        }
    }

    @Override
    public void render() {
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
        final List<GraphicNote> notesPlaying = this.notes.stream().filter(n -> (n.getStartTime()) <= actualTime).collect(Collectors.toList());


        //drawing of each note
        for (final GraphicNote n : notesPlaying) {
            drawNote(n.getPosxNote(), n.getPosyNote(), n.getxNote(), n.getyNote());
            final float deltaTime = Gdx.graphics.getDeltaTime();
            if (n.getKeyboard().isEmpty()) {
                n.setKeyboard();
                n.setStartNote(Instant.now().toEpochMilli());
            }
            n.updateNote(deltaTime, this.startTime);

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
                    this.score += this.logic.verifyNote(n.getColumn(), n.getKey().get().getInitialTime() * 1000 - this.shift.getTimeShift(), (n.getKey().get().getFinalTime()) * 1000 - this.shift.getTimeShift());
                }

            }


            //set the sparks
            if (n.getKeyboard().get().isColumnSelected(this.numberOfColumns)) {
                isSparked(n);
            }


            //change the style of the buttons if they are clicked
            if (n.getKeyboard().get().isButtonPressed(n.getColumn().getNumericValue(), this.numberOfColumns)) {
                this.buttons.get(n.getColumn().getNumericValue() - 1).setStyle(this.styleDown);
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
        this.song = data.getSelectedSong();
        this.selectedTrack = data.getSelectedTrack();
        this.songHash = data.getSongHash();
        this.numberOfColumns = data.getNumColumns();
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