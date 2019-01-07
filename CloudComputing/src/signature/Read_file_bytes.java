package signature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Read_file_bytes {
    public static int len = 0;
    public byte[][] readFileBytes(String fileName)
    {
        String src = fileName;
        try {
            FileInputStream in = new FileInputStream(src);
            byte buffer[][] = new byte[1024][200];


            int count,i = 0;
            while((count=in.read(buffer[i]))!=-1)
            {
                // for循环保证只写入count个byte, 否则会写入1024个byte
                len ++;
               i++;
            }
            in.close();
        return buffer;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public void writeFileBytes(byte[][] buffer, String destPath)
    {
        try {
            FileOutputStream out = new FileOutputStream(destPath);
            int  i, j;
            for(i=0; i<Read_file_bytes.len; i++)
            {
                for(j=0; j<buffer[i].length; j++)
                out.write(buffer[i][j]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
