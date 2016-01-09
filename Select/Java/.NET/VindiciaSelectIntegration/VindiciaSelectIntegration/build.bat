
REM Build script that generates VindiciaSelect dll from Select v1.1 WSDL

REM Destination drive
F:
REM Destination directory for generated dll
set DIR=\dotNet\Select_v1.1

wsdl /out:%DIR%\VindiciaSelect.cs /n:com.vindicia.soap https://soap.vindicia.com/1.1/Select.wsdl

pause

csc /t:library /debug /out:%DIR%\VindiciaSelect.dll %DIR%\VindiciaSelect.cs

dir
pause
