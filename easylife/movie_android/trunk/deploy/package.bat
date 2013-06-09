@echo off


set "SrcDir=D:\kooks\workspace\movie\target"
cd D:\kooks\workspace\movie
d:
for %%a in (local) do (
mvn clean resources:resources  -Pproduct -Dumeng-channel=%%a package    
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\movie\%%a-%%~nxi  )
)

   

