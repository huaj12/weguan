@echo off


set "SrcDir=D:\kooks\workspace\movie\target"
cd D:\kooks\workspace\movie
d:
for %%a in (local xiaomi hiapk 360 appchina wandoujia 91 anzhi mumayi nduoa baidu eoe sohu tencent mm wow 163 meizu 3g lenovo gfan) do (
mvn clean resources:resources  -Pproduct -Dumeng-channel=%%a package    
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\movie\%%a-%%~nxi  )
)

   

