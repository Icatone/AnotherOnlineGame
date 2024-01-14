package com.example.anotheronlinegame;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PersonNode extends ImageView {
    double speed = 5.0;
    List<TakenObjectNode> takenObjects = new ArrayList<>();
    TakenObjectNode choozenTakenObjectNode;
    final BooleanProperty isUp;
    final BooleanProperty isDown;
    final BooleanProperty isLeft;
    final BooleanProperty isRight;
    final BooleanBinding isPressed;

    ObjectProperty<EventHandler<? super intersectEvent>> onTakenObjectIntersect = new SimpleObjectProperty<>();


    AnimationTimer movingTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            double vMoving = PersonNode.super.getY();
            double hMoving = PersonNode.super.getX();

            if(isUp.getValue()){
                vMoving -=speed;
            }
            if(isDown.getValue()){
                vMoving +=speed;
            }
            if(isRight.getValue()){
                hMoving +=speed;
            }
            if(isLeft.getValue()){
                hMoving -=speed;
            }

            if(vMoving<0)
            {
                vMoving=0;
            }
            else if(vMoving>256-PersonNode.super.getBoundsInLocal().getHeight()){
                vMoving=256-PersonNode.super.getBoundsInLocal().getHeight();
            }
            if(hMoving<0)
            {
                hMoving=0;
            }
            else if(hMoving>256-PersonNode.super.getBoundsInLocal().getWidth()){
                hMoving=256-PersonNode.super.getBoundsInLocal().getWidth();
            }

            PersonNode.super.setY(vMoving);
            PersonNode.super.setX(hMoving);
            Rectangle2D area = new Rectangle2D(PersonNode.super.getX(),PersonNode.super.getY(),24,24);

            if(choozenTakenObjectNode==null) {
                for (TakenObjectNode takenObjectNode : takenObjects) {
                    if (area.intersects(new Rectangle2D(takenObjectNode.getX(), takenObjectNode.getY(), 7, 8))) {
                        if (onTakenObjectIntersect.isNotNull().getValue()) {
                            onTakenObjectIntersect.get().handle(new intersectEvent(Event.ANY, takenObjectNode));
                            choozenTakenObjectNode = takenObjectNode;
                        }
                    }
                }
            }
        }
    };
    public PersonNode(Image image, BooleanProperty upProp, BooleanProperty downProp, BooleanProperty leftProp, BooleanProperty rightProp, List<TakenObjectNode> takenObjects){
        super(image);

        this.takenObjects.addAll(takenObjects);

        isUp = upProp;
        isDown = downProp;
        isLeft = leftProp;
        isRight = rightProp;
        isPressed = isUp.or(isDown).or(isLeft).or(isRight);
        isPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(isPressed.getValue()){
                    movingTimer.start();
                }
                else {
                    movingTimer.stop();
                }
            }
        });
    }

    public void setOnTakenObjectIntersect(EventHandler<? super intersectEvent> onTakenObjectIntersect) {
        this.onTakenObjectIntersect.set(onTakenObjectIntersect);
    }

    public TakenObjectNode takeChoozenTakenObjectNode() {
        TakenObjectNode out = choozenTakenObjectNode;
        choozenTakenObjectNode = null;
        return out;
    }
}
