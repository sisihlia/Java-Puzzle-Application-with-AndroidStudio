package com.example.sisily.puzzle_15;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;

/**
 * Created by sisily on 01/02/18.
 */

public class BoardView extends View {
    private Board board;

    /** The width. */
    private float width;

    /** The height. */
    private float height;

    /**
     * Instantiates a new board view.
     *
     * @param context
     *            the context
     * @param board
     *            the board
     */
    public BoardView(Context context, Board board) {
        super(context);
        this.board = board;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onSizeChanged(int, int, int, int)
    */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w / this.board.size();
        this.height = h / this.board.size();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * Locate position.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @return the position
     */
    private Position locatePlace(float x, float y) {
        int ix = (int) (x / width);
        int iy = (int) (y / height);

        return board.atPosition(ix + 1, iy + 1);
    }

    /** return boolean value of onTouchEvent*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        Position p = locatePlace(event.getX(), event.getY());
        if (p != null && p.slidable()) {
            p.slide();
            invalidate();
        }
        return true;
    }

    /** draw the board with given size (size=3) */
    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.board_color));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.tile_color));
        dark.setStrokeWidth(15);

        // Draw the major grid lines
        for (int i = 0; i < this.board.size(); i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
        }

        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.tile_color));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER);

        float x = width / 2;
        Paint.FontMetrics fm = foreground.getFontMetrics();
        float y = (height / 2) - (fm.ascent + fm.descent) / 2;

        Iterator<Position> it = board.places().iterator();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                if (it.hasNext()) {
                    Position p = it.next();
                    if (p.hasTile()) {
                        String number = Integer.toString(p.getTile().number());
                        canvas.drawText(number, i * width + x, j * height + y,
                                foreground);
                    } else {
                        canvas.drawRect(i * width, j * height, i * width
                                + width, j * height + height, dark);
                    }
                }
            }
        }
    }
}
