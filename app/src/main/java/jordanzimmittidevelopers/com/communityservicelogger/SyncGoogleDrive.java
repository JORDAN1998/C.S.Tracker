package jordanzimmittidevelopers.com.communityservicelogger;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// google_drive_sync Class Created By Jordan Zimmitti 3-12-17//
public class SyncGoogleDrive extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //<editor-fold desc="Date Time">

    // Define And Instantiate Variable Calendar calendar//
    private Calendar calendar = Calendar.getInstance();

    // Define And Instantiate Variable int month//
    private int month = calendar.get(Calendar.MONTH) + 1;

    // Define And Instantiate Variable int day//
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    // Define And Instantiate Variable int year//
    private int year = calendar.get(Calendar.YEAR);

    // Define And Instantiate Variable int hour//
    private int hour = calendar.get(Calendar.HOUR_OF_DAY);

    // Define And Instantiate Variable int minute//
    private int minute = calendar.get(Calendar.MINUTE);

    // Define And Instantiate Variable int second//
    private int second = calendar.get(Calendar.SECOND);

    //</editor-fold>

    //<editor-fold desc="Drive">

    // Define Variable DriveFile fileEventsBackup//
    private DriveFile fileEventsBackup;

    // Define Variable DriveFile fileRemindersBackup//
    private DriveFile fileRemindersBackup;

    // Define Variable DriveFile fileEventsBackup//
    private DriveFile fileUsersBackup;

    // Define DriveFolder folderEventsBackup//
    private DriveFolder folderEventsBackup;

    // Define DriveFolder folderRemindersBackup//
    private DriveFolder folderRemindersBackup;

    // Define DriveFolder folderUsersBackup//
    private DriveFolder folderUsersBackup;

    // Define Variable GoogleApiClient googleApiClient//
    private GoogleApiClient googleApiClient;

    // Define Variable String fileName//
    private String cloudFileName;

    //</editor-fold>

    // What Happens When App Is Successfully Connected To Users Google Drive//
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        // Sync With Google Drive//
        Drive.DriveApi.requestSync(googleApiClient);

        // Define And Instantiate Variable Query queryForEventsBackup / Search For Events Backup Folder In App Folder//
        Query queryForEventsBackup = new Query.Builder().addFilter(Filters.eq(SearchableField.TITLE, "Events Backup")).build();

        // Get All Folders In App Folder That Fit In The Query Above//
        Drive.DriveApi.getAppFolder(googleApiClient).queryChildren(googleApiClient, queryForEventsBackup).setResultCallback(getEventsBackupFolder);
    }

    // What Happens When App Fails To Connect To Google Drive//
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        // What Happens When The Connection Issue Can Be Resolved//
        if (connectionResult.hasResolution()) {

            // If Above Statement Is True, Try To Fix Connection//
            try {

                // Resolve The Connection//
                connectionResult.startResolutionForResult(this, 3);

            } catch (IntentSender.SendIntentException ignored) {
            }

        } else {

            // Show Connection Error//
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), 0).show();
        }
    }

    // What Happens When Connection Is Suspended//
    @Override
    public void onConnectionSuspended(int i) {

    }

    // What Happens When App Is Paused//
    @Override
    protected void onPause() {

        // What Happens When Api Is Defined//
        if (googleApiClient != null) {

            // Disconnect From Google Drive//
            googleApiClient.disconnect();
        }

        // Pause / Stop App//
        super.onPause();
    }

    // What Happens When App Is Resumed//
    @Override
    protected void onResume() {

        // Initiate writeToExternalStorage Method//
        writeToExternalStorage();

        // Initiate createLocalFolders Method//
        createLocalFolders();

        // Initiate connectToGoogleDrive Method//
        connectToGoogleDrive();

        // Resume App//
        super.onResume();
    }

    // Method That Backup All Events//
    private void backupEvents() {

        // Attempt To Backup The File//
        try {

            // What Happens When App Can Write To The SD Card//
            if (Environment.getExternalStorageDirectory().canWrite()) {

                // Define And Instantiate Variable File localFolder / Get localFolder File Location//
                File localFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/");

                // Define And Instantiate Variable File mainDatabase / Get mainDatabase File Location//
                File mainDatabase = new File(Environment.getDataDirectory() + "//data//jordanzimmittidevelopers.com.communityservicelogger//databases//events_database");

                // What Happens When There is a File In The localFolder//
                if (!(localFolder.listFiles().length == 0)) {

                    // List All The Files//
                    String[] children = localFolder.list();

                    // Delete All Files//
                    for (String aChildren : children) {

                        // Delete File//
                        new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/", aChildren).delete();
                    }
                }

                // What Happens When mainDatabase Exists//
                if (mainDatabase.exists()) {

                    // Define And Instantiate Variable FileChannel mainDatabaseDirectory//
                    FileChannel mainDatabaseDirectory = new FileInputStream(mainDatabase).getChannel();

                    // Define And Instantiate Variable FileChannel backupDatabaseDirectory//
                    FileChannel backupDatabaseDirectory = new FileOutputStream(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/" + "Events.db" + " " + month + "-" + day + "-" + year + " " + hour + ":" + minute + ":" + second + " ").getChannel();

                    // Transfer Data From mainDatabaseDirectory To backupDatabaseDirectory//
                    backupDatabaseDirectory.transferFrom(mainDatabaseDirectory, 0, mainDatabaseDirectory.size());

                    // Close Src Transfer//
                    mainDatabaseDirectory.close();

                    // Close Dest Transfer//
                    backupDatabaseDirectory.close();

                    // Shows That The File Was Backed Up Successfully//
                    Toast.makeText(getApplicationContext(), "Event Backup Completed", Toast.LENGTH_SHORT).show();
                }
            }

            // What Happens When The Code Fails//
        } catch (Exception ignored) {
            Toast.makeText(getApplicationContext(), "Failed To Backup: You Must Have At Least One Event To Backup Database To Drive", Toast.LENGTH_SHORT).show();
        }
    }

    // Method That Connects To Google Drive//
    private void connectToGoogleDrive() {

        // Create Api Client//
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addScope(Drive.SCOPE_APPFOLDER)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Attempt To Connect To Google drive//
        googleApiClient.connect();
    }

    // Method That Creates All Necessary Local Folders//
    private void createLocalFolders() {

        // Define And Instantiate Variable File appFolder / Get App Folder Directory//
        File appFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/");

        // What Happens When Folder Does Not Exist //
        if (!appFolder.exists()) {

            // Create Folder//
            appFolder.mkdirs();
        }

        // Define And Instantiate Variable File eventsBackupFolder / Get Events Backup Folder Directory//
        File eventsBackupFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/");

        // What Happens When Folder Does Not Exist //
        if (!eventsBackupFolder.exists()) {

            // Create Folder//
            eventsBackupFolder.mkdirs();
        }

        // Define And Instantiate Variable File remindersBackupFolder / Get Reminders Backup Folder Directory//
        File remindersBackupFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Reminders Backup/");

        // What Happens When Folder Does Not Exist //
        if (!remindersBackupFolder.exists()) {

            // Create Folder//
            remindersBackupFolder.mkdirs();
        }

        // Define And Instantiate Variable File usersBackupFolder / Get Users Backup Folder Directory//
        File usersBackupFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Users Backup/");

        // What Happens When Folder Does Not Exist //
        if (!usersBackupFolder.exists()) {

            // Create Folder//
            usersBackupFolder.mkdirs();
        }
    }

    // Method That Downloads The Cloud Database File To Device//
    private void syncFile() {

        //<editor-fold desc="Download Listener">

        // Downloads Cached File To Device//
        DriveFile.DownloadProgressListener listener = new DriveFile.DownloadProgressListener() {

            // Shows progress Of Download//
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {

            }
        };

        //</editor-fold>

        //<editor-fold desc="Sync App">

        // Define And Instantiate Variable File localEventsBackupFolder / Get Events Backup Folder Directory//
        File localEventsBackupFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/");

        // What Happens When There Is No Local File On Device But There is One In Google Drive//
        if (localEventsBackupFolder.listFiles().length == 0) {

            // Open Drive File Based On Id//
            fileEventsBackup.open(googleApiClient, DriveFile.MODE_READ_ONLY, listener).setResultCallback(downloadCloudEventsBackupFile);
        }

        // List Files In localEventsBackupFolder//
        for (File file : localEventsBackupFolder.listFiles()) {
            if (file.isFile()) {

                // Define And Instantiate Variable String[] localEventsBackupFileName / Get Just The Date From The String//
                String[] localEventsBackupFileNameString = file.getName().split("Events.db");

                // Define And Instantiate Variable String localEventsBackupFileDateString//
                String localEventsBackupFileDateString = localEventsBackupFileNameString[1].trim();

                // Define And Instantiate Variable String[] cloudEventsBackupFileNameString / Get Just The Date From The String//
                String[] cloudEventsBackupFileNameString = cloudFileName.split("Events.db");

                // Define And Instantiate Variable String cloudEventsBackupFileDateString//
                String cloudEventsBackupFileDateString = cloudEventsBackupFileNameString[1].trim();

                // Define And Instantiate SimpleDateFormat dateFormat//
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");

                try {

                    // Get Date From localEventsBackupFileDateString//
                    java.util.Date localEventsBackupFileDate = dateFormat.parse(localEventsBackupFileDateString);

                    // Get Date From cloudEventsBackupFileDateString//
                    java.util.Date cloudEventsBackupFileDate = dateFormat.parse(cloudEventsBackupFileDateString);

                    // What Happens When localEventsBackupFileDate Is Older Then cloudEventsBackupFileDate//
                    if (localEventsBackupFileDate.before(cloudEventsBackupFileDate)) {

                        // Open Drive File Based On Id//
                        fileEventsBackup.open(googleApiClient, DriveFile.MODE_READ_ONLY, listener).setResultCallback(downloadCloudEventsBackupFile);

                        return;
                    }

                    // What Happens When localEventsBackupFileDate Is Newer Then cloudEventsBackupFileDate//
                    else if (localEventsBackupFileDate.after(cloudEventsBackupFileDate)) {

                        // Sync With Google Drive//
                        Drive.DriveApi.requestSync(googleApiClient);

                        fileEventsBackup.delete(googleApiClient);

                        // Begin File Creation Process//
                        Drive.DriveApi.newDriveContents(googleApiClient).setResultCallback(createCloudEventsBackupFile);

                        return;
                    }

                    // What Happens when localEventsBackupFileDate Equals cloudEventsBackupFileDate//
                    else if (localEventsBackupFileDate.equals(cloudEventsBackupFileDate)) {

                        // Define And Instantiate Variable Intent syncReminders / Start Activity SyncReminders //
                        //Intent syncReminders = new Intent(SyncEvents.this, SyncReminders.class);
                        //syncReminders.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        //startActivityForResult(syncReminders, 0);
                        //overridePendingTransition(0, 0);
                    }

                    // What Happens When Code Fails//
                } catch (Exception ignored) {
                    Toast.makeText(getApplicationContext(), "Failed: Local File Or Meta File Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }
        }

        //</editor-fold>
    }

    // Method That Gives App Permission To Access System Storage//
    private void writeToExternalStorage() {

        // What Happens If Write To External Storage Was Not Granted//
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Request To Write To External Storage//
            ActivityCompat.requestPermissions(SyncGoogleDrive.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //<editor-fold desc="downloadCloudEventsBackupFile">

    // Checks If File Can Be Opened//
    private ResultCallback<DriveApi.DriveContentsResult> downloadCloudEventsBackupFile = new ResultCallback<DriveApi.DriveContentsResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveApi.DriveContentsResult result) {

            //<editor-fold desc="When Result Failed">

            // What Happens When File Failed To Create//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Google Drive Error")

                        // Content Of Dialog//
                        .content("Could not download events backup to device, check internet connection. If Error Persists contact the developer of this app")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc="Download Cloud Events Backup File On Device">

            // Define And Instantiate Variable DriveContents driveContents//
            DriveContents driveContents = result.getStatus().isSuccess() ? result.getDriveContents() : null;

            // Gets The Data for The File//
            if (driveContents != null) try {

                // Define And Instantiate Variable OutputStream outputStream//
                OutputStream outputStream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/" + cloudFileName);

                // Define And Instantiate Variable InputStream inputStream//
                InputStream inputStream = driveContents.getInputStream();

                // Define And Instantiate Variable Byte buffer//
                byte[] buffer = new byte[5000];

                // Define Variable Int data//
                int data;

                // Run Code While data Is Bigger Then Zero//
                while ((data = inputStream.read(buffer, 0, buffer.length)) > 0) {

                    // Write To outputStream//
                    outputStream.write(buffer, 0, data);

                    // Flush outputStream//
                    outputStream.flush();
                }

                // Close outputStream//
                outputStream.close();

                inputStream.close();

                // Discard Drive Contents//
                driveContents.discard(googleApiClient);

                // Define And Instantiate Variable Intent syncReminders / Start Activity SyncReminders //
                //Intent syncReminders = new Intent(SyncEvents.this, SyncReminders.class);
                //syncReminders.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                //startActivityForResult(syncReminders, 0);
                //overridePendingTransition(0, 0);

                // Shows When Code Failed//
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "File Failed To Download", Toast.LENGTH_LONG).show();
            }

            //</editor-fold>
        }
    };

    //</editor-fold>

    //<editor-fold desc="isBackupFileCreated">

    // Checks If File Is created Correctly Or In-Correctly//
    final private ResultCallback<DriveFolder.DriveFileResult> isBackupFileCreated = new ResultCallback<DriveFolder.DriveFileResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveFolder.DriveFileResult result) {

            //<editor-fold desc="When Result Failed">

            // What Happens When File Failed To Create//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Upload Failed")

                        // Content Of Dialog//
                        .content("Events backup was not successfully uploaded to drive")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();
                return;
            }

            //</editor-fold>

            // Create Dialog//
            new MaterialDialog.Builder(SyncGoogleDrive.this)

                    // Title Of Dialog//
                    .title("Upload Complete")

                    // Content Of Dialog//
                    .content("Events backup was successfully uploaded to drive")

                    // Negative Text Name For Button//
                    .negativeText("Ok")

                    // Don't Let User Close Dialog By Clicking Outside Dialog//
                    .canceledOnTouchOutside(false)

                    // What Happens When Negative Button Is Pressed//
                    .onNegative(new MaterialDialog.SingleButtonCallback() {

                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            // Define And Instantiate Variable Intent syncReminders / Start Activity SyncReminders //
                            //Intent syncReminders = new Intent(SyncEvents.this, SyncReminders.class);
                            //syncReminders.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            //startActivityForResult(syncReminders, 0);
                            //overridePendingTransition(0, 0);
                        }

                    }).show();
        }
    };

    //</editor-fold>

    //<editor-fold desc="isBackupFolderCreated">

    // Checks If File Is created Correctly Or In-Correctly//
    final private ResultCallback<DriveFolder.DriveFileResult> isBackupFolderCreated = new ResultCallback<DriveFolder.DriveFileResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveFolder.DriveFileResult result) {

            //<editor-fold desc="When Result Failed">

            // What Happens When File Failed To Create//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Google Drive Error")

                        // Content Of Dialog//
                        .content("Could not create events folder in google drive, Do you have any events to back up? If so then check internet connection or try again later. If it persists contact the developer of this app")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();

                return;
            }

            //</editor-fold>

            // Sync With Google Drive//
            Drive.DriveApi.requestSync(googleApiClient);

            // Define And Instantiate Variable Query queryForEventsBackup / Search For Events Backup Folder In App Folder//
            Query queryForEventsBackup = new Query.Builder().addFilter(Filters.eq(SearchableField.TITLE, "Events Backup")).build();

            // Get All Folders In App Folder That Fit In The Query Above//
            Drive.DriveApi.getAppFolder(googleApiClient).queryChildren(googleApiClient, queryForEventsBackup).setResultCallback(getEventsBackupFolder);
        }
    };

    //</editor-fold>

    //<editor-fold desc="getEventsBackupFolder">

    // Grabs The Id For Events Backup Folder In Google Drive//
    final private ResultCallback<DriveApi.MetadataBufferResult> getEventsBackupFolder = new ResultCallback<DriveApi.MetadataBufferResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveApi.MetadataBufferResult result) {

            //<editor-fold desc="When Result Failed">

            // Runs When App Failed To Retrieve Results//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Google Drive Error")

                        // Content Of Dialog//
                        .content("Could not find events folder in google drive, check internet connection or try again later. If it persists contact the developer of this app")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc="Get Folder Id">

            // Define And Instantiate Variable MetadataBuffer metadataBuffer//
            MetadataBuffer metadataBuffer = result.getMetadataBuffer();

            // Get Number Of Folders In App Folder That Matched The Query//
            for (int i = 0; i < metadataBuffer.getCount(); i++) {

                // Get Drive Folder Id And Put It Into A String//
                String googleDriveFolderId = metadataBuffer.get(i).getDriveId().encodeToString();

                // Instantiate Variable DriveFolder folderEventsBackup//
                folderEventsBackup = DriveId.decodeFromString(googleDriveFolderId).asDriveFolder();

                // Define And Instantiate Variable Query folderEventsBackupQuery / Search For Drive Files In App Folder With Specified Mime Type//
                Query folderEventsBackupQuery = new Query.Builder().addFilter(Filters.eq(SearchableField.MIME_TYPE, "application/x-sqlite3")).build();

                // Get All Files In folderEventsBackup That Fit In The Query Above//
                folderEventsBackup.queryChildren(googleApiClient, folderEventsBackupQuery).setResultCallback(getEventsBackupFile);
            }

            // What Happens When There Is No File In Drive//
            if (metadataBuffer.getCount() == 0) {

                backupEvents();

                // Sync With Google Drive//
                Drive.DriveApi.requestSync(googleApiClient);

                // Begin Folder Creation Process//
                Drive.DriveApi.newDriveContents(googleApiClient).setResultCallback(createCloudEventsBackupFolder);
            }

            // Release Code//
            result.release();

            //</editor-fold>
        }
    };

    //</editor-fold>

    //<editor-fold desc="getEventsBackupFile">

    // Grabs The Id For Events Backup File In Google Drive//
    final private ResultCallback<DriveApi.MetadataBufferResult> getEventsBackupFile = new ResultCallback<DriveApi.MetadataBufferResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveApi.MetadataBufferResult result) {

            //<editor-fold desc="When Result Failed">

            // Runs When App Failed To Retrieve Results//
            if (!result.getStatus().isSuccess()) {

                // Log That Code Failed//
                Log.d("Failed", "Problem while retrieving results");

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc="Get File Id">

            // Define And Instantiate Variable MetadataBuffer metaData//
            MetadataBuffer metaData = result.getMetadataBuffer();

            // Get Number Of Files In Folder That Match Query//
            for (int i = 0; i < metaData.getCount(); i++) {

                // Get Drive File Id And Put It Into A String//
                String googleDriveFileId = metaData.get(i).getDriveId().encodeToString();

                // Instantiate Variable DriveFile driveFile//
                fileEventsBackup = DriveId.decodeFromString(googleDriveFileId).asDriveFile();

                // Get Metadata For File//
                fileEventsBackup.getMetadata(googleApiClient).setResultCallback(getEventsFileMetadata);
            }

            // What Happens When There Is No File In Drive//
            if (metaData.getCount() == 0) {

                // Sync With Google Drive//
                Drive.DriveApi.requestSync(googleApiClient);

                // Initiate backupEvents Method//
                backupEvents();

                // Begin File Creation Process//
                Drive.DriveApi.newDriveContents(googleApiClient).setResultCallback(createCloudEventsBackupFile);
            }

            // Release Code//
            result.release();

            //</editor-fold>
        }
    };

    //</editor-fold>

    //<editor-fold desc="getEventsFileMetadata">

    // Gets Metadata For File//
    ResultCallback<DriveResource.MetadataResult> getEventsFileMetadata = new ResultCallback<DriveResource.MetadataResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveResource.MetadataResult result) {

            //<editor-fold desc="When Result Failed">

            // Runs When File Failed To Open//
            if (!result.getStatus().isSuccess()) {

                // Log That File Failed To Open//
                Log.d("TAG", "Problem While Trying To Fetch Metadata");

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc="Get File Name">

            // Gets File Metadata//
            Metadata metadata = result.getMetadata();

            // Instantiate Variable String fileName//
            cloudFileName = metadata.getTitle();

            // Initiate syncFile Method//
            syncFile();

            //</editor-fold>
        }
    };

    //</editor-fold>

    //<editor-fold desc="createCloudEventsBackupFile">

    // What Happens When App Creates A New Cloud Events Backup//
    final private ResultCallback<DriveApi.DriveContentsResult> createCloudEventsBackupFile = new ResultCallback<DriveApi.DriveContentsResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveApi.DriveContentsResult result) {

            //<editor-fold desc="When Result Failed">

            // What Happens When File Failed To Create//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Google Drive Error")

                        // Content Of Dialog//
                        .content("Could not upload events backup to drive, check internet connection. If Error Persists contact the developer of this app")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc=" Gets File Data">

            // Define And Instantiate Variable DriveContents driveContents//
            DriveContents driveContents = result.getStatus().isSuccess() ? result.getDriveContents() : null;

            // Define And Instantiate Variable File localEventsBackupFolder / Get Events Backup Folder Directory//
            File localEventsBackupFolder = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/");

            // Define Variable String fileTitle//
            String fileTitle = null;

            // List Files In Directory//
            for (File f : localEventsBackupFolder.listFiles()) {
                if (f.isFile()) {

                    // Instantiate Variable String fileTitle//
                    fileTitle = f.getName();

                    // Define Variable File localEventsFile//
                    File localEventsBackupFile = new File(Environment.getExternalStorageDirectory() + "/C.S. Tracker/Events Backup/", fileTitle);

                    // Gets The Data for The File//
                    if (driveContents != null) try {

                        // Define And Instantiate Variable OutputStream cloudEventsBackupFileDirectory//
                        OutputStream cloudEventsBackupFileDirectory = driveContents.getOutputStream();

                        // Define And Instantiate Variable InputStream localEventsBackupFileDirectory//
                        InputStream localEventsBackupFileDirectory = new FileInputStream(localEventsBackupFile);

                        // Start Writing Data To File//
                        if (cloudEventsBackupFileDirectory != null) try {

                            // Define And Instantiate Variable Byte buffer//
                            byte[] buffer = new byte[5000];

                            // Define Variable Int data//
                            int data;

                            // Run Code While data Is Bigger Then Zero//
                            while ((data = localEventsBackupFileDirectory.read(buffer, 0, buffer.length)) > 0) {

                                // Write To outputStream//
                                cloudEventsBackupFileDirectory.write(buffer, 0, data);

                                // Flush outputStream//
                                cloudEventsBackupFileDirectory.flush();
                            }

                        } finally {

                            // Close cloudEventsBackupFileDirectory//
                            cloudEventsBackupFileDirectory.close();

                            // Close localEventsBackupFileDirectory//
                            localEventsBackupFileDirectory.close();
                        }

                        // Shows When Code Failed//
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed To Upload: No Backup File Found", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }

            // Create Metadata For Database Files//
            MetadataChangeSet meta = new MetadataChangeSet.Builder().setTitle(fileTitle).setMimeType("application/x-sqlite3").build();

            // Put File In Folder Events Backup//
            folderEventsBackup.createFile(googleApiClient, meta, driveContents).setResultCallback(isBackupFileCreated);

            //</editor-fold>
        }
    };

    //</editor-fold>

    //<editor-fold desc="createCloudEventsBackupFolder">

    // Creates Events Backup Folder In App Folder In Google Drive//
    final private ResultCallback<DriveApi.DriveContentsResult> createCloudEventsBackupFolder = new ResultCallback<DriveApi.DriveContentsResult>() {

        // Gets Result Of Callback//
        @Override
        public void onResult(@NonNull DriveApi.DriveContentsResult result) {

            //<editor-fold desc="When Result Failed">

            // What Happens When File Contents Failed To Create//
            if (!result.getStatus().isSuccess()) {

                // Create Dialog//
                new MaterialDialog.Builder(SyncGoogleDrive.this)

                        // Title Of Dialog//
                        .title("Google Drive Error")

                        // Content Of Dialog//
                        .content("Could not create events folder in google drive, check internet connection or try again later. If it persists contact the developer of this app")

                        // Negative Text Name For Button//
                        .negativeText("Ok")

                        // Don't Let User Close Dialog By Clicking Outside Dialog//
                        .canceledOnTouchOutside(false).show();

                // End Code//
                return;
            }

            //</editor-fold>

            //<editor-fold desc=" Gets File Data">

            // Define And Instantiate Variable DriveContents driveContents//
            DriveContents driveContents = result.getStatus().isSuccess() ? result.getDriveContents() : null;

            // Define And Instantiate Variable MetadataChangeSet meta / Create Metadata For Events Backup Folder//
            MetadataChangeSet meta = new MetadataChangeSet.Builder().setTitle("Events Backup").build();

            // Put Folder In User Hidden App Folder//
            Drive.DriveApi.getAppFolder(googleApiClient).createFile(googleApiClient, meta, driveContents).setResultCallback(isBackupFolderCreated);

            //</editor-fold>
        }
    };

    //</editor-fold>
}


