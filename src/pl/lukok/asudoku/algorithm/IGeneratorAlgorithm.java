package pl.lukok.asudoku.algorithm;

import pl.lukok.asudoku.entity.SudokuBoard;

/**
 * Created with IntelliJ IDEA.
 * User: lukasz
 * Date: 10/28/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
interface IGeneratorAlgorithm {


    public SudokuBoard generateBoard(int level);

}
