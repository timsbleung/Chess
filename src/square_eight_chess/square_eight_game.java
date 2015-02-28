package square_eight_chess;

import javax.swing.*;

/**
 * Created by tl on 2/19/15.
 *
 * main class for the square eight game, does no work, is just the main function that starts everything
 */
public class square_eight_game {

    public static void main(String[] args) {
        //init the view
        square_eight_view view = new square_eight_view();
        //init the controller and pass him the view
        square_eight_controller controller = new square_eight_controller(view);

        JFrame gui = new JFrame("Chess Game");
        gui.setSize(800,800);
        gui.add(view);
        gui.setVisible(true);
        gui.add(view);
        controller.start_game();

    }
}
