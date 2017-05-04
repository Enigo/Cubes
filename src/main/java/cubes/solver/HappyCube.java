package cubes.solver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HappyCube {

    private CubeSide currentSide = CubeSide.GROUND;
    private boolean isCompleted;

    private final Map<CubeSide, Piece> piecesBySide = new HashMap<>();

    public void setPiece(Piece piece) {
        piecesBySide.put(currentSide, piece);
    }

    public void nextSide() {
        currentSide = currentSide.next();
    }

    public void previousSide() {
        piecesBySide.put(currentSide, null);
        currentSide = currentSide.previous();
    }

    public CubeSide getCurrentSide() {
        return currentSide;
    }

    public Piece getGroundPiece() {
        return piecesBySide.get(CubeSide.GROUND);
    }

    public Piece getLeftPiece() {
        return piecesBySide.get(CubeSide.LEFT);
    }

    public Piece getBackPiece() {
        return piecesBySide.get(CubeSide.BACK);
    }

    public Piece getRightPiece() {
        return piecesBySide.get(CubeSide.RIGHT);
    }

    public Piece getFrontPiece() {
        return piecesBySide.get(CubeSide.FRONT);
    }

    public Piece getTopPiece() {
        return piecesBySide.get(CubeSide.TOP);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void completeCube() {
        isCompleted = true;
    }

    public enum CubeSide {
        GROUND(0), LEFT(1), BACK(2), RIGHT(3), FRONT(4), TOP(5);

        private final int solveIndex;

        CubeSide(int solveIndex) {
            this.solveIndex = solveIndex;
        }

        public CubeSide next() {
            return getByIndex(solveIndex + 1);
        }

        public CubeSide previous() {
            return getByIndex(solveIndex - 1);
        }

        private static CubeSide getByIndex(int index) {
            return Stream.of(CubeSide.values())
                    .filter(cubeSide -> cubeSide.solveIndex == index)
                    .findFirst().orElse(index > values().length ? TOP : GROUND);
        }
    }

}
