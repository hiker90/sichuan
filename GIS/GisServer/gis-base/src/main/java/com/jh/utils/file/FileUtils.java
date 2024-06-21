package com.jh.utils.file;

import com.jh.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileUtils
{
    /**
     * 文件上传
     *
     * @param file 上传的文件
     * @param fileName 文件名uuid
     * @return 文件处理结果
     */
    public static Boolean fileUpload(MultipartFile file, String fileName)
    {
        String baseDir = SystemConfig.getFilepath();
        try
        {
            File desc = getAbsoluteFile(baseDir, fileName);
            file.transferTo(desc);
        } catch (IOException | IllegalStateException e) {
            log.error("保存文件失败：" + fileName + ":" + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名uuid
     */
    public static void deleteFile(String fileName)
    {
        String baseDir = SystemConfig.getFilepath();

        try
        {
            File desc = getAbsoluteFile(baseDir, fileName);
            if(!desc.delete()) {
                log.error("删除文件失败：" + fileName);
            }
        } catch (IOException | IllegalStateException e) {
            log.error("删除文件失败：" + fileName + ":" + e.getMessage());
        }
    }


    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    private static File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists())
        {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }
}
