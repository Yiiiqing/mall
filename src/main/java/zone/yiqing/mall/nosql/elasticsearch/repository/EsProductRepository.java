package zone.yiqing.mall.nosql.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import zone.yiqing.mall.nosql.elasticsearch.document.EsProduct;

/**
 * 商品 ES 操作类.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-13.
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

  /**
   * 搜索查询
   *
   * @param name     商品名称
   * @param subTitle 商品标题
   * @param keywords 商品关键字
   * @param page     分页信息
   * @return
   */
  Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords,
      Pageable page);
}
