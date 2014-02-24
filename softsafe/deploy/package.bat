@echo off


set "SrcDir=D:\kooks\workspace\softsafe\target"
cd D:\kooks\workspace\softsafe
d:
for %%a in (lxr0224) do (
mvn clean resources:resources  -Pproduct -Dumeng-channel=%%a package   
for /r "%SrcDir%\" %%i in (*.apk) do (
echo f| xcopy /y  %%i E:\apk\softsafe\%%a-%%~nxi  )
)

   

