package resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by hayesj3 on 4/25/2015.
 */
public final class Resource {

	private String name;
	private Path thePath = null;
	private File theFile = null;
	private boolean isImage = false;
	private BufferedImage theImage = null;
	/**
	 * @param path the path to the folder with the resource
	 * @param fileName the name of the file to be used
	 */
	public Resource(Path path, String fileName) {
		this.thePath = Paths.get(path.toString(), fileName);
		this.theFile = new File(this.thePath.toString());
		this.name = this.theFile.getName();
		this.generateImage();
		if (this.isImage) {
			ResourceLoader.getGraphicalResources().put(this.name, this.theImage);
		} else {
			ResourceLoader.getFileResources().put(this.name, this.theFile);
		}
		ResourceLoader.getResources().put(this.name, this);
		System.out.println(this.name + " loaded at " + this.thePath);
	}

	public String getName() { return this.name; }
	public Path getPath() { return this.thePath; }
	public File getFile() { return this.theFile; }
	public BufferedImage getImage() { return this.theImage; }

	private void generateImage() {
		try {
			this.theImage = ImageIO.read(this.theFile);
			this.isImage = true;
		} catch (IOException ioe) {
			this.isImage = false;
			System.out.println(ioe.getMessage());
		}
	}
}
