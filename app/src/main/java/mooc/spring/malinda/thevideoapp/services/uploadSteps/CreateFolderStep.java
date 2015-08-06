package mooc.spring.malinda.thevideoapp.services.uploadSteps;

import java.text.SimpleDateFormat;
import java.util.Date;

import mooc.spring.malinda.thevideoapp.framework.Constants;
import mooc.spring.malinda.thevideoapp.operations.VideoEx;
import mooc.spring.malinda.thevideoapp.retrofit.FolderCreationRequest;
import mooc.spring.malinda.thevideoapp.retrofit.ServiceEndPointAdapter;
import mooc.spring.malinda.thevideoapp.utils.L;
import retrofit.RetrofitError;

public class CreateFolderStep implements Step {

    public void execute(VideoEx video) {

        L.logI("Creating the folder in remote server");

        Date date = new Date(video.getDateTaken());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        FolderCreationRequest request = new FolderCreationRequest();
        request.name = fmt.format(date);
        request.folder = new Object(); // Null object to get {} in request JSON.

        L.logI("Formatted date is " + request.name);

        try
        {
            ServiceEndPointAdapter.create().createFolder(request);
            return;
        }
        catch (RetrofitError error)
        {
            int status = error.getResponse().getStatus();
            L.logI("There was an error creating a folder in remote server, status = " + status);

            if (status == Constants.ConflictHttpStatus) {
                L.logI("Folder already exists, which is a good thing.");
                return;
            }

            throw error;
        }
    }
}
