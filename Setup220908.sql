-- MySQL dump 10.13  Distrib 8.0.24, for Linux (x86_64)
--
-- Host: localhost    Database: jancoblog
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_article`
--

DROP TABLE IF EXISTS `tbl_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_article` (
  `article_id` varchar(25) NOT NULL,
  `article_title` varchar(255) DEFAULT NULL,
  `article_author` int DEFAULT NULL,
  `article_type` int DEFAULT NULL,
  `article_summary` varchar(255) DEFAULT NULL,
  `article_html` longtext,
  `article_md` longtext,
  `article_is_comment` tinyint DEFAULT '1',
  `article_rank` tinyint DEFAULT NULL,
  `article_post_time` datetime DEFAULT NULL,
  `article_edit_time` datetime DEFAULT NULL,
  `article_view_count` int DEFAULT '0',
  `article_comment_count` int DEFAULT '0',
  `article_like_count` int DEFAULT '0',
  PRIMARY KEY (`article_id`),
  KEY `tbl_article___fk_article_type` (`article_type`),
  KEY `tbl_article___fk_author_id` (`article_author`),
  CONSTRAINT `tbl_article___fk_article_type` FOREIGN KEY (`article_type`) REFERENCES `tbl_type` (`type_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbl_article___fk_author_id` FOREIGN KEY (`article_author`) REFERENCES `tbl_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_article`
--

LOCK TABLES `tbl_article` WRITE;
/*!40000 ALTER TABLE `tbl_article` DISABLE KEYS */;
INSERT INTO `tbl_article` VALUES ('11661992312179','CuteHttpFileServer/chfs搭建局域网文件传输服务 ',1,4,' 本文参考了  https://www.bilibili.com/read/cv16106328/ 作者：爷真可爱qwqwq 出处：bilibili  客户端服务器搭建 下载 1.19.0 服务端文件......','<blockquote>\n<p>本文参考了  https://www.bilibili.com/read/cv16106328/ 作者：爷真可爱qwqwq 出处：bilibili</p>\n</blockquote>\n<h2>客户端服务器搭建</h2>\n<p>下载 1.19.0 服务端文件, 并上传到服务器</p>\n<p>地址:  https://bmclapi2.bangbang93.com/version/1.19/server</p>\n<blockquote>\n<p>注意: MC1.19.0需要Java版本&gt;= 17.0.3, 版本过低会导致运行失败</p>\n</blockquote>\n<pre><code data-language=\"bash\" class=\"lang-bash\">➜  mc java -Xms1G -Xmx2G -jar server.jar\n</code></pre>\n<p>运行之后命令行最后出现:</p>\n<pre><code>[20:08:40] [ServerMain/INFO]: Building unoptimized datafixer\n[20:08:41] [ServerMain/ERROR]: Failed to load properties from file: server.properties\n[20:08:41] [ServerMain/WARN]: Failed to load eula.txt\n[20:08:41] [ServerMain/INFO]: You need to agree to the EULA in order to run the server. Go to eula.txt for more info.\n</code></pre>\n<p>他告诉我们需要同意EULA来运行服务器</p>\n<p>这时候的文件夹结构</p>\n<pre><code>➜  mc ll    \ntotal 44M\n-rw-rw-r-- 1 jancoyan jancoyan  158 Jul 19 20:08 eula.txt\ndrwxrwxr-x 8 jancoyan jancoyan 4.0K Jul 19 20:08 libraries\ndrwxrwxr-x 2 jancoyan jancoyan 4.0K Jul 19 20:08 logs\n-rwxr-xr-x 1 www      www       44M Jul 19 18:55 server.jar\n-rw-rw-r-- 1 jancoyan jancoyan 1.3K Jul 19 20:08 server.properties\ndrwxrwxr-x 3 jancoyan jancoyan 4.0K Jul 19 20:08 versions\n</code></pre>\n<p>编辑<code data-backticks=\"1\">Eula.txt</code> , 把里面的false改成true即可</p>\n<p>改完之后的文件夹结构:</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">  1 #By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).\n  2 #Tue Jul 19 20:08:41 CST 2022\n  3 eula=true \n</code></pre>\n<p>这时候再次运行java服务到后台</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">sudo nohup java -Xms1G -Xmx2G -jar server.jar &gt; log.txt &amp; \n</code></pre>\n<p>如果你无法加入说明使用的不是正版，打开server.properties文件把online-mode改为false即可</p>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/%E5%9B%BE%E7%89%87-1658236384078.png\" alt=\"图片-1658236384078\"></p>\n<p>这个时候的文件夹结构:</p>\n<pre><code>total 44M\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 banned-ips.json # 禁止加入的ip\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 banned-players.json # 禁止加入的玩家\n-rw-rw-r--  1 jancoyan jancoyan  157 Jul 19 20:14 eula.txt # 用户协议\ndrwxrwxr-x  8 jancoyan jancoyan 4.0K Jul 19 20:08 libraries\ndrwxrwxr-x  2 jancoyan jancoyan 4.0K Jul 19 20:52 logs # 日志文件\n-rw-rw-r--  1 jancoyan jancoyan    0 Jul 19 20:20 log.txt # server启动的日志\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 ops.json # 服务器管理员\n-rwxr-xr-x  1 www      www       44M Jul 19 18:55 server.jar# 服务器程序\n-rw-rw-r--  1 jancoyan jancoyan 1.3K Jul 19 20:52 server.properties # 服务器程序配置文件\n-rw-rw-r--  1 jancoyan jancoyan  102 Jul 19 20:54 usercache.json # 缓存, 无意义\ndrwxrwxr-x  3 jancoyan jancoyan 4.0K Jul 19 20:08 versions\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:15 whitelist.json # 白名单\ndrwxrwxr-x 12 jancoyan jancoyan 4.0K Jul 19 20:53 world # 世界\n</code></pre>\n<hr>\n<p>以下内容完全为引用 作者：爷真可爱qwqwq https://www.bilibili.com/read/cv16106328/ 出处：bilibili</p>\n<p>服务器配置如下</p>\n<pre><code>#Minecraft server properties\n#Sun Apr 10 15:32:09 CST 2022\nspawn-protection=16 # 出生点保护 世界出生点一定范围内不可破坏或放置方块，设为0可以停用\nmax-tick-time=60000 # 一个tick跑多少ms以上之后会关闭服务器 不建议修改\nquery.port=25565\ngenerator-settings= # 世界生成设置 一般没用\nsync-chunk-writes=true # 启用同步区块写入 不建议修改\nforce-gamemode=false # 是否强制游戏模式，如果你想让整个服保持一个游戏模式可以设置为true\nallow-nether=true # 是否启用下界 设为false则传送门不会生效\nenforce-whitelist=false # 执行白名单 设置为true后会在reload白名单文件后踢出不在白名单里的在线玩家\ngamemode=survival # 默认游戏模式，还可改为creative，adventure或spectator\nbroadcast-console-to-ops=true # 是否把在控制台使用的命令发给所有管理员\nenable-query=false\nplayer-idle-timeout=0 # 如果不是0，将会把挂机若干分钟的玩家踢出\ntext-filtering-config=\ndifficulty=easy # 游戏难度，还可改成peaceful，normal和hard\nspawn-monsters=true # 是否生成怪物\nbroadcast-rcon-to-ops=true # 把远程控制的命令发送给所有在线管理员\nop-permission-level=4 #默认管理员权限等级\npvp=true # 玩家互相伤害\nentity-broadcast-range-percentage=100\nsnooper-enabled=true\nlevel-type=default # 世界类型\nhardcore=false # 极限 死去的玩家会自动被服务器ban掉\nenable-status=true\nenable-command-block=false # 是否启用命令方块\nmax-players=20 # 最大玩家数\nnetwork-compression-threshold=256\nresource-pack-sha1= # 资源包的sha1哈希\nmax-world-size=29999984 # 最大世界大小（单位）取决于worldbounder位置，设置为1000允许玩家在2000*2000范围内\nfunction-permission-level=2 # 数据包函数权限等级\nrcon.port=25575 # 远程连接所用端口\nserver-port=25565 # 服务器绑定端口\nserver-ip=\nspawn-npcs=true\nallow-flight=false # 是否允许生存模式飞行\nlevel-name=world # 世界名字\nview-distance=10 # 视距 建议调大一点\nresource-pack= # 资源包\nspawn-animals=true # 是否生成动物\nwhite-list=false # 是否开启白名单\nrcon.password= # 远程控制的密码\ngenerate-structures=true # 是否生成结构 如下界要塞等\nmax-build-height=256 # 最高建筑上限 修改了可能出现问题\nonline-mode=true # 是否启用正版验证 调为false允许非正版玩家进入 你进不了服务器的罪魁祸首（\nlevel-seed= # 世界种子\nuse-native-transport=true\nprevent-proxy-connections=false # 是否阻止玩家使用v*n或代理\nenable-jmx-monitoring=false\nenable-rcon=false # 启用远程控制\nrate-limit=0\nmotd=A Minecraft Server # 服务器介绍，中文可能会乱码\n</code></pre>\n<p>最后可以在控制台里输入stop指令来停止服务器</p>\n<p>请注意所有加入服务器的玩家都默认不为管理员（不能使用大部分指令），哪怕你是第一个进服的也一样</p>\n<p>可以通过执行op 玩家名 的指令来让某位玩家成为管理员（拥有命令使用权）</p>\n<p>控制台使用命令不需要打斜杠</p>\n<p>平时需要用到的服务器指令：</p>\n<pre><code>令一位玩家成为管理员			op &lt;player name&gt;\n剥夺玩家的管理员身份			deop &lt;player name&gt;\n显示所有的管理员				ops\n停止/关不服务器				stop\n重新加载（配方和数据包		reload\n将服务器存至硬盘				save-all\n启用自动保存					save-on\n禁用自动保存					save-off\n踢出玩家						kick &lt;player name&gt;\n禁止玩家进入服务器			ban &lt;player name&gt;\n禁止玩家的ip加入服务器		ban-ip &lt;player name&gt;\n解除玩家的封禁				pardon &lt;player name&gt;\n解除玩家的ip的封禁			pardon-ip &lt;player name&gt;\n</code></pre>\n','\n\n> 本文参考了  https://www.bilibili.com/read/cv16106328/ 作者：爷真可爱qwqwq 出处：bilibili \n\n## 客户端服务器搭建\n\n下载 1.19.0 服务端文件, 并上传到服务器\n\n地址:  https://bmclapi2.bangbang93.com/version/1.19/server\n\n> 注意: MC1.19.0需要Java版本>= 17.0.3, 版本过低会导致运行失败\n\n```bash\n➜  mc java -Xms1G -Xmx2G -jar server.jar\n```\n\n运行之后命令行最后出现: \n\n```\n[20:08:40] [ServerMain/INFO]: Building unoptimized datafixer\n[20:08:41] [ServerMain/ERROR]: Failed to load properties from file: server.properties\n[20:08:41] [ServerMain/WARN]: Failed to load eula.txt\n[20:08:41] [ServerMain/INFO]: You need to agree to the EULA in order to run the server. Go to eula.txt for more info.\n```\n\n他告诉我们需要同意EULA来运行服务器\n\n这时候的文件夹结构\n\n```\n➜  mc ll    \ntotal 44M\n-rw-rw-r-- 1 jancoyan jancoyan  158 Jul 19 20:08 eula.txt\ndrwxrwxr-x 8 jancoyan jancoyan 4.0K Jul 19 20:08 libraries\ndrwxrwxr-x 2 jancoyan jancoyan 4.0K Jul 19 20:08 logs\n-rwxr-xr-x 1 www      www       44M Jul 19 18:55 server.jar\n-rw-rw-r-- 1 jancoyan jancoyan 1.3K Jul 19 20:08 server.properties\ndrwxrwxr-x 3 jancoyan jancoyan 4.0K Jul 19 20:08 versions\n```\n\n编辑`Eula.txt` , 把里面的false改成true即可\n\n改完之后的文件夹结构: \n\n```bash\n  1 #By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).\n  2 #Tue Jul 19 20:08:41 CST 2022\n  3 eula=true \n```\n\n这时候再次运行java服务到后台\n\n```bash\nsudo nohup java -Xms1G -Xmx2G -jar server.jar > log.txt & \n```\n\n如果你无法加入说明使用的不是正版，打开server.properties文件把online-mode改为false即可\n\n![图片-1658236384078](http://blog.evilemperor.top/upload/2022/07/%E5%9B%BE%E7%89%87-1658236384078.png)\n\n这个时候的文件夹结构: \n\n```\ntotal 44M\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 banned-ips.json # 禁止加入的ip\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 banned-players.json # 禁止加入的玩家\n-rw-rw-r--  1 jancoyan jancoyan  157 Jul 19 20:14 eula.txt # 用户协议\ndrwxrwxr-x  8 jancoyan jancoyan 4.0K Jul 19 20:08 libraries\ndrwxrwxr-x  2 jancoyan jancoyan 4.0K Jul 19 20:52 logs # 日志文件\n-rw-rw-r--  1 jancoyan jancoyan    0 Jul 19 20:20 log.txt # server启动的日志\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:52 ops.json # 服务器管理员\n-rwxr-xr-x  1 www      www       44M Jul 19 18:55 server.jar# 服务器程序\n-rw-rw-r--  1 jancoyan jancoyan 1.3K Jul 19 20:52 server.properties # 服务器程序配置文件\n-rw-rw-r--  1 jancoyan jancoyan  102 Jul 19 20:54 usercache.json # 缓存, 无意义\ndrwxrwxr-x  3 jancoyan jancoyan 4.0K Jul 19 20:08 versions\n-rw-rw-r--  1 jancoyan jancoyan    2 Jul 19 20:15 whitelist.json # 白名单\ndrwxrwxr-x 12 jancoyan jancoyan 4.0K Jul 19 20:53 world # 世界\n```\n\n---\n\n以下内容完全为引用 作者：爷真可爱qwqwq https://www.bilibili.com/read/cv16106328/ 出处：bilibili\n\n服务器配置如下\n```\n#Minecraft server properties\n#Sun Apr 10 15:32:09 CST 2022\nspawn-protection=16 # 出生点保护 世界出生点一定范围内不可破坏或放置方块，设为0可以停用\nmax-tick-time=60000 # 一个tick跑多少ms以上之后会关闭服务器 不建议修改\nquery.port=25565\ngenerator-settings= # 世界生成设置 一般没用\nsync-chunk-writes=true # 启用同步区块写入 不建议修改\nforce-gamemode=false # 是否强制游戏模式，如果你想让整个服保持一个游戏模式可以设置为true\nallow-nether=true # 是否启用下界 设为false则传送门不会生效\nenforce-whitelist=false # 执行白名单 设置为true后会在reload白名单文件后踢出不在白名单里的在线玩家\ngamemode=survival # 默认游戏模式，还可改为creative，adventure或spectator\nbroadcast-console-to-ops=true # 是否把在控制台使用的命令发给所有管理员\nenable-query=false\nplayer-idle-timeout=0 # 如果不是0，将会把挂机若干分钟的玩家踢出\ntext-filtering-config=\ndifficulty=easy # 游戏难度，还可改成peaceful，normal和hard\nspawn-monsters=true # 是否生成怪物\nbroadcast-rcon-to-ops=true # 把远程控制的命令发送给所有在线管理员\nop-permission-level=4 #默认管理员权限等级\npvp=true # 玩家互相伤害\nentity-broadcast-range-percentage=100\nsnooper-enabled=true\nlevel-type=default # 世界类型\nhardcore=false # 极限 死去的玩家会自动被服务器ban掉\nenable-status=true\nenable-command-block=false # 是否启用命令方块\nmax-players=20 # 最大玩家数\nnetwork-compression-threshold=256\nresource-pack-sha1= # 资源包的sha1哈希\nmax-world-size=29999984 # 最大世界大小（单位）取决于worldbounder位置，设置为1000允许玩家在2000*2000范围内\nfunction-permission-level=2 # 数据包函数权限等级\nrcon.port=25575 # 远程连接所用端口\nserver-port=25565 # 服务器绑定端口\nserver-ip=\nspawn-npcs=true\nallow-flight=false # 是否允许生存模式飞行\nlevel-name=world # 世界名字\nview-distance=10 # 视距 建议调大一点\nresource-pack= # 资源包\nspawn-animals=true # 是否生成动物\nwhite-list=false # 是否开启白名单\nrcon.password= # 远程控制的密码\ngenerate-structures=true # 是否生成结构 如下界要塞等\nmax-build-height=256 # 最高建筑上限 修改了可能出现问题\nonline-mode=true # 是否启用正版验证 调为false允许非正版玩家进入 你进不了服务器的罪魁祸首（\nlevel-seed= # 世界种子\nuse-native-transport=true\nprevent-proxy-connections=false # 是否阻止玩家使用v*n或代理\nenable-jmx-monitoring=false\nenable-rcon=false # 启用远程控制\nrate-limit=0\nmotd=A Minecraft Server # 服务器介绍，中文可能会乱码\n```\n\n最后可以在控制台里输入stop指令来停止服务器\n\n请注意所有加入服务器的玩家都默认不为管理员（不能使用大部分指令），哪怕你是第一个进服的也一样\n\n可以通过执行op 玩家名 的指令来让某位玩家成为管理员（拥有命令使用权）\n\n控制台使用命令不需要打斜杠\n\n平时需要用到的服务器指令：\n\n```\n令一位玩家成为管理员			op <player name>\n剥夺玩家的管理员身份			deop <player name>\n显示所有的管理员				ops\n停止/关不服务器				stop\n重新加载（配方和数据包		reload\n将服务器存至硬盘				save-all\n启用自动保存					save-on\n禁用自动保存					save-off\n踢出玩家						kick <player name>\n禁止玩家进入服务器			ban <player name>\n禁止玩家的ip加入服务器		ban-ip <player name>\n解除玩家的封禁				pardon <player name>\n解除玩家的ip的封禁			pardon-ip <player name>\n```\n\n',1,0,'2022-09-01 00:31:52','2022-09-01 00:31:52',2,0,0),('11661993724268','Uni-APP Scroll-View ',1,1,'下拉刷新无法正常恢复的问题 有一个非常关键的关键规则没说清楚，组件属性例子： refresher-triggered=\"triggered\" @refresherrefresh=\"onRefresh\"......','<h1>下拉刷新无法正常恢复的问题</h1>\n<p>有一个非常关键的关键规则没说清楚，组件属性例子：</p>\n<p>refresher-triggered=\"triggered\" @refresherrefresh=\"onRefresh\" @refresherrestore=\"onRestore\" scrolltolower=\"loadMore\"</p>\n<p>1.<strong>通过程序将triggered设为true时</strong>，将触发onRefresh；</p>\n<p>2.<strong>不管triggered为何值，在界面中下拉</strong>，也会触发onRefresh，但不会自动改变triggered值(不能双向绑定，这是问题的根本原因)；</p>\n<p>3.onRefresh执行完毕，<strong>不会自动触发onRestore</strong>（这是问题的表现），使得刷新图标一直显示，<strong>必须是triggered由true变为false</strong>，才会触发onRestore并隐藏刷新图标；如果triggered一直为false，或一直为true，都不会触发。</p>\n<p>解决办法：</p>\n<p>1.在进入onRefresh后，<strong>如果triggered为false，则将它置为true</strong>，当执行完你的刷新操作（通常是获取新的数据）后，<strong>将triggered置为false</strong>。</p>\n<p>2.由于上一步中将triggered置为true，会再次触发onRefresh，故需再增加一个_freshing，表示是否正在执行刷新操作，在onRefresh中做判断，如_freshing为true，不执行刷新操作直接返回。</p>\n<p>如果scroll-view有多个，要每个用自己的 triggered和refreshing来控制。</p>\n<pre><code data-language=\"html\" class=\"lang-html\">&lt;template&gt;\n	&lt;view&gt;\n		&lt;view class=\"wrap\"&gt;\n			&lt;view class=\"u-tabs-box\"&gt;\n				&lt;u-tabs-swiper \n					activeColor=\"#5098ff\" \n					ref=\"tabs\"\n					:list=\"typeList\" \n					:current=\"current\"\n					 name=\"typeName\"\n					@change=\"change\" \n					:is-scroll=\"false\" \n					swiperWidth=\"750\"&gt;&lt;/u-tabs-swiper&gt;\n			&lt;/view&gt;\n			&lt;u-top-tips ref=\"uTips\"&gt;&lt;/u-top-tips&gt;\n			&lt;swiper class=\"swiper-box\" \n				:current=\"swiperCurrent\" \n				@transition=\"transition\" \n				@animationfinish=\"animationfinish\"&gt;\n				&lt;swiper-item class=\"swiper-item\" v-for=\"(comments,index) in commentList\"&gt;\n					&lt;scroll-view \n					scroll-y\n					refresher-enabled=\"true\"\n					refresher-background=\"#5098ff\"\n					:refresher-triggered=\"triggered\"\n					@refresherrefresh=\"onRefresh\" \n					@refresherrestore=\"onRestore\" \n					@scrolltolower=\"reachBottom\"&gt;\n						&lt;u-card v-for=\"(item,index) in comments\" :show-head=\"false\"&gt;\n							&lt;view class=\"\" slot=\"body\"&gt;\n								{{item.commentContent}}\n							&lt;/view&gt;\n							&lt;view class=\"info\" slot=\"foot\"&gt;\n								&lt;view class=\"author\"&gt; {{item.userName}} &lt;/view&gt;\n								&lt;view class=\"date\"&gt; {{item.postDate}} &lt;/view&gt;\n								&lt;!-- &lt;view class=\"like\"&gt; 点赞 &lt;/view&gt; --&gt;\n								&lt;!-- &lt;view class=\"collect\"&gt; 收藏 &lt;/view&gt; --&gt;\n							&lt;/view&gt;\n						&lt;/u-card&gt;\n						&lt;u-loadmore :status=\"loadStatus[0]\"  bgColor=\"#f2f2f2\" :load-text=\"loadText\"&gt;&lt;/u-loadmore&gt;\n					&lt;/scroll-view&gt;\n				&lt;/swiper-item&gt;\n			&lt;/swiper&gt;\n		&lt;/view&gt;\n		\n		&lt;u-tabbar :list=\"tabbar\" :mid-button=\"true\" active-color=#5098FF&gt;&lt;/u-tabbar&gt;\n	&lt;/view&gt;\n&lt;/template&gt;\n\n&lt;script&gt;\nexport default {\n	data() {\n		return {\n			triggered: true,\n			// 默认是 false\n			_freshing: false,\n			\n	},\n	onLoad() {\n		// 获取所有分类和每一个页面的数据\n		this.getTypeList();\n		// 必须在这初始化\n		this._freshing = false\n		this.triggered = true\n	},\n	onReachBottom() {\n		this.reachBottom();\n	},\n	methods: {\n		onRefresh() {\n			this.debug(\"in Onrefresh\")\n			if (this._freshing) return\n			if(!this.triggered) this.triggered = true\n			this._freshing = true\n			this.currentPage[this.current] = 1\n			this.maxPage[this.current] = 1\n			this.getCommentList(0, this.tabAndType[this.current], this.current)\n			setTimeout(() =&gt; {\n				this.triggered = false;\n				this._freshing = false;\n				this.$refs.uTips.show({\n					title: \'已刷新 o(〃\\\\\'▽\\\\\'〃)o\',\n					type: \'success\',\n					duration: \'1000\'\n				})\n			}, 1000)\n		},\n		onRestore() {\n			this.debug(\"restore\")\n			this.triggered = false // 需要重置\n			this._freshing = false // 需要重置\n		},\n};\n&lt;/script&gt;\n</code></pre>\n<p>uni-app 解决scroll-view下拉刷新无法正常恢复的问题</p>\n<h1>Scroll-View 无法下滑问题</h1>\n<p>给 Scroll-view 一个高度即可</p>\n<p>在scroll-view的属性中加上这样一行代码：</p>\n<pre><code data-language=\"jsx\" class=\"lang-jsx\">style=\"height: calc(100vh - var(--window-top) - var(--window-bottom))\"\n</code></pre>\n','# 下拉刷新无法正常恢复的问题\n\n有一个非常关键的关键规则没说清楚，组件属性例子：\n\nrefresher-triggered=\"triggered\" @refresherrefresh=\"onRefresh\" @refresherrestore=\"onRestore\" scrolltolower=\"loadMore\"\n\n1.**通过程序将triggered设为true时**，将触发onRefresh；\n\n2.**不管triggered为何值，在界面中下拉**，也会触发onRefresh，但不会自动改变triggered值(不能双向绑定，这是问题的根本原因)；\n\n3.onRefresh执行完毕，**不会自动触发onRestore**（这是问题的表现），使得刷新图标一直显示，**必须是triggered由true变为false**，才会触发onRestore并隐藏刷新图标；如果triggered一直为false，或一直为true，都不会触发。\n\n解决办法：\n\n1.在进入onRefresh后，**如果triggered为false，则将它置为true**，当执行完你的刷新操作（通常是获取新的数据）后，**将triggered置为false**。\n\n2.由于上一步中将triggered置为true，会再次触发onRefresh，故需再增加一个_freshing，表示是否正在执行刷新操作，在onRefresh中做判断，如_freshing为true，不执行刷新操作直接返回。\n\n如果scroll-view有多个，要每个用自己的 triggered和refreshing来控制。\n\n```html\n<template>\n	<view>\n		<view class=\"wrap\">\n			<view class=\"u-tabs-box\">\n				<u-tabs-swiper \n					activeColor=\"#5098ff\" \n					ref=\"tabs\"\n					:list=\"typeList\" \n					:current=\"current\"\n					 name=\"typeName\"\n					@change=\"change\" \n					:is-scroll=\"false\" \n					swiperWidth=\"750\"></u-tabs-swiper>\n			</view>\n			<u-top-tips ref=\"uTips\"></u-top-tips>\n			<swiper class=\"swiper-box\" \n				:current=\"swiperCurrent\" \n				@transition=\"transition\" \n				@animationfinish=\"animationfinish\">\n				<swiper-item class=\"swiper-item\" v-for=\"(comments,index) in commentList\">\n					<scroll-view \n					scroll-y\n					refresher-enabled=\"true\"\n					refresher-background=\"#5098ff\"\n					:refresher-triggered=\"triggered\"\n					@refresherrefresh=\"onRefresh\" \n					@refresherrestore=\"onRestore\" \n					@scrolltolower=\"reachBottom\">\n						<u-card v-for=\"(item,index) in comments\" :show-head=\"false\">\n							<view class=\"\" slot=\"body\">\n								{{item.commentContent}}\n							</view>\n							<view class=\"info\" slot=\"foot\">\n								<view class=\"author\"> {{item.userName}} </view>\n								<view class=\"date\"> {{item.postDate}} </view>\n								<!-- <view class=\"like\"> 点赞 </view> -->\n								<!-- <view class=\"collect\"> 收藏 </view> -->\n							</view>\n						</u-card>\n						<u-loadmore :status=\"loadStatus[0]\"  bgColor=\"#f2f2f2\" :load-text=\"loadText\"></u-loadmore>\n					</scroll-view>\n				</swiper-item>\n			</swiper>\n		</view>\n		\n		<u-tabbar :list=\"tabbar\" :mid-button=\"true\" active-color=#5098FF></u-tabbar>\n	</view>\n</template>\n\n<script>\nexport default {\n	data() {\n		return {\n			triggered: true,\n			// 默认是 false\n			_freshing: false,\n			\n	},\n	onLoad() {\n		// 获取所有分类和每一个页面的数据\n		this.getTypeList();\n		// 必须在这初始化\n		this._freshing = false\n		this.triggered = true\n	},\n	onReachBottom() {\n		this.reachBottom();\n	},\n	methods: {\n		onRefresh() {\n			this.debug(\"in Onrefresh\")\n			if (this._freshing) return\n			if(!this.triggered) this.triggered = true\n			this._freshing = true\n			this.currentPage[this.current] = 1\n			this.maxPage[this.current] = 1\n			this.getCommentList(0, this.tabAndType[this.current], this.current)\n			setTimeout(() => {\n				this.triggered = false;\n				this._freshing = false;\n				this.$refs.uTips.show({\n					title: \\\'已刷新 o(〃\\\\\\\'▽\\\\\\\'〃)o\\\',\n					type: \\\'success\\\',\n					duration: \\\'1000\\\'\n				})\n			}, 1000)\n		},\n		onRestore() {\n			this.debug(\"restore\")\n			this.triggered = false // 需要重置\n			this._freshing = false // 需要重置\n		},\n};\n</script>\n```\n\nuni-app 解决scroll-view下拉刷新无法正常恢复的问题\n\n# Scroll-View 无法下滑问题\n\n给 Scroll-view 一个高度即可\n\n在scroll-view的属性中加上这样一行代码：\n\n```jsx\nstyle=\"height: calc(100vh - var(--window-top) - var(--window-bottom))\"\n```',1,0,'2022-09-01 00:55:24','2022-09-01 00:55:24',1,8,0),('11661993775160','Axios跨域请求的session不一致问题',1,1,'vue-element-admin+Axios跨域请求session不一致问题 场景复现  前端：Vue-Cli + Axios + ElementUI + Node.js + vue-element......','<h1>vue-element-admin+Axios跨域请求session不一致问题</h1>\n<h3>场景复现</h3>\n<blockquote>\n<p>前端：Vue-Cli + Axios + ElementUI + Node.js + vue-element-admin</p>\n<p>后端：Spring Boot</p>\n<p>数据库：MySQL + Redis</p>\n</blockquote>\n<p>在做用户注册的验证码功能的时候，前端发起请求，后端生成验证码图片发送到前端，并将验证码字符串存放到session中，但是在前端输入表单发送注册请求的时候，一直显示注册失败，通过调试发现两次请求的SessionID不同。</p>\n<h3>解决步骤</h3>\n<ol>\n<li>\n<p>设置[[proxy]]代理（根目录下 vue.config.js 文件，没有就新建一个）</p>\n<pre><code data-language=\"js\" class=\"lang-js\">module.exports = {\n　　　　devServer: {\n　　// 设置代理\n　　　　　　proxy: {\n　　　　　　　\'/app\': {\n  　　　　　　　　// 目标 API 地址\n　　　　　　　　　target: \'http://localhost:8080/\',\n　　　　　　　　　changeOrigin: true,\n　　　　　　　　　pathRewrite:{\n　　　　　　　　　　\'^/app\':\"\"\n　　　　　　　　　　}\n　　　　　　　　}\n　　　　　}\n　　}\n}\n</code></pre>\n</li>\n<li>\n<p>main.js 中配置axios</p>\n<pre><code data-language=\"js\" class=\"lang-js\">// 在引入axios之后加入下面这句话\nAxios.defaults.withCredentials=true;\n</code></pre>\n</li>\n<li>\n<p>后端过滤器加入两行代码</p>\n<pre><code data-language=\"java\" class=\"lang-java\">\npublic class AllowOriginIntercepter implements HandlerInterceptor {\n\n    @Override\n    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {\n        // 过滤器\n      // 注释掉这一句\n      // response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n      // 加入这两句\n        response.setHeader(\"Access-Control-Allow-Origin\", request.getHeader(\"Origin\"));\n        response.setHeader(\"Access-Control-Allow-Credentials\", \"true\");\n      \n      // --------------无关代码-----------------------\n        response.setHeader(\"Access-Control-Allow-Methods\",\"GET, POST, OPTIONS\");\n        response.setHeader(\"Access-Control-Allow-Headers\",\"Authorization, Content-Type, token\");\n        return true;\n    }\n}\n\n</code></pre>\n</li>\n</ol>\n<p>问题解决。</p>\n<h3>参考文章</h3>\n<p>https://www.cnblogs.com/guangixn/p/9843946.html</p>\n','# vue-element-admin+Axios跨域请求session不一致问题\n\n### 场景复现\n\n> 前端：Vue-Cli + Axios + ElementUI + Node.js + vue-element-admin\n>\n> 后端：Spring Boot\n>\n> 数据库：MySQL + Redis\n\n\n\n在做用户注册的验证码功能的时候，前端发起请求，后端生成验证码图片发送到前端，并将验证码字符串存放到session中，但是在前端输入表单发送注册请求的时候，一直显示注册失败，通过调试发现两次请求的SessionID不同。\n\n\n\n### 解决步骤\n\n1. 设置[[proxy]]代理（根目录下 vue.config.js 文件，没有就新建一个）\n\n   ```js\n   module.exports = {\n   　　　　devServer: {\n   　　// 设置代理\n   　　　　　　proxy: {\n   　　　　　　　\\\'/app\\\': {\n     　　　　　　　　// 目标 API 地址\n   　　　　　　　　　target: \\\'http://localhost:8080/\\\',\n   　　　　　　　　　changeOrigin: true,\n   　　　　　　　　　pathRewrite:{\n   　　　　　　　　　　\\\'^/app\\\':\"\"\n   　　　　　　　　　　}\n   　　　　　　　　}\n   　　　　　}\n   　　}\n   }\n   ```\n\n   \n\n2. main.js 中配置axios\n\n   ```js\n   // 在引入axios之后加入下面这句话\n   Axios.defaults.withCredentials=true;\n   ```\n\n\n\n3. 后端过滤器加入两行代码\n\n   ```java\n   \n   public class AllowOriginIntercepter implements HandlerInterceptor {\n   \n       @Override\n       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {\n           // 过滤器\n         // 注释掉这一句\n         // response.setHeader(\"Access-Control-Allow-Origin\", \"*\");\n         // 加入这两句\n           response.setHeader(\"Access-Control-Allow-Origin\", request.getHeader(\"Origin\"));\n           response.setHeader(\"Access-Control-Allow-Credentials\", \"true\");\n         \n         // --------------无关代码-----------------------\n           response.setHeader(\"Access-Control-Allow-Methods\",\"GET, POST, OPTIONS\");\n           response.setHeader(\"Access-Control-Allow-Headers\",\"Authorization, Content-Type, token\");\n           return true;\n       }\n   }\n   \n   ```\n\n   \n\n问题解决。\n\n\n\n### 参考文章\n\nhttps://www.cnblogs.com/guangixn/p/9843946.html\n\n',1,0,'2022-09-01 00:56:15','2022-09-01 00:56:15',2,0,0),('11661993851324','Python-OS模块',1,3,'[TOC]  os.walk 是遍历目录常用的模块，返回一个包含 3 个元素的元组：(dirpath, dirnames, filenames)。  dirpath 是以 string 字符串形式返回......','<p>[TOC]</p>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/%E5%9B%BE%E7%89%87-1656853707216.png\" alt=\"图片-1656853707216\"></p>\n<p>os.walk 是遍历目录常用的模块，返回一个包含 3 个元素的元组：(dirpath, dirnames, filenames)。</p>\n<ul>\n<li>dirpath 是以 string 字符串形式返回该目录下所有的绝对路径；</li>\n<li>dirnames 是以列表 list 形式返回每一个绝对路径下的文件夹名字；</li>\n<li>filenames 是以列表 list 形式返回该路径下所有文件名字。</li>\n</ul>\n','[TOC]\n\n![图片-1656853707216](http://blog.evilemperor.top/upload/2022/07/%E5%9B%BE%E7%89%87-1656853707216.png)\n\nos.walk 是遍历目录常用的模块，返回一个包含 3 个元素的元组：(dirpath, dirnames, filenames)。\n\n- dirpath 是以 string 字符串形式返回该目录下所有的绝对路径；\n- dirnames 是以列表 list 形式返回每一个绝对路径下的文件夹名字；\n- filenames 是以列表 list 形式返回该路径下所有文件名字。',1,0,'2022-09-01 00:57:31','2022-09-02 09:25:06',5,0,0),('11661993886344','Python学习笔记-文件读写',1,3,' 学习Github开源书籍 Python-Explore 的学习笔记     模式 介绍     \'r\' 读模式   ‘w\' 写模式   ‘a\' 追加模式   ‘b\' 二进制模式（可添加到其他模式中使......','<blockquote>\n<p>学习Github开源书籍 <a href=\"https://funhacks.gitbooks.io/explore-python/content/\">Python-Explore</a> 的学习笔记</p>\n</blockquote>\n<table>\n<thead>\n<tr>\n<th>模式</th>\n<th>介绍</th>\n</tr>\n</thead>\n<tbody>\n<tr>\n<td>\'r\'</td>\n<td>读模式</td>\n</tr>\n<tr>\n<td>‘w\'</td>\n<td>写模式</td>\n</tr>\n<tr>\n<td>‘a\'</td>\n<td>追加模式</td>\n</tr>\n<tr>\n<td>‘b\'</td>\n<td>二进制模式（可添加到其他模式中使用）</td>\n</tr>\n<tr>\n<td>‘+\'</td>\n<td>读/写模式（可添加到其他模式中使用）</td>\n</tr>\n</tbody>\n</table>\n<h2>文本文件</h2>\n<p>使用open函数直接打开一般需要 打开\\ 读取\\ 关闭文件三个步骤</p>\n<p>但是我们可以使用 with</p>\n<pre><code data-language=\"python\" class=\"lang-python\">with open(\'/path/to/file\', \'r\') as f:\n    data = f.read()\n</code></pre>\n<p>读取方式</p>\n<ul>\n<li>一次性读取所有内容， <code data-backticks=\"1\">read()</code> 或 <code data-backticks=\"1\">readlines()</code></li>\n<li>按字节读取， <code data-backticks=\"1\">read(size)</code></li>\n<li>按行读取， <code data-backticks=\"1\">readline()</code></li>\n</ul>\n<p>文件可以作为迭代器使用:</p>\n<pre><code data-language=\"python\" class=\"lang-python\">with open(\'data.txt\', \'r\') as f:\n    for line in f:\n        print line,\n</code></pre>\n<p>写文件使用 <code data-backticks=\"1\">write</code> 方法：</p>\n<pre><code data-language=\"python\" class=\"lang-python\">with open(\'data2.txt\', \'w\') as f:\n    f.write(\'one\\n\')\n    f.write(\'two\')\n</code></pre>\n<ul>\n<li>如果上述文件已存在，则会清空原内容并覆盖掉；</li>\n<li>如果上述路径是正确的但是文件不存在（data2.txt 不存在），则会新建一个文件，并写入上述内容；</li>\n<li>如果上述路径是不正确, 会抛出 IOError；</li>\n</ul>\n<h2>二进制文件</h2>\n<ul>\n<li>读取二进制文件使用 \'rb\'</li>\n<li>写入二进制文件使用 \'wb\'</li>\n</ul>\n','> 学习Github开源书籍 [Python-Explore](https://funhacks.gitbooks.io/explore-python/content/) 的学习笔记\n\n\n\n| 模式 | 介绍 |\n|---|---|\n| \\\'r\\\' | 读模式 |\n|‘w\\\'|写模式|\n|‘a\\\'|追加模式|\n|‘b\\\'|二进制模式（可添加到其他模式中使用）|\n|‘+\\\'|读/写模式（可添加到其他模式中使用）|\n\n## 文本文件\n\n使用open函数直接打开一般需要 打开\\ 读取\\ 关闭文件三个步骤\n\n但是我们可以使用 with \n\n```python\nwith open(\\\'/path/to/file\\\', \\\'r\\\') as f:\n    data = f.read()\n```\n\n读取方式\n- 一次性读取所有内容， `read()` 或 `readlines()`\n- 按字节读取， `read(size)`\n- 按行读取， `readline()`\n\n文件可以作为迭代器使用:\n\n```python\nwith open(\\\'data.txt\\\', \\\'r\\\') as f:\n    for line in f:\n        print line,\n```\n\n写文件使用 `write` 方法：\n\n```python\nwith open(\\\'data2.txt\\\', \\\'w\\\') as f:\n    f.write(\\\'one\\n\\\')\n    f.write(\\\'two\\\')\n```\n\n-   如果上述文件已存在，则会清空原内容并覆盖掉；\n-   如果上述路径是正确的但是文件不存在（data2.txt 不存在），则会新建一个文件，并写入上述内容；\n-   如果上述路径是不正确, 会抛出 IOError；\n\n## 二进制文件\n\n-   读取二进制文件使用 \\\'rb\\\' \n-   写入二进制文件使用 \\\'wb\\\' ',1,0,'2022-09-01 00:58:06','2022-09-01 00:58:06',0,0,0),('11661993957585','Python学习笔记-基本数据类型及其操作',1,3,' 学习Github开源书籍 Python-Explore 的学习笔记  [TOC] 序列 根据索引获取元素, 有一些通用的操作 序列  列表 list 元组 tuple 字符串 string  映射 ......','<blockquote>\n<p>学习Github开源书籍 <a href=\"https://funhacks.gitbooks.io/explore-python/content/\">Python-Explore</a> 的学习笔记</p>\n</blockquote>\n<p>[TOC]</p>\n<h2>序列</h2>\n<p>根据索引获取元素, 有一些通用的操作</p>\n<p>序列</p>\n<ul>\n<li>列表 list</li>\n<li>元组 tuple</li>\n<li>字符串 string</li>\n</ul>\n<p>映射</p>\n<ul>\n<li>字典 dict</li>\n</ul>\n<p>集合</p>\n<ul>\n<li>集合 set</li>\n</ul>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.tophttp://blog.evilemperor.top/upload/2022/07/Snipaste_2022-07-03_15-33-48.jpg\" alt=\"Snipaste_2022-07-03_15-33-48\"></p>\n<h3>索引</h3>\n<p>获取序列中单个元素</p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; nums = [1, 2, 3, 4, 5, 6, 7, 8, 9]\n&gt;&gt;&gt; \n&gt;&gt;&gt; # 第0个元素\n&gt;&gt;&gt; nums[0]\n1\n&gt;&gt;&gt; # 最后一个元素\n&gt;&gt;&gt; nums[-1]\n9\n&gt;&gt;&gt; s = \'abcdefghijklmn\'\n&gt;&gt;&gt; \n&gt;&gt;&gt; # 第0个元素\n&gt;&gt;&gt; s[0]\n\'a\'\n&gt;&gt;&gt; # 最后一个元素\n&gt;&gt;&gt; s[-1]\n\'n\'\n</code></pre>\n<h3>分片</h3>\n<p>获取序列中的一个区间的元素</p>\n<p><code data-backticks=\"1\">左闭右开</code>的区间</p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; nums = [1, 2, 3, 4, 5, 6, 7]\n\n&gt;&gt;&gt; # 访问前面的元素和后面的元素\n&gt;&gt;&gt; nums[0:4]\n[1, 2, 3, 4]\n&gt;&gt;&gt; nums[3:4]\n[4]\n\n# 访问前面和后面的元素\n&gt;&gt;&gt; nums[:3]\n[1, 2, 3]\n&gt;&gt;&gt; nums[-3:]\n[5, 6, 7]\n\n&gt;&gt;&gt; # 整个序列\n&gt;&gt;&gt; nums[:]\n[1, 2, 3, 4, 5, 6, 7]\n&gt;&gt;&gt;\n\n&gt;&gt;&gt; # 使用步长, 默认为1\n&gt;&gt;&gt; nums[1:5]\n[2, 3, 4, 5]\n&gt;&gt;&gt; nums[1:5:1]\n[2, 3, 4, 5]\n&gt;&gt;&gt;\n\n&gt;&gt;&gt; # 对正数步长，从左向右取元素；对负数步长，从右向左取元素\n&gt;&gt;&gt; nums[1:5:-1]\n[]\n&gt;&gt;&gt; nums[5:1:-1]\n[6, 5, 4, 3]\n&gt;&gt;&gt;\n\n&gt;&gt;&gt; # 前两个元素置空\n&gt;&gt;&gt; nums[::-2]\n[7, 5, 3, 1]\n&gt;&gt;&gt; nums[::2]\n[1, 3, 5, 7]\n\n&gt;&gt;&gt; nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]\n&gt;&gt;&gt; nums[:6]\n[1, 2, 3, 4, 5, 6]\n&gt;&gt;&gt; nums[:6:-2]\n[15, 13, 11, 9]\n&gt;&gt;&gt; nums[:6:2]\n[1, 3, 5]\n</code></pre>\n<h3>+</h3>\n<p>加法就是 <code data-backticks=\"1\">拼接</code></p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; [1, 2, 3] + [4, 5, 6, 7, 8, 9, 10]\n[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]\n\n&gt;&gt;&gt; (1, 2, 3) + (4, 5, 6)\n(1, 2, 3, 4, 5, 6)\n\n&gt;&gt;&gt; \'hello\' + \'world\'\n\'helloworld\'\n</code></pre>\n<h3>x</h3>\n<p>加法就是 <code data-backticks=\"1\">多次拼接</code></p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; [1, 1, 2] * 3\n[1, 1, 2, 1, 1, 2, 1, 1, 2]\n\n&gt;&gt;&gt; \'123\' * 3\n\'123123123\'\n</code></pre>\n<h3>in</h3>\n<p>就是<code data-backticks=\"1\">在不在序列中</code></p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; 1 in [1, 2, 3, 4, 5]\nTrue\n\n&gt;&gt;&gt; 8 in [1, 2, 3, 4, 5]\nFalse\n\n&gt;&gt;&gt; \'el\' in \'hello\'\nTrue\n</code></pre>\n<h2>列表</h2>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/image-20220703175407.png\" alt=\"\"></p>\n<pre><code data-language=\"python\" class=\"lang-python\">\n# -----index-----\n\n&gt;&gt;&gt; strs = [\'hello\', \'hi\', \'nice\']\n&gt;&gt;&gt; strs.index(\'hi\')\n1\n&gt;&gt;&gt; nums = [1, 2, 3, 4, 5]\n&gt;&gt;&gt; nums.index(5)\n4\n\n# -----count-----\n\n&gt;&gt;&gt; nums = [1, 1, 2, 1, 2, 4, 5]\n&gt;&gt;&gt; nums.count(1)\n3\n&gt;&gt;&gt; nums.count(3)\n0\n\n# -----append-----\n\n&gt;&gt;&gt; nums = [1, 2]\n&gt;&gt;&gt; nums\n[1, 2]\n&gt;&gt;&gt; nums.append(3)\n&gt;&gt;&gt; nums\n[1, 2, 3]\n&gt;&gt;&gt; nums.append([4, 5])\n&gt;&gt;&gt; nums\n[1, 2, 3, [4, 5]]\n\n# -----extend-----\n\n&gt;&gt;&gt; a = [1, 1, 1]\n&gt;&gt;&gt; b = [2, 2, 2]\n&gt;&gt;&gt; a\n[1, 1, 1]\n&gt;&gt;&gt; b\n[2, 2, 2]\n&gt;&gt;&gt; a.extend(b)\n&gt;&gt;&gt; a\n[1, 1, 1, 2, 2, 2]\n&gt;&gt;&gt; b.extend(a)\n&gt;&gt;&gt; b\n[2, 2, 2, 1, 1, 1, 2, 2, 2]\n\n# -----insert-----\n\n&gt;&gt;&gt; nums\n[1, 3]\n&gt;&gt;&gt; nums.insert(1, 0)\n&gt;&gt;&gt; nums\n[1, 0, 3]\n\n# -----pop----\n\n&gt;&gt;&gt; nums\n[1, 2, 3, [4, 5]]\n&gt;&gt;&gt; nums.pop()\n[4, 5]\n&gt;&gt;&gt; nums\n[1, 2, 3]\n&gt;&gt;&gt; nums.pop(1)\n2\n&gt;&gt;&gt; nums\n[1, 3]\n\n# -----remove-----\n\n&gt;&gt;&gt; nums\n[3, 0, 1, 3]\n&gt;&gt;&gt; nums.remove(3)\n&gt;&gt;&gt; nums\n[0, 1, 3]\n\n# -----reverse-----\n\n&gt;&gt;&gt; nums\n[1, 0, 3]\n&gt;&gt;&gt; nums.reverse()\n&gt;&gt;&gt; nums\n[3, 0, 1]\n\n# -----sort-----\n\n&gt;&gt;&gt; nums.sort()\n&gt;&gt;&gt; nums\n[0, 0, 1, 1, 3, 3, 5, 6, 23, 33]\n\n&gt;&gt;&gt; nums\n[0, 1, 3, 3, 5, 6, 23, 33, 1, 0]\n&gt;&gt;&gt; sorted(nums)\n[0, 0, 1, 1, 3, 3, 5, 6, 23, 33]\n&gt;&gt;&gt; sorted(nums, reverse=True)\n[33, 23, 6, 5, 3, 3, 1, 1, 0, 0]\n&gt;&gt;&gt;\n\n&gt;&gt;&gt; students\n[(\'aaa\', 12, 15, 12), (\'qww\', 45, 12, 55), (\'erw\', 12, 10, 23), (\'asdd\', 1, 20, 123), (\'qqq\', 32, 20, 55), (\'bbb\', 43, 16, 30)]\n\n# 对第 3 列排序 (从小到大)\n&gt;&gt;&gt; sorted(students, key=lambda student: student[2])\n[(\'erw\', 12, 10, 23), (\'qww\', 45, 12, 55), (\'aaa\', 12, 15, 12), (\'bbb\', 43, 16, 30), (\'asdd\', 1, 20, 123), (\'qqq\', 32, 20, 55)]\n\n# 对第 2 列排序（从小到大），再对第 3 列从大到小排序\n&gt;&gt;&gt; sorted(students, key=lambda student: (student[1], -student[2]))\n[(\'asdd\', 1, 20, 123), (\'aaa\', 12, 15, 12), (\'erw\', 12, 10, 23), (\'qqq\', 32, 20, 55), (\'bbb\', 43, 16, 30), (\'qww\', 45, 12, 55)]\n</code></pre>\n<p>排序  https://wiki.python.org/moin/HowTo/Sorting</p>\n<h2>元组</h2>\n<p>因为是不可变的序列, 也就没有排序什么的操作了</p>\n<p>创建</p>\n<pre><code data-language=\"python\" class=\"lang-python\">&gt;&gt;&gt; t = ()\n&gt;&gt;&gt; t\n()\n&gt;&gt;&gt; t = (1,)\n&gt;&gt;&gt; t\n(1,)\n</code></pre>\n<h2>字符串</h2>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/image-20220703183522.png\" alt=\"\"></p>\n<p>不可变, 不能对他进行赋值等操作</p>\n<pre><code data-language=\"python\" class=\"lang-python\">\n# ----find----\n\n&gt;&gt;&gt; s\n\"when i was young i\'d listen to the radio\"\n\n&gt;&gt;&gt; s.find(\'i\')\n5\n# 指定起始位置\n&gt;&gt;&gt; s.find(\'i\', 5)\n5\n\n&gt;&gt;&gt; s.find(\'i\', 6)\n17\n# 指定起始位置和结束位置\n&gt;&gt;&gt; s.find(\'i\', 6, 15)\n-1\n\n# ----split----\n\n&gt;&gt;&gt; \'/root/OS-lab/lab01\'.split(\'/\')\n[\'\', \'root\', \'OS-lab\', \'lab01\']\n\n# 默认以 所有 空格（空格、制表符、换行等）作为分隔符\n&gt;&gt;&gt; \' when i was    young i d listen\'.split()\n[\'when\', \'i\', \'was\', \'young\', \'i\', \'d\', \'listen\']\n\n# 以 空格 作为分隔符\n&gt;&gt;&gt; \' when i was    young i d listen\'.split(\' \')\n[\'\', \'when\', \'i\', \'was\', \'\', \'\', \'\', \'young\', \'i\', \'d\', \'listen\']\n\n# ----join----\n\n&gt;&gt;&gt; \' \'.join([\'\', \'root\', \'OS-lab\', \'lab01\'])\n\' root OS-lab lab01\'\n\n&gt;&gt;&gt; \'/\'.join([\'\', \'root\', \'OS-lab\', \'lab01\'])\n\'/root/OS-lab/lab01\'\n\n# ----strip----\n\n&gt;&gt;&gt; \'     strip string    \'.strip()\n\'strip string\'\n&gt;&gt;&gt; \'     strip string    \'.rstrip()\n\'     strip string\'\n&gt;&gt;&gt; \'     strip string    \'.lstrip()\n\'strip string    \'\n# 移除所有的字符1和2\n&gt;&gt;&gt; \'123     strip string1212\'.strip(\'12\')\n\'3     strip string\'\n&gt;&gt;&gt;\n\n# ----replace----\n\n&gt;&gt;&gt; \'     strip string    \'.replace(\'s\', \'S\')\n\'     Strip String    \'\n\n# ----lower/upper----\n\n&gt;&gt;&gt; \'Python\'.upper()\n\'PYTHON\'\n&gt;&gt;&gt; \'Python\'.lower()\n\'python\'\n&gt;&gt;&gt;\n</code></pre>\n<h2>字典</h2>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/image-20220703193734.png\" alt=\"\"></p>\n<p>创建. 遍历, 判断</p>\n<pre><code data-language=\"python\" class=\"lang-python\">\n# ----创建----\n\n# 空\n&gt;&gt;&gt; dic = {}\n&gt;&gt;&gt; dic\n{}\n# 字典\n&gt;&gt;&gt; dic = {\'name\': \'yan jingcun\', \'age\': 22}\n&gt;&gt;&gt; dic\n{\'name\': \'yan jingcun\', \'age\': 22}\n# dict函数\n&gt;&gt;&gt; dic = dict(name=\'yjc\', age=20)\n&gt;&gt;&gt; dic\n{\'name\': \'yjc\', \'age\': 20}\n&gt;&gt;&gt; item = [(\'name\', \'yjc\'), (\'age\', 23)]\n&gt;&gt;&gt; dic = dict(item)\n&gt;&gt;&gt; dic\n{\'name\': \'yjc\', \'age\': 23}\n\n# -----遍历\n\n&gt;&gt;&gt; dic = {\'name\':\'yjc\', \'age\':12}\ney in dic:\n    &gt;&gt;&gt; for key in dic:\n...         print(\'%s:%s\' %(key, dic[key]))\n...\nname:yjc\nage:12\n&gt;&gt;&gt; dic[\'name\']\n\'yjc\'\n\n&gt;&gt;&gt; for key in dic.keys():\n...     if key == \'name\':\n...             del dic[key]\n&gt;&gt;&gt; dic\n{\'age\': 12}\n\n# ----判断是否在字典里面\n\n&gt;&gt;&gt; \'name\' in dic\nTrue\n&gt;&gt;&gt; \'ad\' in dic\nFalse\n&gt;&gt;&gt;\n\n</code></pre>\n<pre><code data-language=\"python\" class=\"lang-python\">\n# ----clear----\n\n# 直接赋值不能清除dic2的值\n&gt;&gt;&gt; dic1 = {\'name\':\'yjc\', \'age\':12, \'school\':\'stdu\'}\n&gt;&gt;&gt; dic2 = dic1\n&gt;&gt;&gt; dic2\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\'}\n&gt;&gt;&gt; dic1 = {}\n&gt;&gt;&gt; dic2\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\'}\n# clear可以\n&gt;&gt;&gt; dic1\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\'}\n&gt;&gt;&gt; dic2\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\'}\n&gt;&gt;&gt; dic1.clear()\n&gt;&gt;&gt; dic2\n{}\n&gt;&gt;&gt;\n\n# ----copy----\n\n# 浅拷贝\n&gt;&gt;&gt; dic\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [33, 55, 66]}\n&gt;&gt;&gt; dic1 = dic.copy()\n&gt;&gt;&gt; dic1\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [33, 55, 66]}\n&gt;&gt;&gt; dic1[\'name\'] = \'123\'\n&gt;&gt;&gt; dic1[\'scores\'][0] = 60\n&gt;&gt;&gt; dic\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n&gt;&gt;&gt;\n\n# 深拷贝\n# 对任何对象的操作都不会影响到彼此\n&gt;&gt;&gt; from copy import deepcopy\n&gt;&gt;&gt; dic1 = deepcopy(dic)\n&gt;&gt;&gt; dic1\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n&gt;&gt;&gt; dic[\'name\'] = \'123\'\n&gt;&gt;&gt; dic1\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n&gt;&gt;&gt; dic[\'name\'] = \'321\'\n&gt;&gt;&gt; dic1\n{\'name\': \'yjc\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n\n# ----get----\n&gt;&gt;&gt; dic\n{\'name\': \'321\', \'age\': 12, \'school\': \'stdu\', \'scores\': [60, 55, 66]}\n&gt;&gt;&gt; dic.get(\'name\')\n\'321\'\n&gt;&gt;&gt; dic.get(\'nam0\')\n&gt;&gt;&gt;\n\n# ----setdefault----\n# 当键不存在的时候，setdefault 返回设定的默认值并且更新字典。\n# 当键存在的时候，会返回已有的值，但不会更新字典。\n\n&gt;&gt;&gt; d\n{\'name\': \'123\'}\n&gt;&gt;&gt; d = {}\n&gt;&gt;&gt; d.setdefault(\'name\', \'111\')\n\'111\'\n&gt;&gt;&gt; d\n{\'name\': \'111\'}\n&gt;&gt;&gt; d.setdefault(\'name\', \'1112\')\n\'111\'\n&gt;&gt;&gt; d\n{\'name\': \'111\'}\n\n# ----update----\n\n# 创建\n&gt;&gt;&gt; dic\n{\'name\': \'321\', \'age\': 12, \'school\': \'stdu\'}\n&gt;&gt;&gt; dic1 = {\'score\':[1, 2, 3]}\n&gt;&gt;&gt; dic.update(dic1)\n&gt;&gt;&gt; dic\n{\'name\': \'321\', \'age\': 12, \'school\': \'stdu\', \'score\': [1, 2, 3]}\n&gt;&gt;&gt;\n\n#更新\n&gt;&gt;&gt; dic\n{\'name\': \'321\', \'age\': 12, \'school\': \'stdu\', \'score\': [1, 2, 3]}\n&gt;&gt;&gt; dic1\n{\'name\': \'123\'}\n&gt;&gt;&gt; dic.update(dic1)\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12, \'school\': \'stdu\', \'score\': [1, 2, 3]}\n&gt;&gt;&gt;\n\n# ----pop----\n\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12, \'school\': \'stdu\', \'score\': [1, 2, 3]}\n&gt;&gt;&gt; dic.pop(\'score\')\n[1, 2, 3]\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12, \'school\': \'stdu\'}\n&gt;&gt;&gt;\n\n# ----popitem----\n\n&gt;&gt;&gt; dic.popitem()\n(\'school\', \'stdu\')\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12}\n&gt;&gt;&gt;\n\n# ----keys----\n\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12}\n&gt;&gt;&gt; dic.keys()\ndict_keys([\'name\', \'age\'])\n\n# ----values----\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12}\n&gt;&gt;&gt; dic.values()\ndict_values([\'123\', 12])\n&gt;&gt;&gt;\n\n\n# ----items----\n&gt;&gt;&gt; dic\n{\'name\': \'123\', \'age\': 12}\n&gt;&gt;&gt; dic.items()\ndict_items([(\'name\', \'123\'), (\'age\', 12)])\n&gt;&gt;&gt; for k,v in dic.items():\n...     print(\'%s:%s\'%(k,v))\n...\nname:123\nage:12\n\n</code></pre>\n<h2>集合</h2>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/07/image-20220703194110.png\" alt=\"\"></p>\n<pre><code data-language=\"python\" class=\"lang-python\">\n\n# ----创建-----\n&gt;&gt;&gt; s = {\'a\', \'f\', \'d\', \'s\', \'q\'}\n&gt;&gt;&gt; s\n{\'s\', \'d\', \'f\', \'a\', \'q\'}\n&gt;&gt;&gt; type(s)\n&lt;class \'set\'&gt;\n&gt;&gt;&gt; set(\'aaaaaaa\')\n{\'a\'}\n&gt;&gt;&gt;\n\n# ----遍历----\n\n&gt;&gt;&gt; s\n{\'s\', \'d\', \'f\', \'a\', \'q\'}\n&gt;&gt;&gt; for e in s:\n...     print(e, end=\'\')\n...\nsdfaq\n\n# 增删\n\n&gt;&gt;&gt; s = {\'a\'}\n&gt;&gt;&gt; s\n{\'a\'}\n&gt;&gt;&gt; s.add(\'b\')\n&gt;&gt;&gt; s\n{\'a\', \'b\'}\n&gt;&gt;&gt; s.remove(\'a\')\n&gt;&gt;&gt; s\n{\'b\'}\n&gt;&gt;&gt;\n\n#----交集, 并集, 差集----\n\n&gt;&gt;&gt; s1 = {1, 2, 3}\n&gt;&gt;&gt; s2 = {3, 4, 5}\n&gt;&gt;&gt; s1 &amp; s2\n{3}\n&gt;&gt;&gt; s1 | s2\n{1, 2, 3, 4, 5}\n&gt;&gt;&gt; s1 - s2\n{1, 2}\n&gt;&gt;&gt;\n\n</code></pre>\n','> 学习Github开源书籍 [Python-Explore](https://funhacks.gitbooks.io/explore-python/content/) 的学习笔记\n\n\n[TOC]\n\n## 序列\n\n根据索引获取元素, 有一些通用的操作\n\n序列\n- 列表 list\n- 元组 tuple\n- 字符串 string\n\n映射\n- 字典 dict\n\n集合\n- 集合 set\n\n\n![Snipaste_2022-07-03_15-33-48](http://blog.evilemperor.tophttp://blog.evilemperor.top/upload/2022/07/Snipaste_2022-07-03_15-33-48.jpg)\n\n### 索引\n\n获取序列中单个元素\n\n```python\n>>> nums = [1, 2, 3, 4, 5, 6, 7, 8, 9]\n>>> \n>>> # 第0个元素\n>>> nums[0]\n1\n>>> # 最后一个元素\n>>> nums[-1]\n9\n>>> s = \\\'abcdefghijklmn\\\'\n>>> \n>>> # 第0个元素\n>>> s[0]\n\\\'a\\\'\n>>> # 最后一个元素\n>>> s[-1]\n\\\'n\\\'\n```\n\n### 分片\n\n获取序列中的一个区间的元素\n\n`左闭右开`的区间\n\n```python\n>>> nums = [1, 2, 3, 4, 5, 6, 7]\n\n>>> # 访问前面的元素和后面的元素\n>>> nums[0:4]\n[1, 2, 3, 4]\n>>> nums[3:4]\n[4]\n\n# 访问前面和后面的元素\n>>> nums[:3]\n[1, 2, 3]\n>>> nums[-3:]\n[5, 6, 7]\n\n>>> # 整个序列\n>>> nums[:]\n[1, 2, 3, 4, 5, 6, 7]\n>>>\n\n>>> # 使用步长, 默认为1\n>>> nums[1:5]\n[2, 3, 4, 5]\n>>> nums[1:5:1]\n[2, 3, 4, 5]\n>>>\n\n>>> # 对正数步长，从左向右取元素；对负数步长，从右向左取元素\n>>> nums[1:5:-1]\n[]\n>>> nums[5:1:-1]\n[6, 5, 4, 3]\n>>>\n\n>>> # 前两个元素置空\n>>> nums[::-2]\n[7, 5, 3, 1]\n>>> nums[::2]\n[1, 3, 5, 7]\n\n>>> nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]\n>>> nums[:6]\n[1, 2, 3, 4, 5, 6]\n>>> nums[:6:-2]\n[15, 13, 11, 9]\n>>> nums[:6:2]\n[1, 3, 5]\n```\n\n### +\n\n加法就是 `拼接`\n\n```python\n>>> [1, 2, 3] + [4, 5, 6, 7, 8, 9, 10]\n[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]\n\n>>> (1, 2, 3) + (4, 5, 6)\n(1, 2, 3, 4, 5, 6)\n\n>>> \\\'hello\\\' + \\\'world\\\'\n\\\'helloworld\\\'\n```\n\n### x\n\n加法就是 `多次拼接`\n\n```python\n>>> [1, 1, 2] * 3\n[1, 1, 2, 1, 1, 2, 1, 1, 2]\n\n>>> \\\'123\\\' * 3\n\\\'123123123\\\'\n```\n\n### in\n\n就是`在不在序列中`\n\n```python\n>>> 1 in [1, 2, 3, 4, 5]\nTrue\n\n>>> 8 in [1, 2, 3, 4, 5]\nFalse\n\n>>> \\\'el\\\' in \\\'hello\\\'\nTrue\n```\n\n## 列表\n\n![](http://blog.evilemperor.top/upload/2022/07/image-20220703175407.png)\n\n```python\n\n# -----index-----\n\n>>> strs = [\\\'hello\\\', \\\'hi\\\', \\\'nice\\\']\n>>> strs.index(\\\'hi\\\')\n1\n>>> nums = [1, 2, 3, 4, 5]\n>>> nums.index(5)\n4\n\n# -----count-----\n\n>>> nums = [1, 1, 2, 1, 2, 4, 5]\n>>> nums.count(1)\n3\n>>> nums.count(3)\n0\n\n# -----append-----\n\n>>> nums = [1, 2]\n>>> nums\n[1, 2]\n>>> nums.append(3)\n>>> nums\n[1, 2, 3]\n>>> nums.append([4, 5])\n>>> nums\n[1, 2, 3, [4, 5]]\n\n# -----extend-----\n\n>>> a = [1, 1, 1]\n>>> b = [2, 2, 2]\n>>> a\n[1, 1, 1]\n>>> b\n[2, 2, 2]\n>>> a.extend(b)\n>>> a\n[1, 1, 1, 2, 2, 2]\n>>> b.extend(a)\n>>> b\n[2, 2, 2, 1, 1, 1, 2, 2, 2]\n\n# -----insert-----\n\n>>> nums\n[1, 3]\n>>> nums.insert(1, 0)\n>>> nums\n[1, 0, 3]\n\n# -----pop----\n\n>>> nums\n[1, 2, 3, [4, 5]]\n>>> nums.pop()\n[4, 5]\n>>> nums\n[1, 2, 3]\n>>> nums.pop(1)\n2\n>>> nums\n[1, 3]\n\n# -----remove-----\n\n>>> nums\n[3, 0, 1, 3]\n>>> nums.remove(3)\n>>> nums\n[0, 1, 3]\n\n# -----reverse-----\n\n>>> nums\n[1, 0, 3]\n>>> nums.reverse()\n>>> nums\n[3, 0, 1]\n\n# -----sort-----\n\n>>> nums.sort()\n>>> nums\n[0, 0, 1, 1, 3, 3, 5, 6, 23, 33]\n\n>>> nums\n[0, 1, 3, 3, 5, 6, 23, 33, 1, 0]\n>>> sorted(nums)\n[0, 0, 1, 1, 3, 3, 5, 6, 23, 33]\n>>> sorted(nums, reverse=True)\n[33, 23, 6, 5, 3, 3, 1, 1, 0, 0]\n>>>\n\n>>> students\n[(\\\'aaa\\\', 12, 15, 12), (\\\'qww\\\', 45, 12, 55), (\\\'erw\\\', 12, 10, 23), (\\\'asdd\\\', 1, 20, 123), (\\\'qqq\\\', 32, 20, 55), (\\\'bbb\\\', 43, 16, 30)]\n\n# 对第 3 列排序 (从小到大)\n>>> sorted(students, key=lambda student: student[2])\n[(\\\'erw\\\', 12, 10, 23), (\\\'qww\\\', 45, 12, 55), (\\\'aaa\\\', 12, 15, 12), (\\\'bbb\\\', 43, 16, 30), (\\\'asdd\\\', 1, 20, 123), (\\\'qqq\\\', 32, 20, 55)]\n\n# 对第 2 列排序（从小到大），再对第 3 列从大到小排序\n>>> sorted(students, key=lambda student: (student[1], -student[2]))\n[(\\\'asdd\\\', 1, 20, 123), (\\\'aaa\\\', 12, 15, 12), (\\\'erw\\\', 12, 10, 23), (\\\'qqq\\\', 32, 20, 55), (\\\'bbb\\\', 43, 16, 30), (\\\'qww\\\', 45, 12, 55)]\n```\n\n排序  https://wiki.python.org/moin/HowTo/Sorting\n\n\n## 元组\n\n因为是不可变的序列, 也就没有排序什么的操作了\n\n创建\n\n```python\n>>> t = ()\n>>> t\n()\n>>> t = (1,)\n>>> t\n(1,)\n```\n\n## 字符串\n\n![](http://blog.evilemperor.top/upload/2022/07/image-20220703183522.png)\n\n不可变, 不能对他进行赋值等操作\n\n```python\n\n# ----find----\n\n>>> s\n\"when i was young i\\\'d listen to the radio\"\n\n>>> s.find(\\\'i\\\')\n5\n# 指定起始位置\n>>> s.find(\\\'i\\\', 5)\n5\n\n>>> s.find(\\\'i\\\', 6)\n17\n# 指定起始位置和结束位置\n>>> s.find(\\\'i\\\', 6, 15)\n-1\n\n# ----split----\n\n>>> \\\'/root/OS-lab/lab01\\\'.split(\\\'/\\\')\n[\\\'\\\', \\\'root\\\', \\\'OS-lab\\\', \\\'lab01\\\']\n\n# 默认以 所有 空格（空格、制表符、换行等）作为分隔符\n>>> \\\' when i was    young i d listen\\\'.split()\n[\\\'when\\\', \\\'i\\\', \\\'was\\\', \\\'young\\\', \\\'i\\\', \\\'d\\\', \\\'listen\\\']\n\n# 以 空格 作为分隔符\n>>> \\\' when i was    young i d listen\\\'.split(\\\' \\\')\n[\\\'\\\', \\\'when\\\', \\\'i\\\', \\\'was\\\', \\\'\\\', \\\'\\\', \\\'\\\', \\\'young\\\', \\\'i\\\', \\\'d\\\', \\\'listen\\\']\n\n# ----join----\n\n>>> \\\' \\\'.join([\\\'\\\', \\\'root\\\', \\\'OS-lab\\\', \\\'lab01\\\'])\n\\\' root OS-lab lab01\\\'\n\n>>> \\\'/\\\'.join([\\\'\\\', \\\'root\\\', \\\'OS-lab\\\', \\\'lab01\\\'])\n\\\'/root/OS-lab/lab01\\\'\n\n# ----strip----\n\n>>> \\\'     strip string    \\\'.strip()\n\\\'strip string\\\'\n>>> \\\'     strip string    \\\'.rstrip()\n\\\'     strip string\\\'\n>>> \\\'     strip string    \\\'.lstrip()\n\\\'strip string    \\\'\n# 移除所有的字符1和2\n>>> \\\'123     strip string1212\\\'.strip(\\\'12\\\')\n\\\'3     strip string\\\'\n>>>\n\n# ----replace----\n\n>>> \\\'     strip string    \\\'.replace(\\\'s\\\', \\\'S\\\')\n\\\'     Strip String    \\\'\n\n# ----lower/upper----\n\n>>> \\\'Python\\\'.upper()\n\\\'PYTHON\\\'\n>>> \\\'Python\\\'.lower()\n\\\'python\\\'\n>>>\n```\n\n## 字典\n\n![](http://blog.evilemperor.top/upload/2022/07/image-20220703193734.png)\n\n\n创建. 遍历, 判断\n```python\n\n# ----创建----\n\n# 空\n>>> dic = {}\n>>> dic\n{}\n# 字典\n>>> dic = {\\\'name\\\': \\\'yan jingcun\\\', \\\'age\\\': 22}\n>>> dic\n{\\\'name\\\': \\\'yan jingcun\\\', \\\'age\\\': 22}\n# dict函数\n>>> dic = dict(name=\\\'yjc\\\', age=20)\n>>> dic\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 20}\n>>> item = [(\\\'name\\\', \\\'yjc\\\'), (\\\'age\\\', 23)]\n>>> dic = dict(item)\n>>> dic\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 23}\n\n# -----遍历\n\n>>> dic = {\\\'name\\\':\\\'yjc\\\', \\\'age\\\':12}\ney in dic:\n    >>> for key in dic:\n...         print(\\\'%s:%s\\\' %(key, dic[key]))\n...\nname:yjc\nage:12\n>>> dic[\\\'name\\\']\n\\\'yjc\\\'\n\n>>> for key in dic.keys():\n...     if key == \\\'name\\\':\n...             del dic[key]\n>>> dic\n{\\\'age\\\': 12}\n\n# ----判断是否在字典里面\n\n>>> \\\'name\\\' in dic\nTrue\n>>> \\\'ad\\\' in dic\nFalse\n>>>\n\n```\n\n\n```python\n\n# ----clear----\n\n# 直接赋值不能清除dic2的值\n>>> dic1 = {\\\'name\\\':\\\'yjc\\\', \\\'age\\\':12, \\\'school\\\':\\\'stdu\\\'}\n>>> dic2 = dic1\n>>> dic2\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n>>> dic1 = {}\n>>> dic2\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n# clear可以\n>>> dic1\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n>>> dic2\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n>>> dic1.clear()\n>>> dic2\n{}\n>>>\n\n# ----copy----\n\n# 浅拷贝\n>>> dic\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [33, 55, 66]}\n>>> dic1 = dic.copy()\n>>> dic1\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [33, 55, 66]}\n>>> dic1[\\\'name\\\'] = \\\'123\\\'\n>>> dic1[\\\'scores\\\'][0] = 60\n>>> dic\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n>>>\n\n# 深拷贝\n# 对任何对象的操作都不会影响到彼此\n>>> from copy import deepcopy\n>>> dic1 = deepcopy(dic)\n>>> dic1\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n>>> dic[\\\'name\\\'] = \\\'123\\\'\n>>> dic1\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n>>> dic[\\\'name\\\'] = \\\'321\\\'\n>>> dic1\n{\\\'name\\\': \\\'yjc\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n\n# ----get----\n>>> dic\n{\\\'name\\\': \\\'321\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'scores\\\': [60, 55, 66]}\n>>> dic.get(\\\'name\\\')\n\\\'321\\\'\n>>> dic.get(\\\'nam0\\\')\n>>>\n\n# ----setdefault----\n# 当键不存在的时候，setdefault 返回设定的默认值并且更新字典。\n# 当键存在的时候，会返回已有的值，但不会更新字典。\n\n>>> d\n{\\\'name\\\': \\\'123\\\'}\n>>> d = {}\n>>> d.setdefault(\\\'name\\\', \\\'111\\\')\n\\\'111\\\'\n>>> d\n{\\\'name\\\': \\\'111\\\'}\n>>> d.setdefault(\\\'name\\\', \\\'1112\\\')\n\\\'111\\\'\n>>> d\n{\\\'name\\\': \\\'111\\\'}\n\n# ----update----\n\n# 创建\n>>> dic\n{\\\'name\\\': \\\'321\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n>>> dic1 = {\\\'score\\\':[1, 2, 3]}\n>>> dic.update(dic1)\n>>> dic\n{\\\'name\\\': \\\'321\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'score\\\': [1, 2, 3]}\n>>>\n\n#更新\n>>> dic\n{\\\'name\\\': \\\'321\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'score\\\': [1, 2, 3]}\n>>> dic1\n{\\\'name\\\': \\\'123\\\'}\n>>> dic.update(dic1)\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'score\\\': [1, 2, 3]}\n>>>\n\n# ----pop----\n\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\', \\\'score\\\': [1, 2, 3]}\n>>> dic.pop(\\\'score\\\')\n[1, 2, 3]\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12, \\\'school\\\': \\\'stdu\\\'}\n>>>\n\n# ----popitem----\n\n>>> dic.popitem()\n(\\\'school\\\', \\\'stdu\\\')\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12}\n>>>\n\n# ----keys----\n\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12}\n>>> dic.keys()\ndict_keys([\\\'name\\\', \\\'age\\\'])\n\n# ----values----\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12}\n>>> dic.values()\ndict_values([\\\'123\\\', 12])\n>>>\n\n\n# ----items----\n>>> dic\n{\\\'name\\\': \\\'123\\\', \\\'age\\\': 12}\n>>> dic.items()\ndict_items([(\\\'name\\\', \\\'123\\\'), (\\\'age\\\', 12)])\n>>> for k,v in dic.items():\n...     print(\\\'%s:%s\\\'%(k,v))\n...\nname:123\nage:12\n\n```\n\n## 集合\n\n![](http://blog.evilemperor.top/upload/2022/07/image-20220703194110.png)\n\n```python\n\n\n# ----创建-----\n>>> s = {\\\'a\\\', \\\'f\\\', \\\'d\\\', \\\'s\\\', \\\'q\\\'}\n>>> s\n{\\\'s\\\', \\\'d\\\', \\\'f\\\', \\\'a\\\', \\\'q\\\'}\n>>> type(s)\n<class \\\'set\\\'>\n>>> set(\\\'aaaaaaa\\\')\n{\\\'a\\\'}\n>>>\n\n# ----遍历----\n\n>>> s\n{\\\'s\\\', \\\'d\\\', \\\'f\\\', \\\'a\\\', \\\'q\\\'}\n>>> for e in s:\n...     print(e, end=\\\'\\\')\n...\nsdfaq\n\n# 增删\n\n>>> s = {\\\'a\\\'}\n>>> s\n{\\\'a\\\'}\n>>> s.add(\\\'b\\\')\n>>> s\n{\\\'a\\\', \\\'b\\\'}\n>>> s.remove(\\\'a\\\')\n>>> s\n{\\\'b\\\'}\n>>>\n\n#----交集, 并集, 差集----\n\n>>> s1 = {1, 2, 3}\n>>> s2 = {3, 4, 5}\n>>> s1 & s2\n{3}\n>>> s1 | s2\n{1, 2, 3, 4, 5}\n>>> s1 - s2\n{1, 2}\n>>>\n\n```',1,0,'2022-09-01 00:59:18','2022-09-01 00:59:18',0,0,0),('11661994007882','git push 报错无法读取远程仓库',1,4,'报错信息 Administrator@Jancoyan MINGW64 /r/GITHUB/MyNotes (master) $ git push ssh: connect to host githu......','<p>报错信息</p>\n<pre><code>Administrator@Jancoyan MINGW64 /r/GITHUB/MyNotes (master)\n$ git push\nssh: connect to host github.com port 22: Connection refused\nfatal: Could not read from remote repository.\n\nPlease make sure you have the correct access rights\nand the repository exists.\n</code></pre>\n<p>可能是Github的节点挂了</p>\n<p>我在host里面改了DNS解析, 筛查之后发现是节点挂了.</p>\n<p>更换了节点之后发现可以了.</p>\n','报错信息\n\n```\nAdministrator@Jancoyan MINGW64 /r/GITHUB/MyNotes (master)\n$ git push\nssh: connect to host github.com port 22: Connection refused\nfatal: Could not read from remote repository.\n\nPlease make sure you have the correct access rights\nand the repository exists.\n```\n\n可能是Github的节点挂了\n\n我在host里面改了DNS解析, 筛查之后发现是节点挂了.\n\n更换了节点之后发现可以了.',1,0,'2022-09-01 01:00:08','2022-09-01 01:00:08',2,1,0),('11661994040088','vim使用vim-plug实现插件管理',1,4,'起初是想实现 vim的自动补全功能的, 但是无意间发现了这个管理vim中插件的方法  vim https://github.com/vim/vim.git vim插件管理软件  vim-plug ht......','<p>起初是想实现 vim的自动补全功能的, 但是无意间发现了这个管理vim中插件的方法</p>\n<blockquote>\n<p>vim https://github.com/vim/vim.git<br>\nvim插件管理软件  vim-plug https://github.com/junegunn/vim-plug<br>\n下载完管理插件的之后 从这里下载插件 https://vimawesome.com/</p>\n</blockquote>\n<h2>Vim8.1 的安装</h2>\n<h3>卸载旧版本Vim8.0</h3>\n<ol>\n<li>查看所有与 vim 有关的包</li>\n</ol>\n<p><code data-backticks=\"1\">dpkg -l | grep vim</code></p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">➜  ~ dpkg -l | grep vim\nii  vim                                    2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor\nii  vim-common                             2:8.0.1453-1ubuntu1.8               all          Vi IMproved - Common files\nrc  vim-nox                                2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor - with scripting languages support\nii  vim-runtime                            2:8.0.1453-1ubuntu1.8               all          Vi IMproved - Runtime files\nrc  vim-tiny                               2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor - compact version\n</code></pre>\n<ol start=\"2\">\n<li>删除所有的相关包</li>\n</ol>\n<p><code data-backticks=\"1\">sudo apt-get remove vim vim-runtime vim-common</code></p>\n<h3>安装vim8.1</h3>\n<pre><code data-language=\"bash\" class=\"lang-bash\">git clone https://github.com/vim/vim.git\n\ncd vim\n\n./configure\n\nsudo make\n\nsudo make install\n</code></pre>\n<h2>vim-plug的安装</h2>\n<blockquote>\n<p>简单翻译一下这个repo的readme.md</p>\n</blockquote>\n<h3>安装</h3>\n<p>下载 <code data-backticks=\"1\">plug.vim</code> 到vim目录, 我的是<code data-backticks=\"1\">/usr/share/vim/vim80/autoload</code>, 直接从<a href=\"https://github.com/junegunn/vim-plug/blob/master/plug.vim\">github的plug.vim</a>中复制然后编辑文件也可以.</p>\n<p>如果没有这个文件夹, 就从用户文件夹中创建<code data-backticks=\"1\">.vimrc/autoload</code> 在这个文件夹中进行操作</p>\n<h3>使用</h3>\n<p>在<code data-backticks=\"1\">~/.vimrc</code>中添加vim插件的片段</p>\n<p>例子如下:</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">call plug#begin()\n\nPlug \'junegunn/vim-easy-align\'\n\nPlug \'SirVer/ultisnips\' | Plug \'honza/vim-snippets\'\n\nPlug \'rdnetto/YCM-Generator\', { \'branch\': \'stable\' }\n\nPlug \'nsf/gocode\', { \'tag\': \'v.20150303\', \'rtp\': \'vim\' }\n\nPlug \'junegunn/fzf\', { \'dir\': \'~/.fzf\', \'do\': \'./install --all\' }\n\nPlug \'~/my-prototype-plugin\'\n\ncall plug#end()\n</code></pre>\n<h3>举例 -- 安装 Nerd Tree 插件</h3>\n<p>打开 https://vimawesome.com/plugin/nerdtree-red</p>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640264136.png\" alt=\"图片-1655640264136\"></p>\n<p>在 <code data-backticks=\"1\">.vimrc</code> 中加入 <code data-backticks=\"1\">Plug \'scrooloose/nerdtree\'</code></p>\n<p>然后在vim编辑器中运行命令</p>\n<p><code data-backticks=\"1\">:source %</code><br>\n<code data-backticks=\"1\">:PlugInstall</code></p>\n<p>如图</p>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640274409.png\" alt=\"图片-1655640274409\"></p>\n<p>输入PlugInstall命令之后显示, 成功</p>\n<p><img style=\"max-width:70%\" src=\"http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640281093.png\" alt=\"图片-1655640281093\"></p>\n<p>在<code data-backticks=\"1\">/vimrc</code> 中添加 <code data-backticks=\"1\">autocmd VimEnter * NERDTree</code> 设置自启动</p>\n<h3>卸载插件</h3>\n<p>先删除vimrc 中的Plug命令, 再在vim中执行命令 <code data-backticks=\"1\">:PlugClean</code></p>\n','起初是想实现 vim的自动补全功能的, 但是无意间发现了这个管理vim中插件的方法\n\n> vim https://github.com/vim/vim.git\n> vim插件管理软件  vim-plug https://github.com/junegunn/vim-plug\n> 下载完管理插件的之后 从这里下载插件 https://vimawesome.com/\n\n## Vim8.1 的安装\n\n### 卸载旧版本Vim8.0\n\n1. 查看所有与 vim 有关的包\n\n`dpkg -l | grep vim`\n\n```bash\n➜  ~ dpkg -l | grep vim\nii  vim                                    2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor\nii  vim-common                             2:8.0.1453-1ubuntu1.8               all          Vi IMproved - Common files\nrc  vim-nox                                2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor - with scripting languages support\nii  vim-runtime                            2:8.0.1453-1ubuntu1.8               all          Vi IMproved - Runtime files\nrc  vim-tiny                               2:8.0.1453-1ubuntu1.8               amd64        Vi IMproved - enhanced vi editor - compact version\n```\n\n2. 删除所有的相关包\n\n`sudo apt-get remove vim vim-runtime vim-common`\n\n### 安装vim8.1\n\n\n```bash\ngit clone https://github.com/vim/vim.git\n\ncd vim\n\n./configure\n\nsudo make\n\nsudo make install\n```\n\n\n## vim-plug的安装\n\n> 简单翻译一下这个repo的readme.md\n\n### 安装\n\n下载 `plug.vim` 到vim目录, 我的是`/usr/share/vim/vim80/autoload`, 直接从[github的plug.vim](https://github.com/junegunn/vim-plug/blob/master/plug.vim)中复制然后编辑文件也可以.\n\n如果没有这个文件夹, 就从用户文件夹中创建`.vimrc/autoload` 在这个文件夹中进行操作 \n\n### 使用\n\n在`~/.vimrc`中添加vim插件的片段\n\n例子如下: \n\n```bash\ncall plug#begin()\n\nPlug \\\'junegunn/vim-easy-align\\\'\n\nPlug \\\'SirVer/ultisnips\\\' | Plug \\\'honza/vim-snippets\\\'\n\nPlug \\\'rdnetto/YCM-Generator\\\', { \\\'branch\\\': \\\'stable\\\' }\n\nPlug \\\'nsf/gocode\\\', { \\\'tag\\\': \\\'v.20150303\\\', \\\'rtp\\\': \\\'vim\\\' }\n\nPlug \\\'junegunn/fzf\\\', { \\\'dir\\\': \\\'~/.fzf\\\', \\\'do\\\': \\\'./install --all\\\' }\n\nPlug \\\'~/my-prototype-plugin\\\'\n\ncall plug#end()\n```\n\n### 举例 -- 安装 Nerd Tree 插件\n\n打开 https://vimawesome.com/plugin/nerdtree-red \n\n![图片-1655640264136](http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640264136.png)\n\n在 `.vimrc` 中加入 `Plug \\\'scrooloose/nerdtree\\\'`\n\n然后在vim编辑器中运行命令 \n\n`:source %`\n`:PlugInstall`\n\n如图\n\n![图片-1655640274409](http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640274409.png)\n\n输入PlugInstall命令之后显示, 成功\n\n![图片-1655640281093](http://blog.evilemperor.top/upload/2022/06/%E5%9B%BE%E7%89%87-1655640281093.png)\n\n在`/vimrc` 中添加 `autocmd VimEnter * NERDTree` 设置自启动\n\n### 卸载插件\n\n先删除vimrc 中的Plug命令, 再在vim中执行命令 `:PlugClean`',1,0,'2022-09-01 01:00:40','2022-09-01 01:00:40',3,0,0),('11661994061469','zsh安装与配置',1,4,'apt安装zsh 安装zsh sudo apt install zsh 切换shell为zsh chsh -s %(which zsh) 查看当前shell echo $SHELL 源码安装zsh 下......','<h2>apt安装zsh</h2>\n<h3>安装zsh</h3>\n<p><code data-backticks=\"1\">sudo apt install zsh</code></p>\n<h3>切换shell为zsh</h3>\n<p><code data-backticks=\"1\">chsh -s %(which zsh)</code></p>\n<h3>查看当前shell</h3>\n<p><code data-backticks=\"1\">echo $SHELL</code></p>\n<h2>源码安装zsh</h2>\n<h3>下载zsh</h3>\n<pre><code>wget https://nchc.dl.sourceforge.net/project/zsh/zsh/5.8/zsh-5.8.tar.xz\n</code></pre>\n<h3>解压并安装</h3>\n<pre><code>解压\ntar xvf zsh-5.8.tar.xz\n安装\ncd zsh-5.8\n./configure --prefix=$HOME/.local\nmake &amp;&amp; make install\n</code></pre>\n<h3>配置环境变量</h3>\n<pre><code>vim .bashrc\n添加\nexport PATH=$PATH:$HOME/.local/bin	# 设置环境变量\nexport SHELL=`which zsh`      		# 设置$SHELL为zsh\nexec `which zsh` -l           		# 设置登录为zsh\n</code></pre>\n<h2>配置zsh</h2>\n<h3>安装oh-my-zsh</h3>\n<ul>\n<li>从github安装</li>\n</ul>\n<pre><code data-language=\"bash\" class=\"lang-bash\"> wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh\n \n zsh install.sh\n</code></pre>\n<ul>\n<li>\n<p>从gitee安装</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">1.下载安装文件\nwget https://gitee.com/mirrors/oh-my-zsh/raw/master/tools/install.sh\n\n2.修改install.sh文件内容\n找到以下内容\n----------------------------------------------------------------\n# Default settings\nZSH=${ZSH:-~/.oh-my-zsh}\nREPO=${REPO:-ohmyzsh/ohmyzsh}\nREMOTE=${REMOTE:-https://github.com/${REPO}.git}\nBRANCH=${BRANCH:-master}\n----------------------------------------------------------------\n将其中的第3，4行替换为以下内容\n----------------------------------------------------------------\nREPO=${REPO:-mirrors/oh-my-zsh}\nREMOTE=${REMOTE:-https://gitee.com/${REPO}.git}\n----------------------------------------------------------------\n3.保存文件，赋予执行权限\n4.执行\n5.修改仓库地址\ncd ~/.oh-my-zsh\ngit remote set-url origin https://gitee.com/mirrors/oh-my-zsh.git\ngit pull\n6.结束\n</code></pre>\n</li>\n</ul>\n<h3>安装插件</h3>\n<ul>\n<li>autojump</li>\n<li>autosuggestioins</li>\n<li>syntax-highlighting</li>\n</ul>\n<h3>安装autjump</h3>\n<pre><code data-language=\"bash\" class=\"lang-bash\">#.下载插件autojump到/.oh-my-zsh/custom目录中 \ngit clone https://gitee.com/haha-web/autojump.git $ZSH_CUSTOM/plugins/autojump \n\n#.到目录autojump中 \ncd $ZSH_CUSTOM/plugins/autojump \n\n#执行install.py \n./install.py\n</code></pre>\n<p>在Gitee上直接搜一个就可以了</p>\n<p>install完之后会提示在zshrc上添加一段(会提示的)</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">[[ -s /home/jancoyan/.autojump/etc/profile.d/autojump.sh ]] &amp;&amp; source /home/jancoyan/.autojump/etc/profile.d/autojump.sh\n\nautoload -U compinit &amp;&amp; compinit -u  \n</code></pre>\n<h3>安装autosuggestioins</h3>\n<pre><code data-language=\"bash\" class=\"lang-bash\">git clone git://github.com/zsh-users/zsh-autosuggestions $ZSH_CUSTOM/plugins/zsh-autosuggestions\n</code></pre>\n<pre><code data-language=\"bash\" class=\"lang-bash\">git clone https://gitee.com/phpxxo/zsh-autosuggestions.git ${ZSH_CUSTOM}/plugins/zsh-autosuggestions\n</code></pre>\n<h3>安装syntax-highlighting</h3>\n<pre><code data-language=\"bash\" class=\"lang-bash\">github\ngit clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting\n\ngitee\ngit clone https://gitee.com/wxzxingtian/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting\n</code></pre>\n<h3>加载插件</h3>\n<pre><code>$ vim ~/.zshrc\n修改以下内容\nplugins=(git)为\n\nplugins=(git extract zsh-autosuggestions zsh-syntax-highlighting)\n\n$ source~/.zshrc\n</code></pre>\n<hr>\n<p><a href=\"https://www.wangt.cc//2021/11/%E5%AE%89%E8%A3%85%E4%B8%8E%E9%85%8D%E7%BD%AEzsh/\">参考文章: 安装与配置zsh</a></p>\n','## apt安装zsh\n\n### 安装zsh\n\n`sudo apt install zsh`\n\n### 切换shell为zsh\n\n`chsh -s %(which zsh)`\n\n### 查看当前shell\n\n`echo $SHELL`\n\n\n## 源码安装zsh\n\n### 下载zsh\n\n```\nwget https://nchc.dl.sourceforge.net/project/zsh/zsh/5.8/zsh-5.8.tar.xz\n```\n\n### 解压并安装\n\n```\n解压\ntar xvf zsh-5.8.tar.xz\n安装\ncd zsh-5.8\n./configure --prefix=$HOME/.local\nmake && make install\n```\n\n### 配置环境变量\n\n```\nvim .bashrc\n添加\nexport PATH=$PATH:$HOME/.local/bin	# 设置环境变量\nexport SHELL=`which zsh`      		# 设置$SHELL为zsh\nexec `which zsh` -l           		# 设置登录为zsh\n```\n\n## 配置zsh\n\n### 安装oh-my-zsh\n\n-   从github安装\n\n```bash\n wget https://github.com/robbyrussell/oh-my-zsh/raw/master/tools/install.sh\n \n zsh install.sh\n```\n\n-   从gitee安装\n\n    ```bash\n    1.下载安装文件\n    wget https://gitee.com/mirrors/oh-my-zsh/raw/master/tools/install.sh\n    \n    2.修改install.sh文件内容\n    找到以下内容\n    ----------------------------------------------------------------\n    # Default settings\n    ZSH=${ZSH:-~/.oh-my-zsh}\n    REPO=${REPO:-ohmyzsh/ohmyzsh}\n    REMOTE=${REMOTE:-https://github.com/${REPO}.git}\n    BRANCH=${BRANCH:-master}\n    ----------------------------------------------------------------\n    将其中的第3，4行替换为以下内容\n    ----------------------------------------------------------------\n    REPO=${REPO:-mirrors/oh-my-zsh}\n    REMOTE=${REMOTE:-https://gitee.com/${REPO}.git}\n    ----------------------------------------------------------------\n    3.保存文件，赋予执行权限\n    4.执行\n    5.修改仓库地址\n    cd ~/.oh-my-zsh\n    git remote set-url origin https://gitee.com/mirrors/oh-my-zsh.git\n    git pull\n    6.结束\n    ```\n\n### 安装插件\n\n-   autojump\n-   autosuggestioins\n-   syntax-highlighting\n\n### 安装autjump\n\n```bash\n#.下载插件autojump到/.oh-my-zsh/custom目录中 \ngit clone https://gitee.com/haha-web/autojump.git $ZSH_CUSTOM/plugins/autojump \n\n#.到目录autojump中 \ncd $ZSH_CUSTOM/plugins/autojump \n\n#执行install.py \n./install.py\n```\n\n在Gitee上直接搜一个就可以了\n\ninstall完之后会提示在zshrc上添加一段(会提示的)\n\n```bash\n[[ -s /home/jancoyan/.autojump/etc/profile.d/autojump.sh ]] && source /home/jancoyan/.autojump/etc/profile.d/autojump.sh\n\nautoload -U compinit && compinit -u  \n```\n\n\n\n### 安装autosuggestioins\n\n```bash\ngit clone git://github.com/zsh-users/zsh-autosuggestions $ZSH_CUSTOM/plugins/zsh-autosuggestions\n```\n\n```bash\ngit clone https://gitee.com/phpxxo/zsh-autosuggestions.git ${ZSH_CUSTOM}/plugins/zsh-autosuggestions\n```\n\n### 安装syntax-highlighting\n\n```bash\ngithub\ngit clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting\n\ngitee\ngit clone https://gitee.com/wxzxingtian/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting\n```\n\n### 加载插件\n\n```\n$ vim ~/.zshrc\n修改以下内容\nplugins=(git)为\n\nplugins=(git extract zsh-autosuggestions zsh-syntax-highlighting)\n\n$ source~/.zshrc\n```\n\n---\n\n[参考文章: 安装与配置zsh](https://www.wangt.cc//2021/11/%E5%AE%89%E8%A3%85%E4%B8%8E%E9%85%8D%E7%BD%AEzsh/)',1,0,'2022-09-01 01:01:01','2022-09-01 01:01:01',8,0,0),('11661994080306','ubuntu卸载预装openjdk并安装jdk17',1,4,'1.卸载openjdk sudo apt-get remove openjdk-11-jdk  2.卸载jre sudo apt-get remove openjdk-11-jre-headless ......','<p><strong>1.卸载openjdk</strong></p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">sudo apt-get remove openjdk-11-jdk\n</code></pre>\n<p><strong>2.卸载jre</strong></p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">sudo apt-get remove openjdk-11-jre-headless \n</code></pre>\n<h3>3.安装并配置java 环境</h3>\n<p>1).进入/usr/lib/目录创建jvm文件夹</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">sudo mkdir /usr/libjvm\n</code></pre>\n<p>2).解压jdk安装包到/usr/lib/jvm目录下</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">tar -xzvf jdk-17_linux-x64_bin.tar.gz -C /usr/lib/jvm\n</code></pre>\n<p><strong>3).配置环境变量</strong></p>\n<p><strong>修改/etc/profile文件</strong></p>\n<pre><code data-language=\"bash\" class=\"lang-bash\"> sudo vim /etc/profile\n</code></pre>\n<p>在文件首部或末尾添加如下信息：</p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">export JAVA_HOME=/usr/lib/jvm/jdk-17.0.3.1\nexport PATH=${JAVA_HOME}/bin:${PATH}\n</code></pre>\n<p>保存并退出。</p>\n<p><strong>4).使Java环境生效</strong></p>\n<pre><code data-language=\"bash\" class=\"lang-bash\">source /etc/profile\n</code></pre>\n','**1.卸载openjdk**\n\n```bash\nsudo apt-get remove openjdk-11-jdk\n```\n\n**2.卸载jre**\n\n```bash\nsudo apt-get remove openjdk-11-jre-headless \n```\n\n### 3.安装并配置java 环境\n\n1).进入/usr/lib/目录创建jvm文件夹\n\n```bash\nsudo mkdir /usr/libjvm\n```\n\n2).解压jdk安装包到/usr/lib/jvm目录下\n\n```bash\ntar -xzvf jdk-17_linux-x64_bin.tar.gz -C /usr/lib/jvm\n```\n\n**3).配置环境变量**\n\n**修改/etc/profile文件**\n\n```bash\n sudo vim /etc/profile\n```\n\n在文件首部或末尾添加如下信息：\n\n```bash\nexport JAVA_HOME=/usr/lib/jvm/jdk-17.0.3.1\nexport PATH=${JAVA_HOME}/bin:${PATH}\n```\n\n保存并退出。\n\n**4).使Java环境生效**\n\n```bash\nsource /etc/profile\n```\n',1,0,'2022-09-01 01:01:20','2022-09-01 01:01:20',22,2,1),('11662331875335','markdown样式测试',1,5,'markdown样式测试','<h1>markdown样式测试</h1>\n<h1>标题一</h1>\n<h2>标题二</h2>\n<h3>标题三</h3>\n<h4>标题四</h4>\n<h5>标题五</h5>\n<h6>标题六</h6>\n<p>正文</p>\n<p><strong>粗体内容</strong></p>\n<p><em>斜体内容</em></p>\n<pre><code data-language=\"c++\" class=\"lang-c++\">int a = 0;\n\nint b = 1;\n\nint c = 0;\n\nc = a + b\n</code></pre>\n<blockquote>\n<p>请在这里输入引用内容<br>\n引用引用引用引用引用引用引用引用</p>\n<p>引用</p>\n</blockquote>\n<ol>\n<li>有序列表内容\n<ol>\n<li>asnknv</li>\n<li>nsicndfviokvfv\n<ol>\n<li>asnckdsvnf</li>\n<li>sdbkcjdfvb</li>\n<li>dsvidvdf\n<ol>\n<li>ancdsonv</li>\n<li>ssbcjksdnvkj\n<ol>\n<li>sancdsovndv\n<ol>\n<li>sdbjhk</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n<li>xzcvfbg</li>\n</ol>\n<ul>\n<li>asncofdnv\n<ul>\n<li>ncjdvndfjk\n<ul>\n<li>sdnvifdhbe\n<ul>\n<li>c jdv dfvfdvn fkj\n<ul>\n<li>hjsbvdfvnfi</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n<h1>标题一</h1>\n<h2>标题二</h2>\n<h3>标题三</h3>\n<h4>标题四</h4>\n<h5>标题五</h5>\n<h6>标题六</h6>\n<p>正文</p>\n<p><strong>粗体</strong></p>\n<p><em>斜体</em></p>\n<p><s>删除线</s></p>\n<blockquote>\n<p>markdown的下划线和链接样式冲突，只能用html的形式实现下划线，所以不提供这个功能</p>\n</blockquote>\n<ol>\n<li>有序列表内容\n<ol>\n<li>asnknv</li>\n<li>nsicndfviokvfv\n<ol>\n<li>asnckdsvnf</li>\n<li>sdbkcjdfvb</li>\n<li>dsvidvdf\n<ol>\n<li>ancdsonv</li>\n<li>ssbcjksdnvkj\n<ol>\n<li>sancdsovndv\n<ol>\n<li>sdbjhk</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n</ol>\n</li>\n<li>xzcvfbg</li>\n</ol>\n<hr>\n<ul>\n<li>asncofdnv\n<ul>\n<li>ncjdvndfjk\n<ul>\n<li>sdnvifdhbe\n<ul>\n<li>c jdv dfvfdvn fkj\n<ul>\n<li>hjsbvdfvnfi</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n</li>\n</ul>\n<hr>\n<ul>\n<li>任务列表</li>\n<li>任务列表2</li>\n</ul>\n<blockquote>\n<p>引用1</p>\n<p>引用</p>\n<p>引用</p>\n</blockquote>\n<pre><code data-language=\"js\" class=\"lang-js\">window.addEventListener(\'scroll\', () =&gt; {\n const bottom = dataSectionEl.getBoundingClientRect().bottom;\n const top = dataSectionEl.getBoundingClientRect().top;\n\n if(bottom &gt;= 0 &amp;&amp; top &lt;= window.innerHeight){\n dataSectionEl.style.backgroundPosition = `center calc(50% - ${bottom / 5}px)`\n }\n});\n</code></pre>\n','# markdown样式测试\n\n# 标题一\n\n\n\n## 标题二\n\n\n\n### 标题三\n\n\n\n#### 标题四\n\n\n\n##### 标题五\n\n\n\n###### 标题六\n\n正文\n\n**粗体内容**\n\n_斜体内容_\n\n```c++\nint a = 0;\n\nint b = 1;\n\nint c = 0;\n\nc = a + b\n```\n\n> 请在这里输入引用内容  \n> 引用引用引用引用引用引用引用引用\n> \n> 引用\n\n\n1.  有序列表内容\n    1.  asnknv\n    2.  nsicndfviokvfv\n        1.  asnckdsvnf\n        2.  sdbkcjdfvb\n        3.  dsvidvdf\n            1.  ancdsonv\n            2.  ssbcjksdnvkj\n                1.  sancdsovndv\n                    1.  sdbjhk\n2.  xzcvfbg\n\n-   asncofdnv\n    -   ncjdvndfjk\n        -   sdnvifdhbe\n            -   c jdv dfvfdvn fkj\n                -   hjsbvdfvnfi\n\n\n# 标题一\n\n\n## 标题二\n\n\n### 标题三\n\n\n#### 标题四\n\n\n##### 标题五\n\n\n###### 标题六\n\n正文\n\n**粗体**\n\n_斜体_\n\n~~删除线~~\n\n> markdown的下划线和链接样式冲突，只能用html的形式实现下划线，所以不提供这个功能\n\n1.  有序列表内容\n    1.  asnknv\n    2.  nsicndfviokvfv\n        1.  asnckdsvnf\n        2.  sdbkcjdfvb\n        3.  dsvidvdf\n            1.  ancdsonv\n            2.  ssbcjksdnvkj\n                1.  sancdsovndv\n                    1.  sdbjhk\n2.  xzcvfbg\n\n---\n\n-   asncofdnv\n    -   ncjdvndfjk\n        -   sdnvifdhbe\n            -   c jdv dfvfdvn fkj\n                -   hjsbvdfvnfi\n\n---\n\n-   任务列表\n-   任务列表2\n\n> 引用1\n> \n> 引用\n> \n> 引用\n\n```js\nwindow.addEventListener(\\\'scroll\\\', () => {\n const bottom = dataSectionEl.getBoundingClientRect().bottom;\n const top = dataSectionEl.getBoundingClientRect().top;\n\n if(bottom >= 0 && top <= window.innerHeight){\n dataSectionEl.style.backgroundPosition = `center calc(50% - ${bottom / 5}px)`\n }\n});\n```',1,1,'2022-09-04 22:51:15','2022-09-04 22:51:15',25,1,0);
/*!40000 ALTER TABLE `tbl_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_article_image`
--

DROP TABLE IF EXISTS `tbl_article_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_article_image` (
  `filename` varchar(40) NOT NULL,
  `article_id` varchar(25) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  PRIMARY KEY (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_article_image`
--

LOCK TABLES `tbl_article_image` WRITE;
/*!40000 ALTER TABLE `tbl_article_image` DISABLE KEYS */;
INSERT INTO `tbl_article_image` VALUES ('%E5%9B%BE%E7%89%87-1656853707216.png','11661993851324','2022-09-01 00:57:31'),('%E5%9B%BE%E7%89%87-1658236384078.png','11661992312179','2022-09-01 00:31:52'),('image-20220703175407.png','11661993957585','2022-09-01 00:59:18'),('image-20220703183522.png','11661993957585','2022-09-01 00:59:18'),('image-20220703193734.png','11661993957585','2022-09-01 00:59:18'),('image-20220703194110.png','11661993957585','2022-09-01 00:59:18'),('Snipaste_2022-07-03_15-33-48.jpg','11661993957585','2022-09-01 00:59:18');
/*!40000 ALTER TABLE `tbl_article_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `pre_comment_id` int DEFAULT NULL,
  `comment_article_id` varchar(25) DEFAULT NULL,
  `comment_author_id` int DEFAULT NULL,
  `comment_author_name` varchar(25) DEFAULT NULL,
  `comment_author_email` varchar(55) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  `comment_author_ip` varchar(15) DEFAULT NULL,
  `comment_like_count` int DEFAULT '0',
  PRIMARY KEY (`comment_id`),
  KEY `tbl_comment___fk_article_id` (`comment_article_id`),
  CONSTRAINT `tbl_comment___fk_article_id` FOREIGN KEY (`comment_article_id`) REFERENCES `tbl_article` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_comment`
--

LOCK TABLES `tbl_comment` WRITE;
/*!40000 ALTER TABLE `tbl_comment` DISABLE KEYS */;
INSERT INTO `tbl_comment` VALUES (1,NULL,'11661994080306',1,'admin',NULL,'测试一下子评论','2022-09-01 01:17:52','http',0),(2,NULL,'11661994080306',1,'admin',NULL,'再测试一下评论','2022-09-01 01:18:04','http',0),(3,NULL,'11661994007882',1,'admin',NULL,'换个文章评论一下','2022-09-01 01:18:17','http',0),(4,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试1','2022-09-01 01:18:48','http',0),(5,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试2','2022-09-01 01:18:51','http',0),(6,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试3','2022-09-01 01:18:55','http',0),(7,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试4','2022-09-01 01:18:59','http',0),(8,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试5','2022-09-01 01:19:08','http',0),(9,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试6','2022-09-01 01:19:11','http',0),(10,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试7','2022-09-01 01:19:22','http',0),(11,NULL,'11661993724268',1,'admin',NULL,'评论翻页测试8','2022-09-01 01:19:25','http',0),(12,NULL,'11662331875335',1,'admin',NULL,'今天的测试评论','2022-09-08 01:09:12','http',0);
/*!40000 ALTER TABLE `tbl_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_deleted_article`
--

DROP TABLE IF EXISTS `tbl_deleted_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_deleted_article` (
  `article_id` varchar(25) NOT NULL,
  `article_title` varchar(255) DEFAULT NULL,
  `article_author` int DEFAULT NULL,
  `article_type` int DEFAULT NULL,
  `article_summary` varchar(255) DEFAULT NULL,
  `article_html` longtext,
  `article_md` longtext,
  `article_is_comment` tinyint DEFAULT '1',
  `article_rank` tinyint DEFAULT NULL,
  `article_post_time` datetime DEFAULT NULL,
  `article_edit_time` datetime DEFAULT NULL,
  `article_view_count` int DEFAULT '0',
  `article_comment_count` int DEFAULT NULL,
  `article_like_count` int DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_deleted_article`
--

LOCK TABLES `tbl_deleted_article` WRITE;
/*!40000 ALTER TABLE `tbl_deleted_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_deleted_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_deleted_comment`
--

DROP TABLE IF EXISTS `tbl_deleted_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_deleted_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `pre_comment_id` int DEFAULT NULL,
  `comment_article_id` varchar(25) DEFAULT NULL,
  `comment_author_id` int DEFAULT NULL,
  `comment_author_name` varchar(25) DEFAULT NULL,
  `comment_author_email` varchar(55) DEFAULT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `comment_date` datetime DEFAULT NULL,
  `comment_author_ip` varchar(15) DEFAULT NULL,
  `comment_like_count` int DEFAULT '0',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_deleted_comment`
--

LOCK TABLES `tbl_deleted_comment` WRITE;
/*!40000 ALTER TABLE `tbl_deleted_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_deleted_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_like_record`
--

DROP TABLE IF EXISTS `tbl_like_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_like_record` (
  `like_id` int NOT NULL AUTO_INCREMENT,
  `article_id` varchar(25) DEFAULT NULL,
  `author_id` int DEFAULT NULL,
  `like_date` datetime DEFAULT NULL,
  PRIMARY KEY (`like_id`),
  KEY `tbl_like_record___fk_article_id` (`article_id`),
  CONSTRAINT `tbl_like_record___fk_article_id` FOREIGN KEY (`article_id`) REFERENCES `tbl_article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_like_record`
--

LOCK TABLES `tbl_like_record` WRITE;
/*!40000 ALTER TABLE `tbl_like_record` DISABLE KEYS */;
INSERT INTO `tbl_like_record` VALUES (2,'11661994080306',1,'2022-09-01 01:29:05');
/*!40000 ALTER TABLE `tbl_like_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_role` (
  `role_id` tinyint NOT NULL,
  `role_name` varchar(35) DEFAULT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role`
--

LOCK TABLES `tbl_role` WRITE;
/*!40000 ALTER TABLE `tbl_role` DISABLE KEYS */;
INSERT INTO `tbl_role` VALUES (0,'admin',NULL),(1,'user',NULL),(2,'user',NULL);
/*!40000 ALTER TABLE `tbl_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_type`
--

DROP TABLE IF EXISTS `tbl_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_type` (
  `type_id` int NOT NULL,
  `type_name` varchar(20) DEFAULT NULL,
  `type_description` varchar(55) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_type`
--

LOCK TABLES `tbl_type` WRITE;
/*!40000 ALTER TABLE `tbl_type` DISABLE KEYS */;
INSERT INTO `tbl_type` VALUES (1,'前端',NULL),(2,'后端',NULL),(3,'Python',NULL),(4,'Linux',NULL),(5,'效率软件',NULL);
/*!40000 ALTER TABLE `tbl_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `user_signature` varchar(50) DEFAULT NULL,
  `user_password` varchar(32) DEFAULT NULL,
  `user_role` tinyint DEFAULT NULL,
  `user_create_date` datetime DEFAULT NULL,
  `user_last_login_date` datetime DEFAULT NULL,
  `user_ip` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `tbl_user_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user`
--

LOCK TABLES `tbl_user` WRITE;
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` VALUES (1,'admin','Hello World','310dcbbf4cce62f762a2aaa148d556bd',0,'2022-08-30 14:10:33','2022-09-08 00:03:56','0:0:0:0:0:0:0:1'),(15,'user','Hello World','310dcbbf4cce62f762a2aaa148d556bd',2,'2022-09-05 01:03:18','2022-09-05 01:32:38','127.0.0.1');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_info`
--

DROP TABLE IF EXISTS `tbl_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user_info` (
  `user_id` int NOT NULL,
  `user_email` varchar(30) DEFAULT NULL,
  `user_sex` int DEFAULT NULL,
  `user_region` varchar(40) DEFAULT NULL,
  `user_birthdate` date DEFAULT NULL,
  `user_telephone` varchar(11) DEFAULT NULL,
  `user_real_name` varchar(10) DEFAULT NULL,
  `user_school` varchar(20) DEFAULT NULL,
  `user_major` varchar(20) DEFAULT NULL,
  `user_enter_school_date` date DEFAULT NULL,
  `user_academic_degree` varchar(10) DEFAULT NULL,
  `user_company` varchar(20) DEFAULT NULL,
  `user_position` varchar(10) DEFAULT NULL,
  `user_field` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_info`
--

LOCK TABLES `tbl_user_info` WRITE;
/*!40000 ALTER TABLE `tbl_user_info` DISABLE KEYS */;
INSERT INTO `tbl_user_info` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tbl_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_login`
--

DROP TABLE IF EXISTS `tbl_user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user_login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login_user` int DEFAULT NULL,
  `login_date` datetime DEFAULT NULL,
  `login_ip` varchar(15) DEFAULT NULL,
  `login_address` varchar(30) DEFAULT NULL,
  `user_agent` varchar(150) DEFAULT NULL,
  `browser_name` varchar(15) DEFAULT NULL,
  `browser_version` varchar(20) DEFAULT NULL,
  `os_name` varchar(10) DEFAULT NULL,
  `os_version` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_login`
--

LOCK TABLES `tbl_user_login` WRITE;
/*!40000 ALTER TABLE `tbl_user_login` DISABLE KEYS */;
INSERT INTO `tbl_user_login` VALUES (1,1,'2022-08-30 14:19:12','0:0:0:0:0:0:0:1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(2,1,'2022-08-30 14:25:25','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(3,1,'2022-08-30 14:34:43','127.0.0.1',NULL,'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(4,1,'2022-09-01 00:08:12','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(5,1,'2022-09-01 00:14:08','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(6,1,'2022-09-01 00:34:23','0:0:0:0:0:0:0:1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(7,1,'2022-09-02 00:17:37','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(8,1,'2022-09-02 01:05:49','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(9,1,'2022-09-02 02:19:01','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(10,1,'2022-09-02 02:19:37','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(11,1,'2022-09-04 22:50:54','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(12,15,'2022-09-05 01:08:47','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(13,15,'2022-09-05 01:21:35','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(14,15,'2022-09-05 01:32:31','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(15,15,'2022-09-05 01:32:38','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(16,1,'2022-09-06 02:12:53','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(17,1,'2022-09-07 01:39:27','0:0:0:0:0:0:0:1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64'),(18,1,'2022-09-08 00:03:57','127.0.0.1','XXXX内网IP','Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0','Firefox 8','83.0','Windows 10',' Win64');
/*!40000 ALTER TABLE `tbl_user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_article_deleted_list`
--

DROP TABLE IF EXISTS `v_article_deleted_list`;
/*!50001 DROP VIEW IF EXISTS `v_article_deleted_list`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_article_deleted_list` AS SELECT 
 1 AS `article_id`,
 1 AS `user_id`,
 1 AS `user_name`,
 1 AS `article_title`,
 1 AS `article_rank`,
 1 AS `article_type`,
 1 AS `type_name`,
 1 AS `article_post_time`,
 1 AS `article_edit_time`,
 1 AS `article_like_count`,
 1 AS `article_view_count`,
 1 AS `article_comment_count`,
 1 AS `article_is_comment`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_article_get_single`
--

DROP TABLE IF EXISTS `v_article_get_single`;
/*!50001 DROP VIEW IF EXISTS `v_article_get_single`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_article_get_single` AS SELECT 
 1 AS `article_id`,
 1 AS `user_name`,
 1 AS `article_title`,
 1 AS `article_author`,
 1 AS `article_html`,
 1 AS `article_is_comment`,
 1 AS `article_post_time`,
 1 AS `article_view_count`,
 1 AS `article_like_count`,
 1 AS `article_comment_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_article_get_single_deleted`
--

DROP TABLE IF EXISTS `v_article_get_single_deleted`;
/*!50001 DROP VIEW IF EXISTS `v_article_get_single_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_article_get_single_deleted` AS SELECT 
 1 AS `article_id`,
 1 AS `user_name`,
 1 AS `article_title`,
 1 AS `article_author`,
 1 AS `article_html`,
 1 AS `article_is_comment`,
 1 AS `article_post_time`,
 1 AS `article_view_count`,
 1 AS `article_like_count`,
 1 AS `article_comment_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_article_list_show`
--

DROP TABLE IF EXISTS `v_article_list_show`;
/*!50001 DROP VIEW IF EXISTS `v_article_list_show`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_article_list_show` AS SELECT 
 1 AS `article_id`,
 1 AS `article_author`,
 1 AS `user_name`,
 1 AS `article_title`,
 1 AS `article_summary`,
 1 AS `article_rank`,
 1 AS `article_type`,
 1 AS `article_view_count`,
 1 AS `article_like_count`,
 1 AS `article_post_time`,
 1 AS `article_comment_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_article_manage_list`
--

DROP TABLE IF EXISTS `v_article_manage_list`;
/*!50001 DROP VIEW IF EXISTS `v_article_manage_list`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_article_manage_list` AS SELECT 
 1 AS `article_id`,
 1 AS `user_id`,
 1 AS `user_name`,
 1 AS `article_title`,
 1 AS `article_rank`,
 1 AS `article_type`,
 1 AS `type_name`,
 1 AS `article_post_time`,
 1 AS `article_edit_time`,
 1 AS `article_like_count`,
 1 AS `article_view_count`,
 1 AS `article_comment_count`,
 1 AS `article_is_comment`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_comment_article`
--

DROP TABLE IF EXISTS `v_comment_article`;
/*!50001 DROP VIEW IF EXISTS `v_comment_article`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_comment_article` AS SELECT 
 1 AS `comment_article_id`,
 1 AS `comment_content`,
 1 AS `comment_author_name`,
 1 AS `comment_date`,
 1 AS `comment_like_count`,
 1 AS `comment_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_comment_article_deleted`
--

DROP TABLE IF EXISTS `v_comment_article_deleted`;
/*!50001 DROP VIEW IF EXISTS `v_comment_article_deleted`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_comment_article_deleted` AS SELECT 
 1 AS `comment_article_id`,
 1 AS `comment_content`,
 1 AS `comment_author_name`,
 1 AS `comment_date`,
 1 AS `comment_like_count`,
 1 AS `comment_id`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_comment_manage_list`
--

DROP TABLE IF EXISTS `v_comment_manage_list`;
/*!50001 DROP VIEW IF EXISTS `v_comment_manage_list`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_comment_manage_list` AS SELECT 
 1 AS `comment_id`,
 1 AS `pre_comment_id`,
 1 AS `comment_article_id`,
 1 AS `article_id`,
 1 AS `user_id`,
 1 AS `comment_author_name`,
 1 AS `comment_author_id`,
 1 AS `comment_author_email`,
 1 AS `article_title`,
 1 AS `comment_like_count`,
 1 AS `comment_content`,
 1 AS `comment_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_login`
--

DROP TABLE IF EXISTS `v_login`;
/*!50001 DROP VIEW IF EXISTS `v_login`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_login` AS SELECT 
 1 AS `user_id`,
 1 AS `user_name`,
 1 AS `user_signature`,
 1 AS `user_password`,
 1 AS `role_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_page_article`
--

DROP TABLE IF EXISTS `v_page_article`;
/*!50001 DROP VIEW IF EXISTS `v_page_article`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_page_article` AS SELECT 
 1 AS `article_id`,
 1 AS `article_author`,
 1 AS `article_title`,
 1 AS `article_post_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_page_comment`
--

DROP TABLE IF EXISTS `v_page_comment`;
/*!50001 DROP VIEW IF EXISTS `v_page_comment`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_page_comment` AS SELECT 
 1 AS `comment_author_name`,
 1 AS `article_id`,
 1 AS `article_author`,
 1 AS `article_title`,
 1 AS `comment_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_page_like`
--

DROP TABLE IF EXISTS `v_page_like`;
/*!50001 DROP VIEW IF EXISTS `v_page_like`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_page_like` AS SELECT 
 1 AS `article_id`,
 1 AS `article_author`,
 1 AS `article_title`,
 1 AS `like_date`,
 1 AS `user_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_user_manage_show`
--

DROP TABLE IF EXISTS `v_user_manage_show`;
/*!50001 DROP VIEW IF EXISTS `v_user_manage_show`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_user_manage_show` AS SELECT 
 1 AS `user_id`,
 1 AS `user_name`,
 1 AS `user_role`,
 1 AS `user_create_date`,
 1 AS `user_last_login_date`,
 1 AS `user_ip`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_user_total_data`
--

DROP TABLE IF EXISTS `v_user_total_data`;
/*!50001 DROP VIEW IF EXISTS `v_user_total_data`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_user_total_data` AS SELECT 
 1 AS `article_author`,
 1 AS `user_name`,
 1 AS `user_signature`,
 1 AS `total_article`,
 1 AS `total_view_count`,
 1 AS `total_like_count`,
 1 AS `total_comment_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'jancoblog'
--

--
-- Dumping routines for database 'jancoblog'
--
/*!50003 DROP PROCEDURE IF EXISTS `proc_recover_article` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`jancoblog`@`%` PROCEDURE `proc_recover_article`(IN article__id varchar(25))
begin
    declare `title` varchar(35);
    declare `author` int(11);
    declare `type` int(11);
    declare `summary` varchar(255);
    declare `html` longtext;
    declare `md` longtext;
    declare `isComment` int(11);
    declare `rank` int(11);
    declare `post` datetime;
    declare `edit` datetime default NOW();
    declare `view` int(11);
    declare `comment` int(11);
    declare `like` int(11);

    select article_title, article_author, article_type, article_summary,
           article_html, article_md, article_is_comment, article_rank, article_post_time,
           article_edit_time, article_view_count, article_comment_count, article_like_count
    into `title`, `author`, `type`, `summary`, `html`, `md`, `isComment`, `rank`, `post`, `edit`,
        `view`, `comment`, `like` from tbl_deleted_article where article_id = article__id;

    insert into tbl_article values (article__id, `title`, `author`, `type`, `summary`,
                                    `html`, `md`, `isComment`, `rank`, `post`, `edit`,
                                    `view`, `comment`, `like` );
    delete from tbl_deleted_article where article_id = article__id;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `proc_recover_comment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`jancoblog`@`%` PROCEDURE `proc_recover_comment`(IN comment__id varchar(25))
begin
    declare `id` int;
    declare `pre_id` int;
    declare `article` varchar(25);
    declare `author` int;
    declare `name` varchar(25);
    declare `email` varchar(35);
    declare `content` varchar(255);
    declare `date` datetime;
    declare `ip` varchar(15);
    declare `like` int;

    select comment_id, pre_comment_id, comment_article_id, comment_author_id,
           comment_author_name, comment_author_email, comment_content,
           comment_date, comment_author_ip, comment_like_count
    into `id`, `pre_id`, `article`, `author`, `name`, `email`, `content`, `date`,
        `ip`, `like`
    from tbl_deleted_comment where comment_id = comment__id;

    insert into tbl_comment values (`id`, `pre_id`, `article`, `author`, `name`,
                                    `email`, `content`, `date`, `ip`, `like`);

    delete from tbl_deleted_comment where comment_id = comment__id;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `proc_transfer_comment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`jancoblog`@`%` PROCEDURE `proc_transfer_comment`(IN comment__id varchar(25))
begin
    declare `id` int;
    declare `pre_id` int;
    declare `article` varchar(25);
    declare `author` int;
    declare `name` varchar(25);
    declare `email` varchar(35);
    declare `content` varchar(255);
    declare `date` datetime;
    declare `ip` varchar(15);
    declare `like` int;

    select comment_id, pre_comment_id, comment_article_id, comment_author_id,
           comment_author_name, comment_author_email, comment_content,
           comment_date, comment_author_ip, comment_like_count
    into `id`, `pre_id`, `article`, `author`, `name`, `email`, `content`, `date`,
        `ip`, `like`
    from tbl_comment where comment_id = comment__id;

    insert into tbl_deleted_comment values (`id`, `pre_id`, `article`, `author`, `name`,
                                            `email`, `content`, `date`, `ip`, `like`);

    delete from tbl_comment where comment_id = comment__id;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `v_article_deleted_list`
--

/*!50001 DROP VIEW IF EXISTS `v_article_deleted_list`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_article_deleted_list` AS select `ta`.`article_id` AS `article_id`,`tu`.`user_id` AS `user_id`,`tu`.`user_name` AS `user_name`,`ta`.`article_title` AS `article_title`,`ta`.`article_rank` AS `article_rank`,`ta`.`article_type` AS `article_type`,`tt`.`type_name` AS `type_name`,`ta`.`article_post_time` AS `article_post_time`,`ta`.`article_edit_time` AS `article_edit_time`,`ta`.`article_like_count` AS `article_like_count`,`ta`.`article_view_count` AS `article_view_count`,`ta`.`article_comment_count` AS `article_comment_count`,`ta`.`article_is_comment` AS `article_is_comment` from ((`tbl_deleted_article` `ta` join `tbl_user` `tu` on((`tu`.`user_id` = `ta`.`article_author`))) join `tbl_type` `tt` on((`ta`.`article_type` = `tt`.`type_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_article_get_single`
--

/*!50001 DROP VIEW IF EXISTS `v_article_get_single`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_article_get_single` AS select `ta`.`article_id` AS `article_id`,`tu`.`user_name` AS `user_name`,`ta`.`article_title` AS `article_title`,`ta`.`article_author` AS `article_author`,`ta`.`article_html` AS `article_html`,`ta`.`article_is_comment` AS `article_is_comment`,`ta`.`article_post_time` AS `article_post_time`,`ta`.`article_view_count` AS `article_view_count`,`ta`.`article_like_count` AS `article_like_count`,`ta`.`article_comment_count` AS `article_comment_count` from (`tbl_article` `ta` join `tbl_user` `tu` on((`tu`.`user_id` = `ta`.`article_author`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_article_get_single_deleted`
--

/*!50001 DROP VIEW IF EXISTS `v_article_get_single_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_article_get_single_deleted` AS select `ta`.`article_id` AS `article_id`,`tu`.`user_name` AS `user_name`,`ta`.`article_title` AS `article_title`,`ta`.`article_author` AS `article_author`,`ta`.`article_html` AS `article_html`,`ta`.`article_is_comment` AS `article_is_comment`,`ta`.`article_post_time` AS `article_post_time`,`ta`.`article_view_count` AS `article_view_count`,`ta`.`article_like_count` AS `article_like_count`,`ta`.`article_comment_count` AS `article_comment_count` from (`tbl_deleted_article` `ta` join `tbl_user` `tu` on((`tu`.`user_id` = `ta`.`article_author`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_article_list_show`
--

/*!50001 DROP VIEW IF EXISTS `v_article_list_show`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_article_list_show` AS select `ta`.`article_id` AS `article_id`,`ta`.`article_author` AS `article_author`,`tu`.`user_name` AS `user_name`,`ta`.`article_title` AS `article_title`,`ta`.`article_summary` AS `article_summary`,`ta`.`article_rank` AS `article_rank`,`ta`.`article_type` AS `article_type`,`ta`.`article_view_count` AS `article_view_count`,`ta`.`article_like_count` AS `article_like_count`,`ta`.`article_post_time` AS `article_post_time`,`ta`.`article_comment_count` AS `article_comment_count` from (`tbl_article` `ta` join `tbl_user` `tu` on((`tu`.`user_id` = `ta`.`article_author`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_article_manage_list`
--

/*!50001 DROP VIEW IF EXISTS `v_article_manage_list`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_article_manage_list` AS select `ta`.`article_id` AS `article_id`,`tu`.`user_id` AS `user_id`,`tu`.`user_name` AS `user_name`,`ta`.`article_title` AS `article_title`,`ta`.`article_rank` AS `article_rank`,`ta`.`article_type` AS `article_type`,`tt`.`type_name` AS `type_name`,`ta`.`article_post_time` AS `article_post_time`,`ta`.`article_edit_time` AS `article_edit_time`,`ta`.`article_like_count` AS `article_like_count`,`ta`.`article_view_count` AS `article_view_count`,`ta`.`article_comment_count` AS `article_comment_count`,`ta`.`article_is_comment` AS `article_is_comment` from ((`tbl_article` `ta` join `tbl_user` `tu` on((`tu`.`user_id` = `ta`.`article_author`))) join `tbl_type` `tt` on((`ta`.`article_type` = `tt`.`type_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_comment_article`
--

/*!50001 DROP VIEW IF EXISTS `v_comment_article`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_comment_article` AS select `tc`.`comment_article_id` AS `comment_article_id`,`tc`.`comment_content` AS `comment_content`,`tc`.`comment_author_name` AS `comment_author_name`,`tc`.`comment_date` AS `comment_date`,`tc`.`comment_like_count` AS `comment_like_count`,`tc`.`comment_id` AS `comment_id` from `tbl_comment` `tc` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_comment_article_deleted`
--

/*!50001 DROP VIEW IF EXISTS `v_comment_article_deleted`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_comment_article_deleted` AS select `tc`.`comment_article_id` AS `comment_article_id`,`tc`.`comment_content` AS `comment_content`,`tc`.`comment_author_name` AS `comment_author_name`,`tc`.`comment_date` AS `comment_date`,`tc`.`comment_like_count` AS `comment_like_count`,`tc`.`comment_id` AS `comment_id` from `tbl_comment` `tc` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_comment_manage_list`
--

/*!50001 DROP VIEW IF EXISTS `v_comment_manage_list`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_comment_manage_list` AS select `tc`.`comment_id` AS `comment_id`,`tc`.`pre_comment_id` AS `pre_comment_id`,`tc`.`comment_article_id` AS `comment_article_id`,`ta`.`article_id` AS `article_id`,`ta`.`article_author` AS `user_id`,`tc`.`comment_author_name` AS `comment_author_name`,`tc`.`comment_author_id` AS `comment_author_id`,`tc`.`comment_author_email` AS `comment_author_email`,`ta`.`article_title` AS `article_title`,`tc`.`comment_like_count` AS `comment_like_count`,`tc`.`comment_content` AS `comment_content`,`tc`.`comment_date` AS `comment_date` from (`tbl_comment` `tc` join `tbl_article` `ta` on((`tc`.`comment_article_id` = `ta`.`article_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_login`
--

/*!50001 DROP VIEW IF EXISTS `v_login`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_login` AS select `tu`.`user_id` AS `user_id`,`tu`.`user_name` AS `user_name`,`tu`.`user_signature` AS `user_signature`,`tu`.`user_password` AS `user_password`,`tr`.`role_name` AS `role_name` from (`tbl_user` `tu` join `tbl_role` `tr` on((`tu`.`user_role` = `tr`.`role_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_page_article`
--

/*!50001 DROP VIEW IF EXISTS `v_page_article`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_page_article` AS select `ta`.`article_id` AS `article_id`,`ta`.`article_author` AS `article_author`,`ta`.`article_title` AS `article_title`,`ta`.`article_post_time` AS `article_post_time` from `tbl_article` `ta` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_page_comment`
--

/*!50001 DROP VIEW IF EXISTS `v_page_comment`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_page_comment` AS select `tc`.`comment_author_name` AS `comment_author_name`,`tc`.`comment_article_id` AS `article_id`,`ta`.`article_author` AS `article_author`,`ta`.`article_title` AS `article_title`,`tc`.`comment_date` AS `comment_date` from (`tbl_comment` `tc` join `tbl_article` `ta` on((`tc`.`comment_article_id` = `ta`.`article_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_page_like`
--

/*!50001 DROP VIEW IF EXISTS `v_page_like`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_page_like` AS select `ta`.`article_id` AS `article_id`,`ta`.`article_author` AS `article_author`,`ta`.`article_title` AS `article_title`,`tlr`.`like_date` AS `like_date`,`tu`.`user_name` AS `user_name` from ((`tbl_like_record` `tlr` join `tbl_article` `ta` on((`tlr`.`article_id` = `ta`.`article_id`))) join `tbl_user` `tu` on((`ta`.`article_author` = `tu`.`user_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_user_manage_show`
--

/*!50001 DROP VIEW IF EXISTS `v_user_manage_show`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_user_manage_show` AS select `tu`.`user_id` AS `user_id`,`tu`.`user_name` AS `user_name`,`tu`.`user_role` AS `user_role`,`tu`.`user_create_date` AS `user_create_date`,`tu`.`user_last_login_date` AS `user_last_login_date`,`tu`.`user_ip` AS `user_ip` from `tbl_user` `tu` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_user_total_data`
--

/*!50001 DROP VIEW IF EXISTS `v_user_total_data`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`jancoblog`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_user_total_data` AS select `tu`.`user_id` AS `article_author`,`tu`.`user_name` AS `user_name`,`tu`.`user_signature` AS `user_signature`,count(`ta`.`article_id`) AS `total_article`,sum(`ta`.`article_view_count`) AS `total_view_count`,sum(`ta`.`article_like_count`) AS `total_like_count`,sum(`ta`.`article_comment_count`) AS `total_comment_count` from (`tbl_user` `tu` left join `tbl_article` `ta` on((`tu`.`user_id` = `ta`.`article_author`))) group by `tu`.`user_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-08 19:05:37
