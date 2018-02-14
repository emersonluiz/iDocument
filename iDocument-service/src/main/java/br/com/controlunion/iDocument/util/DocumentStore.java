package br.com.controlunion.iDocument.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Named;

import br.com.controlunion.iDocument.exception.BadRequestException;

@Named
public class DocumentStore {

    private static final String server_address = (System.getProperty("os.name").equals("Linux")) ? ("/tmp/Uploads/iDocument"):("D:/Web/Controlunion.com.br/Www/Docs_Sistemas/iDocument");

    public InputStream download(String root, String file) throws Exception {
        try {
            InputStream inputStream = new FileInputStream(server_address + "/" + root + "/" + file);
            return inputStream;
        } catch (Exception e) {
            throw e;
        }
    }

    public void upload(String root, InputStream inputStream, String fileName) throws Exception {
        try {
            if (inputStream == null) {
                //logger.error("Upload Unknown");
                throw new BadRequestException("Upload Unknown");
            }

            File dir = new File(server_address+"/"+root);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(server_address+"/"+root+"/"+fileName);

            OutputStream out = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            inputStream.close();
            out.flush();
            out.close();

            //logger.debug("Upload was Successfully");
        } catch (Exception e) {
            //logger.error(e.getMessage());
            throw e;
        }
    }

    public String getServerAddress() {
        return server_address;
    }

    public void delete(String folder, String file) {
        try {
            File fileFound = new File(server_address + folder + "/" + file);
            fileFound.delete();
        } catch (Exception e) {
            throw e;
        }
    }

}
