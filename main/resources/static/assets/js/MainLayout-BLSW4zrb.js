import{u as a}from"../index-CK1BbKi4.js";import{x as s,aC as e,z as o,A as t,P as l,H as n,u as r,G as u,K as i,M as d,ax as m,ag as c,y as p,L as v}from"./vue-vendor-DbIISveR.js";import{_ as f}from"./_plugin-vue_export-helper-BCo6x5W8.js";import"./element-plus-C89kzd44.js";const _={class:"layout"},g={class:"header"},x={class:"nav"},y={class:"user-info"},h=["src"],j={class:"main"},k=f(s({__name:"MainLayout",setup(s){const f=m(),k=a(),{isAdmin:A,userInfo:C}=e(k),I=()=>{k.logout(),f.push("/login")};return(a,s)=>{var e,m;const f=c("router-link"),k=c("router-view");return p(),o("div",_,[t("header",g,[s[3]||(s[3]=t("div",{class:"logo"},"人力资源管理系统",-1)),t("nav",x,[l(f,{to:"/"},{default:n((()=>s[0]||(s[0]=[v("首页")]))),_:1}),l(f,{to:"/profile"},{default:n((()=>s[1]||(s[1]=[v("个人信息")]))),_:1}),r(A)?(p(),u(f,{key:0,to:"/employee-management"},{default:n((()=>s[2]||(s[2]=[v("员工管理")]))),_:1})):i("",!0)]),t("div",y,[t("img",{src:(null==(e=r(C))?void 0:e.avatar)||"/default-avatar.png",class:"avatar"},null,8,h),t("span",null,d(null==(m=r(C))?void 0:m.username),1),t("button",{onClick:I},"退出登录")])]),t("main",j,[l(k)])])}}}),[["__scopeId","data-v-c320680c"]]);export{k as default};
