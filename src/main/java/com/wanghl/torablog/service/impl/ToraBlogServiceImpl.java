package com.wanghl.torablog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.config.ElasticSearchConfig;
import com.wanghl.torablog.constant.EsConstant;
import com.wanghl.torablog.entity.*;
import com.wanghl.torablog.mapper.ToraBlogMapper;
import com.wanghl.torablog.service.ToraBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Service
public class ToraBlogServiceImpl extends ServiceImpl<ToraBlogMapper, ToraBlog> implements ToraBlogService {

    @Autowired
    private RestHighLevelClient esClient;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public IPage<ToraBlog> getBlogPage(Page<ToraBlog> page) {
        IPage<ToraBlog> blogList = baseMapper.selectBlogPage(page);
        return blogList;
    }

    @Override
    public List<ToraBlog> getTop4Recommend() {
        String top4Recommend = redisTemplate.opsForValue().get("Top4Recommend");
        List<ToraBlog> toraBlogs = JSON.parseObject(top4Recommend, new TypeReference<List<ToraBlog>>() {});
        if (toraBlogs==null){
            List<ToraBlog> blogList = baseMapper.selectTop4Recommend();
            redisTemplate.opsForValue().set("Top4Recommend",JSON.toJSONString(blogList),1, TimeUnit.HOURS);
            return blogList;
        } else {
            return toraBlogs;
        }
    }

    @Override
    public List<ToraBlog> getNew4Blog() {
        String new4Blog = redisTemplate.opsForValue().get("New4Blog");
        List<ToraBlog> toraBlogs = JSON.parseObject(new4Blog, new TypeReference<List<ToraBlog>>() {});
        if (toraBlogs==null){
            List<ToraBlog> blogList = baseMapper.selectNew4Blog();
            redisTemplate.opsForValue().set("New4Blog",JSON.toJSONString(blogList),1, TimeUnit.HOURS);
            return blogList;
        } else {
            return toraBlogs;
        }
    }

    @Override
    public ToraBlog getBlogById(int id) {
        return baseMapper.selectBlogById(id);
    }

    @Override
    public List<ToraBlog> getBlogPageByClassifyId(Page<ToraBlog> page, String classifyId) {
        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        return baseMapper.selectBlogPageByClassifyId(classifyId,current,size);
    }

    @Override
    public SearchResult search(SearchParam searchParam) throws IOException {
        // dsl
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(searchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchParam.getKeyword(), "title", "username", "content");
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        if (!StringUtils.isEmpty(searchParam.getClassify())){
            boolQueryBuilder.filter(QueryBuilders.termQuery("classify",searchParam.getClassify()));
        }

        if (searchParam.getTagList() != null){
            for (String tag : searchParam.getTagList()) {
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(
                        "tagList",
                        QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("tagList.name", tag)),
                        ScoreMode.None);
                boolQueryBuilder.filter(nestedQuery);
            }
        }


        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from((searchParam.getCurrentPage()-1)*EsConstant.BLOG_SEARCH_PAGE_SIZE);
        searchSourceBuilder.size(EsConstant.BLOG_SEARCH_PAGE_SIZE);
        System.out.println(searchSourceBuilder);
        SearchRequest searchRequest = new SearchRequest(EsConstant.BLOG_SEARCH_INDEX);
        searchRequest.source(searchSourceBuilder);

        System.out.println(searchParam);

        SearchResponse searchResponse = esClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        SearchHits hits = searchResponse.getHits();

        SearchResult searchResult = new SearchResult();
        if (!ArrayUtils.isEmpty(hits.getHits())){
            searchResult.setBlogInfoList(new ArrayList<>());
            for (SearchHit hit : hits.getHits()) {
                String source = hit.getSourceAsString();
                BlogInfo blogInfo = JSON.parseObject(source, BlogInfo.class);
                searchResult.getBlogInfoList().add(blogInfo);
            }
        }


        long totalCount = hits.getTotalHits().value;
        long totalPage = totalCount % EsConstant.BLOG_SEARCH_PAGE_SIZE == 0 ? totalCount / EsConstant.BLOG_SEARCH_PAGE_SIZE : ((totalCount / EsConstant.BLOG_SEARCH_PAGE_SIZE) + 1);
        searchResult.setCurrentPage(searchParam.getCurrentPage());
        searchResult.setPageSize(EsConstant.BLOG_SEARCH_PAGE_SIZE);
        searchResult.setTotalCount((int) totalCount);
        searchResult.setTotalPage((int) totalPage);

        return searchResult;
    }
}
