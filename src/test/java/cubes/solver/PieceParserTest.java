package cubes.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PieceParserTest {
    
    private final PieceParser pieceParser = new PieceParser();
    
    @Test
    public void testFileExists(){
        final List<Piece> pieces = pieceParser.parsePiece("red.dat");
        Assert.assertFalse(pieces.isEmpty());
        Assert.assertEquals(6, pieces.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFileNotFound(){
        pieceParser.parsePiece("notFound");
    }
    
}
