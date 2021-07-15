//package com.digitalgd.dpd.elasticsearch.config;
//
//import io.micrometer.core.instrument.util.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.ElasticsearchException;
//import org.elasticsearch.action.DocWriteRequest;
//import org.elasticsearch.action.DocWriteResponse;
//import org.elasticsearch.action.bulk.BulkItemResponse;
//import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetRequest;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.support.replication.ReplicationResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.*;
//import org.elasticsearch.common.Strings;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
//import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
//import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.util.*;
//@Slf4j
//public class ElasticSearchUtil {
//	private static RestHighLevelClient client = null;
//	//单例获取客户端
//	public static RestHighLevelClient getClient() {
////		if (client == null) {
////			synchronized (ElasticSearchUtil.class) {
////				List<HttpHost> hosts = new ArrayList<>();
////				try {
////					ClassPathResource r = new ClassPathResource("application.properties");
////					if (r.exists()) {
////						Properties p = new Properties();
////						p.load(r.getInputStream());
////						String ips = p.get("elasticsearch.ip").toString();
////						for (String ipport : ips.split(",")) {
////							String ip = ipport.split(":")[0];
////							String port = ipport.split(":")[1];
////							hosts.add(new HttpHost(ip, Integer.parseInt(port), "http"));
////						}
////					}else{
////						Properties p =yaml2Properties("application.yml");
////						String ips = p.get("elasticsearch.ip").toString();
////						for (String ipport : ips.split(",")) {
////							String ip = ipport.split(":")[0];
////							String port = ipport.split(":")[1];
////							hosts.add(new HttpHost(ip, Integer.parseInt(port), "http"));
////						}
////					}
////				} catch (Exception e) {
////					e.printStackTrace();
////				}
////
////				RequestConfigCallback requestConfigCallback = new RequestConfigCallback() {
////					@Override
////					public Builder customizeRequestConfig(Builder requestConfigBuilder) {
////						requestConfigBuilder.setConnectTimeout(2000);
////						requestConfigBuilder.setSocketTimeout(300000);
////						return requestConfigBuilder;
////					}
////				};
////				client = new RestHighLevelClient(RestClient.builder(hosts.toArray(new HttpHost[0]))
////						.setRequestConfigCallback(requestConfigCallback));
////			}
////		}
//		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("xtbgzww.digitalgd.com.cn",80,"http")).setPathPrefix("/es/search");;
//		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//		credentialsProvider.setCredentials(AuthScope.ANY,
//				new UsernamePasswordCredentials("elastic", "ap20pOPS20"));
//		restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
//			httpClientBuilder.disableAuthCaching().setDefaultCredentialsProvider(credentialsProvider);
//			return httpClientBuilder;
//		});
//
//		// 异步httpclient连接延时配置
////        setConnectTimeOutConfig(restClientBuilder);
//		// 异步httpclient连接数配置
////        setConnectConfig(restClientBuilder);
////        setAuth(restClientBuilder);
//		RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
//		return client;
//	}
//
//	public static void main(String[] args) throws Exception {
//		Map<String, Object> map = ElasticSearchUtil.getListData("operation_log",null);
//		System.out.println();
//	}
//	// yml转properties
//	public static Properties yaml2Properties(String yamlSource) {
//		try {
//			YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//			yaml.setResources(new ClassPathResource(yamlSource));
//			return yaml.getObject();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//
//
//	/**
//	 * 递归回调获取数据
//	 * @param data
//	 * @param datalist
//	 * @param fields
//	 * @param aggs
//	 * @param index
//	 */
//	private static  void getGroupData(Map<String, String> data,List<Map<String, String>> datalist,String fields[],List<Object> aggs,int index){
//			//普通统计
//			if (aggs.get(index) instanceof ParsedStringTerms ) {
//				for (Bucket bucket : ((ParsedStringTerms)aggs.get(index)).getBuckets()) {
//					if (data==null) {
//						data = new LinkedHashMap<String, String>();
//					}
//					if (index+1==aggs.size()) {//如果到最后一层则输出
//					    data.put(fields[index], bucket.getKeyAsString());
//					    data.put("total", bucket.getDocCount()+"");
//					    Map<String, String> tempdata = new LinkedHashMap<String, String>();
//					    tempdata.putAll(data);
//					    if (bucket.getDocCount()>0) {//数据为0的不要
//					    	 datalist.add(tempdata);
//						}
//					}else{
//					    data.put(fields[index], bucket.getKeyAsString());
//					    aggs.set(index+1, bucket.getAggregations().get(fields[index+1]));//切换上层对应的聚合结果
//					    getGroupData(data,datalist,fields,aggs,index+1);
//					}
//				}
//			}
//			//日期统计
//			if (aggs.get(index) instanceof ParsedDateHistogram ) {
//				for (org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket bucket : ((ParsedDateHistogram)aggs.get(index)).getBuckets()) {
//					if (index+1==aggs.size()) {//如果到最后一层则输出
//					    data.put(fields[index], bucket.getKeyAsString());
//					    data.put("total", bucket.getDocCount()+"");
//					    Map<String, String> tempdata = new LinkedHashMap<String, String>();
//					    tempdata.putAll(data);
//					    if (bucket.getDocCount()>0) {//数据为0的不要
//					    	 datalist.add(tempdata);
//						}
//					}else{
//						if (data==null) {
//							data = new LinkedHashMap<String, String>();
//						}
//					    data.put(fields[index], bucket.getKeyAsString());
//					    aggs.set(index+1, bucket.getAggregations().get(fields[index+1]));//切换上层对应的聚合结果
//					    getGroupData(data,datalist,fields,aggs,index+1);
//					}
//				}
//			}
//
//	}
//
//	/**
//	 * 分组统计方法
//	 * @param indexs 索引(支持多个索引联合查询) 相当于表名，多个相当于union
//	 * @param fields 统计字段 相当于 group by 字段名,注意如果存在日期字段统计，日期放最后一个
//	 * @param filterMap 过滤参数 相当于where 字段名=值
//	 */
//	public static List<Map<String, String>> queryForGroupBy(String index,String fields[],Map<String,String> filterMap){
//		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
//		 try {
//			// 1、创建search请求
//			SearchRequest searchRequest = new SearchRequest(index);
//			// 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//			sourceBuilder.size(0);
//			//多字段分组
//			List<Object> termsBuilders = new ArrayList<Object>();
//			for (int i = 0; i < fields.length; i++) {
//				if ("AcceptTime".equals(fields[i])) {//日期字段统计
//					DateHistogramAggregationBuilder termsBuilder = AggregationBuilders.dateHistogram("AcceptTime").field("AcceptTime").dateHistogramInterval(new DateHistogramInterval("1d")).format("yyyy-MM-dd").minDocCount(1);
//					termsBuilders.add(termsBuilder);
//					if (i!=0) {
//						((TermsAggregationBuilder)termsBuilders.get(i-1)).subAggregation(termsBuilder);
//					}
//				}else if ("ViolationTime".equals(fields[i])) {//日期字段统计
//					DateHistogramAggregationBuilder termsBuilder = AggregationBuilders.dateHistogram("ViolationTime").field("ViolationTime").dateHistogramInterval(new DateHistogramInterval("1d")).format("yyyy-MM-dd").minDocCount(1);
//					termsBuilders.add(termsBuilder);
//					if (i!=0) {
//						((TermsAggregationBuilder)termsBuilders.get(i-1)).subAggregation(termsBuilder);
//					}
//				}else{//普通字符串字段统计
//					TermsAggregationBuilder termsBuilder = AggregationBuilders.terms(fields[i]).field(fields[i]).size(10000);//不设size每个分组默认返回十条
//					termsBuilders.add(termsBuilder);
//					if (i!=0) {
//						((TermsAggregationBuilder)termsBuilders.get(i-1)).subAggregation(termsBuilder);
//					}
//				}
//			}
//			//加入聚合
//			sourceBuilder.aggregation((TermsAggregationBuilder)termsBuilders.get(0));
//			//过滤条件
//			if(filterMap!=null){
//				BoolQueryBuilder globalBuilder = QueryBuilders.boolQuery();
//				for (String key : filterMap.keySet()) {
//						QueryBuilder matchQueryBuilder = QueryBuilders.termsQuery(key, filterMap.get(key));
//						globalBuilder.must(matchQueryBuilder);
//				}
//				sourceBuilder.query(globalBuilder);
//			}
//			log.info("分组统计请求参数："+index+"   "+sourceBuilder);
//			searchRequest.source(sourceBuilder);
//			//3、发送请求
//			RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//			builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(1*1024*1024*1024));//5G
//			SearchResponse searchResponse = getClient().search(searchRequest,builder.build());
//			//4、处理响应
//			//搜索结果状态信息
//			if(RestStatus.OK.equals(searchResponse.status())) {
//			    // 获取聚合结果
//			    Aggregations aggregations = searchResponse.getAggregations();
//			    List<Object> aggs = new ArrayList<Object>();
//			    for (int i = 0; i < fields.length; i++) {
//			    	if (i==0) {
//			    		aggs.add(aggregations.get(fields[i]));
//					}else if((aggs.size()>i-1)&&(aggs.get(i-1) instanceof ParsedStringTerms)&&
//							((ParsedStringTerms)aggs.get(i-1)).getBuckets().size()>0) {//第一个字段没数据，后面都为空 //普通字符串字段统计
//						aggs.add(((ParsedStringTerms) aggs.get(i-1)).getBuckets().get(0).getAggregations().get(fields[i]));
//					}else if((aggs.size()>i-1)&&(aggs.get(i-1) instanceof ParsedDateHistogram)&&
//							((ParsedDateHistogram)aggs.get(i-1)).getBuckets().size()>0) {//第一个字段没数据，后面都为空 //日期字段统计
//						aggs.add(((ParsedDateHistogram) aggs.get(i-1)).getBuckets().get(0).getAggregations().get(fields[i]));
//					}
//				}
//			    if (aggs.size()==fields.length) {
//			    	getGroupData(null, list, fields, aggs, 0);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		log.info("结果数："+list.size());
//		return list;
//	}
//
//	/**
//	 * 日期统计
//	 * @param indexs 索引(支持多个索引联合查询) 相当于表名，多个相当于union
//	 * @param timefield 日期字段
//	 * @param interval 间隔 1d 一天 1M 一个月 1y 一年
//	 * @param format 日期字段
//	 * @param filterMap 过滤参数 相当于where 字段名=值
//	 */
//	public static Map<String, Object> queryForTimeInterval(String index,String timefield,String interval,String format,Map<String,String> filterMap){
//		Map<String, Object> result = new LinkedHashMap<String, Object>();
//		 try {
//			// 1、创建search请求
//			SearchRequest searchRequest = new SearchRequest(index);
//			// 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//			sourceBuilder.size(0);
//			//多字段分组
//			DateHistogramAggregationBuilder termsBuilder = AggregationBuilders.dateHistogram(timefield).field(timefield).dateHistogramInterval(new DateHistogramInterval(interval)).format(format);
//
//			//加入聚合
//			sourceBuilder.aggregation(termsBuilder);
//			//过滤条件
//			if(filterMap!=null){
//				BoolQueryBuilder globalBuilder = QueryBuilders.boolQuery();
//
//				if (filterMap.containsKey("ViolationTime")) {
//					String timerange[] = filterMap.get("ViolationTime").toString().split(" - ");
//					RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("ViolationTime");
//					if (timerange.length>0) {
//						rangeQueryBuilder.gte(timerange[0]);
//					}
//					if (timerange.length>1) {
//						rangeQueryBuilder.lt(timerange[1]);
//					}
//					globalBuilder.must(rangeQueryBuilder);
//					filterMap.remove("ViolationTime");
//				}
//
//				for (String key : filterMap.keySet()) {
//						QueryBuilder matchQueryBuilder = QueryBuilders.termsQuery(key, filterMap.get(key).split(","));
//						globalBuilder.must(matchQueryBuilder);
//				}
//				sourceBuilder.query(globalBuilder);
//			}
//			log.info("分组统计请求参数："+index+"   "+sourceBuilder);
//			searchRequest.source(sourceBuilder);
//			//3、发送请求
//			RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
//			builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(1*1024*1024*1024));//5G
//			SearchResponse searchResponse = getClient().search(searchRequest,builder.build());
//			//4、处理响应
//			//搜索结果状态信息
//			if(RestStatus.OK.equals(searchResponse.status())) {
//			    // 获取聚合结果
//			    Aggregations aggregations = searchResponse.getAggregations();
//			    ParsedDateHistogram parsedDateHistogram = aggregations.get(timefield);
//			    for (org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket bucket : parsedDateHistogram.getBuckets()) {
//					System.out.println(bucket.getKeyAsString()+"  "+bucket.getDocCount());
//					result.put(bucket.getKeyAsString(), bucket.getDocCount());
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		log.info("结果数："+result.size());
//		return result;
//	}
//
//	/**
//	 * 获取es数据
//	 * @param indexs 索引名
//	 * @param paramsMap 过滤参数
//	 * @return
//	 * @throws Exception
//	 */
//	public static Map<String, Object> getListData(String index,Map<String,Object> paramsMap)throws Exception{
//		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//		// 1、创建search请求
//		SearchRequest searchRequest = new SearchRequest(index);
//		// 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.trackTotalHits(true);
//		//过滤条件
//		if(paramsMap!=null){
//			BoolQueryBuilder globalBuilder = QueryBuilders.boolQuery();
//			if (paramsMap.containsKey("CreateDate")) {//上次最新时间
//				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("CreateDate").gt(paramsMap.get("CreateDate"));
//				globalBuilder.must(rangeQueryBuilder);
//			}
//			if (paramsMap.containsKey("AcceptTime")) {//上次最新时间
//				String timerange[] = paramsMap.get("AcceptTime").toString().split(" - ");
//				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("AcceptTime");
//				if (timerange.length>0) {
//					rangeQueryBuilder.gte(timerange[0]);
//				}
//				if (timerange.length>1) {
//					rangeQueryBuilder.lte(timerange[1]);
//				}
//				globalBuilder.must(rangeQueryBuilder);
//			}
//			if (paramsMap.containsKey("notType")) {//设置返回数量，不设置默认返回十条，最大返回10000条，超过10000条需要设置，全部返回设为0
//				BoolQueryBuilder notinqueryQuery = QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery("Type", paramsMap.get("notType")));
//				globalBuilder.must(notinqueryQuery);
//			}
//			if (paramsMap.containsKey("page")&&paramsMap.containsKey("size")) {//分页，从1开始
//				sourceBuilder.from((Integer.parseInt(paramsMap.get("page").toString())-1)*Integer.parseInt(paramsMap.get("size").toString()));
//			}
//			if (paramsMap.containsKey("size")) {//设置返回数量，不设置默认返回十条，最大返回10000条，超过10000条需要设置，全部返回设为0
//				Integer size = Integer.parseInt(paramsMap.get("size").toString());
//				sourceBuilder.size(size);
//			}
//			if (paramsMap.containsKey("sortName")&&paramsMap.containsKey("sortOrder")) {//排序
//				sourceBuilder.sort(paramsMap.get("sortName").toString(), (SortOrder)paramsMap.get("sortOrder"));
//			}
//			if (paramsMap.containsKey("OperatorID")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("OperatorID", paramsMap.get("OperatorID"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("MSISDN")) {
//				//QueryBuilder  queryBuilder = QueryBuilders.termQuery("MSISDN", paramsMap.get("MSISDN"));
//				QueryBuilder  queryBuilder = QueryBuilders.wildcardQuery("MSISDN", "*"+paramsMap.get("MSISDN")+"*");
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("IMEI")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("IMEI", paramsMap.get("IMEI"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("Industry")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("Industry", paramsMap.get("Industry"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("Province")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("Province", paramsMap.get("Province"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("Data")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("Data", paramsMap.get("Data"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("Voice")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("Voice", paramsMap.get("Voice"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("Message")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("Message", paramsMap.get("Message"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("ViolationType")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("ViolationType", paramsMap.get("ViolationType"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("ViolationTime")) {
//				String timerange[] = paramsMap.get("ViolationTime").toString().split(" - ");
//				RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("ViolationTime");
//				if (timerange.length>0) {
//					rangeQueryBuilder.gte(timerange[0]);
//				}
//				if (timerange.length>1) {
//					rangeQueryBuilder.lt(timerange[1]);
//				}
//				globalBuilder.must(rangeQueryBuilder);
//			}
//
//			/*合规性检查*/
//			if (paramsMap.containsKey("ID")) {
//				QueryBuilder  queryBuilder = QueryBuilders.termQuery("ID", paramsMap.get("ID"));
//				globalBuilder.must(queryBuilder);
//			}
//			if (paramsMap.containsKey("TypeID")&& StringUtils.isNotBlank(paramsMap.get("TypeID").toString())) {
//				String arr[] =paramsMap.get("TypeID").toString().split(",");
//				QueryBuilder  queryBuilder = QueryBuilders.termsQuery("TypeID", arr);
//				globalBuilder.must(queryBuilder);
//			}
//			/*合规性检查*/
//			sourceBuilder.query(globalBuilder);
//		}
//		searchRequest.source(sourceBuilder);
////		Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
////		searchRequest.scroll(scroll);
//
//		log.info("获取列表请求参数："+index+"   "+sourceBuilder);
//		//3、发送请求
//		SearchResponse searchResponse = getClient().search(searchRequest,RequestOptions.DEFAULT);
//		long total = searchResponse.getHits().getTotalHits().value;
//
//		//4、处理响应
//		//搜索结果状态信息
//		if(RestStatus.OK.equals(searchResponse.status())) {
//
////			String scrollId = searchResponse.getScrollId();
//			SearchHit[] searchHit = searchResponse.getHits().getHits();
//			for (SearchHit hit : searchHit) {
//				list.add(hit.getSourceAsMap());
//			}
//			System.out.println("总数："+total+" 当前："+list.size()+" 本次返回："+searchHit.length);
////			while( searchHit != null && searchHit.length > 0 ) {
////				System.out.println("总数："+total+" 当前："+list.size()+" 本次返回："+searchHit.length);
////				for (SearchHit hit : searchHit) {
////					list.add(hit.getSourceAsMap());
////				}
////
////				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
////				scrollRequest.scroll(scroll);
////
////				searchResponse = getClient().searchScroll(scrollRequest,RequestOptions.DEFAULT);
////				scrollId = searchResponse.getScrollId();
////				searchHit = searchResponse.getHits().getHits();
////			}
//
////			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
////			clearScrollRequest.addScrollId(scrollId);
////			ClearScrollResponse clearScrollResponse = getClient().clearScroll(clearScrollRequest,RequestOptions.DEFAULT);
//		}
//		//System.out.println(JSON.toJSONString(list));
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("list", list);
//		result.put("total", total);
//		return result;
//	}
//
//	public static void insert(String index,Map<String,Object> map) throws Exception{
//		IndexRequest indexRequest = null;
//		if (map.containsKey("_id")) {
//			 String _id = map.get("_id").toString();
//			 System.out.println("插入文档："+index+"  "+_id);
//			 map.remove("_id");
//			 indexRequest = new IndexRequest(index,"_doc",_id).source(map);
//		}else{
//			indexRequest = new IndexRequest(index).source(map);
//		}
//
//		IndexResponse indexResponse = getClient().index(indexRequest, RequestOptions.DEFAULT);
//		//5、处理响应
//        if(indexResponse != null) {
//            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
////                System.out.println("新增文档成功："+JSON.toJSONString(map));
//            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
////                System.out.println("修改文档成功:"+JSON.toJSONString(map));
//            }
//            // 分片处理信息
//            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
//            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
//
//            }
//            // 如果有分片副本失败，可以获得失败原因信息
//            if (shardInfo.getFailed() > 0) {
//                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
//                    String reason = failure.reason();
//                    System.out.println("副本失败原因：" + reason);
//                }
//            }
//        }
//	}
//
//
//	public static void update(String index,String id,Map<String,Object> map) throws Exception{
//		UpdateRequest request = new UpdateRequest(index, id).doc(map);
//		UpdateResponse response = getClient().update(request, RequestOptions.DEFAULT);
//		//5、处理响应
//        if(response != null) {
//            if (response.getResult() == DocWriteResponse.Result.CREATED) {
//                System.out.println("新增文档成功，处理逻辑代码写到这里。");
//            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
//                System.out.println("修改文档成功:"+id);
//            }
//            // 分片处理信息
//            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
//            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
//
//            }
//            // 如果有分片副本失败，可以获得失败原因信息
//            if (shardInfo.getFailed() > 0) {
//                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
//                    String reason = failure.reason();
//                    System.out.println("副本失败原因：" + reason);
//                }
//            }
//        }
//	}
//
//	/**
//	 * 根据ID获取文档
//	 * @param index
//	 * @param id
//	 * @return
//	 */
//	public static Map<String, Object> getDocument(String index,String id) throws Exception{
//            // 1、创建获取文档请求
//            GetRequest request = new GetRequest(index,"_doc",id);
//            //选择返回的字段
//            String[] includes = new String[]{"*"};
//            String[] excludes = Strings.EMPTY_ARRAY;
//            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
//            request.fetchSourceContext(fetchSourceContext);
//
//            //3、发送请求
//            GetResponse getResponse = null;
//            try {
//                // 同步请求
//                getResponse = getClient().get(request,RequestOptions.DEFAULT);
//            } catch (ElasticsearchException e) {
//                if (e.status() == RestStatus.NOT_FOUND) {
//                	System.out.println("没有找到该id的文档" );
//                }
//                if (e.status() == RestStatus.CONFLICT) {
//                	System.out.println("获取时版本冲突了，请在此写冲突处理逻辑！" );
//                }
//                System.out.println("获取文档异常"+ e.getDetailedMessage());
//            }
//
//            //4、处理响应
//            if(getResponse != null) {
//                if (getResponse.isExists()) { // 文档存在
//                    String sourceAsString = getResponse.getSourceAsString(); //结果取成 String
//                    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();  // 结果取成Map
//                    System.out.println("获取文档："+sourceAsString);
//                    return sourceAsMap;
//                } else {
//                	System.out.println("没有找到该id的文档,index:"+index+" id:"+id );
//                }
//    }
//		 return null;
//	}
//
//
//	/**
//	 * 批量插入
//	 * @param index
//	 * @param list
//	 */
//	public static void bulkInsert(String index,List<Map<String, Object>> list) throws Exception{
//		 BulkRequest request = new BulkRequest();
//		 for (Map<String, Object> map : list) {
//			if (map.containsKey("_id")) {
//				 String _id = map.get("_id").toString();
//				 map.remove("_id");
//				 request.add(new IndexRequest(index,"_doc",_id).source(map));
//			}else{
//				request.add(new IndexRequest(index).source(map));
//			}
//
//		}
//		 BulkResponse bulkResponse = getClient().bulk(request, RequestOptions.DEFAULT);
//		 //4、处理响应
//         if(bulkResponse != null) {
//             for (BulkItemResponse bulkItemResponse : bulkResponse) {
//                 DocWriteResponse itemResponse = bulkItemResponse.getResponse();
//                 Failure failure = bulkItemResponse.getFailure();
//                 if (failure!=null) {
//					System.out.println(failure.getMessage());
//                 }else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX|| bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
//                     IndexResponse indexResponse = (IndexResponse) itemResponse;
//                     //TODO 新增成功的处理
//
//                 } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
//                     UpdateResponse updateResponse = (UpdateResponse) itemResponse;
//                    //TODO 修改成功的处理
//
//                 } else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
//                     DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
//                     //TODO 删除成功的处理
//                 }
//             }
//         }
//	}
//}
