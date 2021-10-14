package zone.yiqing.mall.service;

import org.springframework.data.domain.Page;
import zone.yiqing.mall.nosql.elasticsearch.document.EsProduct;

import java.util.List;

/**
 * 商品搜索管理 service
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-14.
 */
public interface EsProductService {

  /**
   * 导入数据库所有商品到 es
   */
  int importAll();

  /**
   * 根据id删除商品
   */
  void delete(Long id);

  /**
   * 根据id创建商品
   */
  EsProduct create(Long id);

  /**
   * 批量删除商品
   */
  void delete(List<Long> ids);

  /**
   * 根据关键字搜索名称或者副标题
   */
  Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);
}
