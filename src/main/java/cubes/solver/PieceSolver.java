package cubes.solver;

import java.util.ArrayList;
import java.util.List;

public class PieceSolver {

    private static final int TOTAL_ROTATIONS = 5;

    public HappyCube solve(List<Piece> pieces) {
        final HappyCube cube = new HappyCube();
        solve(cube, pieces);
        return cube;
    }

    private static boolean solve(HappyCube cube, List<Piece> pieces) {
        if (cube.isCompleted()) {
            return true;
        }
        for (Piece piece : pieces) {
            for (int i = 0; i < TOTAL_ROTATIONS; i++) {
                piece.rotateClockwise();
                if (fillNext(cube, piece)) {
                    List<Piece> copy = new ArrayList<>(pieces);
                    copy.remove(piece);
                    if (solve(cube, copy)) {
                        return true;
                    }
                }
                piece.flipHorizontal();
                if (fillNext(cube, piece)) {
                    List<Piece> copy = new ArrayList<>(pieces);
                    copy.remove(piece);
                    if (solve(cube, copy)) {
                        return true;
                    }
                }
            }
        }

        cube.previousSide();
        return false;
    }

    private static boolean fillNext(HappyCube cube, Piece piece) {
        boolean isSet;
        switch (cube.getCurrentSide()) {
            case GROUND:
                isSet = true;
                break;
            case LEFT:
                isSet = piece.matchLeftTo(cube.getGroundPiece());
                break;
            case BACK:
                isSet = piece.matchBackTo(cube.getGroundPiece(), cube.getLeftPiece());
                break;
            case RIGHT:
                isSet = piece.matchRightTo(cube.getGroundPiece(), cube.getBackPiece());
                break;
            case FRONT:
                isSet = piece.matchFrontTo(cube.getGroundPiece(), cube.getLeftPiece(), cube.getRightPiece());
                break;
            case TOP:
                isSet = piece.matchTopTo(cube.getLeftPiece(), cube.getRightPiece(), cube.getBackPiece(), cube.getFrontPiece());
                if (isSet) {
                    cube.completeCube();
                }
                break;
            default:
                isSet = false;
                break;
        }
        if (isSet) {
            cube.setPiece(piece);
            cube.nextSide();
        }
        return isSet;
    }
}
