package com.hoanghoa.userpurchased.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class QRUtil {

    private static final Integer QR_HEIGHT = 500;
    private static final Integer QR_WIDTH  = 500;

    /**
     * Generate QC code for user info
     * @param data data to write
     * @throws WriterException error occur while write file to image
     * @throws IOException error occur while write file to image
     */
    public static void generateQRCode(String data, File file) throws WriterException, IOException {
        Path path = file.toPath();
        String pathStr = path.toString();
        //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass
        // for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(StringUtil.CHARSET), StringUtil.CHARSET),
                BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
        MatrixToImageWriter.writeToPath(matrix,
                pathStr.substring(pathStr.lastIndexOf('.') + 1), file.toPath());
    }

    /**
     * Create file path
     * @param fileName file name
     * @return File
     */
    public static File createFilePath(String fileName) {
        String home = System.getProperty("user.home");

        return new File(home + File.separator + "Documents" + File.separator + "files"
                + File.separator + fileName + ".png");
    }
}
