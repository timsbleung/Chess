package chess_core;

import java.awt.*;

/**
 * Created by tl on 2/12/15.
 *
 * A "Sliding" chess piece
 * a sliding chess piece is allowed to move in a certain direction until it hits a wall or another piece
 * Sliding pieces include pieces like chess_core.rook (moves horizontally and vertically), chess_core.bishop (diagonal) and chess_core.queen
 *
 * movement_vectors = directions a sliding piece can move in with varying magnitudes
 * incrementally tries movements in one direction until a wall is hit
 *
 * To make a sliding chess piece, all that needs to be done is the movement_vectors need to be defined
 */
public abstract class sliding_chess_piece extends chess_piece {
    protected Point[] movement_vectors = null;

    public sliding_chess_piece(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
    }

    @Override
    protected void update_possible_moves() {
        possible_moves.clear();
        for (Point velocity : movement_vectors) {
            int magnitude = 1;
            while (add_move(new Point(position.x+(magnitude*velocity.x), position.y+(magnitude*velocity.y))))
                magnitude++;
        }
    }
}
