# Notify_in_Univisity
大学活动的通知，包括android客户端和服务器端
下载xampp开发环境到D:\xampp目录下。
安装好xampp。
把D:\xampp\php和D:\xampp\mysql\bin加入到Windows系统环境变量Path中去。
解压源码中的notify.rar,这是客户端源码，并将其拷贝到目录D:\workspace目录下。
解压源码中的android-notify.rar，这是服务器源码，并将其拷贝到目录D:\workspace目录下。
使用源码目录下的httpd-vhosts.conf文件覆盖D:\xampp\apache\conf\extra 目录下的同名 Apache 配置文件。
在Xampp控制台（D:\xampp\xampp-control.exe）启动Xampp的Apache和Mysql。
打开系统的命令提示符，cd D:\workspace\android-notify\server\bin目录，运行“cli sys init”，初始化hush Framework框架实例，如果出现报错请参考以下“可能碰到的问题”部分。
重启Xampp的Apache和Mysql，访问以下站点，确保可访问：
由于本项目所使用的端口是9001，所以访问的地址为：
本项目API调试后台：http://127.0.0.1:9001
本项目WEB站点：http://127.0.0.1:9002


