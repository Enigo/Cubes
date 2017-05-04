package cubes.solver;

import cubes.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Piece {

    private static final char ONE_CELL_IN_PIECE = 'o';
    private final List<String> lines;

    public Piece(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public void flipHorizontal() {
        final List<String> result = lines.stream()
                .map(line -> new StringBuilder(line).reverse().toString())
                .collect(Collectors.toList());
        lines.clear();
        lines.addAll(result);
    }

    public void rotateClockwise() {
        final List<String> result = new ArrayList<>(lines.size());
        int lineIndex = 0;
        while (lineIndex < Constants.PIECE_SIDE_SIZE) {
            StringBuilder builder = new StringBuilder();
            for (int i = lines.size() - 1; i >= 0; i--) {
                builder.append(lines.get(i).charAt(lineIndex));
            }
            result.add(builder.toString());
            lineIndex++;
        }
        lines.clear();
        lines.addAll(result);
    }

    public List<String> getLines() {
        return lines;
    }

    public String getLeftEdge() {
        StringBuilder builder = new StringBuilder();
        lines.forEach(line -> builder.append(line.charAt(0)));
        return builder.toString();
    }

    public String getTopEdge() {
        return lines.get(0);
    }

    public String getBottomEdge() {
        return lines.get(lines.size() - 1);
    }

    public String getRightEdge() {
        StringBuilder builder = new StringBuilder();
        lines.forEach(line -> builder.append(line.charAt(line.length() - 1)));
        return builder.toString();
    }

    public boolean matchLeftTo(Piece groundPieceToMatch) {
        for (int i = 0; i < Constants.PIECE_SIDE_SIZE; i++) {
            if (groundPieceToMatch.getLeftEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getRightEdge().charAt(i) == ONE_CELL_IN_PIECE) {
                return false;
            }
        }
        return true;
    }

    public boolean matchBackTo(Piece groundPieceToMatch, Piece leftPieceToMatch) {
        for (int i = 0; i < Constants.PIECE_SIDE_SIZE; i++) {
            final boolean illegalMatchGround = groundPieceToMatch.getTopEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchLeft = leftPieceToMatch.getTopEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getLeftEdge().charAt(i) == ONE_CELL_IN_PIECE;
            if (illegalMatchGround || illegalMatchLeft) {
                return false;
            }
        }
        return groundPieceToMatch.getTopEdge().charAt(0) == ONE_CELL_IN_PIECE
                || leftPieceToMatch.getRightEdge().charAt(0) == ONE_CELL_IN_PIECE
                || getBottomEdge().charAt(0) == ONE_CELL_IN_PIECE;
    }

    public boolean matchRightTo(Piece groundPieceToMatch, Piece backPieceToMatch) {
        int backwardIndex = Constants.PIECE_SIDE_SIZE - 1;

        for (int i = 0; i < Constants.PIECE_SIDE_SIZE; i++) {
            final boolean illegalMatchGround = groundPieceToMatch.getRightEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getLeftEdge().charAt(i) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchTop = backPieceToMatch.getRightEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getTopEdge().charAt(backwardIndex) == ONE_CELL_IN_PIECE;

            if (illegalMatchGround || illegalMatchTop) {
                return false;
            }
            backwardIndex--;
        }
        return groundPieceToMatch.getTopEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || backPieceToMatch.getBottomEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || getLeftEdge().charAt(0) == ONE_CELL_IN_PIECE;
    }

    public boolean matchFrontTo(Piece groundPieceToMatch, Piece leftPieceToMatch, Piece rightPieceToMatch) {
        int backwardIndex = Constants.PIECE_SIDE_SIZE - 1;

        for (int i = 0; i < Constants.PIECE_SIDE_SIZE; i++) {
            final boolean illegalMatchGround = groundPieceToMatch.getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getTopEdge().charAt(i) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchLeft = leftPieceToMatch.getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getLeftEdge().charAt(backwardIndex) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchRight = rightPieceToMatch.getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getRightEdge().charAt(backwardIndex) == ONE_CELL_IN_PIECE;

            if (illegalMatchGround || illegalMatchLeft || illegalMatchRight) {
                return false;
            }
            backwardIndex--;
        }

        final boolean matchRightCorner = groundPieceToMatch.getBottomEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || rightPieceToMatch.getBottomEdge().charAt(0) == ONE_CELL_IN_PIECE
                || getTopEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE;
        final boolean matchLeftCorner = groundPieceToMatch.getBottomEdge().charAt(0) == ONE_CELL_IN_PIECE
                || leftPieceToMatch.getBottomEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || getTopEdge().charAt(0) == ONE_CELL_IN_PIECE;

        return matchRightCorner && matchLeftCorner;
    }

    public boolean matchTopTo(Piece leftPiece, Piece rightPiece, Piece backPiece, Piece frontPiece) {
        int backwardIndex = Constants.PIECE_SIDE_SIZE - 1;

        for (int i = 0; i < Constants.PIECE_SIDE_SIZE; i++) {
            final boolean illegalMatchGround = leftPiece.getLeftEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getLeftEdge().charAt(backwardIndex) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchRight = rightPiece.getRightEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getRightEdge().charAt(backwardIndex) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchBack = backPiece.getTopEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE;
            final boolean illegalMatchFront = frontPiece.getBottomEdge().charAt(i) == ONE_CELL_IN_PIECE &&
                    getTopEdge().charAt(i) == ONE_CELL_IN_PIECE;

            if (illegalMatchGround || illegalMatchRight || illegalMatchBack || illegalMatchFront) {
                return false;
            }
            backwardIndex--;
        }

        final boolean matchUpperLeftCorner = leftPiece.getLeftEdge().charAt(0) == ONE_CELL_IN_PIECE
                || backPiece.getTopEdge().charAt(0) == ONE_CELL_IN_PIECE
                || getLeftEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE;
        final boolean matchUpperRightCorner = rightPiece.getRightEdge().charAt(0) == ONE_CELL_IN_PIECE
                || backPiece.getTopEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || getRightEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE;
        final boolean matchDownLeftCorner = leftPiece.getBottomEdge().charAt(0) == ONE_CELL_IN_PIECE
                || frontPiece.getBottomEdge().charAt(0) == ONE_CELL_IN_PIECE
                || getTopEdge().charAt(0) == ONE_CELL_IN_PIECE;
        final boolean matchDownRightCorner = rightPiece.getBottomEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || frontPiece.getBottomEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE
                || getTopEdge().charAt(Constants.PIECE_SIDE_SIZE - 1) == ONE_CELL_IN_PIECE;

        return matchUpperLeftCorner
                && matchUpperRightCorner
                && matchDownLeftCorner
                && matchDownRightCorner;
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), lines);
    }

    public String beautify() {
        return toString().replaceAll(" ", "  ");
    }
}
