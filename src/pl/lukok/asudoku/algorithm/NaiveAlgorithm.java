package pl.lukok.asudoku.algorithm;

import pl.lukok.asudoku.entity.SudokuField;
import pl.lukok.asudoku.entity.SudokuBoard;
import pl.lukok.asudoku.SudokuUtil;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 10/28/13
 * Time: 9:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class NaiveAlgorithm implements IGeneratorAlgorithm {

    private SudokuBoard board;


    public NaiveAlgorithm() {
        this.board = new SudokuBoard();
    }

    public SudokuBoard generateBoard(int level) {

        System.out.println("Generate new board, level: " + (new Integer(level).toString()));

        // wypelniamy liczbami w formacie
        // 1 2 3 4 5 6 7 8 9
        // 4 5 6 7 8 9 1 2 3
        // 7 8 9 1 2 3 4 5 6

        for(int y = 0, start, loop = 0, value; y < SudokuBoard.FIELD_NUM; y++) {

            if (y % 3 == 0) {
                loop++;
            }

            start = y * 3 + loop;

            for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {

                value = (start + x) % SudokuBoard.FIELD_NUM;
                if (value == SudokuField.INIT_VALUE) {
                    value = SudokuBoard.FIELD_NUM;
                }
                board.getField(x, y).initField(value);
            }
        }

        // sortujemy, mieszamy kolumny, rzedu w obrebie 3 pol{
        for(int start = 0, end = 3; start < SudokuBoard.FIELD_NUM; start += 3, end += 3) {

            int n = SudokuUtil.random(start, end);
            int m = SudokuUtil.random(start, end, n);

            this.switchRows(n, m);
            this.switchColumns(n, m);
        }

        // ustawiamy pola edytowalne wzgledem poziomu trudnosci
        SudokuField field;
        for(int y = 0; y < SudokuBoard.FIELD_NUM; y+=3) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x+=3) {
                for (int n = 0; n < level; ) {

                    field = board.getField(SudokuUtil.random(x, x + 3), SudokuUtil.random(y, y + 3));

                    if (!field.isEditable()) {
                        field.initEditableField();
                        n++;
                    }
                }
            }
        }


        return board;
    }

    private void switchColumns(int x1, int x2) {
        for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
            this.switchField(x1, y, x2, y);
        }
    }

    private void switchRows(int y1, int y2) {
        for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
            this.switchField(x, y1, x, y2);
        }
    }

    private void switchField(int x, int y, int x2, int y2) {
        SudokuField field = board.getField(x, y);

        board.setField(x, y, board.getField(x2, y2));

        board.setField(x2, y2, field);
    }
}
