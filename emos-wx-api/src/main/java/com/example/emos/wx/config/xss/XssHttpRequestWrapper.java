package com.example.emos.wx.config.xss;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpRequestWrapper extends HttpServletRequestWrapper {


    public XssHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        value = filterValue(value);
        return  value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values!=null){
                return (String[]) Arrays.stream(values).map(this::filterValue).toArray();
        }else {
            return values;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        Map<String, String[]> map = new LinkedHashMap<>();
        if (parameterMap!=null){
            parameterMap.forEach((key,values)->{
                values =(String[]) Arrays.stream(values).map(this::filterValue).toArray();
                map.put(key,values);
            });
        }
        return map;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in = super.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader buffer = new BufferedReader(reader);
        StringBuffer body = new StringBuffer();
        String line = buffer.readLine();
        while (line!=null){
            body.append(line);
            line = buffer.readLine();
        }
        Map<String,Object> map= JSONUtil.parseObj(body.toString());
        LinkedHashMap<String, Object> resultMap = new LinkedHashMap<>();
        map.forEach((key,value)->{
            if (value instanceof String){
                resultMap.put(key,filterValue(value.toString()));
            }else {
                resultMap.put(key,value);
            }
        });
        String json = JSONUtil.toJsonStr(resultMap);
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(json.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    private String filterValue(String value){
        if (!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }
}
