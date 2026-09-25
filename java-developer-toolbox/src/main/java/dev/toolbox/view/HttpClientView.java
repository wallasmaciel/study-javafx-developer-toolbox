package dev.toolbox.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HttpClientView implements BuilderView {
    public Node build() {
        var root = new VBox();

        var firstLine = new Label("HTTP Client");
        firstLine.setFont(Font.font(null, FontWeight.BOLD, 22));
        root.getChildren().add(firstLine);

        var secondLine = new HBox();
        secondLine.setSpacing(5);

        var secondLineMethod = new ComboBox<String>();
        secondLineMethod.getItems().addAll("GET", "POST", "PUT", "DELETE", "PATCH");
        secondLineMethod.setValue("GET");
        secondLine.getChildren().add(secondLineMethod);

        var secondLineUrl = new TextField();
        secondLineUrl.setPromptText("Enter URL");
        secondLineUrl.setMaxWidth(Double.MAX_VALUE);
        secondLine.getChildren().add(secondLineUrl);

        HBox.setHgrow(secondLineUrl, Priority.ALWAYS);

        var secondLineSendButton = new Button("Send");
        secondLineSendButton.setDisable(true);
        secondLine.getChildren().add(secondLineSendButton);

        VBox.setMargin(secondLine, new Insets(16, 0, 16, 0));
        root.getChildren().add(secondLine);

        root.getChildren().add(new Separator());

        var fourthLine = new Label("Headers");
        VBox.setMargin(fourthLine, new Insets(18, 0, 0, 0));
        fourthLine.setFont(Font.font(null, FontWeight.BOLD, 14));
        root.getChildren().add(fourthLine);

        var fifthLine = new GridPane();
        fifthLine.setMaxWidth(Double.MAX_VALUE);
        fifthLine.setHgap(5);
        fifthLine.setVgap(2);

        var firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(50);

        var secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(50);

        fifthLine.getColumnConstraints().addAll(firstColumn, secondColumn);

        var fifthLineElement1 = new Label("Key");
        fifthLine.add(fifthLineElement1, 0, 0);

        var fifthLineElement2 = new TextField("Accept");
        fifthLineElement2.setMaxWidth(Double.MAX_VALUE);
        fifthLine.add(fifthLineElement2, 0, 1);

        var fifthLineElement3 = new Label("Value");
        fifthLine.add(fifthLineElement3, 1, 0);

        var fifthLineElement4 = new TextField("application/json");
        fifthLineElement4.setMaxWidth(Double.MAX_VALUE);
        fifthLine.add(fifthLineElement4, 1, 1);

        VBox.setMargin(fifthLine, new Insets(8, 0, 0, 0));
        root.getChildren().add(fifthLine);

        var fifthLineElement5 = new Label("Response");
        fifthLineElement5.setFont(Font.font(null, FontWeight.BOLD, 14));
        VBox.setMargin(fifthLineElement5, new Insets(8, 0, 0, 0));
        root.getChildren().add(fifthLineElement5);

        var fifthLineElement6 = new TextArea();
        fifthLineElement6.setEditable(false);
        fifthLineElement6.setMaxWidth(Double.MAX_VALUE);
        fifthLineElement6.setMaxHeight(Double.MAX_VALUE);
        VBox.setMargin(fifthLineElement6, new Insets(2, 0, 0, 0));
        VBox.setVgrow(fifthLineElement6, Priority.ALWAYS);
        root.getChildren().add(fifthLineElement6);

        return root;
    }
}
