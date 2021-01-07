package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class OInputFilesBaseTasklet implements Tasklet {

    @Autowired
    protected SqlSession sqlSession;

    @Autowired
    protected PlatformTransactionManager postgresTransactionManager;

    /**
     * 文件读取路径
     * 
     * @return
     */
    public abstract String getWorkFilePath();

    /**
     * 障害文件夹
     * 
     * @return
     */
    public abstract String getEvacuationFolderPath();

    /**
     * 模糊匹配文件名
     * 
     * @return
     */
    public abstract String getCsvFileNameBefore();

    /**
     * 文件后缀
     * 
     * @return
     */
    public abstract String getCsvFileNameAfter();

    /**
     * 文件内容读取验证并写入数据库
     * 
     * @param filePathName
     * @param charset
     * @param chunkContext
     */
    public abstract int getCsvFileList(String filePathName, Charset charset, ChunkContext chunkContext)
            throws IOException, CsvValidationException;

    /**
     * @param contribution
     * @param chunkContext
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        String workFilePath = getWorkFilePath();

        String evacuationFolderPath = getEvacuationFolderPath();

        File filePath = new File(workFilePath);

        List<File> filesList = new ArrayList<>();

        String csvFileNameBefore = getCsvFileNameBefore();

        String csvFileNameAfter = getCsvFileNameAfter();

        try {

            if (!new File(evacuationFolderPath).isDirectory()) {
                Boolean fileFlg = new File(evacuationFolderPath).mkdir();

                if (!fileFlg) {
                    throw new IOException("障害文件夹创建失败");
                }
            }

            // 文件夹下文件匹配，不符合规则文件丢入障害文件夹
            File[] resource = filePath.listFiles();
            if (resource != null) {
                for (File file : resource) {
                    if (!file.isDirectory()) {
                        if (file.getName().startsWith(csvFileNameBefore) && file.getName().endsWith(csvFileNameAfter)) {
                            filesList.add(file);
                        } else {

                            Path errorFilePath = Paths.get(file.getPath());
                            Path evacuationFile = Paths.get(evacuationFolderPath, file.getName());
                            try {
                                Files.move(errorFilePath, evacuationFile, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                            }
                            chunkContext.getStepContext().getStepExecution()
                                    .setExitStatus(OExitCodeMapper.EXITSTATUS_WARNING_1);
                        }
                    }
                }
            }

            Charset charset = Charset.forName("UTF-8");

            // 符合规则文件列表遍历
            for (int j = 0; j < filesList.size(); j++) {

                String filePathName = filesList.get(j).getPath();
                int errorflag = getCsvFileList(filePathName, charset, chunkContext);

                // 文件内容异常时文件丢入障害文件夹，并警告结束
                if (errorflag != 0) {

                    Path evacuationFile = Paths.get(evacuationFolderPath, filesList.get(j).getName());
                    Path errorFilePath = Paths.get(filePathName);
                    try {
                        Files.move(errorFilePath, evacuationFile, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                    }
                    if (chunkContext.getStepContext().getStepExecution()
                            .getExitStatus() != OExitCodeMapper.EXITSTATUS_FAILURE_9) {
                        chunkContext.getStepContext().getStepExecution()
                                .setExitStatus(OExitCodeMapper.EXITSTATUS_WARNING_1);
                    }

                } else {
                    // 正常结束的情况文件删除
                    Path evacuationFile = Paths.get(filePathName);
                    try {
                        Files.delete(evacuationFile);
                    } catch (Exception e) {
                        chunkContext.getStepContext().getStepExecution()
                                .setExitStatus(OExitCodeMapper.EXITSTATUS_WARNING_1);
                    }
                }
            }

        } catch (IOException | CsvValidationException e) {

            chunkContext.getStepContext().getStepExecution().setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
        }

        return RepeatStatus.FINISHED;
    }
}
