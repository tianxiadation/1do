## 1do数据库文档

[TOC]

### approval 审批配置表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|id|bigint(20)|编号id|否|主键|自增;|
|source|int(11)|来源 1、call 或者oa 2、主动办 3、三实库 4、其他|是|||
|type|int(11)|1.是0.不是|是|||
|name|varchar(50)|状态名称|是|||
|gmtCreate|bigint(20)|主动创建|是|||
|gmtModified|bigint(20)|被动更新|是|||
|isDeleted|tinyint(1)|1 表示删除，0 表示未删除。|否|||



### holiday 日期假期表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|id|int(11)|主键id|否|主键||
|date|varchar(20)|日期|是|||
|week|varchar(255)|星期|是|||
|type|int(11)|0工作日（包括周末补班），1周末，2节假日|是|||
|desc|varchar(255)|日期类型|是|||
|weekOfYear|varchar(10)|（周跨年算明年）如2018-12-31 为201901|是|||
|modifytime|datetime|修改时间|否|||
|dateTime|datetime|日期时间|是|||
|time|varchar(50)|月日（2.1）|是|||
|weeknum|varchar(10)|星期数值（一到日）|是|||
|data|varchar(10)|数据|是|||
|itemId|bigint(20)|节点id（计算时候使用）|是|||
|atemp|varchar(255)|无|是|||
|btemp|varchar(255)|无|是|||
|dtemp|varchar(255)|无|是|||



### t_1do_attr 1do附件表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|UPLOADUSER|varchar(50)|上传人员账号|否|||
|UPLOADTIME|datetime|上传时间|是|||
|ATTRNAME|text|文件名称|是|||
|ATTRPATH|text|文件云存储路径|是|||
|ATTRORDER|int(11)|文件顺序|是|||
|ISFB|int(11)|是否反馈1.否2.是|是|||
|UPLOADUSERNAME|varchar(255)|无|是|||



### t_1do_base 1do主表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|PARENTID|varchar(50)|父级工单显示ID(父级1do显示ID)|是|||
|OTITLE|text|标题|是|||
|ODESCRIBE|text|问题描述(内容)|否|||
|OCUSTOMER|text|客户(发起人账号)|是|||
|OCUSTOMERNAME|text|客户姓名(发起人姓名)|是|||
|OSTARTTIME|datetime|开始时间(发起时间)|是|||
|CREATEUSER|varchar(50)|创建人(创建人账号)|否|||
|CREATEUSERNAME|varchar(50)|创建人姓名|否|||
|OCREATETIME|datetime|开始时间（创建时间）|是|||
|OFINISHTIME|datetime|拟完成时间|是|||
|RealFINISHTIME|datetime|实际完成时间|是|||
|ORANGE|text|阅读范围(抄送人)|是|||
|ORANGENAME|text|阅读范围人姓名（抄送人人姓名）|是|||
|LASTUPDATETIME|timestamp|最后更新时间|是|||
|OISDELETED|int(11)|删除标识1.正常2.删除|否|||
|DELETETIME|datetime|删除时间|是|||
|DSHOWID|varchar(50)|部门编号|是|||
|OTYPEID|varchar(50)|工单分类（1do分类）目前默认：39509142708158464；；来源为1call办的填（1、城管执法，2、民政，3、环卫，4、住建，5、人社）后续增加另行通知，来源为综合指挥平台，1、zdfw-主动服务，2、yj-预警，3、1do-转1do 4、 yjzh-一键指挥|是|||
|MESSAGEID|text|来源消息id|是|||
|GROUPID|text|来源群id|是|||
|OEXECUTOR|text|受理人|是|||
|OEXECUTORNAME|text|受理人姓名|是|||
|CC|varchar(255)|抄送人账号|是|||
|CCNAME|varchar(255)|抄送人名字|是|||
|ISLOOK|int(11)|是否查看1是2否|是|||
|TYPE|int(11)|置顶|是|||
|USERTYPE|int(11)|无|是|||
|ISAUDIT|int(11)|是否审核1是未审核，2审核|是|||
|star|int(11)|最新评价星星|是|||
|evaluation|varchar(255)|最新评价|是|||
|SENDTIME|bigint(20)|通知时间|是|||
|AT|varchar(2550)|@某人|是|||
|SOURCE|int(11)|来源 1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，8、1call办9,综合指挥平台|是|||
|APARAMETER|int(11)|a参数（source为2即来源为主动办时表示工单id）|是|||
|BPARAMETER|int(11)|b参数（source为2即来源为主动办时表示人数）|是|||
|CPARAMETER|int(11)|C参数（source为2即来源为主动办时表示工单状态）|是|||
|DPARAMETER|int(11)|D参数（source为2即来源为主动办时表示工单类型1事项2方案）|是|||
|ISAPPROVAL|tinyint(1)|是否审批1 表示审批，0 表示未审批。|是|||
|OSTATUS|int(11)|处理状态3.待处理4.处理中5.已完成|是|||
|LOOKNUM|int(11)|查看数量|是|||
|FBNUM|int(11)|反馈数量|是|||
|LIGHTNING|int(11)|催办数（闪电）|是|||
|URGENAME|varchar(255)|催报人，以，隔开|是|||
|URGESHOWID|varchar(255)|催报人SHOWID，以，隔开|是|||
|base|text|转1do数据|是|||
|SIMILARITY|int(11)|相似度|是|||
|LOOKUSER|varchar(1000)|工单查看人员showid，以逗号隔开|是|||
|OHANDLE|varchar(255)|处理人showid|是|||
|OHANDLENAME|varchar(255)|处理人姓名|是|||
|FEEDBACKSIMILARITY|int(11)|反馈相似度|是|||
|RECORDSIMILARITY|int(11)|日志相似度|是|||
|address|varchar(255)|事件地址|是|||
|eventId|varchar(255)|事件编号|是|||
|COMMANDPLATFORMID|varchar(255)|指挥平台id|是|||
|ISSUCCESS|tinyint(1)|1是0否（推送到综合指挥平台是否成功）|是|||
|module|varchar(255)|指挥平台接口module参数|是|||
|STREET|varchar(255)|指挥平台新建1do填写街道（如qz01）：                               下城区：qzx01<br/>天水街道：tszx01<br/>武林街道：wlzhzx01<br/>长庆街道：cqzhzx01<br/>潮鸣街道：cmzhzx01<br/>朝晖街道：zhzhzx01<br/>文晖街道：whzx01<br/>东新街道：dxzhzx01<br/>石桥街道：sqzhzx01|是|||



### t_1do_board 项目看板
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|PARENTID|bigint(20)|父节点id|否|||
|ITEMNAME|varchar(255)|节点名称|是|||
|TYPE|int(11)|类型：1是项目分类节点2是子分类节点3项目节点|是|||
|COMPLETION|varchar(20)|完成情况|是|||
|FINISHDATE|date|完成日期|是|||



### t_1do_board_daliy_report 项目看板日报
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|REPORTID|bigint(20)|无|是|||
|CONTENT|text|日报内容|是|||
|DATE|date|日志日期|否|||
|TIME|timestamp|报送时间|是|||
|COMPANY|varchar(50)|公司showId|否|||
|NUMBER|int(11)|字数|否|||
|PROJECTID|bigint(20)|项目id|否|||
|TASKID|bigint(20)|任务id(任务id与board表id相同)|是|||
|UPDATETIME|timestamp|更新时间|否|||



### t_1do_board_log 项目看板日志
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|ITEMID|bigint(20)|节点id|否|||
|ITEMNAME|varchar(255)|节点名称|是|||
|PARENTID|bigint(20)|父节点id|否|||
|UPDATETIME|timestamp|修改时间|否|||
|TYPE|int(11)|类型1新增2修改3删除|否|||
|OUSER|varchar(50)|1call用户的showid|否|||



### t_1do_board_remark 项目看板备注表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|CONTENT|text|内容|是|||
|DATE|date|日期|否|||
|PROJECTID|bigint(20)|项目id|否|||
|TYPE|int(2)|1-日报评价，2-日报备注，3-项目完整进展备注，4-项目进展备注|否|||



### t_1do_board_task 项目看板项目完整进度表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|TASK|varchar(255)|任务|是|||
|DATE|date|日期|是|||
|PROJECTID|bigint(20)|项目id|是|||
|COMPLETION|varchar(20)|完成情况|是|||
|PRINCIPLE|varchar(1000)|负责人|是|||
|PRINCIPLESHOWID|varchar(1000)|负责人showid|是|||
|PLANNEDDATE|varchar(255)|计划日期|是|||
|DATA|text|各个实际日期状态|是|||
|ITEMID|bigint(20)|项目节点id(t_1do_board中是项目的id关联)|是|||
|ATEMP|text|未完成变成已完成取该字段|是|||
|BTEMP|text|已完成变成未完成取该字段|是|||
|ISOPEN|tinyint(1)|是否展开：1展开1do进度图，0不展开|是|||
|PLANNEDTIME|varchar(255)|计划时间|是|||
|DTEMP|text|未完成变成绿色取该字段|是|||
|ISRELATION|tinyint(1)|是否关联日报 1是0否|是|||



### t_1do_board_task_1do 项目看板项目完整进度表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|DATE|date|日期|是|||
|DATA|text|各个实际日期状态|是|||
|ITEMID|bigint(20)|项目节点id(t_1do_board中是项目的id关联)|是|||
|ATEMP|text|未完成变成已完成取该字段|是|||
|BTEMP|text|已完成变成未完成取该字段|是|||
|SHOWID|varchar(50)|1do的showid|是|||
|PLANNEDTIME|varchar(255)|计划时间|是|||



### t_1do_board_task_remarks 项目看板任务评估
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|TASKID|bigint(20)|任务id|否|||
|COMPANYID|varchar(255)|公司id|否|||
|CONTENT|text|内容|是|||



### t_1do_board_task_report 项目看板项目完整进度表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|DATE|date|日期|是|||
|SUMMARY|text|总结|是|||
|NUMBER|int(11)|编号（1，2，3）三个位置的总结|是|||
|PROJECTID|bigint(20)|项目id|是|||



### t_1do_board_update_log 项目看板日志
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|CONTENT|text|修改前内容|是|||
|UPDATETIME|timestamp|修改时间|是|||
|TYPE|int(11)|项目分类-1，项目子分类-2|是|||
|OUSER|varchar(50)|1call用户的showid|是|||



### t_1do_eventtype 1do事件类型表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|编号|否|主键|自增;|
|EVENTTYPE|int(11)|通知类型|是|||
|EVENTTYPENAME|varchar(50)|通知类型名称|是|||



### t_1do_fbtype 1do反馈分类表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|编号|否|主键|自增;|
|OTYPEID|varchar(50)|1do反馈分类|是|||
|OTYPENAME|varchar(50)|1do反馈分类名称|是|||
|OPARENTID|varchar(50)|上级1do反馈分类ID|是|||



### t_1do_feedback 1do反馈表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|OUSER|varchar(50)|相关人员账号|否|||
|OUSERNAME|varchar(50)|无|是|||
|FBTIME|datetime|反馈时间|是|||
|TIMESTAMP|bigint(20)|时间戳|是|||
|FBCONTENT|text|反馈内容|是|||
|FBTYPE|int(11)|反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7删除反馈,8删除反馈时加1条数据,9.语音反馈填,10网页链接|是|||
|ATTRID|text|附件id组|是|||
|FBUSER|text|对应用户账号组|是|||
|FBUSERNAME|text|对应用户账号组名字|是|||
|USERID|varchar(50)|无|是|||
|ATTRNAME|text|文件名称|是|||
|ATTRPATH|text|文件云存储路径组|是|||
|star|int(11)|星星|是|||
|isoverdue|int(11)|是否过期1否2是|是|||
|modifyTime|timestamp|更新时间|是|||
|AT|varchar(2550)|@某人|是|||
|shortMessage|varchar(255)|催办短信返回值|是|||
|temp|int(11)|零时字段处理脏数据用|是|||
|result|varchar(255)|无|是|||
|ISCREATEREPORT|tinyint(1)|是否转过日志1是0否|是|||



### t_1do_fw 1do整理层管理范围
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|编号|否|主键|自增;|
|OUSER|varchar(50)|整理人员账号|否|||
|SHOWID|varchar(50)|无|是|||
|OUSERNAME|varchar(50)|显示ID（相关人员账号）|否|||
|DSHOWID|varchar(50)|管理部门编号|是|||
|OTYPEID|varchar(50)|1do分类|是|||
|type|int(11)|1正常通知，2 不通知|是|||
|icallshowid|varchar(50)|1call的showid|是|||
|isRead|int(11)|是否查看1是2否|是|||



### t_1do_fwpstatus 1do相关人员状态表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|编号|否|主键|自增;|
|SHOWID|varchar(50)|显示ID|否|||
|OUSER|varchar(50)|显示ID（相关人员账号）|否|||
|isRead|int(11)|是否查看1是2否|是|||
|online|int(11)|是否在线1在线2不在线|是|||
|modifyTime|datetime|无|是|||
|gmtModified|datetime|被动更新|是|||



### t_1do_keyword 
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|编号|否|主键|自增;|
|KEYWORD|varchar(0)|关键词|是|||
|WEIGHT|int(11)|权重|是|||



### t_1do_label 1do传送门标签表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|LABEL|varchar(255)|标签|是|||
|TYPE|int(11)|类型自动解析的未1，整理层加的为2|是|||
|weight|int(11)|权重|是|||
|createTime|datetime|创建时间|是|||



### t_1do_label_feedback 1do反馈标签表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|FBID|int(11)|反馈ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|LABEL|varchar(255)|标签|是|||
|TYPE|int(11)|类型自动解析的未1，整理层加的为2,  3反馈删除了|是|||
|weight|int(11)|权重|是|||
|createTime|datetime|创建时间|是|||



### t_1do_label_record 1do日志标签表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|RECORDID|bigint(20)|日志ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|LABEL|varchar(255)|标签|是|||
|TYPE|int(11)|类型自动解析的未1，整理层加的为2|是|||
|weight|int(11)|权重|是|||
|createTime|datetime|创建时间|是|||



### t_1do_log 1do操作日志审计表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|OUSER|varchar(50)|显示ID（相关人员账号）|否|||
|OUSERNAME|varchar(50)|显示ID（相关人员账号）|否|||
|opTime|datetime|操作时间|是|||
|log|text|操作日志|是|||
|logType|int(11)|操作类型日志状态（1.创建2.查看3.上传4.催办5.确认办结6.评价7.拆项8.移除人9.邀请.10.反馈11.删除反馈,12.删除1do,13.恢复1do,14.修改15.重做1do16.新建子1do17.下载，18修改所属项目）|是|||
|oprator|varchar(50)|实际操作人|是|||
|isoverdue|int(11)|是否过期1否2是（1do重做后数据会过期）|是|||
|modifyTime|timestamp|更新时间|是|||
|content|text|其他参数|是|||
|calculation|tinyint(1)|是否已经计算（评价系统算法）1是0否|是|||



### t_1do_order 1do置顶表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(255)|show_id|是|||
|id|bigint(20)|id|否|主键|自增;|
|useraccount|varchar(255)|用户账号|是|||
|type|int(11)|要我做1，要他做2，整理层 3|是|||
|modifyTime|timestamp|更新时间|是|||



### t_1do_project 项目看板项目表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ITEMID|bigint(20)|项目id(t_1do_board中是项目的id关联)|否|主键||
|OCREATETIME|datetime|创建时间|否|||
|OFINISHTIME|datetime|拟完成时间|是|||
|ISFINISH|tinyint(1)|是否完成1是0否|否|||
|ISKEY|tinyint(1)|是否是重点项目1是0否|否|||
|NAME|varchar(255)|看板名称|是|||
|GROUPNAME|varchar(255)|1CALL群名|是|||
|CRATEUSER|varchar(255)|创建人的showid|是|||
|UPDATETIME|timestamp|修改时间|否|||
|GROUPID|varchar(50)|1CALL群id|是|||



### t_1do_project_1do 项目-1do关联表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(11)|自增id|否|主键|自增;|
|ITEMID|bigint(20)|项目id(t_1do_board中是项目的id关联)|否|||
|SHOWID|varchar(50)|1do的showid|否|||



### t_1do_project_stakeholder 项目-干系人关联表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(11)|自增id|否|主键|自增;|
|ITEMID|bigint(20)|项目id(t_1do_board中是项目的id关联)|否|||
|OUSER|varchar(50)|1call用户的showid|否|||
|COMPANY|varchar(50)|公司showId|否|||
|PROJECTID|bigint(11)|项目ID|否|||



### t_1do_pset 1do相关人员设置表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|OUSER|varchar(50)|相关人员账号|否|||
|EVENTTYPE|text|订阅事件1.送达2.查看3.反馈4.催办5.办结6.评价|是|||
|USERTYPE|int(11)|1发起人2创建人3受理人4抄送人5受邀人|是|||



### t_1do_pstatus 1do相关人员状态表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|OUSER|varchar(50)|显示ID（相关人员账号）|否|||
|OUSERNAME|varchar(50)|显示ID（相关人员账号）|否|||
|OSTATUS|int(11)|处理状态 1.已送达2.无（创建人状态）3.待处理4.处理中5.已完成6.已删除|是|||
|STATUS|varchar(20)|处理状态 1.已送达2.无（创建人状态）3.待接单4.已接单5.已完成6.已删除|是|||
|USERTYPE|int(11)|1发起人2创建人3受理人4抄送人5受邀人6整理层|是|||
|isDelete|int(11)|是否删除1否2是|是|||
|isRead|int(11)|是否查看1是2否（发送的通知）|是|||
|result|text|发送通知返回结果|是|||
|otherid|int(11)|其他身份1受理人，2抄送人|是|||
|online|int(11)|是否在线1在线2不在线|是|||
|gmtModified|datetime|被动更新|是|||
|urgeIsLook|tinyint(1)|催报是否查看1.是0.否|是|||
|sort|int(11)|sort，处理人排1，@xx排2，参与人排3|是|||
|ISLOOK|int(11)|看板红点（是否查看1是2否）|是|||
|original|int(11)|原始人员1，后台添加的人2|是|||



### t_1do_record 日志表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|RECORDID|bigint(20)|日志id|是|||
|ID|int(11)|id|否|主键|自增;|
|NAME|varchar(255)|名字|是|||
|ACCOUNT|varchar(255)|账号|是|||
|CREATETIME|datetime|创建时间|是|||
|RECORD|text|日志内容|是|||
|TYPE|int(11)|状态1正常，2删除|是|||



### t_1do_relation 1do传送门关联表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|RELATIONSHOWID|varchar(50)|关联SHOW_ID|是|||
|SORT|int(11)|排序|是|||
|SIMILARITY|int(11)|相似度|是|||
|TYPE|int(11)|6删除无效,0手动添加的关联|是|||
|MODIFYTIME|datetime|修改时间|是|||
|WEIGHT|int(11)|权重|是|||



### t_1do_relation_feedback 1do传送门关联表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|FBID|int(11)|关联反馈id|是|||
|SORT|int(11)|排序|是|||
|SIMILARITY|int(11)|相似度|是|||
|TYPE|int(11)|1显示0不显示 2反馈删除了。|是|||
|MODIFYTIME|datetime|修改时间|是|||



### t_1do_relation_record 1do传送门日志关联表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|bigint(20)|编号|否|主键|自增;|
|RECORDID|bigint(20)|关联反馈id|是|||
|SORT|int(11)|排序|是|||
|SIMILARITY|int(11)|相似度|是|||
|TYPE|int(11)|1显示0不显示|是|||
|MODIFYTIME|datetime|修改时间|是|||



### t_1do_set 1do默认设置表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|EVENTTYPE|varchar(50)|订阅事件1.送达2.查看3.反馈4.催办5.办结6.评价|是|||



### t_1do_status 1do状态表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|SHOWID|varchar(50)|显示ID|否|||
|ID|int(11)|编号|否|主键|自增;|
|OSTATUS|int(11)|处理状态3.待处理4.处理中5.已完成|是|||



### t_1do_temp 1call转1do缓存表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|无|否|主键||
|BASE|text|无|是|||
|modifyTime|timestamp|无|是|||
|pid|int(11)|无|是|||



### t_1do_type 1do分类表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|编号|否|主键|自增;|
|OPARENTID|int(11)|上级分类ID|是|||
|OTYPEID|int(11)|1do分类|是|||
|OTYPENAME|varchar(50)|1do分类名称|是|||
|OSOURENAME|varchar(50)|1do来源名称|是|||
|OTHERNAME|varchar(255)|其他名称（1do看板里用）|是|||



### t_1do_urge_websocket 审批配置表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|id|bigint(20)|编号id|否|主键|自增;|
|sessionid|varchar(255)|来源1、主动办2、三实库|是|||
|user|varchar(255)|账号/用户showid|是|||
|type|int(11)|来源：1.综合信息平台、2.oa、3.1call、4.android、5.IOS|是|||
|isOnline|tinyint(1)|是否在线1 表示在线，0 表示离线。|否|||
|gmtCreate|bigint(20)|主动创建|是|||
|gmtModified|bigint(20)|被动更新|是|||
|isDeleted|tinyint(1)|1 表示删除，0 表示未删除。|否|||
|modeifyTime|datetime|无|是|||



### t_1do_user 1do(1call用户表)
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|无|否|主键|自增;|
|SHOWID|varchar(255)|无|是|||
|CSHOWID|varchar(255)|无|是|||
|loginName|varchar(255)|无|是|||
|UTRUENAME|varchar(255)|无|是|||
|UMAIL|varchar(255)|无|是|||
|UMOBILE|varchar(255)|无|是|||
|DNAME|varchar(255)|无|是|||
|UDEPTID|varchar(255)|无|是|||



### t_1do_weight 
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|bigint(20)|自增id|否|主键|自增;|
|LABEL|varchar(255)|标签(每个标签两条数据方便计算)|是|||
|WEIGHT|int(11)|权重|是|||



### t_reg_company 企业信息表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|记录ID|否|主键|自增;|
|SHOWID|varchar(50)|显示ID|否|||
|CCODE|varchar(50)|企业编码(域名)|否|||
|CNAME|varchar(200)|企业名称|否|||
|ENDTIME|datetime|有效期|否|||
|CREATEUSER|varchar(50)|创建人|否|||
|CREATEUSERNAME|varchar(50)|创建人姓名|否|||
|CREATETIME|datetime|创建时间|否|||
|CACTIVATIONSTATUS|int(11)|激活状态|是|||
|CACTIVATIONTIME|datetime|激活时间|是|||



### t_reg_company_dept 企业组织架构表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|记录ID|否|主键|自增;|
|SHOWID|varchar(50)|显示ID|否|||
|DNAME|varchar(100)|部门名称|否|||
|DPARENTIDSHOWID|varchar(50)|上级部门|否|||
|DLEVEL|int(11)|部门级别|否|||
|DPATH|text|部门路径|否|||
|DPATHNAME|text|部门名称路径|否|||
|DSORT|int(11)|排序号,越小越前面|否|||
|CSHOWID|varchar(50)|企业显示编码|否|||
|DAVAILABLECOUNT|int(11)|无|否|||
|DUNAVAILABLECOUNT|int(11)|无|否|||
|DCHILDDEPTCOUNT|int(11)|无|否|||
|CREATEUSER|varchar(50)|创建人|否|||
|CREATEUSERNAME|varchar(50)|创建人姓名|否|||
|CREATETIME|datetime|创建时间|否|||
|LASTUPDATETIME|datetime|最后更新时间|否|||
|DICON|text|图标|是|||
|BACKGROUND|text|背景|是|||
|APPID|text|最大应用集|是|||
|APPNAME|text|最大应用集|是|||
|USIGN|int(11)|是否隐藏，0-否，1-是|是|||



### t_reg_user 企业用户表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|用户ID|否|主键|自增;|
|SHOWID|varchar(50)|显示ID|否|||
|LAUNCHRID|bigint(20)|Launchr id|是|||
|UNAME|varchar(50)|用户名|是|||
|UPASSWOED|varchar(50)|用户密码|是|||
|UTRUENAME|varchar(50)|用户姓名|是|||
|UTRUENAMEC|varchar(50)|用户姓名首字母|是|||
|UHIRA|varchar(10)|用户日文片假名|是|||
|UMAIL|varchar(200)|邮箱|是|||
|UJOB|varchar(200)|职位|是|||
|UNUMBER|varchar(50)|工号|是|||
|USTATUS|int(11)|用户状态,0=不可用(锁定),1=可用|是|||
|UTELEPHONE|varchar(30)|办公室电话号码|是|||
|UMOBILE|varchar(50)|手机号码|是|||
|USORT|int(11)|排序号,越小排在越前面|是|||
|ULANGUAGE|varchar(50)|用户选择的默认语言|是|||
|LASTUPDATETIME|datetime|最近更新时间|是|||
|LASTLOGINTIME|datetime|上次登录时间|是|||
|LASTLOGINTOKEN|varchar(36)|上次登录凭证|是|||
|ISADMIN|int(11)|是否管理员,0=否,1=是|是|||
|CSHOWID|varchar(50)|企业显示编码|是|||
|CREATEUSER|varchar(50)|创建人|是|||
|CREATETIME|datetime|创建时间|是|||
|CREATEUSERNAME|varchar(50)|创建人姓名|是|||
|ULOGINNAME|varchar(100)|第三方登录名|是|||
|UTRUENAMEAC|varchar(255)|首字母缩写|是|||
|UISIN|int(11)|白名单，用户等级，1级最低，2级最高|是|||
|UADDRESS|varchar(500)|办公地址|是|||
|USTATE|int(11)|用户登陆状态，-1-离线，0-离开，1-在线|是|||
|UISSHOW|int(11)|是否特殊显示（0=否，1=是）|是|||
|USIGN|int(11)|是否隐藏，0-否，1-是|是|||
|POWER|int(11)|权限表（1整理层2领导3普通用户4项目干系人）项目干系人只在特殊接口里才出现，这里不设置|是|||



### t_reg_user_dept 企业用户部门表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|ID|int(11)|记录ID|否|主键|自增;|
|UNAME|varchar(50)|用户名|是|||
|UDEPTID|varchar(50)|所属部门|是|||
|CSHOWID|varchar(50)|企业显示编码|是|||
|ISMAIN|int(11)|是否主部门,0=否,1=是|是|||
|CREATEUSER|varchar(50)|创建人|是|||
|CREATETIME|datetime|创建时间|是|||
|CREATEUSERNAME|varchar(50)|创建人姓名|是|||
|UDEPTSORT|int(255)|用户在该组织架构中的排序,越小排越前,默认为0|是|||



### tempab 审批配置表
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|id|bigint(20)|编号id|否|主键|自增;|
|source|int(11)|来源 1、call 或者oa 2、主动办 3、三实库 4、其他|是|||
|type|int(11)|1.是0.不是|是|||
|name|varchar(50)|状态名称|是|||
|gmtCreate|bigint(20)|主动创建|是|||
|gmtModified|bigint(20)|被动更新|是|||
|isDeleted|tinyint(1)|1 表示删除，0 表示未删除。|否|||



### websocket 
------------
|参数|类型|注释|允空|键|备注|
|:-------|:-------|:-------|:-------|:-------|:-------|
|id|bigint(20)|编号id|否|主键|自增;|
|sessionid|varchar(255)|来源1、主动办2、三实库|是|||
|showid|varchar(255)|1.是0.不是|是|||
|fbid|int(11)|反馈id|是|||
|isOnline|tinyint(1)|是否在线1 表示在线，0 表示离线。|否|||
|gmtCreate|bigint(20)|主动创建|是|||
|gmtModified|bigint(20)|被动更新|是|||
|isDeleted|tinyint(1)|1 表示删除，0 表示未删除。|否|||



