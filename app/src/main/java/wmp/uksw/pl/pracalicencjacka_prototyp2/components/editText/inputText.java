package wmp.uksw.pl.pracalicencjacka_prototyp2.components.editText;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by MSI on 2016-07-09.
 */
public class InputText extends EditText {

    String text;
    Context context;

    public InputText(Context context) {
        super(context);
        init(context);
    }

    public InputText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init (Context context) {
        this.context = context;
        this.text = getText().toString();
    }

}
