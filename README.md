# 本系统是一个基于阿里云的云存储隐私加密系统
## 使用命令
* 用户加密指定文件<br />
encrypt.jar 1 源文件路径 坐标文件路径 公钥路径
* 用户解密指定文件<br />
encrypt.jar 2 源文件路径 坐标文件路径 私钥路径
* Sanitizer通配符替换<br />
encrypt.jar 3 源文件路径 坐标文件路径
* 生成密钥对<br />
signature.jar 0
* 生成电子签名<br />
signature.jar 1 源文件路径 签名路径 私钥路径
* 电子签名比对<br />
signature.jar 2 源文件电子签名路径 新文件电子签名路径 数据块号
