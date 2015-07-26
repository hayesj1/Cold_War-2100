package resource;

import java.nio.file.Paths;

/**
 * Created by hayesj3 on 4/27/2015.
 *
 * contains references to all the resources used by the game
 */
public final class VisualResources {
    public static final Resource iconR = new Resource(Paths.get("assets"), "icon.png");
    public static final Resource logo = new Resource(Paths.get("assets", "splash"), "logo.png");

    //public static final ImageIcon iconII = new ImageIcon(iconR.getImage());

    private VisualResources() {}
}
