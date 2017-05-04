package cubes.events;

import cubes.solver.HappyCube;
import cubes.solver.Piece;
import cubes.solver.PieceParser;
import cubes.solver.PieceSolver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class SolveEvent implements EventHandler<ActionEvent> {

    private final BorderPane root;
    private final String fileName;
    private final PieceParser pieceParser = new PieceParser();
    private final PieceSolver solver = new PieceSolver();

    public SolveEvent(BorderPane root, String fileName) {
        this.root = root;
        this.fileName = fileName;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            final List<Piece> pieces = pieceParser.parsePiece(fileName);
            final HappyCube cube = solver.solve(pieces);
            if (cube.isCompleted()) {
                final VBox givenPiecesBox = createGivenPiecesView(pieces);
                final VBox solvedCubeBox = createSolvedCubeView(cube);

                final HBox cubesBox = new HBox();
                cubesBox.getChildren().addAll(givenPiecesBox, solvedCubeBox);
                cubesBox.setAlignment(Pos.CENTER);
                root.setBottom(cubesBox);
            } else {
                showAndWaitAlert("Solve error", "Cannot solve the given puzzle!");
            }
        } catch (IllegalArgumentException e) {
            showAndWaitAlert("Error", e.getMessage());
        }
    }

    private static VBox createGivenPiecesView(List<Piece> pieces) {
        final VBox givenPiecesBox = new VBox(10);
        givenPiecesBox.setAlignment(Pos.TOP_CENTER);
        givenPiecesBox.setPadding(new Insets(10, 50, 50, 50));
        givenPiecesBox.getChildren().add(new Label("Given pieces:"));
        pieces.forEach(piece -> givenPiecesBox.getChildren().add(new Label(piece.beautify())));
        return givenPiecesBox;
    }

    private static VBox createSolvedCubeView(HappyCube cube) {
        final VBox solvedCubeBox = new VBox(10);
        solvedCubeBox.setAlignment(Pos.TOP_CENTER);
        solvedCubeBox.setPadding(new Insets(10, 50, 50, 50));
        solvedCubeBox.getChildren().add(new Label("Solved:"));
        final HBox centerPart = new HBox();
        centerPart.getChildren().addAll(new Label(cube.getLeftPiece().beautify()),
                new Label(cube.getGroundPiece().beautify()),
                new Label(cube.getRightPiece().beautify()));

        final Button saveResult = new Button("Save");
        saveResult.setOnAction(new SaveResultsEvent(cube));

        solvedCubeBox.getChildren().addAll(
                new Label(cube.getBackPiece().beautify()),
                centerPart,
                new Label(cube.getFrontPiece().beautify()),
                new Label(cube.getTopPiece().beautify()),
                saveResult);

        return solvedCubeBox;
    }

    private static void showAndWaitAlert(String title, String contentText) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
