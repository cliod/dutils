# 开发工具

Java聚合工具，本工具主要是在开发工程中使用到的或者常用工具的封装集合。

## 一、版本说明

### **[`x.y.z`](./pom.xml)**

* `x`: 主版本，现在是第5个大版本，是一个LTS，长期支持更新和维护
* `y`: 次版本号，奇数为开发版本，偶数为稳定版本。
* `z`: 修订版本，奇数为开发版本，偶数为稳定版本。

## 二、个版本说明

| 模块 | 说明 | 
|:----:|:----|
| [all](./dutils-all/pom.xml) | 模块总和 |
| [base](./dutils-base/pom.xml) | 基础模块，抽象接口 | 
| [core](./dutils-core/pom.xml) | 核心模块，项目说明 |
| [cache](./dutils-cache/pom.xml) | 缓存模块 |
| [captcha](./dutils-captcha/pom.xml) | 图片验证码工具 |
| [common](./dutils-common/pom.xml) | 公共通用模块，常用工具类 |
| [datetime](./dutils-datetime/pom.xml) | 日期模块，日期常用工具 |
| [db](./dutils-db/pom.xml) | 数据库模块，mybatis常用类 |
| [excel](./dutils-excel/pom.xml) | excel模块，excel工具封装 |
| [json](./dutils-json/pom.xml) | json序列化工具封装 |
| [pinyin](./dutils-pinyin/pom.xml) | 拼音工具类 |
| [qrcode](./dutils-qrcode/pom.xml) | 二维码模块，二维码生成工具 |
| [security](./dutils-security/pom.xml) | 安全模块，加密工具，jwt工具 |
| [sms](./dutils-sms/pom.xml) | 短信模块，阿里腾讯短信SDK封装 |
| [storage](./dutils-storage/pom.xml) | 存储模块，阿里腾讯对象云存储SDK封装 |
| [spring-boot-starters](./dutils-spring-boot-starters/pom.xml) | spring-boot-starter 封装总和 |
| [starter-sms](./dutils-spring-boot-starters/dutils-sms-starter/pom.xml)| 短信Restful API |

## 三、快速上手

### 1. 依赖

* maven 依赖。

```xml
<!--dutils-->
<dependency>
    <groupId>com.wobangkj</groupId>
    <artifactId>dutils-all</artifactId>
    <version>5.3.23</version>
</dependency>
```

* 若是只需单独使用某一模块，直接引入该模块。例： 只需要[common](./dutils-common/pom.xml)模块，如下依赖。

```xml
<!--dutils-common-->
<dependency>
    <groupId>com.wobangkj</groupId>
    <artifactId>dutils-common</artifactId>
    <version>5.3.23</version>
</dependency>
```

* 由于该项目使用私有maven仓库，所以还需引用

```xml
<!--仓库-->
<distributionManagement>
    <repository>
        <id>rdc-releases</id>
        <url>https://repo.rdc.aliyun.com/repository/79115-release-8qAEIB/</url>
    </repository>
    <snapshotRepository>
        <id>rdc-snapshots</id>
        <url>https://repo.rdc.aliyun.com/repository/79115-snapshot-nphODc/</url>
    </snapshotRepository>
</distributionManagement>
```

### 2. 代码

生成(uuid)百度算法id

```java
// 生成(uuid)百度算法id
public class UidGeneratorTest {
	@Test
	public void getUid() {
		UidGenerator generator = DefaultUidGenerator.getInstance();
		long id = generator.getUid();
		System.out.println(id);
		System.out.println(generator.parseUid(id));
	}
}
```

生成(uuid)雪花算法id

```java
// 生成(uuid)雪花算法id
public class SnowflakeIdWorkerTest {

	@Test
	public void getNextId() {
		System.out.println(SnowflakeIdWorker.getNextId());
	}

	@Test
	public void nextID() {
		System.out.println(SnowflakeIdWorker.nextID().toString());
	}
}
```

字符串加解密

```java
// 字符串加解密
public class KeyUtilsTest {
	@Test
	public void decode() {
		// 加密
		System.out.println(KeyUtils.encode(""));
		System.out.println(KeyUtils.encode("1"));
		System.out.println(KeyUtils.encode("11234"));
		// 解密
		System.out.println(KeyUtils.decode("MQ=="));
		System.out.println(KeyUtils.decode("MTEyMzQ="));
		System.out.println(KeyUtils.decode(""));
		System.out.println(KeyUtils.decode(new byte[0]));
	}
}
```

跟多例子看[测试代码](dutils-base/src/test/java/com/wobangkj/utils)。