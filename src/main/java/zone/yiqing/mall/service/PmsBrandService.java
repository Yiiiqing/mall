package zone.yiqing.mall.service;

import zone.yiqing.mall.mbg.model.PmsBrand;

import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
public interface PmsBrandService {

  List<PmsBrand> listAllBrand();

  int createBrand(PmsBrand brand);

  int updateBrand(Long id, PmsBrand brand);

  int deleteBrand(Long id);

  List<PmsBrand> listBrand(int pageNum, int pageSize);

  PmsBrand getBrand(Long id);
}
