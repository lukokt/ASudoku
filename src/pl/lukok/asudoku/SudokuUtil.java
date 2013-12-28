package pl.lukok.asudoku;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 10/19/13
 * Time: 8:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuUtil {

    public static int random(int start, int end) {
        return start + (int)((end - start) * Math.random());
    }

    public static int random(int start, int end, int without) {

        int rand;
        do {
            rand = SudokuUtil.random(start, end);
        } while(without == rand);

        return rand;
    }

}
