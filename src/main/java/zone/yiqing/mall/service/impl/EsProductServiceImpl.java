package zone.yiqing.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zone.yiqing.mall.dao.EsProductDao;
import zone.yiqing.mall.nosql.elasticsearch.document.EsProduct;
import zone.yiqing.mall.nosql.elasticsearch.repository.EsProductRepository;
import zone.yiqing.mall.service.EsProductService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 商品搜索管理Service实现类.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-14.
 */
@Service
public class EsProductServiceImpl implements EsProductService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

  @Autowired
  private EsProductDao esProductDao;

  @Autowired
  private EsProductRepository productRepository;

  @Override
  public int importAll() {
    List<EsProduct> allEsProductList = esProductDao.getAllEsProductList(null);
    Iterable<EsProduct> esProducts = productRepository.saveAll(allEsProductList);
    Iterator<EsProduct> iterator = esProducts.iterator();
    int result = 0;
    while (iterator.hasNext()) {
      result++;
      iterator.next();
    }
    return result;
  }

  @Override
  public void delete(Long id) {
    productRepository.deleteById(id);
  }

  @Override
  public EsProduct create(Long id) {
    EsProduct result = new EsProduct();
    List<EsProduct> esProductList = esProductDao.getAllEsProductList(id);
    if (esProductList.size() > 0) {
      EsProduct esProduct = esProductList.get(0);
      result = productRepository.save(esProduct);
    }
    return result;
  }

  @Override
  public void delete(List<Long> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      ArrayList<EsProduct> esProducts = new ArrayList<>();
      for (Long id : ids) {
        EsProduct esProduct = new EsProduct();
        esProduct.setId(id);
        esProducts.add(esProduct);
      }
      productRepository.deleteAll(esProducts);
    }
  }

  @Override
  public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
    return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageRequest);
  }
}
