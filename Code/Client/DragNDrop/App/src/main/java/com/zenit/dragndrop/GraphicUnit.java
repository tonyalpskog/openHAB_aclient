package com.zenit.dragndrop;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by Tony Alpskog on 2013-12-21.
 */
public class GraphicUnit {
    UUID id;
    UnitType type;
    int relativeTop = 150;//TODO - Remove default values.
    int relativeLeft = 150;
    GraphicUnitWidget view;
    boolean isSelected;

    public GraphicUnit(UnitType type) {
        this(type, 150, 150);
    }

    public GraphicUnit(UnitType type, int relativeTop, int relativeLeft) {
        this.type = type;
        this.relativeTop = relativeTop;
        this.relativeLeft = relativeLeft;
        this.view = null;
        isSelected = false;
        this.id = UUID.randomUUID();
    }

    public ImageView getView(Context context) {
        if(view == null) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources()/*Resources.getSystem()*/, R.drawable.ic_lightbulb);
            view = new GraphicUnitWidget(context, bitmap);
            view.setTop(relativeTop);
            view.setLeft(relativeLeft);
            view.setTag(UUID.randomUUID());
        }
        return view;
    }
}
