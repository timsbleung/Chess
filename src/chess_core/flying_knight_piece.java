package chess_core;

import java.awt.*;

/**
 * Created by tl on 2/19/15.
 */
public class flying_knight_piece extends sliding_chess_piece{
    public flying_knight_piece(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
        this.type = piece_type.Flying_Knight;
        movement_vectors = new Point[]{
                new Point(-1, 2),
                new Point(1, -2),
                new Point(-1, -2),
                new Point(1, 2),
                new Point(-2, 1),
                new Point(2, -1),
                new Point(-2, -1),
                new Point(2, 1),
        };
    }
}
