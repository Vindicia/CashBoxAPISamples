
F:

set DIR=\dotNet\Select_v1.1

wsdl /out:%DIR%\VindiciaSelect.cs /n:com.vindicia.soap https://soap.vindicia.com/1.1/Select.wsdl

pause

csc /t:library /debug /out:%DIR%\VindiciaSelect.dll %DIR%\VindiciaSelect.cs

dir
pause
