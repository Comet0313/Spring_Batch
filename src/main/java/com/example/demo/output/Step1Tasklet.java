package com.example.demo.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import com.example.demo.OExitCodeMapper;
import com.example.demo.OOutputFilesBaseTasklet;
import com.example.demo.db.UserCustom;
import com.example.demo.db.UserCustomMapper;
import com.example.demo.utils.DateUtility;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("Step1Tasklet")
@PropertySource({ "classpath:folderPath.properties" })
public class Step1Tasklet extends OOutputFilesBaseTasklet<UserCustom> {

    private static final String ALL_DATA_FLG = "1";

    /** 文件做成路径 */
    @Value("${Folder.Path}")
    private String folderPath;

    /**
     * 从DB取得文件内容
     */
    @Override
    public List<UserCustom> getCSVDataList(Timestamp DBQueryDate, String allDataFlg,
            Timestamp DBSystemDate) {

        List<UserCustom> list;

        UserCustomMapper mapper = sqlSession.getMapper(UserCustomMapper.class);

        if (ALL_DATA_FLG.equals(allDataFlg) || null == DBQueryDate) {
            list = mapper.selectAuth();
        } else {
            list = mapper.selectAuthCondition(DBQueryDate, DBSystemDate);
        }
        return list;
    }

    /**
     * 做成文件
     */
    public void createFile(Charset charset, List<UserCustom> csvDataList, ChunkContext chunkContext)
            throws IOException {
        Writer writer = null;

        // 文件名
        String CSVFileName = "TEST_" + DateUtility.getSystemDateToFileName() + ".csv";

        try {
                //做成路径判断
            if (!new File(folderPath).isDirectory()) {
                Boolean fileFlg = new File(folderPath).mkdir();

                if (!fileFlg) {
                    throw new IOException("文件夹做成失败");
                }
            }

            // 合成完整的做成路径
            Path CSVPath = Paths.get(folderPath, CSVFileName);

            writer = new FileWriter(CSVPath.toString(), charset);

            StatefulBeanToCsv<UserCustom> beanToCsv = new StatefulBeanToCsvBuilder<UserCustom>(
                    writer).build();
            try {
                beanToCsv.write(csvDataList);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                chunkContext.getStepContext().getStepExecution().setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
            }
            writer.close();
        } catch (IOException e) {
            chunkContext.getStepContext().getStepExecution().setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
        }

    }
}
