package com.example.dbvideomarker.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbvideomarker.R;
import com.example.dbvideomarker.activity.MarkEditActivity;
import com.example.dbvideomarker.activity.SearchActivity;
import com.example.dbvideomarker.adapter.MediaAdapter;
import com.example.dbvideomarker.adapter.VideoAdapter;
import com.example.dbvideomarker.adapter.listener.OnItemClickListener;
import com.example.dbvideomarker.adapter.listener.OnItemSelectedListener;
import com.example.dbvideomarker.adapter.util.ViewCase;
import com.example.dbvideomarker.database.entitiy.Media;
import com.example.dbvideomarker.database.entitiy.Video;
import com.example.dbvideomarker.mediastore.MediaStoreLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemSelectedListener, OnItemClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = v.getContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
            if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);  //마지막 인자는 체크해야될 권한 갯수

            } else {
                //Toast.makeText(this, "권한 승인되었음", Toast.LENGTH_SHORT).show();
            }
            List<Media> datas = MediaStoreLoader.getContent(getActivity());
            RecyclerView recyclerView = v.findViewById(R.id.rv_Home);
            MediaAdapter adapter = new MediaAdapter(context, ViewCase.MEDIA, datas);

            // Get a new or existing ViewModel from the ViewModelProvider.
            //homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

            // Add an observer on the LiveData returned by getAlphabetizedWords.
            // The onChanged() method fires when the observed data changes and the activity is
            // in the foreground.
            //homeViewModel.getAllVideo().observe(getActivity(), new Observer<List<Video>>() {
            //    @Override
            //    public void onChanged(List<Video> videos) {
            //        Update the cached copy of the words in the adapter.
            //        adapter.setVideos(videos);
            //    }
            //});

            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }

        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                EditText videoName = new EditText(context);
                builder.setView(videoName);
                builder.setTitle("임시 비디오 추가");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (videoName.getText().toString().trim().length() != 0) {
                            Video video = new Video();
                            video.setvName(videoName.getText().toString());
                            homeViewModel.insertVideo(video);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
 */

            }
        });

        Button buttonSearch = v.findViewById(R.id.btn_Search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intentSearch);
            }
        });
        return v;
    }

    @Override
    public void clickLongItem(View v, int id) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), R.style.PopupMenuOverlapAnchor);
        PopupMenu popupMenu = new PopupMenu(contextThemeWrapper, getView());
        MenuInflater inflater = popupMenu.getMenuInflater();
        Menu menu = popupMenu.getMenu();
        inflater.inflate(R.menu.menu_popup, menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.popup_edit:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        EditText et = new EditText(getActivity());
                        builder.setView(et);
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (et.getText().toString().trim().length() != 0) {
                                    Video video = new Video();
                                    video.setvName(et.getText().toString());
                                    video.setVid(id);

                                    homeViewModel.updateVideo(video);
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case(R.id.popup_delete):
                        homeViewModel.deleteVideo(id);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }

    @Override
    public void clickItem(int id) {
        Intent intent = new Intent(getContext(), MarkEditActivity.class);
        intent.putExtra("동영상 번호", id);
        getContext().startActivity(intent);
    }

    @Override
    public void onItemSelected(View v, ArrayList<Integer> List) {
        //Do Nothing
    }
}