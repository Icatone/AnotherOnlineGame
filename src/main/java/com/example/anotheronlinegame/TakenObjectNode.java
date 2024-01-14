package com.example.anotheronlinegame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class TakenObjectNode extends ImageView {
    public TakenObjectNode(Image sprite) {
        super(sprite);
    }

    public void randomPLace(int MaxY,int MaxX) {
        Random random = new Random();
        super.setX(random.nextDouble()*(MaxX-super.getBoundsInLocal().getWidth()));
        super.setY(random.nextDouble()*(MaxY-super.getBoundsInLocal().getHeight()));
    }
}
