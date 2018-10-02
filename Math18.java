import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import javafx.util.Duration;
import javafx.animation.*;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.Bindings;

public class Math18 extends Application{
   GridPane pane;
   GridPane startPane;
   public void start(Stage primaryStage){
/*      pane = new GridPane();
      pane.setAlignment(Pos.CENTER);
      pane.setStyle("-fx-background-color: rgb(18, 18, 18)");*/

      startPane = new GridPane();
      startPane.setAlignment(Pos.CENTER);
      startPane.setStyle("-fx-background-color: rgb(18, 18, 18)");
      Text title = new Text("How many matrices are you starting with?");
      title.setFont(Font.font("Seravek", FontWeight.THIN, 30));
      title.setFill(Color.WHITE);
      ComboBox<Integer> numMatrices = new ComboBox<>();
      for(int i = 1; i < 9; i++)
        numMatrices.getItems().add(i);
      numMatrices.setStyle("-fx-font-size: 30;"+
                            "-fx-font-family: Seravek;"+
                            "-fx-color: rgb(18, 18, 18);"+
                            "-fx-font-fill: white");
      numMatrices.setValue(2);
      GridPane.setHalignment(title, HPos.CENTER);
      GridPane.setHalignment(numMatrices, HPos.CENTER);
      startPane.add(title, 0, 0, 3, 1);
      startPane.add(numMatrices, 0, 3, 3, 1);

      Scene scene = new Scene(startPane);
      primaryStage.setTitle("Math18 Assistant");
      primaryStage.setScene(scene);
      primaryStage.setWidth(700);
      primaryStage.setHeight(700);
      primaryStage.show();
   }

}
