package com.offcn.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadPicTools {
    // 构造方法私有化
    private UploadPicTools(){}
    // 提供静态方法或属性

    public static String upload(Part part, HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        /*if (part.getSize() == 0) {
            request.setAttribute("msg", "请先选择要上传的图片");
            request.getRequestDispatcher(url).forward(request, response);
        }*/
        // 获取要上传的文件名
        String fileName = part.getSubmittedFileName(); //  ujy.png
        fileName = UUID.randomUUID()+fileName;
        // 图片名赋值给user对象
        // 判断文件类型是否是图片类型
        if(!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif"))){
            request.setAttribute("msg", "不是支持图片类型的文件格式");
            request.getRequestDispatcher(url).forward(request, response);
        }else{
            // 是支持的图片类型, 实现文件上传操作
            // 指定一个存储上传图片文件位置,不存储项目中,因为只要重新部署项目图片就会消失.
            // 所以把图片存储到一个本地文件中
            File file = new File("D:/xmpic");
            if (!file.exists()){ // 文件夹不存在
                file.mkdirs(); // 创建文件夹
            }
            // 把文件存储本地文件夹
            part.write("D:/xmpic/"+fileName);
        }
        return fileName;
    }

}
