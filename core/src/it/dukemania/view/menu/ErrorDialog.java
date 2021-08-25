package it.dukemania.view.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class ErrorDialog extends Dialog {

    public ErrorDialog(final String error, final Skin skin) {
        super("", skin);
        ErrorDialog dialog = this;
        TextButton btnOk = new TextButton("OK", skin);
        this.getContentTable().add(new Label(error, skin));
        this.getButtonTable().add(btnOk);
        btnOk.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                dialog.hide();
            }
        });
    }
}
