### 1功能 ：1do登入（1cll调1do设置session）

#### 请求方法：  /1do/do/login

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



### 3 功能 ：普通反馈

#### 请求方法：  /1do/do/feedback

#### 参数说明：

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| SHOW_ID      | 1do工单编号         | String | 必填     | 1do工单编号                                                  |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7删除反馈,8删除反馈时加1条数据,9.语音反馈填,10网页链接 |
| FB_USER      | 对应用户show_id     | String | 选填     | 反馈时相应的show_id（如回复反馈回复谁的show_id）             |
| FB_USER_NAME | 对应用户show_id名字 | String | 选填     | 反馈时相应的show_id名字（如回复馈回复谁的show_id名字）       |
| star         | 评星                | int    | 选填     | 评价的星星数                                                 |
| AT           | @人相关信息         | String | 选填     | @人相关信息AT如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| loginName    | 人员showid          | String | 选填     | 人员showid（账号未登入时填写）                               |

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



### 39 功能 ：获得百姓爆料处理人发的附件反馈

#### 请求方法：  /1do/bxbl/getExecutorFeedbacks

#### 参数说明：	

| 字段    | 类型   | 是否必填 | 描述及要求  |
| ------- | ------ | -------- | ----------- |
| SHOW_ID | String | 必填     | 1do工单编号 |

```json
{"SHOW_ID":"41068020382040064"}
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
    "data": [
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:25:18",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312393088838402048.png",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594628718002,
            "FB_TYPE": 3,
            "source": 0,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:24:52",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11251,
            "FBCONTENT": "image.png",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1495"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:26:40",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312393431982800896.png",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594628799799,
            "FB_TYPE": 3,
            "source": 0,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:26:14",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11254,
            "FBCONTENT": "image.png",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1496"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:29:37",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312394173133094912.png",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594628976519,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:29:11",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11256,
            "FBCONTENT": "f3b3c0ab-b34f-41f2-9d7b-c6608b5f576f.png",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1497"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:57:03",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312401077154611200.png",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594630622566,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:56:37",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11262,
            "FBCONTENT": "6a3293d4-0cd2-484b-b5a5-50698159323a.png",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1500"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:57:16",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312401133253427200.jpg",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594630635957,
            "FB_TYPE": 3,
            "source": 0,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:56:50",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11264,
            "FBCONTENT": "Navicat_Keygen_Patch_v3.4_By_DFoX_URET.jpg",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1501"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:57:26",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312401173623603200.txt",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594630645566,
            "FB_TYPE": 3,
            "source": 0,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:57:00",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11265,
            "FBCONTENT": "需要安装的软件.txt",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1502"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:57:45",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312401253118246912.pdf",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594630664519,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:57:19",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11266,
            "FBCONTENT": "063e4b7f-c53a-461f-b6de-5bfdea37aad8.pdf",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1503"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 16:58:23",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312401412636016640.xlsx",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594630702628,
            "FB_TYPE": 3,
            "source": 0,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 16:57:57",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11267,
            "FBCONTENT": "下城区招商项目-招商分片.xlsx",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1504"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 17:06:45",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312403520663846912.pdf",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594631205191,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 17:06:19",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11271,
            "FBCONTENT": "1-25??.pdf",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1506"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 17:07:34",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312403726448984064.xlsx",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594631254347,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 17:07:09",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11272,
            "FBCONTENT": "??? (1).xlsx",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1507"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 17:43:15",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312412707305291776.xlsx",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594633395488,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 17:42:50",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11277,
            "FBCONTENT": "100?????????(??).xlsx",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1508"
        },
        {
            "temp": null,
            "FB_USER_NAME": null,
            "ATTR_NAME": null,
            "star": 0,
            "O_USER_NAME": "陆仁杰",
            "FB_TIME": "2020-07-13 17:55:48",
            "ATTR_PATH": "http://59.202.68.43:8080/1do/upload/312415865360351232.xlsx",
            "O_USER": "AYmLEpq71dh9jxd3",
            "TIME_STAMP": 1594634148411,
            "FB_TYPE": 3,
            "source": 1,
            "callMessage": null,
            "result": null,
            "modifyTime": "2020-07-13 17:55:23",
            "AT": null,
            "FB_USER": null,
            "USERID": null,
            "shortMessage": null,
            "SHOW_ID": "312392778262773760",
            "isoverdue": 1,
            "ID": 11279,
            "FBCONTENT": "100?????????(??).xlsx",
            "IS_CREATE_REPORT": false,
            "ATTRID": "1510"
        }
    ],
    "message": "Success"
}
```

### 

















