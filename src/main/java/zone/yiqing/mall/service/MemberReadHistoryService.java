package zone.yiqing.mall.service;

import zone.yiqing.mall.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * 会员浏览记录管理Service.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
public interface MemberReadHistoryService {

  /**
   * 生成浏览记录.
   *
   * @param memberReadHistory
   * @return
   */
  int create(MemberReadHistory memberReadHistory);

  /**
   * 删除浏览记录.
   *
   * @param ids
   * @return
   */
  int delete(List<String> ids);

  /**
   * 获取用户浏览历史记录.
   *
   * @param id
   * @return
   */
  List<MemberReadHistory> list(Long id);
}
