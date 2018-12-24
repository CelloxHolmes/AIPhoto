package com.example.user.firstai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.*;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.provider.*;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

public class MainActivity extends AppCompatActivity {

    static String age = "";
    //
    ImageView imageView;
    //    String age = "";
    //
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        Button btnCamera = findViewById(R.id.btnCamera);
//        imageView = findViewById(R.id.imageView);
//
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
////                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                startActivityForResult(intent, 0);
//
//            }
//        });
//
//
//    }
//
    Bitmap bitmap = null;
    Uri photoURI;
    //
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1 && resultCode == RESULT_OK) {
////            Bundle extras = data.getExtras();
////            Bitmap imageBitmap = (Bitmap) extras.get("data");
////            imageView.setImageBitmap(imageBitmap);
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
//                Drawable d = new BitmapDrawable(getResources(), bitmap);
//                imageView1.setImageDrawable(d);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
//        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) && Camera.getNumberOfCameras() > 0) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                System.out.println("try");
            } catch (IOException ex) {
                System.out.println("catch");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                System.out.println(photoURI);
                startActivityForResult(takePictureIntent, 2);
//                    galleryAddPic();
//                    setPic();

            }
        }

    }

    //
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(mCurrentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }
//
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(
                        intent, "Select Picture"), PICK_IMAGE);

//                dispatchTakePictureIntent();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(
//                        intent, "Select Picture"), PICK_IMAGE);

                dispatchTakePictureIntent();

            }
        });

        detectionProgressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.println(data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
//                        getContentResolver(), photoURI);
                ImageView imageView = findViewById(R.id.imageView1);
//                Button button2 = findViewById(R.id.button2);
                imageView.setImageBitmap(bitmap);
//                setPic();
//                imageView.animate().rotation(-90).start();

                System.out.println("picture pass");

//                Matrix matrix = new Matrix();
//                matrix.postRotate(-90);
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                // Comment out for tutorial
                detectAndFrame(bitmap);


            } catch (IOException e) {
                System.out.println("picture failed");
                e.printStackTrace();
            }
        }
        else System.out.println("picture not pass the check");

        if (requestCode == 2 && resultCode == RESULT_OK) {
//            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
//                        getContentResolver(), uri);
                        getContentResolver(), photoURI);
                ImageView imageView = findViewById(R.id.imageView1);
//                Button button2 = findViewById(R.id.button2);
                imageView.setImageBitmap(bitmap);
//                setPic();
//                imageView.animate().rotation(-90).start();

                System.out.println("picture pass");

                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                // Comment out for tutorial
                detectAndFrame(bitmap);


            } catch (IOException e) {
                System.out.println("picture failed");
                e.printStackTrace();
            }
        }

//        System.out.println("How old are u?");
//
//        Button button2 = findViewById(R.id.button2);
//        button2.setText(age);
    }

    // Replace `<API endpoint>` with the Azure region associated with
// your subscription key. For example,
// apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0"
    private final String apiEndpoint = "https://westcentralus.api.cognitive.microsoft.com/face/v1.0";

    // Replace `<Subscription Key>` with your subscription key.
// For example, subscriptionKey = "0123456789abcdef0123456789ABCDEF"
    private final String subscriptionKey = "19cab909a790473e8bc794a6d7880e0a";

    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    // Detect faces by uploading a face image.
// Frame faces after detection.
    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        @SuppressLint("StaticFieldLeak") AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";


                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
//                                    null          // returnFaceAttributes:
                                    new FaceServiceClient.FaceAttributeType[]{
                                            FaceServiceClient.FaceAttributeType.Age,
                                            FaceServiceClient.FaceAttributeType.Emotion,
                                            FaceServiceClient.FaceAttributeType.Gender}

                            );
                            System.out.println(result[0].faceAttributes.age);
                            System.out.println(result[0].faceAttributes.gender);
                            System.out.print("anger : ");
                            System.out.println(result[0].faceAttributes.emotion.anger);
                            System.out.print("contempt : ");
                            System.out.println(result[0].faceAttributes.emotion.contempt);
                            System.out.print("disgust : ");
                            System.out.println(result[0].faceAttributes.emotion.disgust);
                            System.out.print("fear : ");
                            System.out.println(result[0].faceAttributes.emotion.fear);
                            System.out.print("happiness : ");
                            System.out.println(result[0].faceAttributes.emotion.happiness);
                            System.out.print("neutral : ");
                            System.out.println(result[0].faceAttributes.emotion.neutral);
                            System.out.print("sadness : ");
                            System.out.println(result[0].faceAttributes.emotion.sadness);
                            System.out.print("surprise : ");
                            System.out.println(result[0].faceAttributes.emotion.surprise);
//                            age = String.valueOf(result[0].faceAttributes.age);
                            if (result == null) {
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
//                            System.out.println(result.length);

                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));


                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }

                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }

                    @Override
                    protected void onPostExecute(Face[] result) {
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();

                        if (!exceptionMessage.equals("")) {
                            showError(exceptionMessage);
                        }
                        if (result == null) return;

                        ImageView imageView = findViewById(R.id.imageView1);
                        Bitmap bitmap = drawFaceRectanglesOnBitmap(imageBitmap, result);
                        imageView.setImageBitmap(bitmap);
                        imageBitmap.recycle();
                    }
                };

        detectTask.execute(inputStream);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create().show();
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.GREEN);
        paint2.setStrokeWidth(2);
        paint2.setTextSize(50f);

        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(Color.GREEN);
        paint3.setStrokeWidth(3);
        paint3.setTextSize(50f);


//        age = String.valueOf(faces[0].faceAttributes.age);
        int i = 0;

        if (faces != null && faces.length != 0) {

            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
                canvas.drawText(String.valueOf(faces[i].faceAttributes.age), faceRectangle.left, faceRectangle.top, paint2);

                double biggest;
                biggest = Math.max(face.faceAttributes.emotion.anger, face.faceAttributes.emotion.contempt);
                biggest = Math.max(biggest, face.faceAttributes.emotion.disgust);
                biggest = Math.max(biggest, face.faceAttributes.emotion.fear);
                biggest = Math.max(biggest, face.faceAttributes.emotion.happiness);
                biggest = Math.max(biggest, face.faceAttributes.emotion.neutral);
                biggest = Math.max(biggest, face.faceAttributes.emotion.sadness);
                biggest = Math.max(biggest, face.faceAttributes.emotion.surprise);

                if (biggest == face.faceAttributes.emotion.anger) {
                    canvas.drawText("anger", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.contempt) {
                    canvas.drawText("contempt", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.disgust) {
                    canvas.drawText("disgust", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.fear) {
                    canvas.drawText("fear", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.happiness) {
                    canvas.drawText("happiness", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.neutral) {
                    canvas.drawText("neutral", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.sadness) {
                    canvas.drawText("sadness", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                if (biggest == face.faceAttributes.emotion.surprise) {
                    canvas.drawText("surprise", faceRectangle.left, faceRectangle.top + faceRectangle.height, paint3);
                }
                i = i + 1;
            }
        }
        return bitmap;
    }

    private static void rotatePicture(Bitmap rotatedBitmap, Face[] faces) {


        if (faces != null && faces.length == 0) {

            Matrix matrix = new Matrix();

            matrix.postRotate(90);


            rotatedBitmap = Bitmap.createBitmap(rotatedBitmap, 0, 0, rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), matrix, true);

//                detectAndFrame(rotatedBitmap);

            rotatePicture(rotatedBitmap, faces);


        }

        drawFaceRectanglesOnBitmap(rotatedBitmap, faces);


    }


}
