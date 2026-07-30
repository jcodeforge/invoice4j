package io.github.codeforgecore.networking;

import io.github.codeforgecore.networking.schemes.responses.GetFileInfoResponseScheme;
import io.github.codeforgecore.utils.FileUtils;
import io.github.codeforgecore.utils.Logger;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FilesDownloader {

    private static final String TAG = "FilesDownloader";

    private final ServerApi mServerApi;
    private final Logger mLogger;

    public FilesDownloader(ServerApi serverApi, Logger logger) {
        mServerApi = serverApi;
        mLogger = logger;
    }

    public String downloadFileAndStoreLocally(String fileId) {
        Call<GetFileInfoResponseScheme> fileInfoCall = mServerApi.getFileInfo(fileId);

        try {
            Response<GetFileInfoResponseScheme> fileInfoResponse = fileInfoCall.execute();

            if (fileInfoResponse.isSuccessful() && fileInfoResponse.body() != null) {
                String fileName = fileInfoResponse.body().getName() + "." +
                        fileInfoResponse.body().getExtension();

                Call<ResponseBody> downloadFileCall = mServerApi.downloadFile(fileId, fileName);
                Response<ResponseBody> fileResponse = downloadFileCall.execute();

                String localFilePath = writeResponseBodyToAppCache(fileResponse, fileName);

                if (localFilePath == null) {
                    mLogger.e(TAG, "File storage failed.");
                }

                return localFilePath;

            } else {
                mLogger.e(TAG, "Server call failed. No response.");
                return null;
            }
        } catch (Exception e) {
            mLogger.e(TAG, e.getMessage());
            return null;
        }
    }

    private String writeResponseBodyToAppCache(Response<ResponseBody> response, String fileName) {
        try {
            if (response.isSuccessful()) {
                String currDateTime = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss",
                        Locale.getDefault()).format(new Date());
                fileName = currDateTime + fileName;

                File parentDir = new File(System.getProperty("user.home") + File.separator +
                        ".fpsoft" + File.separator + "cache");

                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                File localFile = new File(parentDir, fileName);

                if (FileUtils.copyFile(response.body().byteStream(), localFile)) {
                    return localFile.getAbsolutePath();
                } else {
                    return null;
                }
            }
            else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
