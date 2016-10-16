start msg * /v /w "It is the time for have a rest, now."
set restPath=%cd% rem default will set c:\window\system32, you can change it to anywhere. 
set /a count = %1+1 
if %count% gtr 4 (
set /a count=1
start msg * /v /w "One hour is gone. Please go walk around."
)
set /a hour=%time:~0,2%
set /a min=%time:~3,2%+15
if %min% gtr 60 (
set /a hour=%hour%+1
set /a min=%min%-60
)
if %min% lss 10 (set min=0%min%)
set nextTime=%hour%:%min%:%time:~6,5%
at /delete /yes
at %nextTime% %restPath%\Rest.bat %count%
echo at %nextTime% %restPath%\Rest.bat %count% >> %restPath%\rest.log

rem end all
