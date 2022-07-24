# 项目名称

SpringBoot在线拍卖竞价拍卖竞拍系统源码+详细讲解视频教程+论文+软件环境+包远程安装配置

# 系统介绍
竞拍系统是根据市场需求的实际情况按照网络化的需求开发的，它的目标非常明确，即通过建立竞拍平台，将原来定时定点线下交易方式转变成通过网络平台进行竞拍的线上模式，使竞拍更加便捷、安全、规范和有针对性。

系统功能需求整体上分为用户使用功能和系统管理功能，其中用户使用功能包括用户浏览竞拍产品、用户登录、用户注册、用户查看常见问题、用户查看新闻资讯、用户查看个人信息等功能；系统管理功能包括管理员管理用户、管理员管理竞拍品、和管理员管理系统设置等功能。

根据以上功能需求分析，通过用例图来描述系统的主要功能。构建用例模型的第一步是确定模型中的使用者有哪些，确定使用者的原则有：谁是系统的维护者、谁是系统的参与者等。维护者处于系统内部，对系统有绝对的控制权；而参与者一般都位于系统的外部，处于系统的控制之外。

现在确定本系统用例模型有三种，分别是用户、机构和系统管理员。下面分别对这三个角色的功能进行描述：

用户

他们可以登录，可以注册，查看竞拍项目，

用户主要功能如下（图3-1为用户用例图）：

登录；
注册。
查看竞拍项目
收藏竞拍项目
查看新闻
收藏新闻
竞拍项目留言
查看常见问题
银行卡管理
提现管理
个人信息
修改密码
修改支付密码
报名的项目
领先的项目

图3-1用户用例图

机构

机构是经过网站合法认证的用户，登录网站后可以添加竞拍项目、查看我的机构信息、我的审核信息、修改支付密码、修改支付密码和查看我的充值。机构主要功能如下（图3-2为机构用例图）：

竞拍列表；
我的机构信息；
我的审核信息；
修改支付密码；
我的银行卡；
我的充值；
提现记录；
修改账户密码

图3-2 机构用例图

系统管理员

系统管理员主要负责系统的后台管理工作，主要功能如下（图3-3为系统管理员用例图）：

菜单管理；
角色管理；
后台用户管理
日志管理；
数据库备份；
标的类型列表
常见问题
竞拍审核
审核记录列表
机构管理
机构提现记录
新闻分类管理
通知公告管理
前台用户列表
前台用户提现记录
查看账户信息
修改密码

图3-3 系统管理员用例图

       在确定了系统用户和用户功能后就可以构建竞拍系统的用例图了，整个系统的用例图如图3-4系统总体用例图所示：

# 环境需要

1.运行环境：最好是java jdk 1.8，我们在这个平台上运行的。其他版本理论上也可以。\
2.IDE环境：IDEA，Eclipse,Myeclipse都可以。推荐IDEA;\
3.tomcat环境：Tomcat 7.x,8.x,9.x版本均可\
4.硬件环境：windows 7/8/10 1G内存以上；或者 Mac OS； \
5.数据库：MySql 5.7版本；\
6.是否Maven项目：否；

# 技术栈

1. 后端：Spring+SpringMVC+Mybatis\
2. 前端：JSP+CSS+JavaScript+jQuery

# 使用说明

1. 使用Navicat或者其它工具，在mysql中创建对应名称的数据库，并导入项目的sql文件；\
2. 使用IDEA/Eclipse/MyEclipse导入项目，Eclipse/MyEclipse导入时，若为maven项目请选择maven;\
若为maven项目，导入成功后请执行maven clean;maven install命令，然后运行；\
3. 将项目中springmvc-servlet.xml配置文件中的数据库配置改为自己的配置;\
4. 运行项目，在浏览器中输入http://localhost:8080/ 登录

# 高清视频演示

https://www.bilibili.com/video/BV15a411U7uX/


​