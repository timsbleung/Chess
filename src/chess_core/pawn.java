package chess_core;

import chess_core.chess_game;
import chess_core.chess_piece;
import chess_core.piece_type;
import chess_core.team_color;

import java.awt.*;

/**
 * Created by tl on 2/10/15.
 * Nothing interesting here, extends chess piece
 */
public class pawn extends chess_piece {

    int forward_direction;

    public pawn(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
        this.type = piece_type.Pawn;
        forward_direction = (team.equals(team_color.White)) ? 1 : -1;
    }


    protected void update_possible_moves() {
        possible_moves.clear();
        Point forward =  new Point(position.x, position.y+forward_direction);
        if (chess_board.is_in_bounds(forward) && chess_board.spot_empty(forward))
            add_move(forward);
        check_capture_spots(new Point(position.x+1, position.y+forward_direction));
        check_capture_spots(new Point(position.x-1, position.y+forward_direction));
    }

    private void check_capture_spots(Point position) {
        if (chess_board.is_in_bounds(position) &&!chess_board.spot_empty(position)
                && !chess_board.get_occupying_color(position).equals(team))
            add_move(position);
    }
}
