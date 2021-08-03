package it.dukemania.View;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import it.dukemania.windowmanager.SwitchWindowNotifier;
import it.dukemania.windowmanager.Window;


//TODO Make factory?
public abstract class AbstractView implements Window {

    protected SwitchWindowNotifier switchWindowNotifier = null;
    protected Stage mainStage;
    protected Stage backgroundStage;
    protected Camera camera;
    protected Skin skin;
    protected Image backgroundImage;

    private Texture backgroundTexture;

    public AbstractView(final String backgroundPath, final Skin skin) {
        backgroundTexture = new Texture(backgroundPath);
        this.skin = skin;
    }

    private void setupStages() {
        backgroundImage = new Image(backgroundTexture);
        mainStage = new Stage(new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera));
        backgroundStage = new Stage(new StretchViewport(backgroundImage.getWidth(), backgroundImage.getHeight()));
    }

    private void setupCamera() {
        //TODO Change magic numbers
        camera = new OrthographicCamera(2560, 1440);
        //Centers the camera to the viewport
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void create() {
        setupCamera();
        setupStages();
        backgroundStage.addActor(backgroundImage);
    }

    @Override
    public void render() {
        mainStage.act();
        backgroundStage.act();
        backgroundStage.draw();
        mainStage.draw();
    }

    @Override
    public void dispose() {
        mainStage.dispose();
        backgroundStage.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void resize(final int width, final int height) {
        mainStage.getViewport().update(width, height);
        backgroundStage.getViewport().update(width, height);
        backgroundStage.getViewport().apply();
        mainStage.getViewport().apply();
    }

    @Override
    public void setWindowListener(final SwitchWindowNotifier notifier) {
        this.switchWindowNotifier = notifier;
    }
}
