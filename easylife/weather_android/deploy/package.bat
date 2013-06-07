@echo off


set "SrcDir=D:\kooks\workspace\weatherclient\target"
cd D:\kooks\workspace\weatherclient
d:
for %%a in (local 19) do (
mvn clean resources:resources -Pproduct -Dumeng-channel=%%a package 
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\weather\%%a-%%~nxi)  )
)

   

