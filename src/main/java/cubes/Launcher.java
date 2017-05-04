package cubes;

import cubes.events.SolveEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(createPane(), 650, 850);
        stage.setScene(scene);
        stage.setTitle("Cubes Puzzle");
        stage.show();
    }

    private static BorderPane createPane() {
        final BorderPane root = new BorderPane();
        final HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.TOP_CENTER);
        buttonsBox.setPadding(new Insets(5));
        buttonsBox.getChildren().addAll(
                createButton("blue", root),
                createButton("purple", root),
                createButton("red", root),
                createButton("yellow", root)
        );

        root.setTop(getMainMenuContainer());
        root.setCenter(buttonsBox);
        return root;
    }

    private static VBox getMainMenuContainer() {
        final VBox topContainer = new VBox();
        final MenuBar mainMenu = new MenuBar();
        topContainer.getChildren().add(mainMenu);
        final Menu file = new Menu("Menu");

        final MenuItem exitApp = new MenuItem("Exit");
        exitApp.setOnAction(event -> Platform.exit());

        file.getItems().add(exitApp);

        mainMenu.getMenus().add(file);
        return topContainer;
    }

    private static Button createButton(final String text, BorderPane root) {
        final Button button = new Button(text);
        button.setOnAction(new SolveEvent(root, text + Constants.FILE_EXTENSION));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
