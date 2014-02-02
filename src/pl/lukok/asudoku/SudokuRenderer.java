package pl.lukok.asudoku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import pl.lukok.asudoku.animation.FieldAnimation;
import pl.lukok.asudoku.entity.SudokuField;
import pl.lukok.asudoku.entity.SudokuBoard;
import pl.lukok.asudoku.entity.SudokuGame;

/**
 * Draw sudoku game - board field background etc
 */
public class SudokuRenderer {

    public int fieldWidth, fieldHeight;

    private int width, height;

    private Paint paint, lightLine, darkLine, highlight, boardBGLight, boardBGDark, outsideBoardBg;

    private Rect boardRect;

    private Resources resources;

    private SudokuGame game;

    private Context context;

    public SudokuRenderer(Context context, Resources resources) {

        this.resources = resources;
        this.context = context;
        this.init();
    }

    private void init() {

        lightLine = new Paint();
        lightLine.setColor(resources.getColor(R.color.board_light_line));

        darkLine = new Paint();
        darkLine.setColor(resources.getColor(R.color.board_dark_line));

        highlight = new Paint();
        highlight.setColor(resources.getColor(R.color.board_highlight));

        boardBGLight = new Paint();
        boardBGLight.setColor(resources.getColor(R.color.board_bg_light));

        boardBGDark = new Paint();
        boardBGDark.setColor(resources.getColor(R.color.board_bg_dark));

        paint = new Paint();
    }

    public void render(Canvas canvas, SudokuGame game) {

        this.game = game;

        Rect rect = new Rect(0, 0, width, height);

        // background
        paint.setColor(resources.getColor(R.color.non_board_bg));
        canvas.drawRect(rect, paint);

        this.render(canvas, game.getBoard());

    }

    public void render(Canvas canvas, SudokuBoard board) {

        // background
        paint.setColor(resources.getColor(R.color.board_dark_line));
        canvas.drawRect(boardRect, paint);

        // draw 9 horizontal lines between each field
        for(int i = 0; i <= SudokuBoard.FIELD_NUM; i++) {

            float y = boardRect.top + i*fieldHeight;
            float x = boardRect.left + i*fieldWidth;

            paint.setColor(resources.getColor(R.color.board_light_line));
        //    canvas.drawLine(boardRect.left, y, boardRect.right, y,     paint);
        //    canvas.drawLine(x,     boardRect.top, x,     boardRect.bottom, paint);

            paint.setColor(resources.getColor(R.color.board_highlight));
            canvas.drawLine(boardRect.left, y + 1, boardRect.right, y + 1, paint);
            canvas.drawLine(x + 1, boardRect.top, x + 1, boardRect.bottom, paint);
        }


        paint.setColor(resources.getColor(R.color.board_dark_line));
        for(int i = 0; i <= SudokuBoard.FIELD_NUM; i += 3) {

            // 3 bold horizontal lines
            float y = boardRect.top + i*fieldHeight;
            canvas.drawLine(boardRect.left, y,     boardRect.right + 2, y,     paint);
            canvas.drawLine(boardRect.left, y + 1, boardRect.right + 2, y + 1, paint);

            // 3 bold vertical lines
            int x = boardRect.left + i*fieldWidth;
            canvas.drawLine(x,     boardRect.top, x,     boardRect.bottom + 2, paint);
            canvas.drawLine(x + 1, boardRect.top, x + 1, boardRect.bottom + 2, paint);
        }


        for(int y = 0; y < SudokuBoard.FIELD_NUM; y++) {
            for(int x = 0; x < SudokuBoard.FIELD_NUM; x++) {
                this.render(canvas, board.getField(x, y));
            }
        }
    }

    public void render(Canvas canvas, FieldAnimation animation) {

    }

    public void render(Canvas canvas, SudokuField field) {


        Paint font = new Paint(Paint.ANTI_ALIAS_FLAG);
        font.setStyle(Paint.Style.FILL);
        font.setTextSize(fieldHeight * 0.75f);
        font.setTextScaleX(fieldWidth / fieldHeight);
        font.setTextAlign(Paint.Align.CENTER);

        int boarder = 2;
        Rect view = new Rect(0, 0, fieldWidth-boarder, fieldHeight-boarder);
        view.offsetTo(boardRect.left + field.x * fieldWidth + boarder, boardRect.top + field.y * fieldHeight + boarder);

        if (field.isEditable()) {
            paint.setColor(resources.getColor(R.color.board_bg_light));
        } else {
            paint.setColor(resources.getColor(R.color.board_bg_dark));
        }
        canvas.drawRect(view, paint);

        if (!field.isEmpty()) {

            Paint.FontMetrics fontMetrics = font.getFontMetrics();

            float deltaWidth  = fieldWidth / 2;
            float deltaHeight = fieldHeight / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;

            int fontColor = field.getFontColor(context);

            font.setColor(resources.getColor(fontColor));

            canvas.drawText(field.toString(), view.left + deltaWidth, view.top + deltaHeight, font);
        }

        if (field.isActive()) {
            paint.setColor(resources.getColor(R.color.non_board_bg));
            view.offsetTo(view.left - boarder, view.top - boarder);

            // left line
            canvas.drawLine(view.left,   view.top, view.left,   view.top+fieldHeight, paint);
            canvas.drawLine(view.left+1, view.top, view.left+1, view.top+fieldHeight, paint);
            // right line
            canvas.drawLine(view.left+fieldWidth, view.top, view.left+fieldWidth, view.top+fieldHeight, paint);
            canvas.drawLine(view.left+fieldWidth-1, view.top, view.left+fieldWidth-1, view.top+fieldHeight, paint);
            // top line
            canvas.drawLine(view.left, view.top,   view.left+fieldWidth, view.top,   paint);
            canvas.drawLine(view.left, view.top+1, view.left+fieldWidth, view.top+1, paint);
            // bottom line
            canvas.drawLine(view.left, view.top+fieldHeight,  view.left+fieldWidth,  view.top+fieldHeight,   paint);
            canvas.drawLine(view.left, view.top+fieldHeight-1,  view.left+fieldWidth-1,  view.top+fieldHeight, paint);
        }
    }

    public void setWorldSize(int width, int height) {

        this.width          = width;
        this.height         = height;
        int boardSize       = Math.min(width, height);
        this.fieldWidth     = (int)Math.floor(boardSize / SudokuBoard.FIELD_NUM);
        this.fieldHeight    = (int)Math.floor(boardSize / SudokuBoard.FIELD_NUM);
        boardSize = fieldWidth * SudokuBoard.FIELD_NUM;

        boardRect = new Rect(0, 0, boardSize, boardSize);
        boardRect.offsetTo((width - boardSize) / 2, (height - boardSize) / 2);
    }

    public Point convertToBoardPosition(float eventX, float eventY) {

        //TODO gdzie konwertowac zmienne event na swiat
        int worldX = (int)Math.floor((eventX - boardRect.left) / fieldWidth);
        int worldY = (int)Math.floor((eventY - boardRect.top) / fieldHeight);

        return new Point(worldX, worldY);
    }
}
