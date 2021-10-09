package zone.yiqing.mall.common.api;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装类.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-09.
 */
@Data
public class CommonPage<T> {

  private Integer pageNum;
  private Integer pageSize;
  private Integer totalPage;
  private Long total;
  private List<T> list;

  /**
   * 将MyBatis Plus 分页结果转化为通用结果
   *
   * @param pageResult
   * @param <T>
   * @return
   */
  public static <T> CommonPage<T> restPage(List<T> pageResult) {
    CommonPage<T> result = new CommonPage<>();
    PageInfo<T> pageInfo = new PageInfo<>();
    result.setTotalPage(pageInfo.getPages());
    result.setPageNum(pageInfo.getPageNum());
    result.setPageSize(pageInfo.getPageSize());
    result.setTotal(pageInfo.getTotal());
    result.setList(pageInfo.getList());
    return result;
  }

}
