package main.java.com.jhayes.resource;

import sun.awt.image.FileImageSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.net.URL;
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
	private Image theImage = null;
	/**
	 * @param path the path to, and including, the main.java.com.jhayes.resource file
	 */
	public Resource(String path) {
		this.thePath = ResourceLoader.getWorkingDir().resolve(Paths.get(path));
		this.theFile = this.thePath.toFile();
		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource(path);
		//String p = url.getFile();
		//this.theFile = new File(p);
		//this.thePath = this.theFile.toPath();
		this.name = this.theFile.getName();
		this.generateImage();
		System.out.println(isImage);
		if (this.isImage) {
			ResourceLoader.getGraphicalResources().put(this.name, this.theImage);
		} else {
			ResourceLoader.getFileResources().put(this.name, this.theFile);
		}
		ResourceLoader.getResources().put(this.name, this);
	}

	public String getName() { return this.name; }
	public Path getPath() { return this.thePath; }
	public File getFile() { return this.theFile; }
	public Image getImage() { return this.theImage; }

	private void generateImage() {
		try {
			FileImageSource image = new FileImageSource(this.getFile().toString());
			System.out.println(this.theFile);
			this.theImage = ImageIO.read(this.theFile);
			this.isImage = true;
			System.out.println(this.name + " loaded at " + this.thePath);
		} catch (Exception ioe) {
			this.isImage = false;
			System.out.println(ioe.getMessage());
		}
	}
}
