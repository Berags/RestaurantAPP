package edu.unifi.model.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfImageObject;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Iterator;

// Used to compress files to save space
public class Compressor {
    // Change this value (0.f to 1.f) to change compression factor
    // Higher means more space but better quality
    public static final float FACTOR = 0.7f;
    private static final String PDF_NAME = "./pdf.pdf";
    private static final String PDF_NAME_DEST = "./pdf_dest.pdf";
    private static final String IMAGE_NAME = "./image.jpg";
    private static final String IMAGE_NAME_DEST = "./image_dest.jpg";

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param base64Pdf Base64 encoded PDF
     * @return Base64 encoded pdf
     */
    public static byte[] manipulatePdf(String base64Pdf) throws IOException, DocumentException {
        convertFromBase64(base64Pdf);
        PdfName key = new PdfName("ITXT_SpecialId");
        PdfName value = new PdfName("123456789");
        // Read the file
        PdfReader reader = new PdfReader(PDF_NAME);
        int n = reader.getXrefSize();
        PdfObject object;
        PRStream stream;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {
            object = reader.getPdfObject(i);
            if (object == null || !object.isStream())
                continue;
            stream = (PRStream) object;
            PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
            System.out.println(stream.type());
            if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                PdfImageObject image;
                try {
                    image = new PdfImageObject(stream);
                } catch (com.itextpdf.text.exceptions.UnsupportedPdfException ex) {
                    continue;
                }
                BufferedImage bi = image.getBufferedImage();
                if (bi == null) continue;
                int width = (int) (bi.getWidth() * FACTOR);
                int height = (int) (bi.getHeight() * FACTOR);
                if (width == 0) {
                    System.out.println("Error while decoding pdf image... continuing...");
                    continue;
                }
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
                Graphics2D g = img.createGraphics();
                g.drawRenderedImage(bi, at);
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(img, "JPG", imgBytes);
                stream.clear();
                stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
                stream.put(PdfName.TYPE, PdfName.XOBJECT);
                stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
                stream.put(key, value);
                stream.put(PdfName.FILTER, PdfName.DCTDECODE);
                stream.put(PdfName.WIDTH, new PdfNumber(width));
                stream.put(PdfName.HEIGHT, new PdfNumber(height));
                stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
            }
        }
        // Save altered PDF
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(PDF_NAME_DEST));
        stamper.close();
        reader.close();

        return encodeToBase64(PDF_NAME, PDF_NAME_DEST);
    }

    public static byte[] manipulateImage(String base64Image) throws IOException {
        convertToJPG(base64Image);

        File input = new File(IMAGE_NAME);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(IMAGE_NAME_DEST);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(1f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
        return encodeToBase64(IMAGE_NAME, IMAGE_NAME_DEST);
    }

    private static void convertFromBase64(String base64Pdf) {
        File file = new File(Compressor.PDF_NAME);
        base64Pdf = base64Pdf.split(",")[1];

        try (FileOutputStream fos = new FileOutputStream(file);) {
            byte[] decoded = Base64.getDecoder().decode(base64Pdf.getBytes(StandardCharsets.UTF_8));

            fos.write(decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encodeToBase64(String fileName, String destName) {
        try {
            File file = new File(destName);

            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            File dest_file = new File(destName);
            File file = new File(fileName);
            boolean dest_deleted = dest_file.delete();
            boolean deleted = file.delete();
        }
    }

    private static void convertToJPG(String base64Image) {
        try {
            try {
                base64Image = base64Image.split(",")[1];
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            final BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            final FileOutputStream fileOutputStream = new FileOutputStream(IMAGE_NAME);
            final boolean canWrite = ImageIO.write(convertedImage, "jpg", fileOutputStream);
            fileOutputStream.close(); // ImageIO.write does not close the output stream

            if (!canWrite) {
                throw new IllegalStateException("Failed to write image.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}