package square_eight_chess;

import chess_core.piece_type;
import chess_core.team_color;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by tl on 2/20/15.
 * A manager for the icons used by the square eight chess game
 */
public class icon_manager {

    public HashMap<String, ImageIcon> imagebase; //storage for icons

    public icon_manager() {
        imagebase = new HashMap<String, ImageIcon>();
    }


    public ImageIcon get_icon(team_color color, piece_type type) {
        String key = new String(color+"_"+type).toLowerCase(); //key to look up table
        ImageIcon icon = imagebase.get(key);
        if (icon==null)
            icon = load_image_from_disk(key); //if not found, load the icon from the disk
        return icon;
    }

    //upon loading from disk, save it to the hashtable to avoid constant disk lookups
    private ImageIcon load_image_from_disk(String key) {
        String file = "images/"+key+".png";
        ImageIcon icon = new ImageIcon(file);
        imagebase.put(key, icon);
        return icon;
    }

}
