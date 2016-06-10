package Utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wangxiao on 16/6/6.
 */
public class FileUtils {
    private String SDPATH;

    public String getSDPATH(){
        return SDPATH;
    }
    public FileUtils(){
        //得到当前外部存储设备的目录
        // /SDCARD
        SDPATH = Environment.getExternalStorageDirectory()+"/";
    }

    /*
     * 在SD卡上创建文件
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH+fileName);
        file.createNewFile();
        return file;
    }

	/*
	 * 在SD卡上创建目录
	 */

    public File createSDDir(String dirName){
        File dir = new File(SDPATH+dirName);
        dir.mkdir();
        return dir;
    }

    /*
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName){
        File file = new File(SDPATH+fileName);
        return file.exists();
    }

	/*
	 * 将一个InputStream里面的数据写入到SD卡中
	 */

    public File write2SDFromInput(String path,String fileName,InputStream input){
        File file = null;
        OutputStream output = null;
        try {
            //创建目录
            createSDDir(path);
            //创建文件
            file = createSDFile(path+fileName);
            //创建输出流
            output = new FileOutputStream(file);
            //创建缓冲区
            byte buffer[] =  new byte[4*1024];
            //写入数据
            while((input.read(buffer))!=-1){
                output.write(buffer);
            }
            //清空缓存
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                //关闭输出流
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
