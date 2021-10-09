package zone.yiqing.mall.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.mbg.mapper.PmsBrandMapper;
import zone.yiqing.mall.mbg.model.PmsBrand;
import zone.yiqing.mall.mbg.model.PmsBrandExample;
import zone.yiqing.mall.service.PmsBrandService;

import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-09-29.
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

  @Autowired
  private PmsBrandMapper brandMapper;

  @Override
  public List<PmsBrand> listAllBrand() {
    return brandMapper.selectByExample(new PmsBrandExample());
  }

  @Override
  public int createBrand(PmsBrand brand) {
    return brandMapper.insertSelective(brand);
  }

  @Override
  public int updateBrand(Long id, PmsBrand brand) {
    brand.setId(id);
    return brandMapper.updateByPrimaryKeySelective(brand);
  }

  @Override
  public int deleteBrand(Long id) {
    return brandMapper.deleteByPrimaryKey(id);
  }

  @Override
  public List<PmsBrand> listBrand(int pageNum, int pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    return brandMapper.selectByExample(new PmsBrandExample());
  }

  @Override
  public PmsBrand getBrand(Long id) {
    return brandMapper.selectByPrimaryKey(id);
  }
}
