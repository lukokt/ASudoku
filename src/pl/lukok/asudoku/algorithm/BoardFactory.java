package pl.lukok.asudoku.algorithm;

import pl.lukok.asudoku.entity.SudokuBoard;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 10/28/13
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoardFactory {


    public static SudokuBoard getBoard(IGeneratorAlgorithm algorithm, int level) {

        return algorithm.generateBoard(level);
    }

}
