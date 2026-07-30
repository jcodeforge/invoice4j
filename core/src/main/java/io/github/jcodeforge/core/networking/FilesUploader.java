package io.github.jcodeforge.core.networking;

import io.github.jcodeforge.core.networking.schemes.responses.PostFileResponseScheme;
import io.github.jcodeforge.core.utils.FileUtils;
import io.github.jcodeforge.core.utils.Logger;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import java.io.File;

public class FilesUploader {

    private static final String TAG = "FilesUploader";

    private final ServerApi mServerApi;
    private final Logger mLogger;

    public FilesUploader(ServerApi serverApi, Logger logger) {
        mServerApi = serverApi;
        mLogger = logger;
    }

    public String uploadFileToServer(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            String mime = FileUtils.getMimeType(file);
            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file", file.getName(),
                            NetworkingUtils.createFileBody(filePath, mime)
            );

            Call<PostFileResponseScheme> call = mServerApi.uploadFile(filePart, null);

            try {
                Response<PostFileResponseScheme> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    return response.body().getId();
                } else {
                    mLogger.e(TAG, "Upload file failed: " + response.message());
                    return null;
                }

            } catch (Exception e) {
                mLogger.e(TAG, "Upload file failed: " + e.getLocalizedMessage());
                return null;
            }
        }

        return null;
    }
}
