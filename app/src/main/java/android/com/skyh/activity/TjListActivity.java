package android.com.skyh.activity;

import android.com.skyh.R;
import android.com.skyh.adapter.TjListAdapter;
import android.com.skyh.base.BaseActivity;
import android.com.skyh.entity.HyList;
import android.com.skyh.entity.RsEntityResult;
import android.com.skyh.entity.TjData;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.com.skyh.until.ToolBar;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TjListActivity extends BaseActivity {

    ToolBar toolBar;
    private List<RsEntityResult> fruitList=new ArrayList<RsEntityResult>();
    private TjListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RadioGroup radioGroup;
    private String url=PrefName.FIND_ZWH_SERVER_URL;
    int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj_list);

        initToolBar();
        radioGroup=(RadioGroup)findViewById(R.id.type_rg) ;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.zwh_rb:

                        flag=1;
                        url=PrefName.FIND_ZWH_SERVER_URL;
                        refreshDatas();
                        break;
                    case R.id.dydh_rb:
                        flag=2;
                        url=PrefName.FIND_DYDY_SERVER_URL;
                        refreshDatas();
                        break;
                    case R.id.dxzh_rb:
                        flag=3;
                        url=PrefName.FIND_DXZH_SERVER_URL;
                        refreshDatas();
                        break;
                    case R.id.dk_rb:
                        flag=4;
                        url=PrefName.FIND_DK_SERVER_URL;
                        refreshDatas();
                        break;
                }
            }
        });
        ListView recyclerView=(ListView) findViewById(R.id.recycler_view);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (flag){

                   case 1:
                      Intent   intent=new Intent(TjListActivity.this,ZwhDetailActivity.class);
                       intent.putExtra(PrefName.HYID,fruitList.get(position).getId());
                       startActivity(intent);
                       break;
                   case 2:
                       Intent   intent2=new Intent(TjListActivity.this,DydhDetailActivity.class);
                       intent2.putExtra(PrefName.HYID,fruitList.get(position).getId());
                       startActivity(intent2);
                       break;
                   case 3:
                       Intent   intent3=new Intent(TjListActivity.this,DxzhDetailActivity.class);
                       intent3.putExtra(PrefName.HYID,fruitList.get(position).getId());
                       startActivity(intent3);
                       break;
                   case 4:
                       Intent   intent4=new Intent(TjListActivity.this,DkxxDetailActivity.class);
                       intent4.putExtra(PrefName.HYID,fruitList.get(position).getId());
                       startActivity(intent4);
                       break;
               }
            }
        });
        adapter=new TjListAdapter(this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }
        });

        findList();
    }
    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("三课一会统计表");
        toolBar.hideRightButton();
        toolBar.hideRightTextButton();
        ToolBar.left_btn.setImageResource(R.mipmap.fh);
        ToolBar.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    private void refreshDatas(){

                        swipeRefreshLayout.setRefreshing(false);
        findList();

    }
    private void findList(){
        showProgBar(getString(R.string.alert_info));
        String url1 = PrefName.DEFAULT_SERVER_URL+ url;
        sendRequest(url1, null, PrefName.GET, ProtocolType.HYJL, resultInfo);
    }
    RequestResult resultInfo=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.HYJL;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            HyList hyList=(HyList) o;
//          ToastUtil.show(TjListActivity.this,o+"");
            fruitList.clear();
            switch (flag){
                case 1:
                    fruitList=hyList.getZbwyhList();
                    adapter.setList(fruitList);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    fruitList=hyList.getDydhbList();
                    adapter.setList(fruitList);
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    fruitList=hyList.getDxzhyList();
                    adapter.setList(fruitList);
                    adapter.notifyDataSetChanged();
                    break;
                case 4:
                    fruitList=hyList.getDkxxljList();
                    adapter.setList(fruitList);
                    adapter.notifyDataSetChanged();
                    break;

            }
            hideProgBar();

        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);
          //  Toast.makeText(DydhActivity.this,failure,Toast.LENGTH_SHORT).show();
            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
       //     Toast.makeText(DydhActivity.this,error,Toast.LENGTH_SHORT).show();
            hideProgBar();
        }
    };
}
