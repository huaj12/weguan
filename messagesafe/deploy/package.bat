@echo off


set "SrcDir=D:\kooks\workspace\messagesafe\target"
cd D:\kooks\workspace\messagesafe
d:
for %%a in (360) do (
mvn clean resources:resources  -Pproduct -Dumeng-channel=%%a package   
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\message\%%a-%%~nxi  )
)

   

