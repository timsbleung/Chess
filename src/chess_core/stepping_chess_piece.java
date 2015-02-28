package chess_core;

import java.awt.*;

/**
 * Created by tl on 2/12/15.
 *
 * a "stepping" chess piece
 * A piece that can step in certain directions, such as a chess_core.knight or a chess_core.king
 * steps = a table of moves a piece can make
 * update_possible_moves() iterates through these steps and sees which are legal
 *
 * To make a stepping class, just extend and fill in the steps table
 */
public abstract class stepping_chess_piece extends chess_piece {
    protected Point[] steps = null;

    public stepping_chess_piece(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
    }

    protected void update_possible_moves() {
        possible_moves.clear();
        for (Point step : steps)
            add_move(new Point(position.x+step.x, position.y+step.y));
    }

}