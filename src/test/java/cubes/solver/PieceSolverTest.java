package cubes.solver;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PieceSolverTest {
    
    private final PieceSolver pieceSolver = new PieceSolver();
    private final PieceParser pieceParser = new PieceParser();
    
    @Test
    public void testBlue(){
        final HappyCube cube = pieceSolver.solve(pieceParser.parsePiece("blue.dat"));
        assertNotNull(cube);
        assertTrue(cube.isCompleted());
    }    
    @Test
    public void testRed(){
        final HappyCube cube = pieceSolver.solve(pieceParser.parsePiece("red.dat"));
        assertNotNull(cube);
        assertTrue(cube.isCompleted());
    }    
    @Test
    public void testPurple(){
        final HappyCube cube = pieceSolver.solve(pieceParser.parsePiece("purple.dat"));
        assertNotNull(cube);
        assertTrue(cube.isCompleted());
    }    
    @Test
    public void testYellow(){
        final HappyCube cube = pieceSolver.solve(pieceParser.parsePiece("yellow.dat"));
        assertNotNull(cube);
        assertTrue(cube.isCompleted());
    }
    
}
