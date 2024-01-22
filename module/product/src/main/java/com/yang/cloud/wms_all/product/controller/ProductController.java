package com.yang.cloud.wms_all.product.controller;

import com.yang.cloud.wms_all.common.vo.product.ProductEsSelectVo;
import com.yang.cloud.wms_all.common.vo.product.ProductSelectVo;
import com.yang.cloud.wms_all.common.vo.product.ProductVo;
import com.yang.cloud.wms_all.core.annontation.WmsController;
import com.yang.cloud.wms_all.core.result.Result;
import com.yang.cloud.wms_all.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@WmsController("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public Result create(@RequestBody @Validated ProductVo productVo) {
        productService.create(productVo);
        return Result.success();
    }

    @GetMapping("/selectBySQL")
    public Result selectBySQL(ProductSelectVo productSelectVo) {
        return Result.success(productService.selectBySQL(productSelectVo));
    }

    @GetMapping("/selectByES")
    public Result selectByES(ProductEsSelectVo productEsSelectVo) {
        return Result.success(productService.selectByES(productEsSelectVo));
    }

    @PutMapping("/update/{id}")
    public Result updateProduct(@PathVariable("id") Long id, @RequestBody ProductVo productVo) {
        productService.updateProduct(id, productVo);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return Result.success();
    }
}
