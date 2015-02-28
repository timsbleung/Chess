package square_eight_chess;

import chess_core.piece_type;
import chess_core.team_color;

/**
 * Created by tl on 2/20/15.
 * a simple class designed to be passed around the view and the controller to determine what kind of piece is at a location
 */
public class tile_state {
    public piece_type type;
    public team_color color;

    public tile_state(piece_type  type, team_color color) {
        this.type = type;
        this.color = color;
    }
}
