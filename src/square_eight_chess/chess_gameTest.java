package square_eight_chess;

import chess_core.*;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class chess_gameTest {

    @Test
    public void testInit_game() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece piece = game.get_piece_at(new Point(0,0));
        assertEquals(piece.get_team(), team_color.White);
        assertEquals(piece.get_type(), piece_type.Rook);

        piece = game.get_piece_at(new Point(5,1));
        assertEquals(piece.get_team(), team_color.White);
        assertEquals(piece.get_type(), piece_type.Pawn);

        piece = game.get_piece_at(new Point(5,6));
        assertEquals(piece.get_team(), team_color.Black);
        assertEquals(piece.get_type(), piece_type.Pawn);

        assertTrue(game.spot_empty(new Point(3, 3)));
        assertFalse(game.spot_empty(new Point(0, 0)));

    }

    @Test
    public void test_move_pawn() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        List<Point> moves = game.get_movelist(new Point(1,1));
        assertEquals(moves.size(), 1);
        assertFalse(game.spot_empty(new Point(1, 1)));
        game.move_piece(new Point(1,1), moves.get(0));
        assertTrue(game.spot_empty(new Point(1, 1)));
        assertFalse(game.spot_empty(new Point(1, 2)));
        assertTrue(game.get_movelist(new Point(1,2)).contains(new Point(1, 3)));
    }

    //@Test
    public void test_capturing() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece pawn = game.get_piece_at(new Point(1,1));
        for (int steps=0; steps<4; steps++)
            pawn.move_piece(pawn.get_possible_moves().get(0));
        assertEquals(pawn.get_position(), new Point(1, 5));
        assertEquals(pawn.get_possible_moves().size(), 2);
        pawn.move_piece(new Point(0, 6));
        assertEquals(game.get_piece_at(new Point(0, 6)).get_team(), team_color.White);
        assertEquals(game.get_player_pieces(team_color.White).size(), 16);
        assertEquals(game.get_player_pieces(team_color.Black).size(), 15);
    }

    //@Test
    public void test_bishop() throws  Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece pawn = game.get_piece_at(new Point(1,1));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        chess_piece bishop = game.get_piece_at(new Point(2, 0));
        assertEquals(bishop.get_team(), team_color.White);
        assertEquals(bishop.get_type(), piece_type.Bishop);
        assertEquals(bishop.get_possible_moves().size(), 2);
        pawn = game.get_piece_at(new Point(3,1));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        assertEquals(bishop.get_possible_moves().size(), 7);
        assertTrue(bishop.get_possible_moves().contains(new Point(7, 5)));
        assertTrue(bishop.get_possible_moves().contains(new Point(4, 2)));
        bishop.move_piece(new Point(4, 2));
        assertEquals(bishop.get_possible_moves().size(), 9);
        assertTrue(bishop.get_possible_moves().contains(new Point(0, 6)));
        assertTrue(bishop.get_possible_moves().contains(new Point(7, 5)));
        assertFalse(bishop.get_possible_moves().contains(new Point(5, 1)));
    }

    //@Test
    public void test_illegal_move() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece rook = game.get_piece_at(new Point(0, 0));
        Throwable e = null;
        try {
            rook.move_piece(new Point(3, 3));
        }
        catch (Exception exception) {
            e = exception;
        }
        assertEquals(e.getMessage(), "invalid position java.awt.Point[x=3,y=3] for piece White Rook from java.awt.Point[x=0,y=0]");
    }

   //@Test
    public void test_rook() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece rook = game.get_piece_at(new Point(0, 0));
        game.capture_piece(new Point(0, 1));
        assertTrue(game.spot_empty(new Point(0, 1)));
        assertEquals(rook.get_team(), team_color.White);
        assertEquals(rook.get_type(), piece_type.Rook);
        assertEquals(rook.get_possible_moves().size(), 6);
        assertTrue(rook.get_possible_moves().contains(new Point(0, 1)));
        assertTrue(rook.get_possible_moves().contains(new Point(0, 3)));
        assertTrue(rook.get_possible_moves().contains(new Point(0, 6)));
        rook.move_piece(new Point(0, 6));
        assertEquals(game.get_player_pieces(team_color.Black).size(), 15);
        rook = game.get_piece_at(new Point(0, 7));
       assertEquals(rook.get_possible_moves().size(), 1);
        assertTrue(rook.get_possible_moves().contains(new Point(0, 6)));
        rook.move_piece(new Point(0, 6));
        assertEquals(game.get_player_pieces(team_color.White).size(), 14);
        rook.move_piece(new Point(0, 3));
        assertEquals(rook.get_possible_moves().size(), 14);
        rook.move_piece(new Point(3,3));
        assertEquals(rook.get_possible_moves().size(), 11);
    }

    //@Test
    public void test_queen() throws  Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece queen = game.get_piece_at(new Point(3, 7));
        assertEquals(queen.get_team(), team_color.Black);
        assertEquals(queen.get_type(), piece_type.Queen);
        game.capture_piece(new Point(3, 6));
        queen.move_piece(new Point(3, 3));
        assertEquals(queen.get_possible_moves().size(), 21);
        assertTrue(queen.get_possible_moves().contains(new Point(3, 1)));
        assertTrue(queen.get_possible_moves().contains(new Point(1, 1)));
        assertTrue(queen.get_possible_moves().contains(new Point(5, 1)));
        assertFalse(queen.get_possible_moves().contains(new Point(0, 7)));
    }

    @Test
    public void test_knight() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        assertEquals(game.get_movelist(new Point(1,7)).size(), 2);
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(0, 5)));
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(2, 5)));
//        chess_core.knight.move_piece(new Point(2, 5));
//        assertEquals(chess_core.knight.get_possible_moves().size(), 5);
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(1, 3)));
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(3, 3)));
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(0, 4)));
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(4, 4)));
//        assertTrue(chess_core.knight.get_possible_moves().contains(new Point(1, 7)));
//        chess_core.knight.move_piece(new Point(3,3));
//        assertEquals(chess_core.knight.get_possible_moves().size(), 8);
    }

   // @Test
    public void test_king() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece king = game.get_piece_at(new Point(4, 7));
        assertEquals(king.get_team(), team_color.Black);
        assertEquals(king.get_type(), piece_type.King);
        assertEquals(king.get_possible_moves().size(), 0);
        game.capture_piece(new Point(4, 6));
        assertEquals(king.get_possible_moves().size(), 1);
        king.move_piece(new Point(4, 6));
        assertEquals(king.get_possible_moves().size(), 2);
        king.move_piece(new Point(4, 5));
        assertEquals(king.get_possible_moves().size(), 4);
    }

    @Test
    public void test_checkedness() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        game.move_piece(new Point(1, 7), new Point(2, 5));
        game.move_piece(new Point (2,5), new Point(4,4));
        assertEquals(game.get_player_status(team_color.White), player_status.Safe);
        game.move_piece(new Point(4, 4), new Point(5, 2));
        //move black chess_core.knight to check white chess_core.king
        assertEquals(game.get_player_status(team_color.White), player_status.Checked);
        game.move_piece(new Point(1,0), new Point(2,2));
        //move white chess_core.knight to check black chess_core.king
        game.move_piece(new Point (2,2), new Point(4,3));
        game.move_piece(new Point (4,3), new Point(5,5));


        assertEquals(game.get_player_status(team_color.Black), player_status.Checked);
//        chess_core.king is checked, chess_core.pawn at edge should have 0 moves available
        assertEquals(game.get_movelist(new Point(0,6)).size(), 0);
        assertEquals(game.get_movelist(new Point(7, 6)).size(), 0);

        //chess_core.king is checked, the chess_core.pawn who can defend should only have one move availabe, to defend the chess_core.king

        assertEquals(game.get_movelist(new Point(6,6)).size(), 1);
        assertTrue(game.get_movelist(new Point(6, 6)).contains(new Point(5, 5)));
        assertFalse(game.get_movelist(new Point(6, 6)).contains(new Point(6, 5)));

        //likewise chess_core.knight should only be able to defend chess_core.king
        assertEquals(game.get_movelist(new Point(6,7)).size(), 1);
        assertTrue(game.get_movelist(new Point(6, 7)).contains(new Point(5, 5)));

        //take the chess_core.knight, black is no longer checked
        assertEquals(game.get_player_status(team_color.Black), player_status.Checked);
        game.move_piece(new Point (6,7), new Point(5,5));
        assertEquals(game.get_player_status(team_color.Black), player_status.Safe);
        assertEquals(game.get_piece_at(new Point(5,5)).get_team(), team_color.Black);
        assertEquals(game.get_player_pieces(team_color.White).size(), 15);
        assertEquals(game.get_player_pieces(team_color.Black).size(), 16);
    }

    //@Test
    public void test_checkmate() throws Exception {
        chess_game game = new square_eight_board();
        game.init_game();
        chess_piece pawn = game.get_piece_at(new Point(5, 6));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        pawn = game.get_piece_at(new Point(6, 6));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        pawn = game.get_piece_at(new Point(4, 1));
        pawn.move_piece(pawn.get_possible_moves().get(0));
        chess_piece queen = game.get_piece_at(new Point(3, 0));
        queen.move_piece(new Point(7, 4));
        for (chess_piece piece : game.get_player_pieces(team_color.Black))
            assertEquals(piece.get_possible_moves().size(), 0);
        //assertEquals(game.get_player_status(chess_core.team_color.Black), chess_core.player_status.Checkmated);

    }
}