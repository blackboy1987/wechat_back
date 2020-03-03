package com.igomall.controller.wechat;

import com.igomall.common.Pageable;
import com.igomall.entity.wechat.material.NewsMaterial;
import com.igomall.entity.wechat.material.NewsMaterialResponse;
import com.igomall.service.wechat.material.NewsMaterialService;
import com.igomall.util.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("wechatMaterialController")
@RequestMapping("/wx/material")
public class MaterialController {

    @Autowired
    private NewsMaterialService newsMaterialService;

    @GetMapping("/news")
    public String index(Pageable pageable){
        NewsMaterialResponse newsMaterialResponse = WechatUtils.getMaterial("news",pageable);
        List<NewsMaterial> newsMaterials = newsMaterialResponse.getItem();
        for (NewsMaterial newsMaterial:newsMaterials) {
            if(!newsMaterialService.mediaIdExists(newsMaterial.getMediaId())){
                newsMaterialService.save(newsMaterial);
            }
        }

        return "ok";
    }
}
