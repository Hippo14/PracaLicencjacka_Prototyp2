package wmp.uksw.pl.pracalicencjacka_prototyp2.components.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by MSI on 2016-07-09.
 */
public class BtnLogin extends Button implements iButton {

    public BtnLogin(Context context) {
        super(context);
    }

    public BtnLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BtnLogin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onClick(String name, String email, String password) {

    }

    @Override
    public void onClick(String email, String password) {

    }
}
