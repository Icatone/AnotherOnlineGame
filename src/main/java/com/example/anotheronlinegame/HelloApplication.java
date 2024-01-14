package com.example.anotheronlinegame;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HelloApplication extends Application {

    final BooleanProperty upMovement = new SimpleBooleanProperty(false);
    final BooleanProperty downMovement = new SimpleBooleanProperty(false);
    final BooleanProperty leftMovement = new SimpleBooleanProperty(false);
    final BooleanProperty rightMovement = new SimpleBooleanProperty(false);


    @Override
    public void start(Stage stage) throws IOException {
        Pane pane1 = new Pane();
        Pane pane2 = new Pane();
        VBox mPane = new VBox();
        mPane.getChildren().add(pane1);
        mPane.getChildren().add(pane2);
        pane1.setBorder(new Border(new BorderStroke(new Color(0,0,0,1),BorderStrokeStyle.SOLID,null,BorderWidths.DEFAULT)));
        pane2.setBorder(new Border(new BorderStroke(new Color(0,1,0,1),BorderStrokeStyle.SOLID,null,BorderWidths.DEFAULT)));

        pane1.setMaxSize(256,256);
        pane1.setMinSize(256,256);

        pane2.setMaxSize(256,256);
        pane2.setMinSize(256,256);

        List<TakenObjectNode> apples= new LinkedList<>();
        for(int i = 0;i<5;++i){
            apples.add(new TakenObjectNode(new Image(new FileInputStream( "src/main/resources/com/example/anotheronlinegame/apple.png"))));
            apples.get(i).randomPLace(256,256);
            pane2.getChildren().add(apples.get(i));
        }

        PersonNode person = new PersonNode(new Image(new FileInputStream("src/main/resources/com/example/anotheronlinegame/down.png")),upMovement,downMovement,leftMovement,rightMovement,apples);
        person.setOnTakenObjectIntersect(new EventHandler<intersectEvent>() {
            @Override
            public void handle(intersectEvent intersectEvent) {
                removeTakenObject(pane2,intersectEvent.getIntersectedObject());
            }
        });

        pane2.getChildren().add(person);

        person.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()==KeyCode.W){
                    upMovement.set(true);
                }
                if(keyEvent.getCode()==KeyCode.S){
                    downMovement.set(true);
                }
                if(keyEvent.getCode()==KeyCode.A){
                    leftMovement.set(true);
                }
                if(keyEvent.getCode()==KeyCode.D){
                    rightMovement.set(true);
                }
            }
        });
        person.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()==KeyCode.W){
                    upMovement.set(false);
                }
                if(keyEvent.getCode()==KeyCode.S){
                    downMovement.set(false);
                }
                if(keyEvent.getCode()==KeyCode.A){
                    leftMovement.set(false);
                }
                if(keyEvent.getCode()==KeyCode.D){
                    rightMovement.set(false);
                }
            }
        });

        pane1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int objectID = apples.indexOf(person.takeChoozenTakenObjectNode());
                if(objectID>-1){
                    apples.get(objectID).setX(mouseEvent.getX());
                    apples.get(objectID).setY(mouseEvent.getY());
                    pane1.getChildren().add(apples.get(objectID));
                }
            }
        });


        Scene scene = new Scene(mPane, 500, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        person.requestFocus();
    }

    private void removeTakenObject(Pane pane,TakenObjectNode cutedObject){
        pane.getChildren().remove(cutedObject);
    }

    public static void main(String[] args) {
        launch();
    }
}