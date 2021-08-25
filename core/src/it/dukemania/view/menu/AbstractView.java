package it.dukemania.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import it.dukemania.model.GameModel;
import it.dukemania.view.notesGraphics.AssetsManager;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;


public abstract class AbstractView implements Window {

    /**
     * The associated SwitchWindowNotifier.
     */
    protected SwitchWindowNotifier switchWindowNotifier = null;
    /**
     * The main stage of the view.
     */
    protected Stage mainStage;
    /**
     * The background stage of the view.
     */
    protected Stage backgroundStage;
    /**
     * The associated camera to the view.
     */
    protected Camera camera;
    /**
     * The associated skin.
     */
    protected Skin skin;
    /**
     * The background image.
     */
    protected Image backgroundImage;
    /**
     * The data received from the WindowManager.
     */
    protected GameModel data;

    private Texture backgroundTexture;
    private static final int VIEWPORT_WIDTH = Gdx.graphics.getWidth() * 4;
    private static final int VIEWPORT_HEIGHT = Gdx.graphics.getHeight() * 4;
    private final String backgroundPath;

    public AbstractView(final String backgroundPath, final Skin skin) {
        this.skin = skin;
        this.backgroundPath = backgroundPath;
    }

    private void setupStages() {
        backgroundImage = new Image(backgroundTexture);
        mainStage = new Stage(new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera));
        backgroundStage = new Stage(new StretchViewport(backgroundImage.getWidth(), backgroundImage.getHeight()));
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        //Centers the camera to the viewport
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create() {
        backgroundTexture = AssetsManager.getInstance().getTexture(backgroundPath);
        setupCamera();
        setupStages();
        backgroundStage.addActor(backgroundImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        mainStage.act();
        backgroundStage.act();
        backgroundStage.draw();
        mainStage.draw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        mainStage.dispose();
        backgroundStage.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(final int width, final int height) {
        mainStage.getViewport().update(width, height);
        backgroundStage.getViewport().update(width, height);
        backgroundStage.getViewport().apply();
        mainStage.getViewport().apply();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWindowListener(final SwitchWindowNotifier notifier) {
        this.switchWindowNotifier = notifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void receiveData(final GameModel data) {
        this.data = data;
    }
}
