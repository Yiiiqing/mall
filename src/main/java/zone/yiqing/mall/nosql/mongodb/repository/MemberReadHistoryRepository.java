package zone.yiqing.mall.nosql.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import zone.yiqing.mall.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
 * 会员商品浏览历史Repository.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {

  /**
   * 根据会员id按时间倒序获取浏览记录.
   *
   * @param memberId
   * @return
   */
  List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
