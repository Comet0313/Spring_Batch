package com.example.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OOutputFilesBaseTasklet<T> implements Tasklet {

    @Autowired
    protected SqlSession sqlSession;

    /**
     * 文件内容集合取得
     */
    public abstract List<T> getCSVDataList(Timestamp DBQueryDate, String allDataFlg, Timestamp DBSystemDate);

    /**
     * 文件做成
     */
    public abstract void createFile(Charset charset, List<T> csvDataList, ChunkContext chunkContext) throws IOException;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // 启动参数取得
        String allDataFlg = null;
        try {
            allDataFlg = (String) chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters()
                    .getParameters().get("allDataFlg").getValue();
        } catch (Exception e) {
            allDataFlg = "2";
        }

        try {
            // 数据库取得batch前回执行时间，暂时系统时间替代
            Date DBQueryDate = Calendar.getInstance().getTime();
            // 数据库取得系统时间，暂时系统时间替代
            Date DBSystemDate = Calendar.getInstance().getTime();

            List<T> csvDataList = null;

            if (DBQueryDate != null) {
                csvDataList = getCSVDataList(new Timestamp(DBQueryDate.getTime()), allDataFlg,
                        new Timestamp(DBSystemDate.getTime()));
            } else {
                csvDataList = getCSVDataList(null, allDataFlg, new Timestamp(DBSystemDate.getTime()));
            }

            if (!csvDataList.isEmpty()) {
                Charset charset = Charset.forName("UTF-8");
                createFile(charset, csvDataList, chunkContext);
            }

        } catch (Exception e) {
            chunkContext.getStepContext().getStepExecution().setExitStatus(OExitCodeMapper.EXITSTATUS_FAILURE_9);
        }

        return RepeatStatus.FINISHED;
    }

}
