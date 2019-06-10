package com.demo.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Timer;

import com.demo.common.model.T1doBase;
import com.demo.common.model.T1doFeedback;
import com.demo.common.model.T1doLabelFeedback;
import com.demo.common.model._MappingKit;
import com.demo.controller.DataController;
import com.demo.controller.DoController;
import com.demo.controller.ShortMessageController;
import com.demo.controller.TestController;
import com.demo.controller.UrgeController;
import com.demo.controller.WebSocketController;
import com.demo.interceptor.MainInterceptor;
import com.demo.interceptor.SimpleCROSFilter;
import com.demo.interceptor.WebSocketHandler;
import com.demo.util.DbUtil;
import com.demo.util.UrlUtil;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;


/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/*public static String jdbcUrl="jdbc:mysql://172.16.10.60:3306/do?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static String user="root";
	public static String password="p@ssw0rd";*/
	public static String jdbcUrl="jdbc:mysql://59.202.68.48:3306/do?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	public static String user="root";
	public static String password="jiaojun";
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
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setMaxPostSize(25428800);
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
	}
	
	public void configEngine(Engine me) {//engine引擎
		//me.addSharedFunction("/common/_layout.html");
		//me.addSharedFunction("/common/_paginate.html");
	}
	
	public static DruidPlugin createDruidPlugin() {
		setJDBCUrl();
		//return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		return new DruidPlugin(jdbcUrl, user, password);
	}
	public static DruidPlugin createDruidPlugin1() {
		
		return new DruidPlugin(PropKit.get("jdbcUrl22"), PropKit.get("user22"), PropKit.get("password22").trim());
		
	}
	public static DruidPlugin createDruidPlugin2() {
		
		return new DruidPlugin(PropKit.get("jdbcUrl22"), PropKit.get("user22"), PropKit.get("password22"),PropKit.get("driver"));
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin();
		me.add(druidPlugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		arp.setShowSql(true);
		me.add(arp);
		
		
		// 配置C3p0数据库连接池插件
		DruidPlugin druidPlugin1 = createDruidPlugin1();
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
		
		UrlUtil.initialization();
		//DbUtil.updateOnline();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				/*Db.update("insert into t_1do_label(SHOW_ID,LABEL)"
						+" SELECT a.SHOW_ID,a.LABEL FROM t_1do_label a,t_1do_weight b where a.LABEL=b.LABEL");
				
				List<T1doFeedback> list=T1doFeedback.dao.find("select * from t_1do_feedback where "
						+ "FB_TYPE=1 or FB_TYPE=2 or FB_TYPE=6");
				list.forEach(t->{
					T1doLabelFeedback.saveAllLabel(t);
				});
				Db.update("truncate t_1do_relation");
				List<T1doBase> tb=T1doBase.dao.find("select * from t_1do_base");
				
				tb.forEach(t->{
					DbUtil.insertIdo(t.getShowId());
					DbUtil.updateType(t.getShowId());
					DbUtil.insertlr1(t.getShowId());					
				});*/
			
				
			}
		}).start();
		new Timer().schedule(new MyTask(), 600000, 3600000);
	}

	
		
	public static void setJDBCUrl(){
		try {
            //用 getLocalHost() 方法创建的InetAddress的对象
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
            
            //测试服务器
            if(!address.getHostAddress().equals("172.16.9.231")){//172.16.9.195
            	jdbcUrl="jdbc:mysql://172.16.8.11:3306/do?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";//附件地址
            	user="root";
            	password="p@ssw0rd";
            	/*jdbcUrl="jdbc:mysql://59.202.68.48:3306/do?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";//附件地址
            	user="root";
            	password="jiaojun";*/
            //本地服务器
            }/*else if(address.getHostAddress().equals("10.18.28.8")){
            	jdbcUrl="jdbc:mysql://localhost:3306/do?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";//附件地址
            	user="root";
            	password="123456";
            }*/
          System.out.println(jdbcUrl);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void beforeJFinalStop() {
		//关闭服务器之前可以写一些方法处理数据。
	}

}
