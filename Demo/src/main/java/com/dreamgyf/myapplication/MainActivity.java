package com.dreamgyf.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.dreamgyf.loadingrecyclerview.LoadingRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private LoadingRecyclerView mLoadingRecyclerView;

    private RecyclerView.LayoutManager mLoadingLayoutManager;

    private LoadingRecyclerAdapter mLoadingRecyclerAdapter;

    private List<Article> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        mLoadingRecyclerView = findViewById(R.id.loading_recycler_view);

        mLoadingLayoutManager = new LinearLayoutManager(this);
//        ((LinearLayoutManager)mLoadingLayoutManager).setOrientation(RecyclerView.HORIZONTAL);

//        mLoadingLayoutManager = new GridLayoutManager(this,5);
//        ((GridLayoutManager) mLoadingLayoutManager).setOrientation(RecyclerView.HORIZONTAL);

//        mLoadingLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);

        mLoadingRecyclerView.setLayoutManager(mLoadingLayoutManager);
        mLoadingRecyclerView.setAdapter(mLoadingRecyclerAdapter = new LoadingRecyclerAdapter(this));
        mLoadingRecyclerAdapter.addDataAtEnd(mDataList);

        mLoadingRecyclerView.setLoadingListener(new LoadingRecyclerView.LoadingListener() {
            @Override
            public void onLoadMore(final int direction, int offset) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(direction == LoadingRecyclerView.Direction.START) {
                            mLoadingRecyclerAdapter.addDataAtStart(mDataList);
                        } else if(direction == LoadingRecyclerView.Direction.END) {
                            mLoadingRecyclerAdapter.addDataAtEnd(mDataList);
                        }
                        mLoadingRecyclerView.loadFinish(direction);
                    }
                },3000);
            }
        });
    }

    private void initData() {
        mDataList.add(new Article("佛洛伊德之死：美国警察与示威者一同单膝跪地","纪念佛洛伊德之死的美国示威，也有和平动容的瞬间。"));
        mDataList.add(new Article("澳洲年轻人与鲨鱼的近距离接触","这些澳洲青年在海里玩浮潜，却遇上不速之客……"));
        mDataList.add(new Article("美国经济学家预测美中冷战对全球威胁“比病毒更大”","美国经济学家萨克斯接受BBC采访时预测，被人们习以为常的现行世界秩序濒临崩溃，全球将在后新冠疫情时期进入“没有领导的大分裂”阶段。"));
        mDataList.add(new Article("新西兰：政府雇佣成人片演员拍摄性教育短片，蹿红网络","新西兰政府推出一则幽默的儿童性教育短片，借由成人片演员到访的场景，鼓励开展亲子对话，令儿童树立正确的性观念。"));
        mDataList.add(new Article("中国印度边境摩擦之后双反各执一词的主要观点","中印几十年在边境争议区时有摩擦，每次都相互指责对方挑衅。最新摩擦也不例外，双方再度己方观点与论述。"));
        mDataList.add(new Article("人类首次无人机战争 利比亚成中国武器试验场","军事观察家们认为，军用无人机将会重塑21世纪的作战模式。中国造无人机在利比亚无人机战争中显露锋芒。"));
        mDataList.add(new Article("北京疫情“第二波”、中印冲突、博尔顿新书和本周更多好故事","刚刚过去的一周，BBC中文以下新闻内容受到读者的关注。如果你错过了它们，BBC中文带你一一回顾。"));
        mDataList.add(new Article("黑人牙膏：受全球黑人抗议冲击的“中国”品牌","因美国黑人佛洛依德之死而遍及全球的抗议活动，也波及到一款在大中华地区销售数十载的口腔保健品牌——“黑人牙膏”。"));
        mDataList.add(new Article("BBC记者盘点让美国总统特朗普郁闷的四件事","距离大选短短5个月的关键阶段，美国总统特朗普最近连番遭遇挫折，政治麻烦有发酵的趋势，对选情相当不利。"));
        mDataList.add(new Article("中国“释放10名印度军人” 致命冲突让两国民众情绪激愤","在中印两国爆发边境致命冲突后，印度称中国释放了10名印度军人。官方力求冲突降温，但两国民间的对抗情绪继续加剧。"));
    }
}