@REM ----------------------------------------------------------------------------
@REM Java client update orchestration batch script (Multi-JAR Version)
@REM
@REM Required arguments :
@REM %1 - Name of the application executable or main class for restart detection
@REM
@REM Required ENV vars:
@REM JAVA_HOME - location of a JDK home dir
@REM ----------------------------------------------------------------------------

@echo off

set EXEC_FILE=%1
set EXEC_DIR=%~dp0

echo Update started...

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OkJHome
echo.
echo Error: JAVA_HOME not found in your environment. >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" goto OkJPath
echo.
echo Error: JAVA_HOME is set to an invalid directory. >&2
echo JAVA_HOME = "%JAVA_HOME%" >&2
echo Please set the JAVA_HOME variable in your environment to match the >&2
echo location of your Java installation. >&2
echo.
goto error

:OkJPath

if not "%EXEC_FILE%" == "" goto init
echo.
echo Error: Required parameters not set. >&2
echo Argument with the executable file name must be given. >&2
echo.
goto error

@REM ==== END VALIDATION ====

:init
for %%I in ("%EXEC_DIR%..\") do set "APP_DIR=%%~fI"

@REM Stop the application
echo Waiting for application to stop...

taskkill /F /IM "%EXEC_FILE%.exe" >nul 2>&1

@REM Wait for the application to stop
goto waitloop

:waitloop
tasklist /FI "IMAGENAME eq %EXEC_FILE%.exe" | find /I "%EXEC_FILE%.exe" >nul
if not errorlevel 1 (
    timeout /t 1 >nul
    goto waitloop
)

echo Application stopped.

@REM Copy the required files to the app directory
echo Applying update...
copy "%EXEC_DIR%*.jar" "%APP_DIR%" /Y >nul

if errorlevel 1 (
    echo Error while copying update files.
    goto error
)

cd /d "%APP_DIR%"
cd ..

@REM "Restart" application
echo Restarting application...

if exist "%EXEC_FILE%.exe" goto endDetectExecFile
echo.
echo Error: No executable file found. >&2
echo.
goto error

:endDetectExecFile
start "" "%EXEC_FILE%.exe"
timeout /t 2 /nobreak >nul

echo Finishing update...

cd /d "%TEMP%"

@REM Remove the update package directory
start /B "" cmd /c "timeout /t 2 /nobreak >nul & rmdir /s /q ""%EXEC_DIR%"""

goto end

:error
echo.
echo ==================================================
echo Update failed.
echo Please report the error above to support.
echo ==================================================
echo.
pause
exit /B 1

:end
exit 0