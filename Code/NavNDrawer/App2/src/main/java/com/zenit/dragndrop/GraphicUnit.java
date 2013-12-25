package com.zenit.dragndrop;

import android.content.Context;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by Tony Alpskog in 2013.
 */
public class GraphicUnit {
    UUID id;
    UnitType type;
    float roomRelativeX = 0;
    float roomRelativeY = 0;
    private GraphicUnitWidget view;
    private boolean isSelected;

    public GraphicUnit(UnitType type) {
        this(type, 3, 4);
    }

    public GraphicUnit(UnitType type, int roomRelativeX, int roomRelativeY) {
        this.type = type;
        this.view = null;
        isSelected = false;
        this.roomRelativeX = roomRelativeX;
        this.roomRelativeX = roomRelativeX;
        this.id = UUID.randomUUID();
    }

    public ImageView getView(Context context) {
        if(view == null) {
            view = new GraphicUnitWidget(context, this);
//            view.setTop(relativeTop);
//            view.setLeft(relativeLeft);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT
//            );
//            params.setMargins(relativeLeft, relativeTop, 0, 0);
//            view.setLayoutParams(params);
            view.setTag(id);
            view.setSelected(isSelected);
        }
        return view;
    }

    public void resetView() {
        view = null;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if(view != null) {
            view.setSelected(isSelected);
            view.drawSelection(isSelected);
        }
    }}
