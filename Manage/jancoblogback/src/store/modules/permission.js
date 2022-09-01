import { asyncRouterMap, constantRouterMap } from '../../router/index';

/**
 * 判断权限role能不能拿到route路由
 * @param {roles} roles 
 * @param {route} route 
 * @returns 
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.role) {
    // 路由表的此一级节点有该权限
    return route.meta.role.indexOf(roles) >= 0
  } else {
    return true
  }
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers;
      state.routers = constantRouterMap.concat(routers);
    }
  },
  actions: {
    // 生成路由表
    GenerateRoutes({ commit }, {roles}) {
      return new Promise(resolve => {
        // 异步路由表, 从异步路由表中过滤
        const accessedRouters = asyncRouterMap.filter(v => {
          // admin 有根节点的所有权限
          if (roles.indexOf('admin') >= 0) return true;
          // 判断子节点能不能拿到本路由
          if (hasPermission(roles, v)) {
            // 有一级权限, 判断二级权限
            if (v.children && v.children.length > 0) {
              // 有子节点
              v.children = v.children.filter(child => {
                // 过滤子节点路由
                if (hasPermission(roles, child)) {
                  return child
                }
                return false;
              });
              return v
            } else {
              return v
            }
          }
          return false;
        });
        commit('SET_ROUTERS', accessedRouters);
        resolve();
      })
    }
  }
};

export default permission;

