package com.gszcn.aptrunner.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2019/12/16 10:52
 */
@Component
@Slf4j
public class SqlRunner implements ApplicationRunner {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SqlContext sqlContext;
    @Autowired
    private ResourceLoader resourceLcoader;
    @Autowired
    DataSource dataSource;


    @Override
    public void run(ApplicationArguments args) throws IOException {
        if (!sqlContext.getEnable()) {
            return;
        }
        //String filepath = "classpath:/META-INF/comment-sql/";
        for (String filepath : sqlContext.getData()) {
            Resource resource = resourceLcoader.getResource(filepath);
            if (!resource.exists()) {
                System.err.println("文件找不到！");
                return;
            }
            String[] filelist = resource.getFile().list();
            assert filelist != null;
            for (String s : filelist) {
                runScripts(resourceLcoader.getResource(filepath + s));
            }
        }
    }

    /**
     * 用流的方式读取文件
     *
     * @param resource
     * @return
     * @throws IOException
     */
    private String readSqlFile(Resource resource) throws IOException {
        InputStream in = resource.getInputStream();
        //<1>创建字节数组输出流，用来输出读取到的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //<2>创建缓存大小// 1KB
        byte[] buffer = new byte[1024];
        //每次读取到内容的长度
        int len = -1;
        //<3>开始读取输入流中的内容 //当等于-1说明没有数据可以读取了
        while ((len = in.read(buffer)) != -1) {
            //把读取到的内容写到输出流中
            baos.write(buffer, 0, len);
        }
        //<4> 把字节数组转换为字符串
        String content = baos.toString();
        //<5>关闭输入流和输出流
        in.close();
        baos.close();
        return content;
    }

    /**
     * sql执行方法
     *
     * @param resource
     */
    private void runScripts(Resource resource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(sqlContext.isContinueOnError());
        populator.setSeparator(";");
        populator.setSqlScriptEncoding("UTF-8");
        populator.addScript(resource);
        try {
            DatabasePopulatorUtils.execute(populator, dataSource);
        } catch (DataAccessException e) {
            //sql语句有异常捕获
            log.error(resource.toString() + "文件中，SQL语句有错误！");
        }
    }
}
