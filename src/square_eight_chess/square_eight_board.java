package square_eight_chess;

import chess_core.chess_game;
import chess_core.chess_piece;
import chess_core.piece_type;
import chess_core.team_color;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by tl on 2/12/15.
 * an implementation of a chess game - the standard 8x8 chess game
 */
public class square_eight_board extends chess_game {

    private int width;
    private int height;

    public square_eight_board() {
        super();
        this.width = 8;
        this.height = 8;
    }

    public void init_game() {
        white = new player(team_color.White);
        black = new player(team_color.Black);
        this.board = new HashMap<Point, chess_piece>();
        init_bishops();
        init_kings();
        init_knights();
        init_pawns();
        init_queens();
        init_rooks();
        white.flip_turn();
        update_all_moves();
        update_statuses();
    }

    @Override
    public boolean is_in_bounds(Point position) {
        return (position.x >= 0  && position.x < width
                && position.y >=0 && position. y < height);

    }

    public void init_pawns() {
        int white_y = 1;
        int black_y = 6;
        for (int x_loc =0; x_loc<8; x_loc++) {
            make_piece(x_loc, white_y, team_color.White, piece_type.Pawn);
            make_piece(x_loc, black_y, team_color.Black, piece_type.Pawn);
        }
    }

    public void init_queens() {
        make_piece(3, 0, team_color.White, piece_type.Queen);
        make_piece(3, 7, team_color.Black, piece_type.Queen);
    }

    public void init_kings() {
        make_piece(4, 0, team_color.White, piece_type.King);
        make_piece(4, 7, team_color.Black, piece_type.King);
    }

    public void init_rooks() {
        int white_y = 0;
        int black_y = 7;
        for (int x_loc =0; x_loc<8; x_loc+=7) {
            make_piece(x_loc, white_y, team_color.White, piece_type.Rook);
            make_piece(x_loc, black_y, team_color.Black, piece_type.Rook);
        }
    }

    public void init_bishops() {
        int white_y = 0;
        int black_y = 7;
        for (int x_loc =2; x_loc<6; x_loc+=3) {
            make_piece(x_loc, white_y, team_color.White, piece_type.Bishop);
            make_piece(x_loc, black_y, team_color.Black, piece_type.Bishop);
        }
    }

    public void init_knights() {
        int white_y = 0;
        int black_y = 7;
        for (int x_loc =1; x_loc<7; x_loc+=5) {
            make_piece(x_loc, white_y, team_color.White, piece_type.Knight);
            make_piece(x_loc, black_y, team_color.Black, piece_type.Knight);
        }
    }
}
