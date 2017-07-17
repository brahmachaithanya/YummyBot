package com.example.brahma.yummybot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.example.brahma.yummybot.MainActivity.name;

public class CreateRecipe extends AppCompatActivity {
    Button cameraButton;
    EditText recipeName;
    EditText recipeDesc;
    EditText recipeSteps;
    EditText recipeIngredients;
    EditText recipevideoURL;
    Button submit;
    DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference().child("recipes").getRef();
    File Userphoto;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Boolean isPictureClicked = false;
    Uri photoURI;
    String uploadedImageUrl;


    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        verifyStoragePermissions(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cameraButton = (Button) findViewById(R.id.clickImage);
        final Spinner spinner = (Spinner) findViewById(R.id.categoriesSpinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.cuisineSpinner);
        recipeDesc = (EditText) findViewById(R.id.createDescription);
        recipeName = (EditText) findViewById(R.id.createRecipeName);
        recipeSteps = (EditText) findViewById(R.id.createSteps);
        recipeIngredients = (EditText) findViewById(R.id.createIngredients);
        recipevideoURL = (EditText) findViewById(R.id.createVideoURL);
// Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.categoriesSpinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        adapter = ArrayAdapter.createFromResource(this,R.array.cuisineSpinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        submit = (Button) findViewById(R.id.ButtonSubmit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPictureClicked = true;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    Userphoto = createImageFile();
                    photoURI = FileProvider.getUriForFile(CreateRecipe.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            Userphoto);
                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                startActivityForResult(cameraIntent, 20);
                // startActivityForResult(cameraIntent, 20);
            }
            });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String reference = "r"+ UUID.randomUUID().toString();
                String newString = parentRef.push().getKey();
                //parentRef.child("recipe_id").setValue(reference);
                DatabaseReference childRef = parentRef.child(newString).getRef();
                //childRef.child("recipe_id").setValue(reference);
                childRef.child("category").setValue("veg");
                childRef.child("cuisine").setValue(spinner2.getSelectedItem().toString());
                childRef.child("description").setValue(recipeDesc.getText().toString());
                childRef.child("sub_category").setValue(spinner.getSelectedItem().toString());
                childRef.child("steps").setValue(recipeSteps.getText().toString());
                childRef.child("video_url").setValue(recipevideoURL.getText().toString());
                childRef.child("ingredients").setValue(recipeIngredients.getText().toString());
                childRef.child("image_URL").setValue(uploadedImageUrl);
                childRef.child("rating").setValue("8");
                childRef.child("recipe_name").setValue(recipeName.getText().toString());
                childRef.child("user").setValue(name);
                childRef.child("view_count").setValue("8");
                Toast.makeText(getBaseContext(), "Successfully added!" , Toast.LENGTH_SHORT ).show();


            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }


        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Context context = this;

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            ImageView imageview = (ImageView) findViewById(R.id.imageView);
            Bitmap bitmap;
            try {
                //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                bitmap = BitmapFactory.decodeFile(Userphoto.getAbsolutePath());
                imageview.setImageBitmap(bitmap);

                String imageURL = UUID.randomUUID().toString() + ".jpg";
                InputStream stream = new FileInputStream(Userphoto);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://yummy-bot-7b256.appspot.com/images");
                StorageReference storageReference = storageRef.child("images/" + imageURL );
               // StorageReference storageReference = FirebaseStorage.getInstance("images").getReference();
                UploadTask uploadTask = storageReference.putStream(stream);



                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        uploadedImageUrl = downloadUrl.toString();
                    }
                });


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            /*Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.pic);
            imageview.setImageBitmap(image);
            Uri uripath= data.getData();
            new GetCityList(this,this,uripath).execute("");*/

        }
    }


}
