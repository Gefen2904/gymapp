package com.example.myfinalproject;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;

public class PicsFragment extends Fragment {
    PictureManager manager;
    PicturesAdapter adapter;
    RecyclerView recyclerView;
    Bitmap bitmap;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton backBtn;
    FloatingActionButton takePicBtn;
    FloatingActionButton uploadPicBtn;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pics, container, false);
        recyclerView = view.findViewById(R.id.pics_recycler);
        backBtn = view.findViewById(R.id.pics_back_btn);
        takePicBtn = view.findViewById(R.id.take_pic_btn);
        uploadPicBtn = view.findViewById(R.id.upload_pic_btn);
        coordinatorLayout = view.findViewById(R.id.pics_list_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        manager = PictureManager.getInstance(this.getContext());
        adapter = new PicturesAdapter(manager.getPictureList());
        adapter.SetListener(new PicturesAdapter.PictureListener() {
            @Override
            public void OnPictureClicked(int i, View view) {
                showCustomDialog(getActivity(), manager.getPicture(i).getPicture());
            }

            @Override
            public void OnPictureLongClick(int i, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure you want to remove this picture?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        manager.removePicture(i);
                        adapter.notifyDataSetChanged();
                        Snackbar.make(coordinatorLayout, "This picture has been removed", Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar.make(coordinatorLayout, "This picture has not been removed", Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
        recyclerView.setAdapter(adapter);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", 0);
                Navigation.findNavController(view).navigate(R.id.action_pics_fragment_to_main_fragment, bundle);
            }
        });
        uploadPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });
        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(intent);
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK && o.getData() != null) {
                    bitmap = (Bitmap) o.getData().getExtras().get("data");
                    Picture picture = new Picture(bitmap);
                    manager.addPicture(picture);
                    adapter.notifyDataSetChanged();
                    Snackbar.make(coordinatorLayout, "picture saved", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode()==RESULT_OK&& o.getData()!= null) {
                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(o.getData().getData());
                                bitmap = BitmapFactory.decodeStream(inputStream);
                                Picture picture = new Picture(bitmap);
                                manager.addPicture(picture);
                                adapter.notifyDataSetChanged();
                                Snackbar.make(coordinatorLayout, "picture saved", Snackbar.LENGTH_SHORT).show();
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                });

        return view;

    }

    private void showCustomDialog(Context context, Bitmap bitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_picture, null);
        ImageView dialogImageView = dialogView.findViewById(R.id.enlargedImageView);
        dialogImageView.setImageBitmap(Bitmap.createBitmap(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 3, bitmap.getHeight() * 3, false)));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}