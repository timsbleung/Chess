package chess_core;

import java.awt.*;

/**
 * Created by tl on 2/19/15.
 */
public class footman extends stepping_chess_piece {
    public footman(Point starting_pos, team_color team, chess_game board) {
        super(starting_pos, team, board);
        this.type = piece_type.Footman;
        steps = new Point[] {
                new Point(1,0),
                new Point(-1,0),
                new Point(0,1),
                new Point(0,-1),
                new Point(3,0),
                new Point(-3,0),
                new Point(0,3),
                new Point(0,-3),
        };
    }
}
