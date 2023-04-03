# 影视小窝
一个简易的影视网站，用于在本地或局域网内搭建一个视频网站，适合影视收藏爱好者使用

## 如何使用
首先，要运行该网站，电脑需要装有[java17](https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/x64/windows/)环境，

然后，从[Releases页面](https://github.com/hellogaos/videohome/releases)下载已构建好的程序，解压运行其中的脚本即可。

（若要自行构建程序，请下载源码，分别按 `VideoHomeWeb/README.md` 和 `VideoHomeServer/README.md` 中的说明进行构建，最终会得到jar包`VideoHomeServer.jar`，
运行 `java -jar VideoHomeServer.jar` 即可启动网站服务。）
## 影视的组织形式
网站运行时会自动扫描加载指定目录下的影视，该目录默认为`./Videos/`（可以在`VideoHomeServer/src/main/resources/application.yml`中的`videoRootPath: `配置项修改默认目录）。
该目录下必须有以下的基础目录结构：
```
Videos/
  └ 电影/
  └ 剧集/
```
### 电影的添加
* 在`Videos/电影/`目录下新建一个以电影名命名的文件夹
* 文件夹中放置电影视频和海报图片
* 海报图片必须以`海报`两字命名，建议从豆瓣下载海报图。
* 电影视频的命名格式必须是：`电影名_年份_评分_类别1_类别2_类别3.mp4`
* 年份是四位数
* 评分为豆瓣评分，数值在0.0至9.9之间
* 类别至少要有一个，最多四个，且必须是以下类别之一：`剧情, 喜剧, 爱情, 动作, 科幻, 动画, 奇幻，悬疑, 犯罪, 恐怖, 惊悚，灾难, 冒险, 历史, 战争, 传记, 纪录`
* 必须满足以上要求，才能被正确读取并显示在网站上

### 剧集的添加
* 在`Videos/剧集/`目录下新建一个以剧名命名的文件夹
* 文件夹中放置剧集视频和海报图片
* 海报图片必须以`海报`两字命名，建议从豆瓣下载海报图。
* 剧集视频的命名格式是：`剧名.集数.mp4` 或  `剧名.集数.集名.mp4`
* 网站会将去除`剧名.`部分后的文件名作为选集标签。

### 影视目录结构示例：
```
Videos/
  └ 电影/
      └ 星际穿越
        └ 星际穿越_2014_9.4_剧情_科幻_冒险.mp4
        └ 海报.webp
      └ 指环王1：护戒使者
        └ 指环王1：护戒使者_2001_9.1_动作_奇幻_冒险.mp4
        └ 海报.webp
      └ 天气之子
        └ 天气之子_2019_7.0_动画_爱情.mp4
        └ 海报.webp
      └ ... 
  └ 剧集/
      └ 海绵宝宝
        └ 海报.webp
        └ 海绵宝宝.001.mp4
        └ 海绵宝宝.002.mp4
        └ 海绵宝宝.003.mp4
        └ ...
      └ 权力的游戏
        └ 海报.webp
        └ 权力的游戏.S01E01.mp4
        └ 权力的游戏.S01E02.mp4
        └ 权力的游戏.S01E03.mp4
        └ ...
      └ 迪迦奥特曼
        └ 海报.webp
        └ 迪迦奥特曼.01.光的继承者.mp4
        └ 迪迦奥特曼.02.石头的神话.mp4
        └ 迪迦奥特曼.03.恶魔的预言.mp4
        └ 迪迦奥特曼.04.再见了，地球.mp4
        └ 迪迦奥特曼.05.怪兽出没的日子.mp4
        └ ...
      └ ...
```

### ！！影视编码格式要求
存入`Videos`目录下的影视必须是H.264/AVC编码的mp4视频（且最好是moov信息前置），只有这样才能在浏览器中顺利播放。
（可以将一个本地mp4视频直接拖到浏览器中，如果能正常播放，则说明浏览器支持该视频的格式）