History Version

Version 1.1
1. 增加 Rule 页面 insert or update 逻辑
2. 增加 Rule 页面 Content Logging on Errors Only 逻辑



Version 1.2
1. bug修复：输入内容中 ： 被替换为 =
2. 增加 Rule 页面 Active Flag on Errors Only 逻辑

Version 1.21
1. bug 修复：无法插入 Rule



Version 1.3
1. 取消 Transaction 默认全查询



Version: 1.4
1. Transaction 页面增加 Content 按钮 -- 未有逻辑
2. Rule 页面增加删除按钮及逻辑
3. Bug fix：修复 Transaction 页面无法查询带下划线参数

Version 1.41
1. 增加版本记录 /WebContent/META-INF/ReadMe.txt
2. 还原 /WebContent/META-INF/MANIFEST.MF

Version 1.42
1. 修复查询逻辑 -- 增加匹配条件：Rule ID 



Version 1.5
1. 增加 getContent 页面跳转逻辑及直接重推逻辑

Version 1.51
1. 调整 QueryAllRules Select 逻辑，改为根据 RULE_ID 升序排列

Version 1.52
1. 修复查询bug -- 无法查询 BUSINESS_TYPE 和 REFERENCE_ID



Version 1.6 -- 20180611
1. 修改 SearchTrasaction 页面，增加 From Time 和 To Time 选项
2. 修改 SearchTrasaction 页面结果展示，按照 MODIFIED_TIMESTAMP 倒序展示
3. 增加数据查询最大 10000 条限制
4. 删除 order by id desc限制

Version 1.61
1. 修改 CREATE_TIME1 和 CREATE_TIME2 readonly 属性为非只读
2. 暂时移除 ScheduleBatchConf 页面跳转到 MAIL_ROUTER_ID 和 RULE_ID 入口

Version 1.62
1. bug 修復：选择时间条件查询结果错误

Version 1.63
1. 移除 Console 打印记录
2. 统一 Log 打印条件 -- 在 Catch 步骤记录



Version 1.7
1. 增加 DB 配置 ServiceName 入口

Version 1.71
1. bug 修复：修复无法解析域名错误

Version 1.8
1. 新增兼容 MySQL（其他类型数据库入口已增加，功能未实现）

