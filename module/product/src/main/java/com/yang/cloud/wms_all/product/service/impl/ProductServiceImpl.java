package com.yang.cloud.wms_all.product.service.impl;

import com.yang.cloud.wms_all.common.vo.product.ProductVo;
import com.yang.cloud.wms_all.common.vo.product.ProductEsSelectVo;
import com.yang.cloud.wms_all.common.vo.product.ProductSelectVo;
import com.yang.cloud.wms_all.core.CoreConstant;
import com.yang.cloud.wms_all.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms_all.core.util.PagedList;
import com.yang.cloud.wms_all.product.entity.Product;
import com.yang.cloud.wms_all.product.mapper.ProductMapper;
import com.yang.cloud.wms_all.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void create(ProductVo productVo) {
        Product product = new Product();
        BeanUtils.copyProperties(productVo, product);
        product.setCreatedId(UserContextHolder.getUserId())
                .setCreatedName(UserContextHolder.getUsername())
                .setUpdatedId(UserContextHolder.getUserId())
                .setUpdatedName(UserContextHolder.getUsername());
        productMapper.insert(product);
        Product productDetail = productMapper.selectById(product.getId());
        elasticsearchRestTemplate.save(productDetail);
    }

    @Override
    public PagedList<Product> selectBySQL(ProductSelectVo productSelectVo) {
        List<Product> products = productMapper.selectByCondition(
                productSelectVo,
                productSelectVo.getPagination().getOffset(),
                productSelectVo.getPagination().getPageSize());
        Long count = productMapper.selectCountByCondition(productSelectVo);
        return PagedList.<Product>builder()
                .result(products)
                .count(count)
                .pageNum(productSelectVo.getPagination().getPageNum())
                .pageSize(productSelectVo.getPagination().getPageSize())
                .build();
    }

    @Override
    public PagedList<Product> selectByES(ProductEsSelectVo productEsSelectVo) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("IS_DELETE", false));
        if (StringUtils.isNotBlank(productEsSelectVo.getContent())) {
            boolQuery.must(QueryBuilders.multiMatchQuery(productEsSelectVo.getContent())
                    .field("CODE", 3.0f)
                    .field("SKU", 3.0f)
                    .field("NAME", 2.0f)
                    .field("PROPERTIES", 1.0f)
                    .tieBreaker(0.3f));
        }
        Sort sort = null;
        if (productEsSelectVo.isOrderByCreatedTimeASC()) {
            sort = Sort.by(Sort.Order.asc("CREATED_TIME"));
        } else {
            sort = Sort.by(Sort.Order.desc("CREATED_TIME"));
        }
        PageRequest pageRequest = PageRequest.of(
                productEsSelectVo.getPagination().getPageNum() - 1,
                productEsSelectVo.getPagination().getPageSize(),
                sort);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageRequest);
        SearchHits<Product> search = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Product.class);
        List<Product> products = search.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
        Long count = search.getTotalHits();
        return PagedList.<Product>builder()
                .result(products)
                .count(count)
                .pageNum(productEsSelectVo.getPagination().getPageNum())
                .pageSize(productEsSelectVo.getPagination().getPageSize())
                .build();
    }

    @Override
    public void updateProduct(Long id, ProductVo productVo) {
        productMapper.updateProductById(id, productVo, UserContextHolder.getUserId(), UserContextHolder.getUsername(), ZonedDateTime.now(ZoneId.of(CoreConstant.SERVER_ZONE)));
        Product product = productMapper.selectById(id);
        elasticsearchRestTemplate.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = new Product();
        product.setId(id)
                .setUpdatedId(UserContextHolder.getUserId())
                .setUpdatedName(UserContextHolder.getUsername())
                .setUpdatedTime(ZonedDateTime.now(ZoneId.of(CoreConstant.SERVER_ZONE)));
        productMapper.deleteProductById(product);
        Product productDetail = productMapper.selectProductById(id);
        elasticsearchRestTemplate.save(productDetail);
    }
}
