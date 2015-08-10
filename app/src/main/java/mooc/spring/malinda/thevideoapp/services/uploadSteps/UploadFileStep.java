package mooc.spring.malinda.thevideoapp.services.uploadSteps;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.retrofit.ServiceEndPointAdapter;
import mooc.spring.malinda.thevideoapp.utils.L;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

public class UploadFileStep implements Step {
    @Override
    public void execute(VideoEx video) {

        // Uploads the file to the server.
        File file = new File(video.getLocation());
        L.logI("Physcal path to file " + file.getAbsolutePath() + " exists? " + file.exists());


        TypedFile typedFile = new TypedFile(video.getContentType(), file);

        Date date = new Date(video.getDateTaken());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        String folderName = fmt.format(date);
        L.logI("Folder name in server is " + folderName);

        try
        {
            ServiceEndPointAdapter.create().uploadVideo(folderName, video.getReference() + ".mp4", typedFile);
        }
        catch (RetrofitError error)
        {
            L.logI("There was a problem updating " + error.getResponse().getStatus());
        }
    }
}
