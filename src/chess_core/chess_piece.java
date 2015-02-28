package chess_core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tl on 2/10/15.
 *
 * Base class for the chess piece. abstract class
 * All pieces should either extend chess piece or extend a subclass
 * Current extendable subclasses : chess_core.sliding_chess_piece and chess_core.stepping_chess_piece (See respective classes)
 */
public abstract class chess_piece {

    protected piece_type type;
    protected Point position;
    protected List<Point> possible_moves;
    protected team_color team;

    protected chess_game chess_board;

    public chess_piece(Point starting_pos, team_color team, chess_game board) {
        this.position = starting_pos;
        this.team = team;
        this.type = piece_type.Bishop;
        chess_board = board;
        this.possible_moves = new ArrayList<Point>();
    }

    /*
    * moves a piece to the new position
     */
    public void move_piece(Point new_position) throws Exception{
        if (!possible_moves.contains(new_position))
            throw new Exception("invalid position "+new_position+" for piece "+team+" "+type+" from "+position);
        position = new_position;
    }

    /*
    * a "simulated" move. Moves a piece but does not check for illegal moves
     */
    public void simulate_move(Point new_position) {
        Point old_position = position;
        position = new_position;
        chess_board.update_board(old_position, new_position, this);
    }

    /*
    * possibly adds a move, and returns whether or not the spot met was occupied
    * If the spot is a legal spot, in bouds and empty, we call safe_add() to add the move to the move list
    * returns true if the spot was empty
    * returns false if the spot was occupied (but adds to the move list if it is an enemy piece occupying it)
     */
    public boolean add_move(Point point) {
        if (chess_board.is_in_bounds(point)) {
            if (chess_board.spot_empty(point)) {
                safe_add(point);
                return true;
            }
            if (!chess_board.get_occupying_color(point).equals(team))
                safe_add(point);
        }
        return false;
    }

    /*
    * "safe" add.
    * Adds the move to the move list if it is safe
    * safety is determined by simulating a move to see if the chess_core.king is placed in risk by that move
     */
    public void safe_add(Point point) {
        try {
            List<Point> temporary_movelist = new ArrayList<Point>(possible_moves);
            if ( chess_board.is_simulating() ||chess_board.simulate_move_safety(this, point))
                temporary_movelist.add(point);
            possible_moves = temporary_movelist;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public piece_type get_type() {
        return type;
    }

    public Point get_position() {
        return position;
    }

    public List<Point> get_possible_moves() {
        update_possible_moves();
        return possible_moves;
    }

    public team_color get_team() {
        return team;
    }

    /*
    * Abstract method to be implemented by all pieces.
    * Tells the piece how to update its move tables
     */
    protected abstract void update_possible_moves();



}
