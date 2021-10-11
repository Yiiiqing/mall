package zone.yiqing.mall.dao;

import org.apache.ibatis.annotations.Param;
import zone.yiqing.mall.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台用户与角色管理自定义Dao.
 *
 * @author yiqing.zhang, {@literal <yiqing.zhang@leyantech.com>}
 * @date 2021-10-11.
 */
public interface UmsAdminRoleRelationDao {

  /**
   * 获取用户所有权限(包括+-权限)
   */
  List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
