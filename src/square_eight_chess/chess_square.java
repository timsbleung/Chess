package square_eight_chess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by tl on 2/19/15.
 * A "Square" on a chessboard, extends the jbutton and can be pressed on for various effects
 */
public class chess_square extends JButton {

    chess_square(Color color) {
        super();
        setBackground(color);
        setContentAreaFilled(false);
        setOpaque(true);
    }

    //set the icon on the button, called by the controller to set what is displayed on the tile based on peice
    public void set_icon(ImageIcon icon) {
        setIcon(icon);
        repaint();
    }
}
