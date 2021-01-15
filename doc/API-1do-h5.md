# 1do接口文档  （默认POST请求，Content-Type: application/json）

## 正式 https://1do.hzxc.gov.cn:8443（172.16.9.231）

## 测试 http://59.202.68.43:8080

[TOC]

------

### 1 功能 ：看板各个状态数量

####  请求方法： /1do/do/searchNum

####  参数说明：

| 字段      | 字段名     | 类型   | 是否必填 | 描述及要求                                                   |
| --------- | ---------- | ------ | -------- | ------------------------------------------------------------ |
| method    | 类型       | String | 必填     | 要我做：medo；要他做：hedo；整理层看板：fwdo      本人全部：all |
| loginName | 当前用户id | String | 选填     | 未登入时填写（XzaqamVZPDtAR3WP）                             |
| source    | 来源       | int    | 选填     | 督查指挥填写:10                                              |
| oTypeId   | 类型       | int    | 选填     | 督查指挥填写                                                 |

```json
{"method":"fwdo","loginName":"PQV8oo3jeeiDkLbY"}
```

####  Output

| 字段      | 类型 | 描述及要求 |
| --------- | ---- | ---------- |
| 3         | int  | 待接单数量 |
| 4         | int  | 处理中数量 |
| 5         | int  | 已完成数量 |
| 6         | int  | 已删除数量 |
| Y         | int  | 已读数量   |
| N         | int  | 未读数量   |
| urge      | int  | 催办数量   |
| medoTotal | int  | 要我做总数 |
| hedoTotal | int  | 要他做总数 |

**Success**

```json
{
    "3": 833,
    "4": 159,
    "5": 102,
    "6": 23,
    "medoTotal": 62,
    "Y": 50,
    "hedoTotal": 110,
    "urge": 64,
    "N": 118
}
```

### 1 功能 ：app列表搜索

####  请求方法： /1do/do/appSearch

####  参数说明：

| 字段          | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------- | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| pageNumber    | 查询起始数          | int    | 必填     | 查询起始数，比如pageNumber:0,onePageNumber:10 ,//检索记录行1-10 .比如pageNumber:5,onePageNumber:10  //检索记录行6-15 |
| onePageNumber | 查询条数            | int    | 必填     | 一页显示的条数                                               |
| method        | 搜索区              | String | 必填     | 要我做：medo；要他做：hedo；全部：all                        |
| relate        | 关联                | String | 选填     | 默认“”，关联人的id（XzaqamVZPDtAR3WP）                       |
| type          | 处理状态            | int    | 必填     | 处理状态 3.待接单 4.处理中 5.已完成 6.已删除 0.全部 7.催办，8.未完成 |
| isLook        | 是否查看            | int    | 必填     | 是否查看1是2否 0全部。                                       |
| loginName     | 当前用户id          | String | 选填     | 未登入时填写（如：XzaqamVZPDtAR3WP）                         |
| source        | 来源                | String | 选填     | （移动端填：app，排序用--作用替换成来源）来源来源 0、全部    1、1do  2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，8、1call办9,综合指挥平台 |
| oTypeId       | 工单分类（1do分类） | String | 选填     | 来源为1call办的填（1、城管执法，2、民政，3、环卫，4、住建，5、人社）后续增加另行通知，来源为综合指挥平台，1、zdfw-主动服务，2、yj-预警，3、1do-转1do 4、 yjzh-一键指挥。 来源为10督查指挥（1、做地出让,2、民生实事,3、基础建设,4、地铁建设,5、区国企自营项目,6、地产项目,7、绿化建设,8、规划编制,9、生态治理） |
| base          | 关键字              | String | 选填     | 默认“”，督查指挥填写关键词、姓名就行搜索                     |
| keyword       | 搜索语句            | String | 选填     | 搜索语句如：催办人是余念， 默认“”  最新回复传：“最新回复”    |

```json
{
    "pageNumber": 0,
    "onePageNumber": 10,
    "method": "all",
    "relate": "",
    "type": 4,
    "isLook": 0,
    "loginName": "8BmEYpm9kQUlNN7m",
    "keyword":""
}
```

#### Output

| 字段             | 字段名         | 类型   | 描述及要求                                                   |
| ---------------- | -------------- | ------ | ------------------------------------------------------------ |
| SHOW_ID          | 1do工单编号    | String | 1do工单编号                                                  |
| O_DESCRIBE       | 内容           | String | 内容                                                         |
| O_CUSTOMER_NAME  | 发起人姓名     | String | 多个发起人以“;”隔开，如："方升群;谢洁"                       |
| O_EXECUTOR_NAME  | 参与人姓名     | String | 多个参与人以“;”隔开，如：“张城;刘佳民”                       |
| USER_TYPE        | 通知设置       | String | 这条工单该用户的角色（1发起人2创建人3参与人6整理层）         |
| O_STATUS         | 状态           | String | 状态有：待接单，已接单，已完成                               |
| LIGHTNING        | 催办数         | int    | 催办数                                                       |
| LOOKNUM          | 查看数量       | int    | 查看数量                                                     |
| FBNUM            | 反馈数量       | int    | 反馈数量                                                     |
| ISLOOK           | 是否查看1是2否 | int    | 是否查看1是2否                                               |
| O_CREATE_TIME    | 创建时间       | date   | 创建时间                                                     |
| O_FINISH_TIME    | 拟完成时间     | date   | 拟完成时间                                                   |
| Real_FINISH_TIME | 办结时间       | date   | 办结时间                                                     |
| O_CUSTOMER       | 发起人show_id  | String | 多个发起人以“;”隔开，如： “PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP” |
| O_EXECUTOR       | 参与人show_id  | String | 多个参与人以“;”隔开，如：                                                                                                  “8BmEYpm9kQUlNN7m; 297NKDKkDzHZe2da” |
| DELETE_TIME      | 删除时间       | date   | 删除时间                                                     |
| SEND_TIME        | 通知时间       | long   | 通知时间                                                     |
| SOURCE           | 来源           | int    | 来源来源 1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息中心81call办 |
| O_TYPE_ID        | 事件类型       | String | 目前默认：39509142708158464；；来源为1call办时（1、城管执法，2、民政，3、环卫，4、住建，5、人社）后续增加另行通知 |
| SYSTEM           | 系统           | String | 系统                                                         |
| EVENT_TYPE       | 事件类型       | String | 事件类型                                                     |
|                  |                |        |                                                              |

**Success**

```json
{
    "code": 200,
    "data": {
        "base": [
           
            {
                "O_CUSTOMER_NAME": "方升群",
                "O_FINISH_TIME": null,
                "SYSTEM": "督查指挥",
                "O_IS_DELETED": 1,
                "LOOKNUM": 1,
                "O_CREATE_TIME": 1585795703000,
                "EVENT_TYPE": "基础建设",
                "LIGHTNING": 0,
                "Real_FINISH_TIME": null,
                "ISLOOK": 1,
                "O_STATUS": "待接单",
                "O_TYPE_ID": "3",
                "O_EXECUTOR_NAME": "方升群;阎秋霞",
                "AT": "[]",
                "FBNUM": 0,
                "SEND_TIME": 1585795702725,
                "USER_TYPE": 1,
                "SOURCE": 10,
                "O_CUSTOMER": "PQV8oo3jeeiDkLbY",
                "O_EXECUTOR": "PQV8oo3jeeiDkLbY;ZD1ybAYvAou36nzJ",
                "SHOW_ID": "275344737592934400",
                "DELETE_TIME": null,
                "O_DESCRIBE": "与哈哈哈哈"
            }
            ....
            {
                "O_CUSTOMER_NAME": "方升群",
                "O_FINISH_TIME": null,
                "SYSTEM": "督查指挥",
                "O_IS_DELETED": 1,
                "LOOKNUM": 10,
                "O_CREATE_TIME": 1582696659000,
                "EVENT_TYPE": "基础建设",
                "LIGHTNING": 0,
                "Real_FINISH_TIME": null,
                "ISLOOK": 1,
                "O_STATUS": "已接单",
                "O_TYPE_ID": "3",
                "O_EXECUTOR_NAME": "方升群;沈霄莉;施健;梁斌",
                "AT": "[]",
                "FBNUM": 2,
                "SEND_TIME": 1582699610875,
                "USER_TYPE": 1,
                "SOURCE": 10,
                "O_CUSTOMER": "PQV8oo3jeeiDkLbY",
                "O_EXECUTOR": "PQV8oo3jeeiDkLbY;lQDNVWVQZ2HnpYoq;dQjN3qYxKdirL1A0;z0y6RJOOo1s8JoW1",
                "SHOW_ID": "262346407711604736",
                "DELETE_TIME": null,
                "O_DESCRIBE": "新建微信群，加公告，加发送第一条信息。1.0价值是是是搞混我啊是安徽if好气啊我还好还好还好我安徽覅合法怒"
            }
        ],
        "allPage": 25
    },
    "message": "Success"
}
```

### 1功能 ：1do登入（1cll调1do设置session）

#### 请求方法：  /1do/do/login（未登入调用其他接口需加上参数loginName）

#### 参数说明：

| 字段        | 类型   | 是否必填 | 描述及要求 |
| ----------- | ------ | -------- | ---------- |
| useraccount | String | 必填     | 账号       |

```json
{
    "useraccount": "fangshengqun",
    
}
```

####  Output

| 字段        | 字段名       | 类型    | 描述及要求                                                   |
| ----------- | ------------ | ------- | ------------------------------------------------------------ |
| D_NAME      | 1call字段    | int     | 我的部门名                                                   |
| U_DEPT_ID   | 1call字段    | list    | 我的部门id                                                   |
| cShowId     | 1call字段    | lsit    | 部门根id                                                     |
| isfw        | 1do字段      | boolean | 是否是整理层false否，true是                                  |
| loginName   | 1call字段    | String  | showid                                                       |
| LoginToken  | 1call字段    | String  | token                                                        |
| useraccount | 1call字段    | String  | 账号                                                         |
| username    | 1call字段    | String  | 姓名                                                         |
| POWER       | 权限         | int     | 权限表（1整理层2领导3普通用户4项目干系人）项目干系人只在特殊接口里才出现，这里不设置 |
| YSCG_POWER  | 云上城管权限 | int     | 云上城管权限，默认0、无权限，1、街道账号，2、整治小组账号，3、专班账号、4、事件发起账号 |

**Success**

```json
{
    "code": 200,
    "data": {
        "IS_DELETED": 0,
        "cShowId": "b5WJZ1bRLDCb7x2B",
        "LAST_UPDATE_TIME": 1591322967000,
        "U_DEPT_ID": [
            "20200602173238757-C1A2-59D0A41C4"
        ],
        "loginName": "GDrRBmLenquJAoGB",
        "LoginToken": "AJUwqcnLrXkrZBQdS352bQggUp0=",
        "POWER": 1,
        "isfw": true,
        "D_NAME": [
            "区集中攻坚指挥专班"
        ],
        "useraccount": "jizhonggongjianzhihui",
        "YSCG_POWER": 4,
        "username": "区集中攻坚指挥专班"
    },
    "message": "Success"
}
```

### 2 功能 ：创建保存1do（1call转1do时）

####  请求方法：  /1do/do/createIdo

####  参数说明：

| 字段                | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------------- | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| BASE                |                     |        |          |                                                              |
| O_TITLE             | 标题                | String | 选填     | 如：“20180621-方升群/谢洁需求”，                             |
| O_DESCRIBE          | 内容                | String | 必填     | 内容（1call办等存储json字符串）                              |
| O_CUSTOMER          | 发起人show_id       | String | 必填     | 多个发起人以“;”隔开，如： “PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP” |
| O_CUSTOMER_NAME     | 发起人姓名          | String | 必填     | 多个发起人以“;”隔开，如："方升群;谢洁"                       |
| O_START_TIME        | 发起时间            | String | 选填     | 拟开始时间                                                   |
| O_FINISH_TIME       | 完成时间            | String | 选填     | 拟完成时间                                                   |
| O_EXECUTOR          | 参与人show_id       | String | 选填     | 多个参与人以“;”隔开，如：                                                                                                  “8BmEYpm9kQUlNN7m; 297NKDKkDzHZe2da” |
| O_EXECUTOR_NAME     | 参与人姓名          | String | 选填     | 多个参与人以“;”隔开，如：“张城;刘佳民”                       |
| CREATE_USER         | 创建人show_id       | String | 必填     | 创建人show_id如:V267EnA0yEUjXZ5N                             |
| CREATE_USER_NAME    | 创建人姓名          | String | 必填     | 创建人姓名如:顾清清                                          |
| SOURCE              | 来源                | int    | 必填     | 来源 0、全部  1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，8、1call办9、城市大脑综合指挥平台 10、督查指挥，11云上城管，12百姓爆料，13红旗班 |
| O_TYPE_ID           | 工单分类（1do分类） | String | 选填     | 目前默认：0；；来源为1call办的填（1、城管执法，2、民政，3、环卫，4、住建，5、人社）后续增加另行通知，来源为综合指挥平台，1、zdfw-主动服务，2、yj-预警，3、1do-转1do 4、 yjzh-一键指挥。来源为10督查指挥（1、做地出让,2、民生实事,3、基础建设,4、地铁建设,5、区国企自营项目,6、地产项目,7、绿化建设,8、规划编制,9、生态治理）13红旗班（1、待定，后续增加自行填写） |
| MESSAGE_ID          | 来源消息id          | String | 选填     | 来源消息id以“;”隔开                                          |
| GROUPID             | 来源群id            | String | 选填     | 来源群id                                                     |
| AT                  | 内容中@相关人员信息 | String | 选填     | 内容中@相关人员信息如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| ATTR                |                     |        |          |                                                              |
| UPLOAD_TIME         | 上传时间            | String | 选填     | 附件上传时间（发送到消息群的时间）                           |
| ATTR_NAME           | 文件名称            | String | 选填     | 附件名                                                       |
| ATTR_PATH           | 文件云存储路径      | String | 选填     | 路径                                                         |
| UPLOAD_USER         | 上传人员show_id     | String | 选填     | 附件上传人员show_id（发送附件到消息群的人员show_id）         |
| UPLOAD_USER_NAME    | 上传人员姓名        | String | 选填     | 附件上传人员姓名（发送附件到消息群的人员姓名）               |
|                     |                     |        |          |                                                              |
| APARAMETER          | a参数               | int    | 选填     | a参数（source为3即来源为主动办时表示工单id，source为11即来云上城管时表示云上城管工单id） |
| BPARAMETER          | b参数               | int    | 选填     | b参数（source为3即来源为主动办时表示人数）                   |
| CPARAMETER          | c参数               | int    | 选填     | C参数（source为3即来源为主动办时表示工单状态）               |
| DPARAMETER          | d参数               | int    | 选填     | D参数（source为3即来源为主动办时表示工单类型1事项2方案）     |
|                     |                     |        |          |                                                              |
| module              | 指挥平台接口module  | String | 选填     | 指挥平台接口module参数                                       |
| COMMAND_PLATFORM_ID | 指挥平台id          | String | 选填     | 指挥平台新建1do填写指挥平台的id                              |
| STREET              | 街道                | String | 选填     | 指挥平台新建1do填写街道（如qz01）：                               下城区：qzx01<br/>天水街道：tszx01<br/>武林街道：wlzhzx01<br/>长庆街道：cqzhzx01<br/>潮鸣街道：cmzhzx01<br/>朝晖街道：zhzhzx01<br/>文晖街道：whzx01<br/>东新街道：dxzhzx01<br/>石桥街道：sqzhzx01 |

```json
{
        "BASE":{
	    "O_TITLE": "20180621-方升群/谢洁需求",
		"O_DESCRIBE": "1.请张城参与关于1call头脑风暴2.请刘佳民整理文件、邮件、报纸、杂志",
        "O_CUSTOMER":"PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP",
        "O_CUSTOMER_NAME":"方升群;谢洁",
        "O_START_TIME":"2018-06-21 12:01:28",
        "O_FINISH_TIME":"2018-06-30 24:00:00",
        "O_EXECUTOR":"8BmEYpm9kQUlNN7m;297NKDKkDzHZe2da",
        "O_EXECUTOR_NAME":"张城;刘佳民",
        "O_TYPE_ID":"39509142708158464",
        "MESSAGE_ID":"1508983949422;1508983949423;1508983949424;1508983949424",
        "GROUPID":"1100@ChatRoom",
        "CREATE_USER":"V267EnA0yEUjXZ5N",
        "CREATE_USER_NAME":"顾清清",
        },
		"ATTR": [{
			"UPLOAD_TIME": "2018-06-21 13:01:28",
			"ATTR_NAME": "陈小雄-1525325288-180503-132749.mp4",
			"ATTR_PATH": "http://172.16.9.102:8080/img/陈小雄-1525325288-180503-132749.mp4",
			"UPLOAD_USER": "PQV8oo3jeeiDkLbY",
           "UPLOAD_USER_NAME":"方升群"
		},{
			"UPLOAD_TIME": "2018-06-21 13:03:28",
			"ATTR_NAME":  "语音12",
			"ATTR_PATH": "http://172.16.9.102:8080/upload/语音12.mp3",
			"UPLOAD_USER":"XzaqamVZPDtAR3WP",
           "UPLOAD_USER_NAME":"谢洁"
		}]
		
	
}
```

#### Output

| 字段      | 字段名  | 类型     | 是否必要 | 描述及要求              |
| ------- | ---- | ------ | ---- | ------------------ |
| code    | 返回状态 | int    | 必要   | 状态值200/400         |
| message | 消息   | String | 必要   | 200创建1do成功！400错误消息 |

**Success**

```json
{
    "code": 200,
    "message": "创建1do成功！"
}
```

### 3 功能 ：普通反馈

#### 请求方法：  /1do/do/feedback

#### 参数说明：

| 字段              | 字段名                 | 类型   | 是否必填 | 描述及要求                                                   |
| ----------------- | ---------------------- | ------ | -------- | ------------------------------------------------------------ |
| loginName         | 1call字段              | String | 选填     | 人员showid（账号未登入时填写）                               |
| SHOW_ID           | 1do工单编号            | String | 必填     | 1do工单编号                                                  |
| FBCONTENT         | 反馈内容               | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE           | 反馈类型               | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7删除反馈,8删除反馈时加1条数据,9.语音反馈填,10网页链接 |
| FB_USER           | 对应用户show_id        | String | 选填     | 反馈时相应的show_id（如回复反馈回复谁的show_id）             |
| FB_USER_NAME      | 对应用户show_id名字    | String | 选填     | 反馈时相应的show_id名字（如回复馈回复谁的show_id名字）       |
| star              | 评星                   | int    | 选填     | 评价的星星数                                                 |
| AT                | @人相关信息            | String | 选填     | @人相关信息AT如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| FourPlatformsUser | （操作人）四个平台账号 | String | 选填     | （操作人）四个平台账号：如：caijy。四个平台调用必填          |
| source            | 来源                   | int    | 必填     | 本系统默认0，其他系统：1微信，2四个平台。                    |

```json
{
    "SHOW_ID": "41068020382040064",
    "FBCONTENT": "",
    "FB_TYPE": 4,
    "FB_USER":"",
    "FB_USER_NAME":""
}
```

```反馈列子
其他反馈列子：
{
    "SHOW_ID": "41068020382040064",
    "FB_TIME": "2018-06-21 12:01:28",
    "FBCONTENT": "回复谢洁保证在下个星期三之前完成",//方升群催办//方升群确认办结
    "FB_TYPE": 2,//4//5
    "FB_USER":"XzaqamVZPDtAR3WP",
    "FB_USER_NAME":"谢洁"
}
```

```
评价反馈
{
    "SHOW_ID": "41068020382040064",
    "FBCONTENT": "干的漂亮
    "FB_TYPE": 6
    "FB_USER":"",
    "FB_USER_NAME":"",
    "star":5
}
```



#### Output

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| O_USER       | 反馈人员show_id     | String | 必填     | 反馈人员show_id                                              |
| O_USER_NAME  | 反馈人员show_id名字 | String | 必填     | 反馈人员show_id名字                                          |
| FB_TIME      | 反馈时间            | String | 必填     | 反馈时间                                                     |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7,删除反馈，【8删除反馈时加1条数据】9.语音反馈 |
| FB_USER_NAME | 对应用户show_id名字 | String | 必填     | 反馈时相应的show_id名字（如回复、催办反馈 回复、催办谁的show_id名字） |
| ATTR_PATH    | 附件路径            | String | 必填     | 附件路径                                                     |
| star         | 评星                | int    | 必填     | 评价的星星。                                                 |
| AT           | @人相关信息         | String | 必填     | @人相关信息                                                  |
| ID           | 编号                | int    | 必填     | 编号                                                         |

**Success**

```json
{
    "code": 200,
    "data": {
        "FB_USER_NAME": "",
        "AT": "[]",
        "star": 0,
        "FB_TIME": "2018-11-09 16:10:02",
        "O_USER_NAME": "刘韬",
        "FB_USER": "",
        "SHOW_ID": "89868108176556032",
        "O_USER": "Np1ZK5KO3ZcXx1NJ",
        "FBCONTENT": "222",
        "FB_TYPE": 1,
        "TIME_STAMP": 1541751002956,
        "ID": 3565
    },
    "message": "Success"
}
```

### 4 功能 ：附件反馈

#### 请求方法：/1do/do/feedbackUpload

**Content-Type: multipart/form-data**

#### 参数说明：

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| FILE         | 附件                | file   | 必填     | 附件                                                         |
| SHOW_ID      | 1do工单编号         | String | 必填     | 1do工单编号                                                  |
| FB_TYPE      | 反馈类型            | int    | 必填     | 默认3，语音反馈填9                                           |
| FB_USER      | 对应用户show_id     | String | 选填     | 反馈时相应的show_id（特殊情况如回复反馈回复谁的show_id）     |
| FB_USER_NAME | 对应用户show_id名字 | String | 选填     | 反馈时相应的show_id名字（特殊情况如回复馈回复谁的show_id名字） |
| source       | 来源                | int    | 必填     | 本系统默认0，其他系统：1微信，2四个平台。                    |
| loginName    | 1call字段           | String | 选填     | 人员showid（账号未登入时填写）                               |

```html
 /1do/do/feedbackUpload HTTP/1.1
Host: 1do.hzxc.gov.cn:8443
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
Cache-Control: no-cache
man-Token: ca4cd25a-3ea8-495b-b16d-2a27d77ab5e4

------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="FILE"; filename="1.jpg"
Content-Type: image/jpeg


------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="SHOW_ID"

90537658605895680
------WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="FB_TYPE"

3
------WebKitFormBoundary7MA4YWxkTrZu0gW--
```



#### Output

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| O_USER       | 反馈人员show_id     | String | 必填     | 反馈人员show_id                                              |
| O_USER_NAME  | 反馈人员show_id名字 | String | 必填     | 反馈人员show_id名字                                          |
| FB_TIME      | 反馈时间            | String | 必填     | 反馈时间                                                     |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7,删除反馈，【8删除反馈时加1条数据】9.语音反馈 |
| FB_USER_NAME | 对应用户show_id名字 | String | 必填     | 反馈时相应的show_id名字（如回复、催办反馈 回复、催办谁的show_id名字） |
| ATTR_PATH    | 附件路径            | String | 必填     | 附件路径                                                     |
| star         | 评星                | int    | 必填     | 评价的星星。                                                 |
| AT           | @人相关信息         | String | 必填     | @人相关信息                                                  |
| ID           | 编号                | int    | 必填     | 编号                                                         |

**Success**

```json
{
    "code": 200,
    "data": {
        "FB_TIME": "2018-11-09 16:18:43",
        "O_USER_NAME": "刘韬",
        "ATTR_PATH": "https://tyhy.hzxc.gov.cn:28443/1do/upload/90610058542448640.jpg",
        "SHOW_ID": "90537658605895680",
        "O_USER": "Np1ZK5KO3ZcXx1NJ",
        "FB_TYPE": 3,
        "TIME_STAMP": 1541751523216,
        "FBCONTENT": "上传 1.jpg",
        "ID": 3568,
        "ATTRID": "372"
    },
    "message": "Success"
}
```

###  5 功能 ：1do详情

#### 请求方法：  /1do/do/getDoDetails

#### 参数说明：

| 字段      | 字段名      | 类型   | 是否必填 | 描述及要求                     |
| --------- | ----------- | ------ | -------- | ------------------------------ |
| SHOW_ID   | 1do工单编号 | String | 必填     | 1do工单编号                    |
| loginName | 1call字段   | String | 选填     | 人员showid（账号未登入时填写） |

```json
{"SHOW_ID":"41068020382040064"，"loginName":"Np1ZK5KO3ZcXx1NJ"}
```

#### Output

| 字段                    | 字段名              | 类型    | 描述及要求                                                   |
| ----------------------- | ------------------- | ------- | ------------------------------------------------------------ |
| SHOW_ID                 | 1do工单编号         | String  | 1do工单编号                                                  |
| O_TITLE                 | 标题                | String  | 标题                                                         |
| O_DESCRIBE              | 内容                | String  | 内容                                                         |
| O_CUSTOMER              | 发起人show_id       | String  | 多个发起人以“;”隔开，如：“ PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP” |
| O_CUSTOMER_NAME         | 发起人姓名          | String  | 多个发起人以“;”隔开，如："方升群;谢洁"                       |
| O_START_TIME            | 发起时间            | String  | 最早发起时间                                                 |
| O_FINISH_TIME           | 完成时间            | String  | 完成时间（多以当月月末为主）                                 |
| Real_FINISH_TIME        | 办结时间            | String  | 办结时间                                                     |
| DELETE_TIME             | 删除时间            | String  | 删除时间                                                     |
| O_CREATE_TIME           | 创建时间            | String  | 创建时间                                                     |
| O_EXECUTOR              | 参与人show_id       | String  | 多个参与人以“;”隔开，如： “8BmEYpm9kQUlNN7m; 297NKDKkDzHZe2da” |
| O_EXECUTOR_NAME         | 参与人姓名          | String  | 多个参与人以“;”隔开，如：“张城;刘佳民”                       |
| EVENT_TYPE              | 通知设置            | String  | 通知设置按1;2;3;4;5;6此格式（1.送达2.查看3.反馈4.催办5.办结6.评价7.删除 ）默认： 1;4;5;6;7 ） |
| LIGHTNING               | 催办数（闪电）      | int     | 催办数（闪电）                                               |
| O_STATUS                | 处理状态            | int     | 处理状态3.待处理4.处理中5.已完成                             |
| SONBASE                 | 子项                |         |                                                              |
| O_TITLE                 | 标题                | String  | 标题                                                         |
| POWER                   | 权限                | int     | 1整理层2领导4项目干系人3普通用户                             |
| ATTR                    | 附件                |         |                                                              |
| ATTR_NAME               | 附件名              | String  | 附件名                                                       |
| ATTR_PATH               | 附件路径            | String  | 附件路径                                                     |
| ID                      | id                  | int     | id（排序作用）                                               |
| AT                      | 内容中@相关人员信息 | String  | 内容中@相关人员信息如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| O_LABEL                 | 标签集合            | list    | 标签集合                                                     |
| LABEL                   | 标签                | String  | 标签                                                         |
| ID                      | ID                  | string  | 删除的时候用到。                                             |
| ccp                     | 抄送人              | list    | 抄送人                                                       |
| executor                | 处理人              | list    | 处理人                                                       |
| CUSTOMER_LIST           | 发起人集合          | list    | 发起人集合                                                   |
| EXECUTOR_LIST           | 参与人集合          | list    | 参与人集合                                                   |
| POWER                   | 权限                | int     | 1整理层2领导3普通人4项目干系人                               |
|                         |                     |         |                                                              |
| ITEM                    | 所属项目            | list    | 所属项目                                                     |
| children                |                     | List    | 子节点                                                       |
| parent_id               |                     | long    | 父节点id                                                     |
| item_name               |                     | stirng  | 节点名称                                                     |
| type                    |                     | int     | 节点类型2子节点3项目节点                                     |
| id                      |                     | long    | 节点id                                                       |
| IS_BINDING_WECHAT_GROUP | 是否绑定微信群      | boolean | 是true否false                                                |
| WECHAT_GROUP_NAME       | 微信群名            | String  | 微信群名                                                     |
| WECHAT_GROUP_ID         | 微信群id            | String  | 微信群id（解绑的时候用）                                     |

Success**

```json
{
    "code": 200,
    "data": {
        "O_FINISH_TIME": "2019-11-11 00:00:00",
        "O_START_TIME": "2019-01-01 00:00:00",
        "O_IS_DELETED": 2,
        "IS_BINDING_WECHAT_GROUP": false,
        "LOOKNUM": 54,
        "WECHAT_GROUP_NAME": null,
        "O_CREATE_TIME": "2018-07-18 09:54:24",
        "WECHAT_GROUP_ID": null,
        "Real_FINISH_TIME": "2018-10-26 10:39:17",
        "ccp": [
            {
                "O_USER_NAME": "骆姜斌",
                "O_USER": "3ZzmorkJ90h0RGWq"
            },
            {
                "O_USER_NAME": "于璟明",
                "O_USER": "l8BpE16JnNUL8Lqp"
            },
            {
                "O_USER_NAME": "沈力伟",
                "O_USER": "LnYroXo7ObfPLkL0"
            }
        ],
        "O_LABEL": [],
        "LOOK_USER": "3LBYkvlD7rHmLn72,3ZzmorkJ90h0RGWq,509xdxzlDDFZRn0K,6ZYbRyY7mDivQ5e7,Bbx8m1GWWJcG6XAO,D2JydYO9qqiaDlqj,l8BpE16JnNUL8Lqp,LnYroXo7ObfPLkL0,NO6lZyJjYRCAKd9R,ppXAJP0PdehB0z93,WeKJDWROnaHQn6Yq,xK6YmWLlbVSYJpYG,PQV8oo3jeeiDkLbY,Np1ZK5KO3ZcXx1NJ,lQDNVWVQZ2HnpYoq",
        "executor": [
            {
                "O_USER_NAME": "李欣欣",
                "O_USER": "Bbx8m1GWWJcG6XAO"
            }
        ],
        "O_CUSTOMER": "ppXAJP0PdehB0z93",
        "SHOW_ID": "49201126158893056",
        "DELETE_TIME": "2020-10-30 14:21:34",
        "ID": 17,
        "O_DESCRIBE": "反馈评论1",
        "O_CUSTOMER_NAME": "杨蔷",
        "O_TITLE": "1.需求：综合客服和业务客服在操作时，用户端是否可显示“对方",
        "CUSTOMER_LIST": [
            {
                "O_USER_NAME": "杨蔷",
                "O_USER": "ppXAJP0PdehB0z93"
            }
        ],
        "POWER": 3,
        "O_STATUS": 6,
        "O_TYPE_ID": "39509142708158464",
        "O_EXECUTOR_NAME": "李欣欣;于璟明;沈力伟;骆姜斌",
        "ITEM": [],
        "AT": "[]",
        "O_EXECUTOR": "Bbx8m1GWWJcG6XAO;l8BpE16JnNUL8Lqp;LnYroXo7ObfPLkL0;3ZzmorkJ90h0RGWq",
        "SOURCE": 1,
        "EXECUTOR_LIST": [
            {
                "O_USER_NAME": "李欣欣",
                "O_USER": "Bbx8m1GWWJcG6XAO"
            },
            {
                "O_USER_NAME": "骆姜斌",
                "O_USER": "3ZzmorkJ90h0RGWq"
            },
            {
                "O_USER_NAME": "沈力伟",
                "O_USER": "LnYroXo7ObfPLkL0"
            },
            {
                "O_USER_NAME": "于璟明",
                "O_USER": "l8BpE16JnNUL8Lqp"
            }
        ]
    },
    "message": "Success"
}
```

### 6 功能 ：获得反馈消息

#### 请求方法：  /1do/do/getDoFeedback

#### 参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求                                                   |
| ------- | ------ | -------- | ------------------------------------------------------------ |
| SHOW_ID | String | 必填     | 1do工单编号                                                  |
| num     | int    | 必填     | 查询条数，比如10，查询十条                                   |
| id      | int    | 必填     | 查询起始数，比如id:0,num:10 ,//检索记录行1-10 .比如id:5,num:10  //检索记录行6-15 |
| type    | int    | 必填     | 类型，全部填1，评论填2，附件填3                              |

```json
{"SHOW_ID":"41068020382040064","num":10,"id":0,"type":1}
```

#### Output

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| O_USER       | 反馈人员show_id     | String | 必填     | 反馈人员show_id                                              |
| O_USER_NAME  | 反馈人员show_id名字 | String | 必填     | 反馈人员show_id名字                                          |
| FB_TIME      | 反馈时间            | String | 必填     | 反馈时间                                                     |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7,删除反馈，【8删除反馈时加1条数据】9.语音反馈 |
| FB_USER_NAME | 对应用户show_id名字 | String | 必填     | 反馈时相应的show_id名字（如回复、催办反馈 回复、催办谁的show_id名字） |
| ATTR_PATH    | 附件路径            | String | 必填     | 附件路径                                                     |
| star         | 评星                | int    | 必填     | 评价的星星。                                                 |
| AT           | @人相关信息         | String | 必填     | @人相关信息如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| ID           | 编号                | int    | 必填     | 编号                                                         |

**Success**

```json
{
    "code": 200,
    "data":[
    {
        "FB_USER_NAME": "",
        "O_USER_NAME": "方升群",
        "FB_TIME": "2018-07-20 23:33:40",
        "FB_USER": "",
        "ATTR_PATH": null,
        "O_USER": "PQV8oo3jeeiDkLbY",
        "ID": 193,
        "FBCONTENT": "测试轮询",
        "FB_TYPE": 1
    },
    {
        "FB_USER_NAME": null,
        "O_USER_NAME": "方升群",
        "FB_TIME": "2018-07-26 11:37:58",
        "FB_USER": null,
        "ATTR_PATH": null,
        "O_USER": "PQV8oo3jeeiDkLbY",
        "ID": 206,
        "FBCONTENT": "上传 省交换项目接口文档.docx ",
        "FB_TYPE": 3
    },
    {
        "FB_USER_NAME": null,
        "O_USER_NAME": "蒋娅楠",
        "FB_TIME": "2018-07-26 13:58:15",
        "FB_USER": null,
        "ATTR_PATH": "bundle.js ",
        "O_USER": "3LBYkvlD7rHmLn72",
        "ID": 207,
        "FBCONTENT": "上传 bundle.js ",
        "FB_TYPE": 3
    },
    {
        "FB_USER_NAME": null,
        "O_USER_NAME": "蒋娅楠",
        "FB_TIME": "2018-07-26 14:07:45",
        "FB_USER": null,
        "ATTR_PATH": "/1do/upload/52163984484204544.js ",
        "O_USER": "3LBYkvlD7rHmLn72",
        "ID": 208,
        "FBCONTENT": "上传 bundle.js ",
        "FB_TYPE": 3
    },
]    "message": "Success"}
```

### 8 功能 ：获得操作日志

#### 请求方法：  /1do/do/getDoLog

#### 参数说明：

| 字段    | 字段名      | 类型   | 是否必填 | 描述及要求  |
| ------- | ----------- | ------ | -------- | ----------- |
| SHOW_ID | 1do工单编号 | String | 必填     | 1do工单编号 |

```json
{"SHOW_ID":"41068020382040064"}
```

#### Output

| 字段        | 字段名       | 类型   | 是否必填 | 描述及要求                                                   |
| ----------- | ------------ | ------ | -------- | ------------------------------------------------------------ |
| log_type    | 操作类型     | String | 必填     | 操作类型日志状态（1.创建2.查看3.上传4.催办5.确认办结6.评价7.拆项8.移除人9.邀请.10.反馈11.删除反馈,12.删除1do,13.恢复1do,14.修改15.重做1do16.新建子1do17.下载） |
| O_USER_NAME | 操作人员名字 | String | 必填     | 操作人员名字                                                 |
| op_time     | 操作时间     | String | 必填     | 操作时间                                                     |
| log         | 日志内容     | String | 必填     | 日志内容                                                     |
| content     | 其他参数     | String | 必填     | 其他参数（如附件名称，移除或邀请人员名称）log_type为14时：before_modification表示内容修改前，after_modification表示修改后。 |

**Success**

```json
{
    "code": 200,
    "data": [
        {
            "log_type": 1,
            "log": "蒋娅楠帮助杨蔷创建了此1do",
            "O_USER_NAME": "蒋娅楠",
            "op_time": "2018-07-18 09:54:24",
            "content": null
        },
        ...
        {
            "log_type": 6,
            "log": "杨蔷评价了此1do",
            "O_USER_NAME": "杨蔷",
            "op_time": "2020-10-30 14:34:54",
            "content": null
        }
    ],
    "message": "Success"
}
```

### 10 功能 ：新建1do保存

#### 请求方法：  /1do/do/saveIdo

#### Content-Type: multipart/form-data

#### 参数说明：

| 字段                | 字段名                     | 类型    | 是否必填 | 描述及要求                                                   |
| ------------------- | -------------------------- | ------- | -------- | ------------------------------------------------------------ |
| FILE                | 附件                       | file    | 选填     | 附件                                                         |
| O_TITLE             | 标题                       | String  | 选填     | 如：“20180621-方升群/谢洁需求”，（来源城管执法的话，填城管执法） |
| O_DESCRIBE          | 内容                       | String  | 必填     | 内容（来源城管执法的话，这个字段是大json格式数据）           |
| O_CUSTOMER          | 发起人show_id              | String  | 必填     | 多个发起人以“;”隔开，如： “PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP” |
| O_CUSTOMER_NAME     | 发起人姓名                 | String  | 必填     | 多个发起人以“;”隔开，如："方升群;谢洁"                       |
| O_START_TIME        | 发起时间                   | String  | 选填     | 拟开始时间                                                   |
| O_FINISH_TIME       | 完成时间                   | String  | 选填     | 拟完成时间                                                   |
| O_EXECUTOR          | 参与人show_id              | String  | 选填     | 多个参与人以“;”隔开，如： “8BmEYpm9kQUlNN7m; 297NKDKkDzHZe2da” |
| O_EXECUTOR_NAME     | 参与人姓名                 | String  | 选填     | 多个参与人以“;”隔开，如：“张城;刘佳民”                       |
| O_TYPE_ID           | 工单分类（1do分类）        | String  | 必填     | 目前默认：39509142708158464；；来源为1call办的填（1、城管执法，2、民政，3、环卫，4、住建，5、人社）后续增加另行通知，来源为综合指挥平台，1、zdfw-主动服务，2、yj-预警，3、1do-转1do 4、 yjzh-一键指挥。 来源为10督查指挥（1、做地出让,2、民生实事,3、基础建设,4、地铁建设,5、区国企自营项目,6、地产项目,7、绿化建设,8、规划编制,9、生态治理） |
| EVENT_TYPE          | 通知设置                   | String  | 选填     | 通知设置按1;2;3;4;5;6此格式（1.送达2.查看3.反馈4.催办5.办结6.评价7.删除 ）默认： 1;4;5;6;7 ） |
| ccp                 | 整理层根据参与人设置抄送人 | String  | 选填     | ZD1ybAYvAou36nzJ;XzaqamVZPDtAR3WP                            |
| executor            | 整理层根据参与人设置处理人 | String  | 选填     | WeKJDWROnaHQn6Yq;Np1ZK5KO3ZcXx1NJ                            |
| AT                  | 内容中@相关人员信息        | String  | 选填     | 内容中@相关人员信息如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| PARENT_ID           | 父工单showid               | String  | 选填     | 新建子1do时填写,默认0                                        |
| SOURCE              | 来源                       | int     | 必填     | 来源 1、call 或者oa 2、主动办 3、三实库 4、其他 5、领导批示6.城市大脑,7综合信息系统，8、1call办,9，指挥信息平台，10督查指挥 |
| ITEM_ID             | 所属项目节点               | Long    | 选填     | 填最后一个节点的id（参考71接口节点返回值）                   |
| COMMAND_PLATFORM_ID | 指挥平台id                 | String  | 选题     | 指挥平台新建1do填写指挥平台的id                              |
| module              | 指挥平台接口module         | String  | 选题     | 指挥平台接口module参数                                       |
| STREET              | 街道                       | String  | 选填     | 指挥平台新建1do填写街道（如qz01）：                               下城区：qzx01br/天水街道：tszx01br/武林街道：wlzhzx01br/长庆街道：cqzhzx01br/潮鸣街道：cmzhzx01br/朝晖街道：zhzhzx01br/文晖街道：whzx01br/东新街道：dxzhzx01br/石桥街道：sqzhzx01 |
| IS_YSCG             | 是否是云上城管             | boolean | 选填     | 是否是云上城管                                               |
| loginName           | 1call字段                  | String  | 选填     | 人员showid（账号未登入时填写）                               |
|                     |                            |         |          |                                                              |



```text

var form = new FormData();
form.append("FILE", "");
form.append("O_DESCRIBE", "新建子1do啊啊啊啊啊啊啊啊");
form.append("O_CUSTOMER", "Np1ZK5KO3ZcXx1NJ");
form.append("O_CUSTOMER_NAME", "刘韬");
form.append("O_START_TIME", "2018-06-21 12:01:28");
form.append("O_FINISH_TIME", "2018-06-30 24:00:00");
form.append("O_EXECUTOR", "XzaqamVZPDtAR3WP;297NKDKkDzHZe2da;WeKJDWROnaHQn6Yq");
form.append("O_EXECUTOR_NAME", "谢洁;刘佳民;王帅帅");
form.append("AT", "");
form.append("PARENT_ID", "152933982655217664");

var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:8080/1do/do/saveIdo",
  "method": "",
  "headers": {
    "Content-Type": "application/json",
    "Cache-Control": "no-cache",
    "man-Token": "075f0b7d-8901-4f5f-b445-e6ce45a316fa"
  },
  "processData": false,
  "contentType": false,
  "mimeType": "multipart/form-data",
  "data": form
}

$.ajax(settings).done(function (response) {
  console.log(response);
});
		

```

```text
       数据实例2
	    "O_TITLE": "20180703-张城/刘佳民需求",
		"O_DESCRIBE": "1.请方升群参与关于1call头脑风暴2.请谢洁整理文件、邮件、报纸、杂志",
        "O_CUSTOMER":"8BmEYpm9kQUlNN7m;297NKDKkDzHZe2da",
        "O_CUSTOMER_NAME":"张城;刘佳民",
        "O_START_TIME":"2018-06-21 12:01:28",
        "O_FINISH_TIME":"2018-06-30 24:00:00",
        "O_EXECUTOR":"PQV8oo3jeeiDkLbY;XzaqamVZPDtAR3WP",
        "O_EXECUTOR_NAME":"方升群;谢洁",
        "O_TYPE_ID":"39509142708158464",
        "EVENT_TYPE":"1;2;3;4;5;6",
        "ccp":"D2JydYO9qqiaDlqj,LnYroXo7ObfPLkL0",
         "executor":"WeKJDWROnaHQn6Yq,Np1ZK5KO3ZcXx1NJ"
        
```



#### Output

| 字段    | 字段名     | 类型   | 描述及要求    |
| ------- | ---------- | ------ | ------------- |
| code    | 返回状态   | int    | 状态值200/400 |
| message | 消息       | String | Success       |
| data    | 工单showid | String | 工单showid    |

**Success**

```json
{
    "code": 200,
    "data": "198262540147359744",
    "message": "Success"
}
```



### 12 功能 ：通讯录获取最近联系人

#### 请求方法： /1do/do/GetContact

#### **Output**

| 字段          | 字段名     | 类型     | 是否必填 | 描述及要求   |
| ----------- | ------- | ------ | ---- | ------- |
| SHOW_ID     | 用户id    | String | 必填   | 用户id    |
| U_TRUE_NAME | 名字      | String | 必填   | 名字      |
| loginName   | show_id | String | 必填   | show_id |

**Success**

```
{
    "Header": {
        "IsSuccess": true,
        "ResCode": 8200,
        "Reason": ""
    },
    "Body": {
        "response": {
            "Data": [
                {
                    "SHOW_ID": "Ne8V8J8b7ASA2DBN",
                    "U_TRUE_NAME": "陈清清",
                    "loginName": "chenqingqing",
                    "type": "user"
                },
                {
                    "SHOW_ID": "Jj8z6v7rBjTQQqYk",
                    "U_TRUE_NAME": "信息中心客服",
                    "loginName": "xxzxkf01",
                    "type": "user"
                },
                {
                    "SHOW_ID": "XzaqamVZPDtAR3WP",
                    "U_TRUE_NAME": "谢洁",
                    "loginName": "XzaqamVZPDtAR3WP",
                    "type": "user"
                },
                {
                    "SHOW_ID": "PQV8oo3jeeiDkLbY",
                    "U_TRUE_NAME": "方升群",
                    "loginName": "PQV8oo3jeeiDkLbY",
                    "type": "user"
                },
                {
                    "SHOW_ID": "ZD1ybAYvAou36nzJ",
                    "U_TRUE_NAME": "阎秋霞",
                    "loginName": "yanqiuxia",
                    "type": "user"
                },
                {
                    "SHOW_ID": "v65VKKvaaZT0dZAJ",
                    "U_TRUE_NAME": "陆芸",
                    "loginName": "luyun1",
                    "type": "user"
                },
                {
                    "SHOW_ID": "6ZYbRyY7mDivQ5e7",
                    "U_TRUE_NAME": "焦俊",
                    "loginName": "jiaojun",
                    "type": "user"
                },
                {
                    "SHOW_ID": "3L2oXNG1YZfX3BB3",
                    "U_TRUE_NAME": "喻杰",
                    "loginName": "yujie2",
                    "type": "user"
                },
                {
                    "SHOW_ID": "3djXOEk7D1u0Jdnv",
                    "U_TRUE_NAME": "洪剑锋",
                    "loginName": "hongjianfeng",
                    "type": "user"
                },
                {
                    "SHOW_ID": "avE8bm0K1LFDYnVl",
                    "U_TRUE_NAME": "徐杰",
                    "loginName": "xujie2",
                    "type": "user"
                },
                {
                    "SHOW_ID": "rJ07DXOYeGTdePYn",
                    "U_TRUE_NAME": "李宥彤",
                    "loginName": "liyoutong",
                    "type": "user"
                }
            ],
            "TotalCount": 11,
            "IsSuccess": true,
            "Code": 8200,
            "Reason": ""
        }
    }
}
```

### 13 功能 ：通讯录获取用户信息

#### 请求方法：/1do/do/CompanyUser

#### 参数说明

| 字段      | 字段名  | 类型     | 是否必填 | 描述及要求 |
| ------- | ---- | ------ | ---- | ----- |
| SHOW_ID | 用户id | String | 必填   | 用户id  |

```json
{"SHOW_ID":"rJ07DXOYeGTdePYn"}
```



#### **Output**

| SHOW_ID     | 用户id    | String | 必填   | 用户id    |
| ----------- | ------- | ------ | ---- | ------- |
| U_TRUE_NAME | 名字      | String | 必填   | 名字      |
| loginName   | show_id | String | 必填   | show_id |
| 字段          | 字段名     | 类型     | 是否必填 | 描述及要求   |

**Success**

```
{
    "Header": {
        "IsSuccess": true,
        "ResCode": 8200,
        "Reason": ""
    },
    "Body": {
        "response": {
            "Data": {
                "SHOW_ID": "rJ07DXOYeGTdePYn",
                "LAUNCHR_ID": 53344634,
                "U_NAME": "rJ07DXOYeGTdePYn",
                "U_TRUE_NAME": "李宥彤",
                "U_TRUE_NAME_C": "L",
                "U_HIRA": "",
                "U_MAIL": "liyoutong@hzxc.gov.cn",
                "U_STATUS": 1,
                "U_MOBILE": "13958146992",
                "U_JOB": "部门经理",
                "U_NUMBER": "",
                "U_SORT": 0,
                "lastUpdateTime": 1530701782000,
                "LAST_LOGIN_TIME": 1527152788000,
                "LAST_LOGIN_TOKEN": "VuLTxlarXWtpVhvC0+/H3nc1eHI=",
                "IS_ADMIN": 0,
                "C_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                "CREATE_USER": "system",
                "CREATE_TIME": 1510887745000,
                "CREATE_USER_NAME": "system",
                "D_NAME": [
                    "政务数据组"
                ],
                "U_DEPT_ID": [
                    "C00338"
                ],
                "D_PARENTID_SHOW_ID": [
                    "D00257"
                ],
                "D_PATH_NAME": [
                    "政府办●信息中心●政务数据组"
                ],
                "U_TELEPHONE": "13958146992",
                "loginName": "liyoutong",
                "extendField": [
                    {
                        "fieldId": "YkBQBeOqpOIJaBWz",
                        "fieldName": "备用邮箱",
                        "fieldValue": ""
                    },
                    {
                        "fieldId": "o9DJz1DK5et8oOb6",
                        "fieldName": "旧登录名",
                        "fieldValue": ""
                    },
                    {
                        "fieldId": "e9KdZaaJy6sBKb1P",
                        "fieldName": "虚拟号",
                        "fieldValue": ""
                    },
                    {
                        "fieldId": "pX7oe8kpRjSqKlKr",
                        "fieldName": "用户状态",
                        "fieldValue": "临时帐号"
                    }
                ],
                "address": null
            },
            "TotalCount": 1,
            "IsSuccess": true,
            "Code": 8200,
            "Reason": ""
        }
    }
}
```

###  14 功能 ：通讯录获取部门列表

#### 请求方法： /1do/do/GetList

#### Output

| 字段                       | 字段名  | 类型     | 是否必填 | 描述及要求                                    |
| ------------------------ | ---- | ------ | ---- | ---------------------------------------- |
| parentId                 | id   | String | 必填   | 获取根部门使用 companyShowID，获取子部门使用当前部门SHOW_ID |
| isContainChildDeptMember | 是否包含 | int    | 必填   | 0不包含、1包含                                 |
|                          |      |        |      |                                          |

**部分返回值**

```
                    "SHOW_ID": "NO6lZyJjYRCAKd9R",
                    "LAUNCHR_ID": 3837058,
                    "U_NAME": "NO6lZyJjYRCAKd9R",
                    "U_TRUE_NAME": "徐步云",
                    "U_TRUE_NAME_C": "X",
                    "U_HIRA": "",
                    "U_MAIL": "boydxu@mintcode.com",
                    "U_STATUS": 1,
                    "U_MOBILE": "",
                    "U_JOB": "IM服务器开发",
                    "U_NUMBER": "",
                    "U_SORT": 0,
                    "U_DEPT_SORT": 0,
                    "LAST_LOGIN_TIME": 1530701333000,
                    "LAST_LOGIN_TOKEN": "Xkr2kvNXQNxpXCb++JnSEbW+xX8=",
                    "IS_ADMIN": 1,
                    "C_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "CREATE_USER": "mLR9mBydz0i5qaVG",
                    "CREATE_TIME": 1461116569000,
                    "CREATE_USER_NAME": "吕阳君",
                    "D_NAME": "政府办信息中心",
                    "U_DEPT_ID": "OLqLNBB9G5fqpz7L123",
                    "D_PARENTID_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "D_PATH_NAME": "政府办信息中心",
                    "U_TELEPHONE": "0588408988",
                    "loginName": "18868808234",
```

### 15 功能 ：通讯录获取部门列表人员

#### 请求方法： /1do/do/GetListUser

#### **参数说明**

| 字段名                      | 数据类型   | 是否必填 | 描述          |
| ------------------------ | ------ | ---- | ----------- |
| deptId                   | String | 必填   | 当前部门SHOW_ID |
| isContainChildDeptMember | int    | 必填   | 0不包含、1包含    |

```json
{"deptId":"b5WJZ1bRLDCb7x2B","isContainChildDeptMember":0}
```

#### **Output**

| SHOW_ID     | 用户id    | String | 必填   | 用户id    |
| ----------- | ------- | ------ | ---- | ------- |
| U_TRUE_NAME | 名字      | String | 必填   | 名字      |
| loginName   | show_id | String | 必填   | show_id |
| 字段          | 字段名     | 类型     | 是否必填 | 描述及要求   |

**部分返回值**

```
                  "SHOW_ID": "NO6lZyJjYRCAKd9R",
                    "LAUNCHR_ID": 3837058,
                    "U_NAME": "NO6lZyJjYRCAKd9R",
                    "U_TRUE_NAME": "徐步云",
                    "U_TRUE_NAME_C": "X",
                    "U_HIRA": "",
                    "U_MAIL": "boydxu@mintcode.com",
                    "U_STATUS": 1,
                    "U_MOBILE": "",
                    "U_JOB": "IM服务器开发",
                    "U_NUMBER": "",
                    "U_SORT": 0,
                    "U_DEPT_SORT": 0,
                    "LAST_LOGIN_TIME": 1530702950000,
                    "LAST_LOGIN_TOKEN": "X8/YmZDxhk0BEM+mgopZkuUpPns=",
                    "IS_ADMIN": 1,
                    "C_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "CREATE_USER": "mLR9mBydz0i5qaVG",
                    "CREATE_TIME": 1461116569000,
                    "CREATE_USER_NAME": "吕阳君",
                    "D_NAME": "政府办信息中心",
                    "U_DEPT_ID": "OLqLNBB9G5fqpz7L123",
                    "D_PARENTID_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "D_PATH_NAME": "政府办信息中心",
                    "U_TELEPHONE": "0588408988",
                    "loginName": "18868808234",


```

### 16 功能 ：1do详情修改发起人/参与人

#### 请求方法：  /1do/do/changeUser

#### 参数说明：

| 字段        | 字段名      | 类型   | 是否必填 | 描述及要求                                                   |
| ----------- | ----------- | ------ | -------- | ------------------------------------------------------------ |
| SHOW_ID     | 1do工单编号 | String | 必填     | 1do工单编号                                                  |
| method      | 方法        | String | 必填     | 删除人填：remove；增加人填：add   覆盖：cover                |
| object      | 对象        | String | 必填     | 修改什么填什么：发起人/参与人                                |
| username    | 姓名        | String | 必填     | 姓名（add时多人以;隔开如：张开代;方升群）                    |
| useraccount | show_id     | String | 必填     | show_id（add时多人以;隔开如D2JydYO9qqiaDlqj;PQV8oo3jeeiDkLbY） |

```json
{"SHOW_ID":"43845041277370368","object":"发起人","method":"add","username":"张开代","useraccount":"D2JydYO9qqiaDlqj"}
```

#### Output

| 字段      | 字段名  | 类型     | 是否必填 | 描述及要求                   |
| ------- | ---- | ------ | ---- | ----------------------- |
| code    | 返回状态 | int    | 必要   | 状态值200/400/202          |
| message | 消息   | String | 必要   | 200修改成功,400错误消息,202权限不足 |

**Success**

```json
{
    "code": 200,
    "message": "修改成功"
}
```

### 17 功能 ：通讯录搜索用户

#### 请求方法：/1do/do/searchUser

#### 参数说明

| 字段                     | 字段名         | 类型   | 是否必填 | 描述及要求     |
| ------------------------ | -------------- | ------ | -------- | -------------- |
| isContainChildDeptMember | 0不包含、1包含 | int    | 必填     | 0不包含、1包含 |
| createPage               | 开始页         | int    | 必填     | 开始页         |
| pageSize                 | 每页条数       | int    | 必填     | 每页条数       |
| searchKey                | 关键字         | String | 必填     | 关键字         |

```json
{"createPage":1,"isContainChildDeptMember":0,"pageSize":30,"searchKey":"12"}
```



#### Output

| 字段        | 字段名  | 类型   | 是否必填 | 描述及要求 |
| ----------- | ------- | ------ | -------- | ---------- |
| SHOW_ID     | 用户id  | String | 必填     | 用户id     |
| U_TRUE_NAME | 名字    | String | 必填     | 名字       |
| loginName   | show_id | String | 必填     | show_id    |

**Success（部分返回值）**

```
{
    "Header": {
        "IsSuccess": true,
        "ResCode": 8200,
        "Reason": ""
    },
    "Body": {
        "response": {
            "Data": [
                {
                    "SHOW_ID": "NO6lZyJjYRCAKd9R",
                    "LAUNCHR_ID": 3837058,
                    "U_NAME": "NO6lZyJjYRCAKd9R",
                    "U_TRUE_NAME": "徐步云",
                    "U_TRUE_NAME_C": "X",
                    "U_HIRA": "",
                    "U_MAIL": "boydxu@mintcode.com",
                    "U_STATUS": 1,
                    "U_MOBILE": "",
                    "U_JOB": "IM服务器开发",
                    "U_NUMBER": "",
                    "U_SORT": 0,
                    "U_DEPT_SORT": 0,
                    "LAST_LOGIN_TIME": 1531123012000,
                    "LAST_LOGIN_TOKEN": "/yF0knGaquH0i0r1bPB+zlqDQEQ=",
                    "IS_ADMIN": 1,
                    "C_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "CREATE_USER": "mLR9mBydz0i5qaVG",
                    "CREATE_TIME": 1461116569000,
                    "CREATE_USER_NAME": "吕阳君",
                    "D_NAME": "政府办信息中心",
                    "U_DEPT_ID": "OLqLNBB9G5fqpz7L123",
                    "D_PARENTID_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "D_PATH_NAME": "政府办信息中心",
                    "U_TELEPHONE": "0588408988",
                    "loginName": "18868808234",
                    "extendField": [
                        {
                            "fieldId": "o9DJz1DK5et8oOb6",
                            "fieldName": "旧登录名",
                            "fieldValue": null
                        },
                        {
                            "fieldId": "YkBQBeOqpOIJaBWz",
                            "fieldName": "备用邮箱",
                            "fieldValue": null
                        },
                        {
                            "fieldId": "pX7oe8kpRjSqKlKr",
                            "fieldName": "用户状态",
                            "fieldValue": null
                        },
                        {
                            "fieldId": "e9KdZaaJy6sBKb1P",
                            "fieldName": "虚拟号",
                            "fieldValue": null
                        }
                    ],
                    "deptNames": [
                        "政府办信息中心"
                    ]
                },
                {
                    "SHOW_ID": "rBmNbanynbs9Wklp",
                    "LAUNCHR_ID": 56246538,
                    "U_NAME": "rBmNbanynbs9Wklp",
                    "U_TRUE_NAME": "陈斌",
                    "U_TRUE_NAME_C": "C",
                    "U_HIRA": "",
                    "U_MAIL": "chenbin@hzxc.gov.cn",
                    "U_STATUS": 1,
                    "U_MOBILE": "13588822099",
                    "U_JOB": "第六纪检组副主任科员",
                    "U_NUMBER": "",
                    "U_SORT": 0,
                    "U_DEPT_SORT": 0,
                    "LAST_LOGIN_TIME": 1530866454000,
                    "LAST_LOGIN_TOKEN": "0xWgNFg3yLLrIIAV9/zn583zusg=",
                    "IS_ADMIN": 0,
                    "C_SHOW_ID": "b5WJZ1bRLDCb7x2B",
                    "CREATE_USER": "system",
                    "CREATE_TIME": 1460357564000,
                    "CREATE_USER_NAME": "system",
                    "D_NAME": "第六纪检组",
                    "U_DEPT_ID": "D00981",
                    "D_PARENTID_SHOW_ID": "C00152",
                    "D_PATH_NAME": "区纪委（区监察委员会）●第六纪检组",
                    "U_TELEPHONE": "13588822099",
                    "loginName": "chenbin",
                    "extendField": [
                        {
                            "fieldId": "o9DJz1DK5et8oOb6",
                            "fieldName": "旧登录名",
                            "fieldValue": "xzzfj-cb"
                        },
                        {
                            "fieldId": "YkBQBeOqpOIJaBWz",
                            "fieldName": "备用邮箱",
                            "fieldValue": ""
                        },
                        {
                            "fieldId": "pX7oe8kpRjSqKlKr",
                            "fieldName": "用户状态",
                            "fieldValue": "机关编制"
                        },
                        {
                            "fieldId": "e9KdZaaJy6sBKb1P",
                            "fieldName": "虚拟号",
                            "fieldValue": ""
                        }
                    ],
                    "deptNames": [
                        "区纪委（区监察委员会）●第六纪检组",
                        "城管局●第六纪检组"
                    ]
                },
```



### 18 功能 ：1do详情修改标题或者内容

#### 请求方法：  /1do/do/changeText

#### 参数说明：

| 字段    | 字段名      | 类型   | 是否必填 | 描述及要求                                                   |
| ------- | ----------- | ------ | -------- | ------------------------------------------------------------ |
| SHOW_ID | 1do工单编号 | String | 必填     | 1do工单编号                                                  |
| target  | 目标        | String | 必填     | 修改标题填：O_TITLE修改内容填：O_DESCRIBE                    |
| content | 内容        | String | 必填     | 修改后内容                                                   |
| AT      | @人相关信息 | String | 选填     | @人相关信息AT如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |

```json
{"target":"O_DESCRIBE","content":"1.测试22","SHOW_ID":"49385403928543232"}
```

#### Output

| 字段      | 字段名  | 类型     | 是否必填 | 描述及要求           |
| ------- | ---- | ------ | ---- | --------------- |
| code    | 返回状态 | int    | 必要   | 状态值200/400      |
| message | 消息   | String | 必要   | 200修改成功,400错误消息 |

**Success**

```json
{
    "code": 200,
    "message": "修改成功"
}
```

### 22 功能 ：删除反馈

#### 请求方法：  /1do/do/deleteFeedback

#### 参数说明：

| 字段   | 字段名  | 类型   | 是否必填 | 描述及要求 |
| ---- | ---- | ---- | ---- | ----- |
| ID   | 反馈id | int  | 必填   | 反馈    |

```json
{"ID":1091}
```

#### Output

| 字段      | 字段名  | 类型     | 是否必填 | 描述及要求                        |
| ------- | ---- | ------ | ---- | ---------------------------- |
| code    | 返回状态 | int    | 必要   | 状态值200/201/400               |
| message | 消息   | String | 必要   | 200删除反馈成功,201删除反馈不成功,400错误消息 |

**Success**

```json
{
    "code": 201,
    "message": "删除反馈不成功"
}
```

### 24 功能 ：webscoket（1do详情反馈聊天）

#### 连接地址：测试：ws://59.202.68.43:8080/1do/websocket

####                             正式：wss://1do.hzxc.gov.cn:8443/1do/websocket

#### 连接成功发送工单SHOW_ID到后台。有新的反馈会返回，返回值参数参考接口6



### 28 功能 ：点击附件留痕

#### 请求方法：/1do/do/clickOnTheAttachment

#### 参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求       |
| :------ | :----- | :------- | :--------------- |
| ID      | int    | 必填     | 反馈消息ID(附件) |
| SHOW_ID | String | 必填     | 1do工单编号      |

```
{
    "SHOW_ID": "62320744423489536",
    "ID": 670
}
```

**Success**

```
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```

### 33 功能:反馈消息转日志

#### 请求方法：  /1do/record/transferLog

#### 参数说明：

| 字段名 | 字段   | 数据类型 | 是否必填 | 描述及要求 |
| ------ | ------ | -------- | -------- | ---------- |
| id     | 消息id | int      | 必填     | 消息id     |

```
{
    "id":"10358"
}
```

##### Output

****

```
{
    "code": 200,
    "message": "转日志成功"
}
```



### 38 功能 ：上传附件

#### 请求方法：  /1do/file/uploadFile

#### 参数说明：form-data

| 字段 | 字段名 | 类型 | 是否必填 |
| ---- | ------ | ---- | -------- |
| file | 附件   | file | 是       |

POST /1do/file/uploadFile HTTP/1.1
Host: 1do.hzxc.gov.cn:8443
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/39805/Pictures/1533141709-mgKwcyIMei.jpeg"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/39805/Pictures/20141223223757_SRi3i.jpeg"
Content-Type: image/jpeg

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW

Success

```json
{
    "code": 200,
    "data": [
        "https://1do.hzxc.gov.cn:8443/1do/upload/297174044304736256.jpeg",
        "https://1do.hzxc.gov.cn:8443/1do/upload/297174044371845120.jpeg"
    ],
    "message": "Success"
}
```




