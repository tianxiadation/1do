package com.luqi.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.json.JFinalJson;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.luqi.common.model._MappingKit;
import com.luqi.controller.BoardController;
import com.luqi.controller.BxblController;
import com.luqi.controller.CommonController;
import com.luqi.controller.DataController;
import com.luqi.controller.DoController;
import com.luqi.controller.FileController;
import com.luqi.controller.RecordController;
import com.luqi.controller.ShortMessageController;
import com.luqi.controller.TestController;
import com.luqi.controller.UrgeController;
import com.luqi.controller.WeChatController;
import com.luqi.controller.WebSocketController;
import com.luqi.interceptor.MainInterceptor;
import com.luqi.interceptor.SimpleCROSFilter;
import com.luqi.interceptor.WebSocketHandler;
import com.luqi.timer.TimerManager;
import com.luqi.util.UrlUtil;


/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("WebRoot", 80, "/", 5);

		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		// JFinal.start("WebRoot", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use(UrlUtil.isHostAddress()?"prod_little_config.txt":"dev_little_config.txt");
		//PropKit.use("xcyun_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setMaxPostSize(25428800);
		JFinalJson.setDefaultConvertDepth(100);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) { //route路线
		//me.add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		//me.add("/blog", BlogController.class);// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		me.add("/do",DoController.class);
		me.add("/data",DataController.class);
		me.add("/wt",WebSocketController.class);
		me.add("/urge",UrgeController.class);
		me.add("/test",TestController.class);
		me.add("/shortMessage",ShortMessageController.class);
		me.add("/projectBoard", BoardController.class);
		me.add("/record", RecordController.class);
		me.add("/file", FileController.class);
	    me.add("/weChat", WeChatController.class);
	    me.add("/bxbl", BxblController.class);
	    me.add("/common", CommonController.class);
		
	}
	
	public void configEngine(Engine me) {//engine引擎
		//me.addSharedFunction("/common/_layout.html");
		//me.addSharedFunction("/common/_paginate.html");
	}

	//1do
	/*public static DruidPlugin createDruidPlugin(int i) {
	
		//如果不是正式环境连接测试数据库
	    if(UrlUtil.isHostAddress()&&i==1)
	         i=4; 
	    System.out.println(PropKit.get("jdbcUrl"+i));
	    return new DruidPlugin(PropKit.get("jdbcUrl"+i), PropKit.get("user"+i), PropKit.get("password"+i).trim());
	    
	}*/
	
	public static DruidPlugin createDruidPlugin(String parameter) {
			System.out.println(PropKit.get("jdbcUrl"+parameter));
	        return new DruidPlugin(PropKit.get("jdbcUrl"+parameter), PropKit.get("user"+parameter), PropKit.get("password"+parameter).trim());
	}

	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me)  {
		// 配置1do C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin("");
		me.add(druidPlugin);
		druidPlugin.setConnectionInitSql("set names utf8mb4");
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arp.setShowSql(true);
		me.add(arp);
		if(UrlUtil.isHostAddress())	{
			// 配置综合信息平台-四个平台对应统一用户 C3p0数据库连接池插件 
			DruidPlugin druidPlugin3 =createDruidPlugin("_zh"); 
			me.add(druidPlugin3); // 配置ActiveRecord插件
			ActiveRecordPlugin arp3 = new ActiveRecordPlugin("zh",druidPlugin3); 
			// 所有映射在 MappingKit 中自动化搞定 
			// _MappingKit.mapping(arp3); 
			arp3.setShowSql(true);
			me.add(arp3);
		
		}
		// 配置日志系统 C3p0数据库连接池插件
		DruidPlugin druidPlugin1 = createDruidPlugin("_record");
		druidPlugin1.setDriverClass(PropKit.get("driver"));
		me.add(druidPlugin1);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp1 = new ActiveRecordPlugin("record",druidPlugin1);
		arp1.setDialect(new SqlServerDialect());
		// 所有映射在 MappingKit 中自动化搞定
		//_MappingKit.mapping(arp1);
		me.add(arp1);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new SimpleCROSFilter());
		me.addGlobalActionInterceptor(new MainInterceptor());
		me.addGlobalActionInterceptor(new TxByMethodRegex("(.*add.*|.*update.*|.*del.*)",true));

	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new WebSocketHandler("/websocket"));
		//me.add(new UrlSkipHandler("^/websocket", false));


	}
	@Override
	public void afterJFinalStart() {
		//根据服务器ip确定各种接口地址
		UrlUtil.initialization();
		//同步日志程序查询到新的日志计算相识度
	    new TimerManager(1,false);
	    //计算甘特图
	    new TimerManager(false,1);
	    //项目看板任务完成趋势图定时任务（每天0点计算前一天任务完成情况）
		TimerManager.boardTaskTrendTask();
	    //new Thread(()->BoardTaskServiceA.temp()).start();
		//BoardTaskService.getInt();
	}

	

	@Override
	public void beforeJFinalStop() {
		//关闭服务器之前可以写一些方法处理数据。
		Db.update("update t_1do_urge_websocket set isOnline=false");
		Db.update("update websocket set isOnline=false");
	}
	

}