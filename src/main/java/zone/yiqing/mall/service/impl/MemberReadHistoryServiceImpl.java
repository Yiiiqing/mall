package zone.yiqing.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.yiqing.mall.nosql.mongodb.document.MemberReadHistory;
import zone.yiqing.mall.nosql.mongodb.repository.MemberReadHistoryRepository;
import zone.yiqing.mall.service.MemberReadHistoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-18.
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {

  @Autowired
  private MemberReadHistoryRepository memberReadHistoryRepository;

  @Override
  public int create(MemberReadHistory memberReadHistory) {
    memberReadHistory.setId(null);
    memberReadHistory.setCreateTime(new Date());
    memberReadHistoryRepository.save(memberReadHistory);
    return 1;
  }

  @Override
  public int delete(List<String> ids) {
    ArrayList<MemberReadHistory> toDeleteList = new ArrayList<MemberReadHistory>();
    for (String id :
        ids) {
      MemberReadHistory memberReadHistory = new MemberReadHistory();
      memberReadHistory.setId(id);
      toDeleteList.add(memberReadHistory);
    }
    memberReadHistoryRepository.deleteAll(toDeleteList);
    return toDeleteList.size();
  }

  @Override
  public List<MemberReadHistory> list(Long id) {
    return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(id);
  }
}
