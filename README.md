学号：202130442143
姓名：易诗航


测试账号1：admin  测试密码1：admin
测试账号2：zhangsan  测试密码2:123
测试账号3：qqq    测试密码3：aaa


src源代码文档说明
-src
  -main  -- 代码部分
    -java  -- Java代码
      -com
        -app
          -controller      -- controller层（接收请求，响应结果）
            -Product_Add           -- 增加商品
            -Product_Buy           -- 购买商品
            -Product_Del           -- 删除商品
            -product_NumAdd        -- 更改商品信息
            -UserServlet_Login     -- 用户登录
            -UserServlet_Logout    -- 用户注销
            -UserServlet_Register  -- 用户注册
          -entity          -- 实体
            -vo              -- 常用对象 ValueOfObject
              MessageModel        -- 消息模型对象，用于数据响应
            -ProductInfo           -- 商品信息对象
            -UserInfo              -- 用户信息对象
          -mapper          -- 接口类（dao层） mapper.xml  mybatis与数据库的相关操作
            -ProducMapper          -- 商品信息操作
            -UserMapper            -- 用户信息操作
          -service         -- service层，业务逻辑判断
            -ProductInfoService    -- 商品业务
            -UserService           -- 用户业务
          -test            -- 测试方法/类
            -Test                  -- 代码编写过程中的一些测试
          -util            -- 工具类
            -GetSqlSession         -- 获取SqlSession对象
            -StringUtil            -- 判断是否为空
          -基本功能.txt     -- 分析项目任务的基本功能
          -用户登录.txt     -- 对用户登录功能的一个详细分析
        -Filtter    -- 过滤器
          -Encode          -- 编码处理，保持request,response编码为UTF-8
          -illegalAccess   -- 非法访问过滤
    -resources   -- 项目资源
      -mapper      -- 映射文件
        -ProductMapper.xml  -- 商品操作与数据库映射
        -UserMapper.xml     --用户操作与数据库映射
      -META_INF
        -beans.xml
        -persistence.xml
      -mybatis-config.xml  -- mybatis配置
      -sqlserver.properties  -- 获取sqlserver登陆信息
    -webapp  -- 网页部分
      -image  -- 存储图片
      -js      -- 存储js
        -jquery-3.5.1.js
        -jquery-3.5.1.min.js
      -WEB-INF
        -lib  -- 存储导入的.jar
          -log4j-1.2-api-2.22.0.jar
          -log4j-core-2.22.0.jar
          -mssql-jdbc-6.1.0.jre.jar
          -mybatis-3.5.14.jar
        web.xml  -- 一些网页设置
      ano2_index.jsp  -- 测试jsp文件2
      ano_index.jsp   -- 测试jsp文件1
      index.jsp            -- 商品展示与购买页面
      product_updata.jsp   -- 商品信息增删修改页面
      user_login.jsp       -- 用户登录页面
      user_logout.jsp      -- 用户注销页面
      user_register.jsp    -- 用户注册页面
 -test  -- 测试
