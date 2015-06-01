package resource;

import javax.swing.*;
import java.nio.file.Paths;

/**
 * Created by hayesj3 on 4/27/2015.
 *
 * contains references to all the resources used by the game
 */
public final class Resources {
    public static Resource iconR = new Resource(Paths.get("assets"), "icon.png");
    public static Resource logo = new Resource(Paths.get("assets", "splash"), "logo.png");

    public static ImageIcon iconII = new ImageIcon(iconR.getImage());

    private Resources() {}
}
