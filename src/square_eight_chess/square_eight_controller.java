package square_eight_chess;

import chess_core.chess_game;
import chess_core.chess_piece;

import java.awt.*;

/**
 * Created by tl on 2/19/15.
 * controller for square eight chess game, acts as the transport layer between the view and the model.
 */
public class square_eight_controller {

    square_eight_view view;
    private chess_game game; //aka the model
    private tile_state[][] states; //only things needed to known by the view is color and piece type
    private static final int dimension = 8;

    public square_eight_controller(square_eight_view view) {
        super();
        this.view = view;
        game = new square_eight_board();
        game.init_game();
        states = new tile_state[dimension][dimension];
        populate_tile_states();
    }

    //get the tile states from the model and populate the tile states
    public void populate_tile_states() {
        for (int x=0; x<dimension; x++) {
            for (int y=0; y<dimension; y++) {
                chess_piece piece = game.get_piece_at(x, y);
                if (piece!=null) {
                    tile_state tile = new tile_state(piece.get_type(), piece.get_team());
                    states[x][y] = tile;
                }
            }
        }

    }

    //ccall to start the game after the initialization has been done
    public void start_game() {
        view.update(states);
        while(true);
    }

}
