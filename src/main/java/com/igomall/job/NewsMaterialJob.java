package com.igomall.job;

import com.igomall.common.Pageable;
import com.igomall.entity.wechat.material.NewsMaterial;
import com.igomall.entity.wechat.material.NewsMaterialResponse;
import com.igomall.service.wechat.material.NewsMaterialService;
import com.igomall.util.wechat.MaterialUtils;
import com.igomall.util.wechat.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsMaterialJob {

    @Autowired
    private NewsMaterialService newsMaterialService;

    @Scheduled(fixedRate = 1000*3600*2)
    public void synchronous(){
        Pageable pageable = new Pageable();
        Integer currentCount = -1;
        Integer itemCount = 0;
        while(currentCount<itemCount){
            NewsMaterialResponse newsMaterialResponse = MaterialUtils.getMaterial("news",pageable);
            currentCount = pageable.getPageNumber()*pageable.getPageSize();
            itemCount = newsMaterialResponse.getItemCount();

            System.out.println(itemCount+":"+currentCount);
            List<NewsMaterialResponse.Material> materials = newsMaterialResponse.getMaterials();
            /*for (NewsMaterial newsMaterial:newsMaterials) {
                if(!newsMaterialService.mediaIdExists(newsMaterial.getMediaId())){
                    newsMaterialService.save(newsMaterial);
                }
            }*/
        }


    }
}
