package michelsmartproduction.getmeback;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class CustomButton extends Button {

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        //accède aux attributs ajoutés à cette CustomTextView
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        //récupère l'attribut font
        String fontName = styledAttrs.getString(R.styleable.CustomTextView_font);

        //permet au garbage collector de récupérer l'espace utilisé
        styledAttrs.recycle();

        //modifie la font de cet élément
        setTypeFace(fontName);
    }

    public void setTypeFace(String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }

}