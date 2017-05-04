package cubes.events;

import cubes.Constants;
import cubes.solver.HappyCube;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveResultsEvent implements EventHandler<ActionEvent> {

    private final HappyCube cube;

    public SaveResultsEvent(HappyCube cube) {
        this.cube = cube;
    }

    @Override
    public void handle(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the results");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File extension", "*" + Constants.FILE_EXTENSION));
        final File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                final List<String> resultCube = constructCubeRepresentation();
                Files.write(file.toPath(), resultCube, StandardCharsets.US_ASCII);
            } catch (IOException e) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error saving the file " + file.getName());
                alert.showAndWait();
            }
        }
    }

    private List<String> constructCubeRepresentation() {
        List<String> resultCube = new ArrayList<>();
        cube.getBackPiece().getLines().forEach(line -> resultCube.add(String.join("",
                Collections.nCopies(Constants.PIECE_SIDE_SIZE, " ")) + line));
        for (int i = 0; i < cube.getGroundPiece().getLines().size(); i++) {
            resultCube.add(cube.getLeftPiece().getLines().get(i) + cube.getGroundPiece().getLines().get(i)
                    + cube.getRightPiece().getLines().get(i));
        }
        cube.getFrontPiece().getLines().forEach(line -> resultCube.add(String.join("",
                Collections.nCopies(Constants.PIECE_SIDE_SIZE, " ")) + line));
        cube.getTopPiece().getLines().forEach(line -> resultCube.add(String.join("",
                Collections.nCopies(Constants.PIECE_SIDE_SIZE, " ")) + line));
        return resultCube;
    }
}
