#!/bin/sh

package_apk(){
    mvn clean resources:resources -Pproduct -Dumeng-channel=$1 package
    for file in ` ls /Users/wujiajun/work/workspace/AndroidClient/target `
    do
	if [ -f /Users/wujiajun/work/workspace/AndroidClient/target/${file} ]; then
	    if [ "${file##*.}" == "apk" ]; then
			echo copy $1-$file
			cp -f /Users/wujiajun/work/workspace/AndroidClient/target/$file /Users/wujiajun/work/workspace/AndroidClient/bin/apk/$1-$file
	    fi
	fi
    done
}

cd /Users/wujiajun/work/workspace/AndroidClient
#小米
package_apk xiaomi
#安卓
package_apk hiapk
#机锋
package_apk gfan
#360
package_apk 360
#应用汇
package_apk appchina
#豌豆荚
package_apk wandoujia
#91
package_apk 91
#安智
package_apk anzhi
#木蚂蚁
package_apk mumayi
#N多
package_apk nduoa
#百度
package_apk baidu
#优亿
package_apk eoe