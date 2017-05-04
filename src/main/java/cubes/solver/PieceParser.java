package cubes.solver;

import cubes.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PieceParser {

    public List<Piece> parsePiece(String fileName) {
        final InputStream resource = getClass().getClassLoader().getResourceAsStream(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Couldn't find the file with the name " + fileName);
        }
        
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(resource))) {
            final List<String> lines = reader.lines().collect(Collectors.toList());
            final List<Piece> pieces = new ArrayList<>(6);
            for (int i = 0; i < lines.size(); i += Constants.PIECE_SIDE_SIZE) {
                pieces.add(new Piece(lines.subList(i, i + Constants.PIECE_SIDE_SIZE)));
            }
            return pieces;
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't read the file with the name " + fileName);
        }
    }
}
