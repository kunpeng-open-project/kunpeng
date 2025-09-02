package com.kp.framework.task;

import com.kp.framework.constant.MinioConstant;
import com.kp.framework.utils.kptool.KPMinioUtil;
import com.kp.framework.utils.kptool.KPRedisUtil;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;

@Component
@Slf4j
public class MinioTask {

    /**
     * @Author lipeng
     * @Description 清空临时上传目录 防止垃圾文件堆积
     * @Date 2022/5/19 15:28
     * @param
     * @return void
     **/
    @Scheduled(cron = "0 0 1 * * ?")
    private void process() {
        if (!KPRedisUtil.lock("process", 300))
            return;

        try {
            Iterable<Result<Item>> myObjects = KPMinioUtil.listObjects(MinioConstant.TEMPORARY_BUCKET_NAME, true);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // 有对象文件，则删除失败
                if (item.size() > 0) {
                    KPMinioUtil.removeObject(MinioConstant.TEMPORARY_BUCKET_NAME, URLDecoder.decode(item.objectName()));
                }
            }
//            DBMinioUtil.removeBucket(MinioConstant.TEMPORARY_BUCKET_NAME);
            log.info("清空桶{}成功！", MinioConstant.TEMPORARY_BUCKET_NAME);
        } catch (NullPointerException e) {}
        catch (Exception ex){
            log.info("清空桶{}失败！ 原因{}", MinioConstant.TEMPORARY_BUCKET_NAME, ex.getMessage());
        }
    }
}
