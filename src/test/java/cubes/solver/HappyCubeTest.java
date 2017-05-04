package cubes.solver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HappyCubeTest {

    @Test
    public void testNextSide() {
        final HappyCube cube = new HappyCube();
        assertEquals(HappyCube.CubeSide.GROUND, cube.getCurrentSide());
        cube.nextSide();
        assertEquals(HappyCube.CubeSide.LEFT, cube.getCurrentSide());
        for (int i = 0; i < 10; i++) {
            cube.nextSide();
        }
        assertEquals(HappyCube.CubeSide.TOP, cube.getCurrentSide());
    }

    @Test
    public void testPreviousSide() {
        final HappyCube cube = new HappyCube();
        cube.nextSide();
        cube.nextSide();
        cube.previousSide();
        assertEquals(HappyCube.CubeSide.LEFT, cube.getCurrentSide());
        for (int i = 0; i < 10; i++) {
            cube.previousSide();
        }
        assertEquals(HappyCube.CubeSide.GROUND, cube.getCurrentSide());
    }

}
