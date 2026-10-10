package dev.toolbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import dev.toolbox.view.BuilderView;
import dev.toolbox.view.HttpClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeveloperToolboxApplication extends Application {
    private static final System.Logger LOGGER = System.getLogger(DeveloperToolboxApplication.class.getName());
    private final BuilderView httpClientView;

    private final AtomicInteger threadCounter = new AtomicInteger(0);
    private final ExecutorService executor;

    public DeveloperToolboxApplication() {
        ThreadFactory threadFactory = runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("toolbox-worker-" + threadCounter.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        };

        executor = Executors.newFixedThreadPool(2, threadFactory);
        httpClientView = new HttpClientView(executor);
    }
    
    @Override
    public void init() {
        logLifecycle("init");
    }
    
    @Override
    public void start(Stage stage) {
        logLifecycle("start");
        
        var root = new BorderPane();

        var header = new Label("Java Developer Toolbox");
        header.setPadding(new Insets(20, 22, 20, 22));
        root.setTop(header);

        var sidebar = new VBox(2);
        sidebar.addEventFilter(ActionEvent.ACTION, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                LOGGER.log(System.Logger.Level.INFO, "CAPTURING: evento recebido pela sidebar");
            }
        });

        sidebar.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                LOGGER.log(System.Logger.Level.INFO, "BUBBLING: evento recebido pela sidebar");
            }
        });

        var httpButton = new Button("HTTP Client");
        httpButton.setMaxWidth(Double.MAX_VALUE);

        var databaseButton = new Button("Database");
        databaseButton.setMaxWidth(Double.MAX_VALUE);

        var jwtButton = new Button("JWT");
        jwtButton.setMaxWidth(Double.MAX_VALUE);

        var rabbitButton = new Button("RabbitMQ");
        rabbitButton.setMaxWidth(Double.MAX_VALUE);

        var jvmButton = new Button("JVM Monitor");
        jvmButton.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(
            httpButton,
            databaseButton,
            jwtButton,
            rabbitButton,
            jvmButton
        );

        sidebar.setPrefWidth(160);
        sidebar.setPadding(new Insets(0, 22, 0, 22));
        root.setLeft(sidebar);

        var workspace = new StackPane(
            new Label("Workspace")
        );
        workspace.setPadding(new Insets(0, 22, 20, 0));
        root.setCenter(workspace);

        var footer = new Label("Ready");
        footer.setPadding(new Insets(20, 22, 20, 22));
        root.setBottom(footer);

        Node httpClientNode = httpClientView.build();
        httpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                workspace.getChildren().setAll(httpClientNode);
                footer.setText("HTTP Client selected");

                LOGGER.log(System.Logger.Level.INFO, "ACTION: tipo={0}, alvo={1}, thread={2}, fxThread={3}", 
                    event.getEventType(), event.getTarget(), Thread.currentThread().getName(), Platform.isFxApplicationThread());
            }
        });

        var scene = new Scene(root, 800, 700);

        stage.setTitle("Java Developer Toolbox");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        logLifecycle("stop");
        executor.shutdown();
    }
    
    private void logLifecycle(String value) {
        LOGGER.log(System.Logger.Level.INFO, "{0} | thread={1} | fxThread={2}", value, 
            Thread.currentThread().getName(), Platform.isFxApplicationThread());
    }
}
