package it.dukemania;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class DukeMania extends ApplicationAdapter {
	SpriteBatch batch;
	FreeTypeFontGenerator generator;
	Stage stage;
	Label.LabelStyle lblStyle;
	TextButton.TextButtonStyle txtBtnStyle;

	@Override
	public void create () {
		String TEXTURE_PATH = "textures/quantum-horizon/quantum-horizon-ui.json";
		String MENU_BACKGROUND_IMAGE_PATH = "DukeMania.png";
		String FONT_PATH = "fonts/agency-fb.ttf";

		int fontSize = 50;
		int fontBorderWidth = 3;
		Color fontColor = Color.BLACK;
		float buttonPadding = 20f;

		batch = new SpriteBatch();
		stage = new Stage(new StretchViewport(1920, 1080));

		Texture img = new Texture(MENU_BACKGROUND_IMAGE_PATH);
		Image backgroundImage = new Image(img);

		float screenWidth = stage.getWidth();
		float screenHeight = stage.getHeight();

		generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT_PATH));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.borderWidth = fontBorderWidth;
		parameter.color = fontColor;
		BitmapFont menuFont = generator.generateFont(parameter);

		Skin skin = new Skin(Gdx.files.internal(TEXTURE_PATH));
		skin.add("title", menuFont);

		txtBtnStyle = new TextButton.TextButtonStyle();
		lblStyle = new Label.LabelStyle();

		TextButton btnPlay = new TextButton("PLAY", skin);
		TextButton btnQuit = new TextButton("QUIT", skin);
		TextButton btnOptions = new TextButton("OPTIONS", skin);

		btnPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Play");
			}
		});

		btnOptions.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Options");
			}
		});

		btnQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("Quit");
			}
		});

		/*
		lblStyle.font = skin.getFont("title");
		lblStyle.fontColor = Color.BLACK;

		Label nameLabel = new Label("", lblStyle);
		TextField nameText = new TextField("", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);
		*/

		Container<Table> mainMenuContainer = new Container<>();
		Table table = new Table(skin);

		table.setDebug(false);
		table.add(btnPlay);
		table.row();
		table.add(btnOptions);
		table.row();
		table.add(btnQuit);

		table.getCells().forEach(s -> s.padTop(buttonPadding));
		mainMenuContainer.setActor(table);
		mainMenuContainer.setPosition(screenWidth / 2, (screenHeight / 2) - byPercent(screenHeight, 0.12f));

		stage.addActor(backgroundImage);
		stage.addActor(mainMenuContainer);
		Gdx.input.setInputProcessor(stage);
	}

	private float byPercent(float size, float percent) {
		return size * percent;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		//batch.draw(img, 0, 0);
		stage.draw();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
		generator.dispose();
		//img.dispose();
	}
}
