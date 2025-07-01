package com.kunpeng.framework.configruation.config;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/**
 * @Author lipeng
 * @Description
 * @Date 2023/11/20 14:31
 * @return
 **/
public class MyResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private HttpServletResponse response;
    private PrintWriter pwrite;

    public MyResponseWrapper(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        // 将数据写到 byte 中
        if (response.getContentType() == null ) return super.getOutputStream();

        if (response.getContentType().contains("application/json") || response.getContentType().contains("text/plain")){
            return new MyServletOutputStream(bytes);
        }else{
            return super.getOutputStream();
        }
    }

    /**
     * 重写父类的 getWriter() 方法，将响应数据缓存在 PrintWriter 中
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        try {
            pwrite = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pwrite;
    }

    /**
     * 获取缓存在 PrintWriter 中的响应数据
     *
     * @return
     */
    public byte[] getBytes() {
        if (null != pwrite) {
            pwrite.close();
            return bytes.toByteArray();
        }

        if (null != bytes) {
            try {
                bytes.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes.toByteArray();
    }

    class MyServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream ostream;

        public MyServletOutputStream(ByteArrayOutputStream ostream) {
            this.ostream = ostream;
        }

        @Override
        public void write(int b) throws IOException {
            ostream.write(b); // 将数据写到 stream　中
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}
