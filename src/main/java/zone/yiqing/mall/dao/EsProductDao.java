package zone.yiqing.mall.dao;

import org.apache.ibatis.annotations.Param;
import zone.yiqing.mall.nosql.elasticsearch.document.EsProduct;

import java.util.List;

/**
 * 搜索系统中的商品管理自定义Dao.
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-14.
 */
public interface EsProductDao {

  List<EsProduct> getAllEsProductList(@Param("id") Long id);

}
