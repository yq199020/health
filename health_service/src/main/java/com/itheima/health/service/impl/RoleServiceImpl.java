package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.MenuService;
import com.itheima.health.service.RoleService;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/6/26 15:44
 * @Version V1.0
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> findPermissionAndMenu() {
        Map<String, Object> map = new HashMap<>();
        List<Permission> permissions = permissionDao.findPermissionList();
        LinkedHashSet<Menu> menus = menuDao.findMenuList();

        map.put("permissions", permissions);
        map.put("menus", menus);


        return map;
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.findPageByString(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);

    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        Integer countPermission = permissionDao.checkPermissionByRoleId(id);

        if (countPermission>0) {
            throw new RuntimeException(MessageConstant.DELETE_ROLEPERMISSION_RELATIO);
        }

        Integer countMenu = menuDao.checkMenuByRoleId(id);

        if (countMenu>0) {
            throw new RuntimeException(MessageConstant.DELETE_ROLEMENUN_RELATION);
        }

        Integer countUser = userDao.checkUserByRoleId(id);
        if (countUser>0) {
            throw new RuntimeException(MessageConstant.DELETE_ROLE_RELATION);
        }

        roleDao.deleteById(id);
    }

    @Override
    public void add(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.add(role);
        setPermissionAndMenu(role.getId(),menuIds,permissionIds);
    }

    @Override
    public Map<String, Object> findMenuIdsAndPermissionIdsByRoleIds(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Integer> menuIds = menuDao.findMenuIdByRoleId(id);
        map.put("menuIds", menuIds);
        List<Integer> permisssionIds = permissionDao.findPermissionIdByRoleId(id);
        map.put("permissionIds", permisssionIds);

        return map;
    }

    @Override
    public void edit(Role role, Integer[] menuIds, Integer[] permissionIds) {

        roleDao.editRole(role);
        permissionDao.deletePermissionById(role.getId());
        menuDao.deleteMenuById(role.getId());
        setPermissionAndMenu(role.getId(),menuIds,permissionIds);
    }

    // 向检查组和检查项的中间表保存数据（Map指定参数）
    private void setPermissionAndMenu(Integer roleId, Integer[] menuIds, Integer[] permissionIds) {
        Map<String, Object> map = new HashMap<>();
        if (menuIds != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                map.put("role_id", roleId);
                map.put("menu_id", menuId);
                menuDao.addRoleAndMenu(map);
            }
        }

        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                map.put("role_id", roleId);
                map.put("permission_id", permissionId);
                permissionDao.addRoleAndPermission(map);
            }
        }
    }

}
