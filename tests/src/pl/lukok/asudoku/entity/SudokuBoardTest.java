package pl.lukok.asudoku.entity;

import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * Created by lukasz on 2/2/14.
 */
public class SudokuBoardTest extends TestCase {

    SudokuBoard mSudokuBoard;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mSudokuBoard = new SudokuBoard();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testIsValid() throws Exception {

        int value = 1;
        int other = value;

        SudokuField field = mSudokuBoard.getField(0, 0);
        assertTrue(mSudokuBoard.isValid(field));

        field.setValue(value);
        assertTrue(mSudokuBoard.isValid(field));

        mSudokuBoard.getField(1, 0).setValue(value);
        assertFalse(mSudokuBoard.isValid(field));

        mSudokuBoard.getField(1, 0).setValue(++other);
        assertTrue(mSudokuBoard.isValid(field));


        mSudokuBoard.getField(2, 2).setValue(value);
        assertFalse(mSudokuBoard.isValid(field));
        mSudokuBoard.getField(2, 2).setValue(++other);
        assertTrue(mSudokuBoard.isValid(field));

        mSudokuBoard.getField(5, 0).setValue(value);
        assertFalse(mSudokuBoard.isValid(field));
        mSudokuBoard.getField(5, 0).setValue(++other);
        assertTrue(mSudokuBoard.isValid(field));
    }

    public void testIsSolved() throws Exception {

    }

    public void testApplyLevel() throws Exception {

    }
}
