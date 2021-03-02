import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class CompressJPEGFile {
	public static void main(String[] args) throws IOException {
		// First Compressed image
		File compressedImage1 = new File("compressRatio_0.5.jpeg");

		ImageEncoder(compressedImage1, 0.5f);

		// Second compressed image
		File compressedImage2 = new File("compressRatio_0.1.jpeg");

		ImageEncoder(compressedImage2, 0.1f);

		// Third compressed image
		File compressedImage3 = new File("compressRatio_0.05.jpeg");

		ImageEncoder(compressedImage3, 0.05f);

		// Fourth compressed image
		File compressedImage4 = new File("compressRatio_0.01.jpeg");

		ImageEncoder(compressedImage4, 0.01f);

		// Fifth compressed image
		File compressedImage5 = new File("compressRatio_0.001.jpeg");
		ImageEncoder(compressedImage5, 0.001f);
	}

	public static void ImageEncoder(File compressedImageFile, float quality) throws IOException {
		File imageFile = new File("original.jpeg");
		InputStream inputStream = new FileInputStream(imageFile);
		OutputStream outputStream = new FileOutputStream(compressedImageFile);

		// Create a BufferedImage as the result of decoding the supplied Input
		BufferedImage image = ImageIO.read(inputStream);

		// get all image writers for JPEG format
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");

		if (!writers.hasNext())
			throw new IllegalStateException("No writers found");

		ImageWriter writer = (ImageWriter) writers.next();
		ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);

		writer.setOutput(imageOutputStream);

		ImageWriteParam imageWriteParam = writer.getDefaultWriteParam();

		// Compress to a given quality
		imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imageWriteParam.setCompressionQuality(quality);

		// Appends a complete image stream containing a single image and
		// associated stream and image metadata and thumbnails to the output
		writer.write(null, new IIOImage(image, null, null), imageWriteParam);

		// Close all streams
		inputStream.close();
		outputStream.close();
		imageOutputStream.close();
		writer.dispose();
	}
}