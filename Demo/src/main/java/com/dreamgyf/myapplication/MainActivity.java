package com.dreamgyf.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.dreamgyf.loadingrecyclerview.LoadingRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private Button btn_linear_h;

	private Button btn_linear_v;

	private Button btn_grid_h;

	private Button btn_grid_v;

	private Button btn_staggered_h;

	private Button btn_staggered_v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_linear_h = findViewById(R.id.btn_linear_h);
		btn_linear_v = findViewById(R.id.btn_linear_v);
		btn_grid_h = findViewById(R.id.btn_grid_h);
		btn_grid_v = findViewById(R.id.btn_grid_v);
		btn_staggered_h = findViewById(R.id.btn_staggered_h);
		btn_staggered_v = findViewById(R.id.btn_staggered_v);

		btn_linear_h.setOnClickListener(this);
		btn_linear_v.setOnClickListener(this);
		btn_grid_h.setOnClickListener(this);
		btn_grid_v.setOnClickListener(this);
		btn_staggered_h.setOnClickListener(this);
		btn_staggered_v.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_linear_h: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER
						, SampleActivity.OrientationType.HORIZONTAL));
			}
			break;
			case R.id.btn_linear_v: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER
						, SampleActivity.OrientationType.VERTICAL));
			}
			break;
			case R.id.btn_grid_h: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.GRID_LAYOUT_MANAGER
						, SampleActivity.OrientationType.HORIZONTAL));
			}
			break;
			case R.id.btn_grid_v: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.GRID_LAYOUT_MANAGER
						, SampleActivity.OrientationType.VERTICAL));
			}
			break;
			case R.id.btn_staggered_h: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.STAGGERED_GRID_LAYOUT_MANAGER
						, SampleActivity.OrientationType.HORIZONTAL));
			}
			break;
			case R.id.btn_staggered_v: {
				startActivity(SampleActivity.createIntent(this
						, SampleActivity.LayoutManagerType.STAGGERED_GRID_LAYOUT_MANAGER
						, SampleActivity.OrientationType.VERTICAL));
			}
			break;
		}
	}
}