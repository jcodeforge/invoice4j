package io.github.codeforgecore.networking;

import io.github.codeforgecore.networking.schemes.responses.GetApiKeyInfoResponseScheme;
import io.github.codeforgecore.networking.schemes.responses.GetFileInfoResponseScheme;
import io.github.codeforgecore.networking.schemes.responses.PostFileResponseScheme;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ServerApi {

    @GET("/apikey")
    Call<List<GetApiKeyInfoResponseScheme>> getApiKeysInfo();

    @Multipart
    @POST("/uploads")
    Call<PostFileResponseScheme> uploadFile(@Part MultipartBody.Part file,
                                            @Part MultipartBody.Part data);

    @GET("/uploads/{fileId}")
    Call<GetFileInfoResponseScheme> getFileInfo(@Path("fileId") String fileId);

    @GET("/files/{fileId}/{fileName}")
    Call<ResponseBody> downloadFile(@Path("fileId") String fileId, @Path("fileName") String fileName);
}
