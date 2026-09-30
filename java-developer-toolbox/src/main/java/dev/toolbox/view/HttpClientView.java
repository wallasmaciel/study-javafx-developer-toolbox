package dev.toolbox.view;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import dev.toolbox.property.HttpHeader;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
        var lbTitle = new Label("HTTP Client");
        lbTitle.setFont(Font.font(null, FontWeight.BOLD, 22));

        return lbTitle;
    }

    private Node requestBar() {
        var content = new HBox();
        content.setSpacing(5);

        var selectMethod = new ComboBox<String>();
        selectMethod.getItems().addAll("GET", "POST", "PUT", "DELETE", "PATCH");
        selectMethod.setValue("GET");
        content.getChildren().add(selectMethod);

        var txtUrl = new TextField();
        txtUrl.setPromptText("Enter URL");
        txtUrl.setMaxWidth(Double.MAX_VALUE);
        content.getChildren().add(txtUrl);

        HBox.setHgrow(txtUrl, Priority.ALWAYS);

        var btnSend = new Button("Send");
        BooleanBinding validUrl = Bindings.createBooleanBinding(() -> 
            validarUrl(txtUrl.getText()
        ), txtUrl.textProperty());
        btnSend.disableProperty().bind(validUrl.not());
        btnSend.setTooltip(
            new Tooltip("Ação de envio será implementada futuramente."));

        content.getChildren().add(btnSend);

        VBox.setMargin(content, new Insets(16, 0, 16, 0));

        return content;
    }

    private GridPane createGrid() {
        var gridPane = new GridPane();

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        var firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(44);

        var secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(44);

        var thirdColumn = new ColumnConstraints();
        thirdColumn.setPercentWidth(12);

        gridPane.getColumnConstraints().addAll(
            firstColumn,
            secondColumn,
            thirdColumn
        );

        return gridPane;
    }

    private Node headers() {
        var box = new VBox();
        box.setMaxWidth(Double.MAX_VALUE);

        var boxTitle = new HBox();
        boxTitle.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(boxTitle, new Insets(18, 0, 0, 0));

        var title = new Label("Headers");
        title.setFont(Font.font(null, FontWeight.BOLD, 14));

        var spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        var addHeader = new Button("+ Header");

        boxTitle.getChildren().addAll(title, spacer, addHeader);

        var headerScroll = new ScrollPane();
        headerScroll.setFitToWidth(true);
        headerScroll.setPrefViewportHeight(150);
        headerScroll.setMaxHeight(Region.USE_PREF_SIZE);

        var headerGrid = createGrid();
        headerGrid.add(new Label("Key"), 0, 0);
        headerGrid.add(new Label("Value"), 1, 0);

        ObservableList<HttpHeader> headers = FXCollections.observableArrayList();
        
        var contentGrid = createGrid();
        renderTable(headers, contentGrid);

        addHeader.setOnAction(event -> headers.add(new HttpHeader()));

        headers.add(new HttpHeader("Accept", "application/json"));
        
        headerScroll.setContent(contentGrid);

        VBox.setMargin(headerScroll, new Insets(8, 0, 0, 0));
        box.getChildren().addAll(boxTitle, headerGrid, headerScroll);

        return box;
    }

    private void renderTable(ObservableList<HttpHeader> headers, GridPane contentGrid) {
        Map<HttpHeader, HeaderControls> controlsByHeader = new IdentityHashMap<>();

        headers.addListener((ListChangeListener<HttpHeader>) change -> {
            while (change.next()) {
                for (var header : change.getRemoved()) {
                    var controls = controlsByHeader.remove(header);
                    if (controls == null) continue;

                    controls.key().textProperty()
                        .unbindBidirectional(header.keyProperty());
                    controls.value().textProperty()
                        .unbindBidirectional(header.valueProperty());

                    contentGrid.getChildren().removeAll(
                        controls.key(),
                        controls.value(),
                        controls.remove()
                    );
                }

                for (var header : change.getAddedSubList()) {
                    var controls = createHeaderControls(header, headers);

                    controlsByHeader.put(header, controls);

                    int row = headers.indexOf(header);

                    contentGrid.add(controls.key(), 0, row);
                    contentGrid.add(controls.value(), 1, row);
                    contentGrid.add(controls.remove(), 2, row);
                }
            }

            IntStream.range(0, headers.size())
                .forEach(i -> {
                    var controls = controlsByHeader.get(headers.get(i));

                    GridPane.setRowIndex(controls.key(), i);
                    GridPane.setRowIndex(controls.value(), i);
                    GridPane.setRowIndex(controls.remove(), i);
                });
        });
    }

    private HeaderControls createHeaderControls(HttpHeader header, ObservableList<HttpHeader> headers) {
        var keyField = new TextField();
        keyField.setMaxWidth(Double.MAX_VALUE);

        keyField.textProperty()
            .bindBidirectional(header.keyProperty());

        var valueField = new TextField();
        valueField.setMaxWidth(Double.MAX_VALUE);

        valueField.textProperty()
            .bindBidirectional(header.valueProperty());

        var removeButton = new Button("Remove");
        removeButton.setOnAction(event ->
            headers.remove(header));

        return new HeaderControls(
            keyField,
            valueField,
            removeButton
        );
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

    private boolean validarUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(url.trim());

            String scheme = uri.getScheme();

            boolean protocoloValido = "http".equalsIgnoreCase(scheme)
                || "https".equalsIgnoreCase(scheme);

            return protocoloValido
                && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private record HeaderControls(
        TextField key,
        TextField value,
        Button remove
    ) {}
}
