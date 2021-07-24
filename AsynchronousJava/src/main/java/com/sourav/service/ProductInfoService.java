package com.sourav.service;

import com.sourav.domain.ProductInfo;
import com.sourav.domain.ProductOption;

import java.util.List;

import static com.sourav.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
