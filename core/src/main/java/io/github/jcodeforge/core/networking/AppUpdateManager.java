package io.github.jcodeforge.core.networking;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.jcodeforge.core.common.BackgroundThreadPoster;
import io.github.jcodeforge.core.common.UiThreadPoster;
import io.github.jcodeforge.core.common.settings.SettingsManager;
import io.github.jcodeforge.core.utils.*;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;

public class AppUpdateManager {

    private static final String TAG = "AppUpdateManager";

    public interface AppUpdateStateListener {
        void onStateUpdate(int installState);
    }

    public static final int INSTALL_STATE_UNKNOWN = 0;
    public static final int INSTALL_STATE_UPDATE_AVAILABLE = 1;
    public static final int INSTALL_STATE_UPDATE_CANCELLED = 2;
    public static final int INSTALL_STATE_PENDING = 3;
    public static final int INSTALL_STATE_FAILED = 4;
    public static final int INSTALL_STATE_INSTALLED = 5;

    private final SettingsManager mSettingsManager;
    private final BackgroundThreadPoster mBackgroundThreadPoster;
    private final UiThreadPoster mUiThreadPoster;
    private final Logger mLogger;

    private AppUpdateStateListener mAppUpdateStateListener;

    public AppUpdateManager(SettingsManager settingsManager,
                            BackgroundThreadPoster backgroundThreadPoster,
                            UiThreadPoster uiThreadPoster, Logger logger) {
        mSettingsManager = settingsManager;
        mBackgroundThreadPoster = backgroundThreadPoster;
        mUiThreadPoster = uiThreadPoster;
        mLogger = logger;
    }

    public void executeUpdate() {
        try {
            File pckgDir = new File(resolveAppDir(), "update");
            if (pckgDir.exists()) {
                File[] jarFiles = pckgDir.listFiles((_, name) -> name.endsWith(".jar"));
                if (jarFiles != null && jarFiles.length > 0) {
                    String runningJarPath = System.getProperty("jar.path");
                    String execName = FilenameUtils.removeExtension(new File(runningJarPath).getName());

                    // Execute batch file to replace current running package and "restart" application
                    new ProcessBuilder("cmd", "/c", "start", "", "update-orchestrator.cmd", execName)
                            .directory(pckgDir)
                            .start();

                    mSettingsManager.setArgumentUpdateState(INSTALL_STATE_PENDING);
                    System.exit(0);
                }
                else {
                    onUpdateFailed();
                    mLogger.e(TAG, "Error starting update execution. Installable set of jar files not found.");
                }
            }
            else {
                onUpdateFailed();
                mLogger.e(TAG, "Error starting update execution. Update folder not found.");
            }
        } catch (Exception e) {
            onUpdateFailed();
            mLogger.e(TAG, e.getMessage());
        }
    }

    public void startUpdateFlow() {
        mSettingsManager.setArgumentUpdateState(INSTALL_STATE_UNKNOWN);
        mBackgroundThreadPoster.post(new Runnable() {
            @Override
            public void run() {
                try (InputStream is = new URI(BuildConfig.PACKAGE_URL + "/version.json").toURL().openStream();
                     InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

                    JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                    int remoteVersion = jsonObject.get("latest_version").getAsInt();
                    String hash = jsonObject.get("hash").getAsString();

                    if (remoteVersion > BuildConfig.VERSION_NUMBER) {
                        mLogger.i(TAG, "Download update package with latest version: " + remoteVersion);

                        File appDir = resolveAppDir();
                        File zipFile = new File(appDir, "update.zip");

                        try (ReadableByteChannel readableByteChannel = Channels.newChannel(new URI(BuildConfig.PACKAGE_URL +
                                "/update.zip").toURL().openStream());
                             FileOutputStream fos = new FileOutputStream(zipFile)) {
                            FileChannel fileChannel = fos.getChannel();
                            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

                            // Verify package integrity
                            if (zipFile.exists()) {
                                String sha256 = CryptUtils.sha256(zipFile);
                                if (sha256.equals(hash)) {
                                    ZipUtils.unzip(zipFile, appDir.getAbsolutePath());
                                    onUpdateAvailable();
                                } else {
                                    String errMsg = "Error: Download failed. SHA256 hash does not match.";
                                    onUpdateFailed();
                                    mLogger.e(TAG, errMsg);
                                }
                            } else {
                                String errMsg = "Error: Download failed. Installable package does not exist.";
                                onUpdateFailed();
                                mLogger.e(TAG, errMsg);
                            }
                        } catch (Exception e) {
                            onUpdateFailed();
                            mLogger.e(TAG, e.getMessage());
                        } finally {
                            if (zipFile.exists()) {
                                zipFile.delete();
                            }
                        }
                    } else {
                            mSettingsManager.setArgumentUpdateState(INSTALL_STATE_UPDATE_CANCELLED);
                    }
                } catch (Exception e) {
                    onUpdateFailed();
                    mLogger.e(TAG, e.getMessage());
                }
            }
        });
    }

    public void completeUpdate() {
        mSettingsManager.setArgumentUpdateState(INSTALL_STATE_INSTALLED);
        if (mAppUpdateStateListener != null) {
            mUiThreadPoster.post(new Runnable() {
                @Override
                public void run() {
                    mAppUpdateStateListener.onStateUpdate(INSTALL_STATE_INSTALLED);
                }
            });
        }
    }

    public void registerListener(AppUpdateStateListener listener) {
        mAppUpdateStateListener = listener;
    }

    private void onUpdateFailed() {
        mSettingsManager.setArgumentUpdateState(INSTALL_STATE_FAILED);
        if (mAppUpdateStateListener != null) {
            mUiThreadPoster.post(new Runnable() {
                @Override
                public void run() {
                    mAppUpdateStateListener.onStateUpdate(INSTALL_STATE_FAILED);
                }
            });
        }
    }

    private void onUpdateAvailable() {
        mSettingsManager.setArgumentUpdateState(INSTALL_STATE_UPDATE_AVAILABLE);
        if (mAppUpdateStateListener != null) {
            mUiThreadPoster.post(new Runnable() {
                @Override
                public void run() {
                    mAppUpdateStateListener.onStateUpdate(INSTALL_STATE_UPDATE_AVAILABLE);
                }
            });
        }
    }

    private File resolveAppDir() {
        try {
            File runningJar = new File(FileUtils.getProtectionDomain(AppUpdateManager.class));
            return runningJar.getParentFile() != null ? runningJar.getParentFile() :
                    new File(System.getProperty("user.dir"), "app");

        } catch (Exception e) {
            return new File(System.getProperty("user.dir"), "app");
        }
    }
}
