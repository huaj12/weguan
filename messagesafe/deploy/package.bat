@echo off


set "SrcDir=D:\kooks\workspace\messagesafe\target"
cd D:\kooks\workspace\messagesafe
d:
for %%a in (W2371 Z2371 S1966 S1915 D1089 G1094 B1099 K1190 ZWL001 ZWL002 ZWL003) do (
mvn clean resources:resources  -Pproduct -Dumeng-channel=%%a package   
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\message\%%a-%%~nxi  )
)

   

