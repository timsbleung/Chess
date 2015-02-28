package square_eight_chess;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tl on 2/19/15.
 * the view fo the square eight game, is esentially the main gui element involved in the chess game
 * Does not communicate with the model on its own, instead uses the controller as a bridge, communicating through
 *  much more stripped down simpler means
 */
public class square_eight_view extends JPanel {

    public static final Color[] tile_colors = {Color.darkGray, Color.lightGray}; //background colors
    public static final int dimension = 8;
    private icon_manager icons;

    public chess_square[][] tiles;



    public square_eight_view() {
        icons = new icon_manager();
        tiles = new chess_square[dimension][dimension];
        for (int width=0; width<dimension; width++) {
            for (int height=0; height<dimension; height++) {
                tiles[width][height] = new chess_square(tile_colors[((width%2)+height%2)%2]);
            }
        }

        init();
    }

    public void update(tile_state[][] states) {
        for (int x=0; x<dimension; x++) {
            for (int y=0; y<dimension; y++) {
                if (states[x][y]!=null) {
                    System.out.println("at "+x+" "+y+" is piece"+states[x][y].color+states[x][y].type);
                    tiles[x][y].set_icon(icons.get_icon(states[x][y].color, states[x][y].type));
                }
            }
        }
    }

    public void init() {
        setLayout(new GridLayout(8, 8));
        for (int y=0; y<dimension; y++)
            for (int x=0; x<dimension; x++)
                add(tiles[x][y]);
        setVisible(true);
    }

    @Override
    public void paintComponent (Graphics g) {
    }
}
