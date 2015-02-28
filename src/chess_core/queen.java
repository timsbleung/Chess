package chess_core;

import chess_core.chess_game;
import chess_core.piece_type;
import chess_core.sliding_chess_piece;
import chess_core.team_color;

import java.awt.*;

/**
 * Created by tl on 2/11/15.
 * Nothing interesting here, extends sliding chess piece
 */
public class queen extends sliding_chess_piece {


    public queen(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
        this.type = piece_type.Queen;
        movement_vectors = new Point[]{
                new Point(1, 0),
                new Point(-1, 0),
                new Point(0, -1),
                new Point(0, 1),
                new Point(1, 1),
                new Point(-1, -1),
                new Point(1, -1),
                new Point(-1, 1)
        };
    }
}
