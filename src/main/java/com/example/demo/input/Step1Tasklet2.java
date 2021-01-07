package com.example.demo.input;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

import com.example.demo.OExitCodeMapper;
import com.example.demo.OInputFilesBaseTasklet;
import com.example.demo.db.LoginUserCustom;
import com.example.demo.db.LoginUserCustomMapper;
import com.example.demo.utils.CommonCheck;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("Step1Tasklet")
@PropertySource({ "classpath:folderPath.properties" })
public class Step1Tasklet2 extends OInputFilesBaseTasklet {

    /** 文件列数 */
    private final int CSV_COLUMN_LENGTH = 12;

    /** 文件读取路径 */
    @Value("${Folder.Path}")
    private String folderPath;

    /** 障害文件存放路径 */
    @Value("${Evacuation.Folder.Path}")
    private String evacuationFolderPath;

    @Override
    public String getWorkFilePath() {
        return folderPath;
    }

    @Override
    public String getEvacuationFolderPath() {
        return evacuationFolderPath;
    }

    @Override
    public String getCsvFileNameBefore() {
        return "GROUP_USER_A_";
    }

    @Override
    public String getCsvFileNameAfter() {
        return ".csv";
    }

    /**
     * 文件内容读取并放入数据库
     * 
     * @param filePathName 文件名
     * @param charset      文件编码格式
     * @param chunkContext
     */
    @Override
    public int getCsvFileList(String filePathName, Charset charset, ChunkContext chunkContext)
            throws IOException, CsvValidationException {
        FileReader fileReader = null;
        CSVReader csvReader = null;
        int errorflag = 0;

        // 文件内容各种check
        try {
            fileReader = new FileReader(filePathName, charset);
            csvReader = new CSVReader(fileReader);

            String[] record = null;
            while ((record = csvReader.readNext()) != null) {
                if (CSV_COLUMN_LENGTH != record.length) {
                    errorflag = 1;
                    continue;
                }

                // 公共方法检查内容
                if (CommonCheck.checkSpaceNull(record[0])) {
                    errorflag = 1;
                    continue;
                }

                // 开启事务
                DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
                definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = postgresTransactionManager.getTransaction(definition);

                try {

                    setLoginUserAuthToInsert(record);

                    // 提交
                    postgresTransactionManager.commit(status);
                } catch (Exception e) {
                    postgresTransactionManager.rollback(status);
                    errorflag = 1;
                    chunkContext.getStepContext().getStepExecution()
                            .setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
                }

            }
        } catch (Exception e) {
            chunkContext.getStepContext().getStepExecution().setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
            errorflag = 1;
            return errorflag;
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (csvReader != null) {
                csvReader.close();
            }
        }

        return errorflag;
    }

    /**
     * 数据插入
     */
    private void setLoginUserAuthToInsert(String[] record) {

        LoginUserCustomMapper loginUserAuthMapper = sqlSession.getMapper(LoginUserCustomMapper.class);
        LoginUserCustom loginUserAuth = new LoginUserCustom();
        loginUserAuth.setPref_cd(record[0]);
        loginUserAuth.setUser_id(record[1]);
        loginUserAuth.setPassword(record[2]);


        loginUserAuthMapper.insert(loginUserAuth);
    }

}
