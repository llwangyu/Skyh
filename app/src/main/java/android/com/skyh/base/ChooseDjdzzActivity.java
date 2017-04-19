package android.com.skyh.base;
import android.com.skyh.R;
import android.com.skyh.adapter.DwbmListAdapter;
import android.com.skyh.adapter.GjdzzListAdapter;
import android.com.skyh.entity.TwoTree;
import android.com.skyh.entity.TwoTreeData;
import android.com.skyh.entity.UserInfo;
import android.com.skyh.entity.UserTree;
import android.com.skyh.service.RequestResult;
import android.com.skyh.tool.PrefName;
import android.com.skyh.tool.ProtocolType;
import android.com.skyh.tool.ToastUtil;
import android.com.skyh.until.ToolBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class ChooseDjdzzActivity extends BaseActivity {
    protected ToolBar toolBar;
    private ListView listView;
    private int index=-1;
    private RelativeLayout rl;
    private List<UserInfo> areaEntityList=new ArrayList<>();
    private List<UserInfo> dates=new ArrayList<>();
    private List<UserInfo> userInfos=new ArrayList<>();
    private GjdzzListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dwbm_choose_list);
        initToolBar();
    }

    private void initToolBar(){
        toolBar = new ToolBar(this);
        toolBar.setToolbarCentreText("各级党组织");
        toolBar.hideRightButton();
        toolBar.hideRightTextButton();
        ToolBar.left_btn.setImageResource(R.mipmap.fh);
        ToolBar.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init(){
        listView=(ListView)findViewById(R.id.house_listview);
     adapter=new GjdzzListAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("item".equals(dates.get(position).getType())){
                    Intent intent = new Intent();
                    intent.putExtra("name", dates.get(position).getZzmc());
                    intent.putExtra("index", dates.get(position).getZzdm());
                    setResult(RESULT_OK, intent);
                    finish();
                }else {

                    userInfos=dates.get(position).getChildren();
                    dates.clear();
                    dates.addAll(userInfos);
                    adapter.setList(dates);
                    adapter.notifyDataSetChanged();
                }



}

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();

                intent.putExtra("name", dates.get(position).getZzmc());
                intent.putExtra("index", dates.get(position).getZzdm());

                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });
        areaList();
    }


    private void areaList(){
        showProgBar(getString(R.string.alert_info));

        String url = PrefName.DEFAULT_SERVER_URL +PrefName.LOGIN_TREE_SERVER_URL;

        sendRequest(url, null, PrefName.GET, ProtocolType.USERTREE, result);
    }
    RequestResult result=new RequestResult() {
        @Override
        public ProtocolType getType() {
            return ProtocolType.USERTREE;
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            hideProgBar();
            UserTree areaResutl=(UserTree) o;
             areaEntityList=areaResutl.getData();
            dates.addAll(areaEntityList);
            if(dates.size()<1){
                ToastUtil.show(ChooseDjdzzActivity.this,"暂无数据");
            }
            adapter.setList(dates);
            adapter.notifyDataSetChanged();



        }
//        }

        @Override
        public void onFailure(CharSequence failure) {
            super.onFailure(failure);

            hideProgBar();

        }

        @Override
        public void OnErrorResult(CharSequence error) {
            super.OnErrorResult(error);
            hideProgBar();
        }
    };




}
