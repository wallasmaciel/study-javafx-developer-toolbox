package dev.toolbox.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HttpClientView implements BuilderView {
    public Node build() {
        var root = new VBox();
        root.setMaxWidth(Double.MAX_VALUE);
        root.setMaxHeight(Double.MAX_VALUE);

        root.getChildren().add(title());
        root.getChildren().add(requestBar());

        root.getChildren().add(new Separator());

        root.getChildren().add(headers());
        root.getChildren().add(response());

        return root;
    }

    private Node title() {
        var firstLine = new Label("HTTP Client");
        firstLine.setFont(Font.font(null, FontWeight.BOLD, 22));

        return firstLine;
    }

    private Node requestBar() {
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

        return secondLine;
    }

    private GridPane createGrid() {
        var gridPane = new GridPane();

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        var firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(50);

        var secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(50);

        gridPane.getColumnConstraints().addAll(
            firstColumn,
            secondColumn
        );

        return gridPane;
    }

    private Node headers() {
        var box = new VBox();

        var title = new Label("Headers");
        VBox.setMargin(title, new Insets(18, 0, 0, 0));
        title.setFont(Font.font(null, FontWeight.BOLD, 14));

        var headerScroll = new ScrollPane();
        headerScroll.setFitToWidth(true);
        headerScroll.setPrefViewportHeight(150);
        headerScroll.setMaxHeight(Region.USE_PREF_SIZE);

        var headerGrid = createGrid();
        headerGrid.add(new Label("Key"), 0, 0);
        headerGrid.add(new Label("Value"), 1, 0);

        var contentGrid = createGrid();
        drawTableHeader(contentGrid, 0, "Accept", "application/json");

        for (int i = 1; i < 12; i++) {
            drawTableHeader(contentGrid, i, null, null);
        }

        headerScroll.setContent(contentGrid);

        VBox.setMargin(headerScroll, new Insets(8, 0, 0, 0));
        box.getChildren().addAll(title, headerGrid, headerScroll);

        return box;
    }

    private void drawTableHeader(GridPane gridPane, int row, String keyPromptText, String valuePromptText) {
        var keyField = new TextField();
        if (keyPromptText != null) keyField.setPromptText(keyPromptText);
        keyField.setMaxWidth(Double.MAX_VALUE);

        var valueField = new TextField();
        if (valuePromptText != null) valueField.setPromptText(valuePromptText);
        valueField.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(keyField, 0, row);
        gridPane.add(valueField, 1, row);
    }

    private VBox response() {
        var title = new Label("Response");
        title.setFont(Font.font(null, FontWeight.BOLD, 14));
        VBox.setMargin(title, new Insets(8, 0, 0, 0));

        var text = new TextArea();
        text.setEditable(false);
        text.setMaxWidth(Double.MAX_VALUE);

        text.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(text, Priority.ALWAYS);

        var box = new VBox(
            title,
            text
        );
        VBox.setMargin(box, new Insets(2, 0, 0, 0));
        VBox.setVgrow(box, Priority.ALWAYS);

        return box;
    }
}
