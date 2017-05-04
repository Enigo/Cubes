package cubes.solver;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PieceTest {

    private Piece pieceLeft;
    private Piece pieceRight;
    private Piece pieceBack;
    private Piece pieceFront;
    private Piece pieceGround;
    private Piece pieceTop;

    @Before
    public void before() {
        pieceLeft = new Piece(Arrays.asList("  o  ", " ooo ", "ooooo", " ooo ", "  o  "));
        pieceRight = new Piece(Arrays.asList(" o o ", " ooo ", "ooooo", " ooo ", "  o  "));
        pieceBack = new Piece(Arrays.asList(" o o ", "oooo ", " oooo", "oooo ", "oo o "));
        pieceFront = new Piece(Arrays.asList("o o  ", "ooooo", " ooo ", "ooooo", " o o "));
        pieceGround = new Piece(Arrays.asList("  o o", "ooooo", " ooo ", "ooooo", " o oo"));
        pieceTop = new Piece(Arrays.asList("o o o", "ooooo", " ooo ", "ooooo", "o o o"));
    }

    @Test
    public void testFlipHorizontal() {
        final Piece simplePiece = new Piece(Arrays.asList("oo oo", "o oo "));
        simplePiece.flipHorizontal();
        final List<String> lines = simplePiece.getLines();

        assertFalse(lines.isEmpty());
        assertEquals(2, lines.size());
        assertEquals("oo oo", lines.get(0));
        assertEquals(" oo o", lines.get(1));
    }

    @Test
    public void testRotateClockwise() {
        final String initial = pieceBack.toString();

        pieceBack.rotateClockwise();
        assertEquals(new Piece(Arrays.asList(
                "oo o ",
                "ooooo",
                " ooo ",
                "ooooo",
                "  o  ")).toString(), pieceBack.toString());
        pieceBack.rotateClockwise();
        pieceBack.rotateClockwise();
        pieceBack.rotateClockwise();
        assertEquals(initial, pieceBack.toString());
    }

    @Test
    public void testGetLeftEdge() {
        assertEquals(" o o ", pieceGround.getLeftEdge());
    }

    @Test
    public void testGetRightEdge() {
        assertEquals("oo oo", pieceGround.getRightEdge());
    }

    @Test
    public void testGetTopEdge() {
        assertEquals("  o o", pieceGround.getTopEdge());
    }

    @Test
    public void testGetBottomEdge() {
        assertEquals(" o oo", pieceGround.getBottomEdge());
    }

    @Test
    public void testMatchLeftToMatch() {
        assertTrue(pieceLeft.matchLeftTo(pieceGround));
    }

    @Test
    public void testMatchLeftToNoMatch() {
        assertFalse(pieceBack.matchLeftTo(pieceLeft));
    }

    @Test
    public void testMatchBackToMatch() {
        assertTrue(pieceBack.matchBackTo(pieceGround, pieceLeft));
    }

    @Test
    public void testMatchBackToNoMatch() {
        assertFalse(pieceBack.matchBackTo(pieceLeft, pieceGround));
    }

    @Test
    public void testMatchRightToMatch() {
        assertTrue(pieceRight.matchRightTo(pieceGround, pieceBack));
    }

    @Test
    public void testMatchRightToNoMatch() {
        assertFalse(pieceRight.matchRightTo(pieceBack, pieceGround));
    }

    @Test
    public void testMatchFrontToMatch() {
        assertTrue(pieceFront.matchFrontTo(pieceGround, pieceLeft, pieceRight));
    }

    @Test
    public void testMatchFrontToNoMatch() {
        assertFalse(pieceFront.matchFrontTo(pieceGround, pieceGround, pieceGround));
    }
    
    @Test
    public void testMatchTopToMatch() {
        assertTrue(pieceTop.matchTopTo(pieceLeft, pieceRight, pieceBack, pieceFront));
    }

    @Test
    public void testMatchTopToNoMatch() {
        assertFalse(pieceTop.matchTopTo(pieceLeft, pieceRight, pieceLeft, pieceLeft));
    }

}
