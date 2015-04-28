package resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

/**
 * @author Jacob Hayes
 * Class to load resources for the game
 * Created by hayesj3 on 4/25/2015.
 */
public final class ResourceLoader {

    private static File workingDir = new File(new File(".").getAbsolutePath());
    private static TreeMap<String, BufferedImage> graphicalResources = new TreeMap<>();
    private static TreeMap<String, File> fileResources = new TreeMap<>();
    private static TreeMap<String, Resource> resources = new TreeMap<>();

    /*private static ResourceLoader instance = null;
    public static ResourceLoader getInstance() {
        if (instance == null) { instance = new ResourceLoader(); }
            return instance;
    }*/

    /** method to get the current working directory */
    public static Path getWorkingDir() { return Paths.get(ResourceLoader.workingDir.getAbsolutePath()); }
    /** method to get the structure containing all the graphical resources */
    public static TreeMap<String, BufferedImage> getGraphicalResources() { return ResourceLoader.graphicalResources; }
    /** method to get the structure containing all the non-graphical resources */
    public static TreeMap<String, File> getFileResources() { return ResourceLoader.fileResources; }
    /** method to get the structure containing all the resources */
    public static TreeMap<String, Resource> getResources() { return ResourceLoader.resources; }
}
