package chess_core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static chess_core.piece_type.*;

/**
 * Created by tl on 2/10/15.
 *
 * chess game main class. an abstract class
 * all chess games made should extend the chess_core.chess_game class, which contains the logic involved for the main functions
 *
 * to make a custom chess game (completely extensible) implement the following two functions:
 * - init_game() - tell the game what pieces to put and where on the board
 * - is_in_bounds(Point position) tells the game what "parts" of the board are in bounds
 *      all functions used to determine activity will determine if a move is legal by calling this function
 */
public abstract class chess_game {
    //map for the chess board. Maps a coordinate to a piece
    protected HashMap<Point, chess_piece> board;
    protected player black;
    protected player white;

    //simulating variable - see simulate_move_safety() for details
    private boolean simulating;

    protected class player {
        /*
         * player class
         * Contains information on player status
         * list of chess pieces
         */
        private team_color color;
        private List<chess_piece> pieces;

        private player_status status;
        private boolean is_turn;

        public player(team_color color) {
            this.color = color;
            pieces = new ArrayList<chess_piece>();
            is_turn = false;
            status = player_status.Safe;
        }

        public void set_status(player_status new_status) {
            status = new_status;
        }

        public void capture_piece(chess_piece piece) {
            pieces.remove(piece);
        }

        public void add_piece(chess_piece piece) {
            pieces.add(piece);
        }

        public void flip_turn() {
            is_turn = !is_turn;
        }

        public player_status get_status() {
            checkmate_check();
            return status;
        }

        /*
        * checks for checkmate
        * checkmate occurs when no moves are available to a player
        */
        public void checkmate_check() {
            for (chess_piece piece : pieces)
                if (piece.get_possible_moves().size()!=0)
                    return;
            status = player_status.Checkmated;
        }

        /*
        * Updates the possible_moves table for each piece of the player
         */
        public void update_all_moves() {
            for (chess_piece piece : pieces)
                piece.update_possible_moves();

        }

        /*
        * gets the chess_core.king piece of the player
         */
        public chess_piece get_king() {
            for (chess_piece piece : pieces)
                if (piece.type.equals(King))
                    return piece;
            return null;
        }

        /*
        * given a coordinate, tells if a player's pieces are able to reach a certain spot
        * Use: give it the other player's chess_core.king's coordinate to determine if the player is in check
         */
        public boolean spot_is_covered(Point position) {
            for (chess_piece piece : pieces) {
                for (Point move : piece.get_possible_moves())
                    if (move.equals(position))
                        return true;
            }
            return false;
        }

        public List<chess_piece> get_pieces() {
            return pieces;
        }
    }

    public chess_game() {
    }
    //abstract functions that should be implemented for chess boards to be played
    public abstract void init_game();
    public abstract boolean is_in_bounds(Point position);

    /*
    * update the player statuses
    * Status include check, checkmated, or safe (no check)
     */
    public void update_statuses() {
        if (white.spot_is_covered(black.get_king().get_position()))
            black.set_status(player_status.Checked);
        else
            black.set_status(player_status.Safe);
        if (black.spot_is_covered(white.get_king().get_position()))
            white.set_status(player_status.Checked);
        else white.set_status(player_status.Safe);
    }

    public chess_game(chess_game other) {
        this.white = other.white;
        this.black = other.black;
        this.board = new HashMap<Point, chess_piece>(other.board);
        this.simulating = false;
    }

    /*
    * update the move tables for both player's pieces
     */
    public void update_all_moves() {
        white.update_all_moves();
        black.update_all_moves();
    }

    /*
    * Update the board to move the piece from old position to new position
    * Updates all piece move tables and statuses of both players
     */
    public void update_board(Point old_position, Point new_position, chess_piece piece) {
        board.remove(old_position);
        if (!spot_empty(new_position))
            capture_piece(new_position);
        board.put(new_position, piece);
        update_all_moves();
        update_statuses();
    }

    /*
    * Captures a piece - aka, nukes it from existence
    * updates all pieces' move tables to reflect the new empty slots
     */
    public void capture_piece(Point position) {
        chess_piece piece = board.get(position);
        board.remove(position);
        if (piece.team.equals(team_color.Black))
            black.capture_piece(piece);
        else
            white.capture_piece(piece);
        update_all_moves();
        update_statuses();
    }

    /*
    * get the color of the piece occupying a certain spot
    * Uses to determine if an occupied spot is in fact a possible desination for a piece
     */
    public team_color get_occupying_color(Point point) {
        if (!spot_empty(point))
            return board.get(point).get_team();
        return null;
    }

    public List<Point> get_movelist(Point point) {
        return get_piece_at(point).get_possible_moves();
    }

    /*
    * Tells if a spot is empty or occupied
     */
    public boolean spot_empty(Point point) {
        return board.get(point)==null;
    }

    /*
    * gets all the pieces belonging to a player
     */
    public List<chess_piece> get_player_pieces(team_color color) {
        if (color.equals(team_color.Black))
            return black.get_pieces();
        return white.get_pieces();
    }

    /*
    * gets the status of a player
    * checked, checkmated, safe
     */
    public player_status get_player_status(team_color color) {
        if (color.equals(team_color.Black))
            return black.get_status();
        return white.get_status();
    }

    /*
    *get the piece object at a certain spot
     */
    public chess_piece get_piece_at(Point position) {
        return board.get(position);
    }

    public chess_piece get_piece_at(int x, int y) {
        return get_piece_at(new Point(x, y));
    }

    public void move_piece(Point from, Point to) throws Exception{
        chess_piece piece = get_piece_at(from);
        if (piece !=null) {
            piece.move_piece(to);
            update_board(from, to, piece);
        }
    }

    /*
    *Creates a piece of type, of the correct color and puts it at the passed spot
     */
    public void make_piece(int x, int y, team_color color, piece_type type) {
        Point loc = new Point(x, y);
        chess_piece piece = null;
        switch (type) {
            case Rook:
                piece = new rook(loc, color, this);
                break;
            case Bishop:
                piece = new bishop(loc, color, this);
                break;
            case Knight:
                piece = new knight(loc, color, this);
                break;
            case King:
                piece = new king(loc, color, this);
                break;
            case Queen:
                piece = new queen(loc, color, this);
                break;
            case Pawn:
                piece = new pawn(loc, color, this);
                break;
        }
        put_piece(piece, loc);
    }

    /*
    *puts a chess piece at a location
     */
    public void put_piece(chess_piece piece, Point position) {
        piece.position = position;
        if (piece.get_team().equals(team_color.White))
            white.add_piece(piece);
        else
            black.add_piece(piece);
        board.put(position, piece);
    }

    /*
    * self explanatory
     */
    public boolean is_simulating() {
        return simulating;
    }

    /*
    * "Simulates" a move, determining if it is a safe move, aka does it put their chess_core.king at risk
    * What it does
    * - sets a flag for simulating - when the game is in simulating mode the move tables are
    *   calculated slightly differently
    * - moves a piece to the hypothetical new spot and determines if a player's chess_core.king is in check
    * - if teh player is check as a result of that move then false is returned, meaning
    *   the spot is not safe
     *
    */
    public boolean simulate_move_safety(chess_piece piece, Point position) throws  Exception {
        simulating = true;
        chess_piece old_occupant = board.get(position);
        if (old_occupant!= null && old_occupant.type.equals(King)) {
            simulating = false;
            return true;
        }
        Point old_location = piece.position;
        piece.simulate_move(position);
        update_all_moves();
        update_statuses();
        boolean safety = get_player_status(piece.get_team()).equals(player_status.Safe);
        piece.simulate_move(old_location);
        if (old_occupant != null)
            put_piece(old_occupant, position);
        update_all_moves();
        update_statuses();
        simulating = false;
        return safety;
    }

}
