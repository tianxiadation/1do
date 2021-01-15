// 环境配置
let api='',cloudUrl = '';
console.log(location.href.search("1do.hzxc.gov.cn:8443") > 0)
if(location.href.search("1do.hzxc.gov.cn:8443") > 0){
  api='https://1do.hzxc.gov.cn:8443';
  //cloudUrl = 'https://10.18.40.1/list';
  cloudUrl = 'https://cgdo.hzxc.gov.cn/list';
}else if(location.href.search("localhost:8080") > 0){
  api='http://localhost:8080';
  cloudUrl = 'http://10.18.40.1/list';
}else if(location.href.search("47.114.174.69:8088") > 0){
	  api='http://47.114.174.69:8088';
	  cloudUrl = 'http://10.18.40.1/list';
}else{
  api='http://59.202.68.43:8080';
  cloudUrl = 'http://10.18.40.1/list';
}
window.config = {
  // 接口路径
  globalPath: api,
  oneDoThreePath: 'https://workflow.hzxc.gov.cn',
  // 接口路径
  LawEnforcementReportPath: 'http://172.16.8.7:7777/index.html#/lawdetails',
  cloudUrbanDetailPath: cloudUrl // 云上城管详情页面
};